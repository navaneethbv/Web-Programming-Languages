package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

import models.Airlines;
import models.BookingInfo;
import models.Flight;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.Secured;
import views.html.*;

/**
 * Controller for user summary
 * 
 * @author Ekal.Golas
 *
 */
@Security.Authenticated(Secured.class)
public class Summary extends Controller {
	/**
	 * AJAX action to get user bookings
	 * 
	 * @return Error if no booking made, else render summary
	 * @throws ParseException
	 */
	public static Result index() throws ParseException {
		// Initialize
		// variablesLogger.info("Getting airport names from the database....");
		String user = session("username");
		List<Info> infos = new ArrayList<>();

		// Get bookings for this user
		String sql = "SELECT DISTINCT id FROM Booking WHERE username like '%"
				+ user + "_%' ORDER BY date_created desc";
		List<SqlRow> booking = Ebean.createSqlQuery(sql).findList();

		// If there are no bookings, error out. Else, get bookings
		if (booking.size() == 0) {
			return badRequest(errors
					.render("You haven`t made any bookings yet. Please make a booking first!",
							session("username")));
		} else {
			// Do for each booking
			Logger.info("Getting bookings made for user " + user + "...");
			for (SqlRow book : booking) {
				Info info = new Info();
				info.seats = "";

				// Get seats
				String bookSql = "SELECT * FROM Booking WHERE id = "
						+ book.getInteger("id");
				List<SqlRow> bookings = Ebean.createSqlQuery(bookSql)
						.findList();
				for (SqlRow b : bookings) {
					Logger.info(b.getString("seat_no"));
					info.seats += b.getString("seat_no").replace(" ", "") + " ";
				}

				info.date_created = bookings.get(0).getString("date_created");
				info.flight_no = bookings.get(0).getString("flight_no");

				// Get flight details
				Flight flight = Flight.find.where()
						.eq("flight_no", info.flight_no).findUnique();
				info.departure_time = flight.departure_time;
				info.duration = flight.duration;
				info.origin = flight.origin;
				info.destination = flight.destination;

				// Calculate arrival time
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				Date date1 = timeFormat.parse(info.departure_time);
				Date date2 = timeFormat.parse(info.duration);
				long sum = date1.getTime() + date2.getTime();
				info.arrival_time = timeFormat.format(new Date(sum));

				// Get booking info
				BookingInfo bookInfo = BookingInfo.find.where()
						.eq("booking_id", book.getInteger("id")).findUnique();
				info.no_of_adults = bookInfo.no_of_adults;
				info.no_of_children = bookInfo.no_of_children;

				// Get airline details
				Airlines airline = Airlines.find.where()
						.eq("flight_no", info.flight_no).findUnique();
				info.airline = airline.airline;

				infos.add(info);
			}
		}

		return ok(summary.render(infos, user));
	}

	/**
	 * Class to represent booking information
	 * 
	 * @author Ekal.Golas
	 *
	 */
	public static class Info {
		/**
		 * Airline name
		 */
		public String airline;

		/**
		 * Flight number
		 */
		public String flight_no;

		/**
		 * Seats for this booking
		 */
		public String seats;

		/**
		 * Departure time from origin
		 */
		public String departure_time;

		/**
		 * Duration of travel
		 */
		public String duration;

		/**
		 * Arrival time at destination
		 */
		public String arrival_time;

		/**
		 * Number of adults
		 */
		public int no_of_adults;

		/**
		 * Number of children
		 */
		public int no_of_children;

		/**
		 * Date this booking was created
		 */
		public String date_created;

		/**
		 * Origin airport
		 */
		public String origin;

		/**
		 * Destination airport
		 */
		public String destination;
	}
}