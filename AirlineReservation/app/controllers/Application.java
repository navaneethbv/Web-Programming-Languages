package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import models.Flight;
import play.Logger;
import play.mvc.*;
import views.html.*;

/**
 * Controller for home page
 * 
 * @author Ekal.Golas
 *
 */
public class Application extends Controller {
	/**
	 * AJAX action to render the home page
	 * 
	 * @return index render
	 */
	public static Result index() {
		// Get origins and destinations
		Logger.info("Getting airport names from the database....");
		List<Flight> flights = Flight.find.all();
		HashSet<String> origins = new HashSet<String>();
		HashSet<String> destinations = new HashSet<String>();
		for (Flight flight : flights) {
			origins.add(flight.origin);
			destinations.add(flight.destination);
		}

		// Transform the data and render it to the home page
		List<String> org = new ArrayList<String>(origins);
		List<String> des = new ArrayList<String>(destinations);
		String user = session("username");
		return ok(index.render(org, des, user));
	}

	/**
	 * AJAX action to log out the user
	 * 
	 * @return redirection to login page
	 */
	public static Result logout() {
		Logger.info("Logging out user" + session("username") + "....");
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Login.index());
	}
}