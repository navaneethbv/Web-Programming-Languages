package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

import models.*;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.mvc.Security;
import util.Secured;

/**
 * Controller for booking a flight
 * 
 * @author Ekal.Golas
 *
 */
@Security.Authenticated(Secured.class)
public class Book extends Controller {
	/**
	 * Form for booking
	 */
	private final static Form<BookInfo> bookForm = new Form<BookInfo>(
			BookInfo.class);

	/**
	 * AJAX action for book request
	 * 
	 * @return confirmation page render
	 * @throws InterruptedException
	 */
	public static Result index() throws InterruptedException {
		// Initialize variables
		Form<BookInfo> form = bookForm.bindFromRequest();
		String user = session("username");

		// Insert data in booking info
		Logger.info("Saving booking info....");
		BookingInfo info = new BookingInfo(form.get().adults,
				form.get().children);
		Ebean.save(info);

		// Create booking for each adult
		for (int i = 0; i < form.get().adults; i++) {
			// Get booking ID from booking info
			int bookingID = BookingInfo.find.orderBy("booking_id desc")
					.findList().get(0).booking_id;

			// Get current date
			SimpleDateFormat timeFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");
			String date = timeFormat.format(new Date());

			// Calculate seat for this adult
			String seat;
			String sql = "SELECT seat_no FROM Booking WHERE flight_no = '"
					+ form.get().flight_no + "' ORDER BY date_created desc";
			List<SqlRow> b = Ebean.createSqlQuery(sql).findList();
			if (b.size() == 0)
				seat = "1 A";
			else {
				String[] details = b.get(0).getString("seat_no").split(" ");
				if (details[1].equals("F"))
					seat = (Integer.parseInt(details[0]) + 1) + " A";
				else
					seat = details[0] + " "
							+ String.valueOf((char) (details[1].charAt(0) + 1));
			}

			// Insert data into booking
			Logger.info("Creating booking for user " + user + " Adult number "
					+ (i + 1) + "....");
			Booking booking = new Booking(bookingID, form.get().flight_no,
					seat, user + "_" + i, date, date);
			Ebean.save(booking);

			// Wait to create difference in dates
			Thread.sleep(1000);
		}

		return ok(confirm.render(user));
	}

	/**
	 * Class to represent booking form
	 * 
	 * @author Ekal.Golas
	 *
	 */
	public static class BookInfo {
		/**
		 * Fight number
		 */
		public String flight_no;

		/**
		 * Number of children
		 */
		public int children;

		/**
		 * Number of adults
		 */
		public int adults;
	}
}