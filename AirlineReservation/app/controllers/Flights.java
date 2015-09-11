package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Airlines;
import models.Cost;
import models.Flight;
import play.cache.Cache;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;
import views.html.*;

/**
 * Controller for displaying search results
 * 
 * @author Ekal.Golas
 *
 */
public class Flights extends Controller {
	/**
	 * Search form
	 */
	private final static Form<SearchResults> searchForm = new Form<SearchResults>(
			SearchResults.class);

	/**
	 * Search form data
	 */
	private static Form<SearchResults> form;

	/**
	 * AJAX action to validate search form
	 * 
	 * @return redirect to search if validated, else redirect to error page
	 */
	public static Result index() {
		// Initialize variables
		Logger.info("Validating the request....");
		form = searchForm.bindFromRequest();
		String user = session("username");

		// Error out if validation failed, else go to search
		if (form.hasErrors()) {
			return badRequest(errors.render(form.globalErrors().get(0)
					.message(), user));
		} else
			return redirect(routes.Flights.search());
	}

	/**
	 * Class to represent the search form
	 * 
	 * @author Ekal.Golas
	 *
	 */
	public static class SearchResults {
		/**
		 * Origin airport
		 */
		public String origin;

		/**
		 * Destination airport
		 */
		public String destination;

		/**
		 * Class of travel
		 */
		public String Class;

		/**
		 * Validate the form
		 * 
		 * @return Error message if not validated, null otherwise
		 */
		public String validate() {
			if (origin.equalsIgnoreCase(destination)) {
				return "Origin and destination cannot be the same";
			}
			return null;
		}
	}

	/**
	 * Class to represent search results
	 * 
	 * @author Ekal.Golas
	 *
	 */
	public static class ResultInfo {
		/**
		 * Airline name
		 */
		public String airline;

		/**
		 * Flight number
		 */
		public String flight_no;

		/**
		 * Cost of the ticket
		 */
		public float price;

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
	}

	/**
	 * AJAX action to search for flights
	 * 
	 * @return Error if no results found, render search results otherwise
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked" })
	public static Result search() throws ParseException {
		// Get key for cache
		String cacheKey = form.get().origin + "_" + form.get().destination
				+ "_" + form.data().get("Class") + "_Airlines";

		// Query database if result is not in the cache
		List<ResultInfo> result = (List<ResultInfo>) Cache.get(cacheKey);
		if (result == null) {
			// Get desired flights
			Logger.info("Events not found in Cache");
			List<Flight> flights = Flight.find.select("flight_no").where()
					.eq("origin", form.get().origin)
					.eq("destination", form.get().destination).findList();

			// Get details and form results
			result = new ArrayList<>();
			for (Flight flight : flights)
				if (Cost.find.select("flight_no").where()
						.eq("Class", form.data().get("Class"))
						.eq("flight_no", flight.flight_no).findUnique() != null) {
					ResultInfo info = new ResultInfo();
					info.airline = Airlines.find.where()
							.eq("flight_no", flight.flight_no).findUnique().airline;
					info.price = Cost.find.where()
							.eq("flight_no", flight.flight_no)
							.eq("class", form.data().get("Class")).findUnique().price;
					info.flight_no = flight.flight_no;
					info.departure_time = Flight.find.where()
							.eq("flight_no", flight.flight_no).findUnique().departure_time;
					info.duration = Flight.find.where()
							.eq("flight_no", flight.flight_no).findUnique().duration;

					// Calculate arrival time
					SimpleDateFormat timeFormat = new SimpleDateFormat(
							"HH:mm:ss");
					Date date1 = timeFormat.parse(info.departure_time);
					Date date2 = timeFormat.parse(info.duration);
					long sum = date1.getTime() + date2.getTime();
					info.arrival_time = timeFormat.format(new Date(sum));

					result.add(info);
				}

			// Set this result in cache
			Cache.set(cacheKey, result);
		} else
			Logger.info("Events found in Cache");

		// If no results were found, error out. Else render the results
		if (result.size() == 0) {
			return badRequest(errors
					.render("Sorry! There are no results to display. Please select different options.",
							session("username")));
		} else
			return ok(flights.render(result, session("username")));
	}
}