package controllers;

import models.UserInfo;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Controller for login service
 * 
 * @author Ekal.Golas
 *
 */
public class Login extends Controller {
	/**
	 * Login form
	 */
	private final static Form<LoginModel> loginForm = new Form<LoginModel>(
			LoginModel.class);

	/**
	 * AJAX action for login
	 * 
	 * @return render login page
	 */
	public static Result index() {
		return ok(login.render(loginForm));
	}

	/**
	 * Class to represent login form
	 * 
	 * @author Ekal.Golas
	 *
	 */
	public static class LoginModel {
		/**
		 * User name
		 */
		public String username;

		/**
		 * Password for the user
		 */
		public String password;

		/**
		 * Validate the user
		 * 
		 * @return Error if not authenticated, null otherwise
		 */
		public String validate() {
			if (UserInfo.authenticate(username, password) == null) {
				return "Invalid user or password";
			}

			return null;
		}
	}

	/**
	 * AJAX action to authenticate the user
	 * 
	 * @return
	 */
	public static Result authenticate() {
		// Initialize form
		Logger.info("Logging in....");
		Form<LoginModel> form = loginForm.bindFromRequest();

		// If login failed, render errors. Else, set session and redirect to
		// home page
		if (form.hasErrors()) {
			return badRequest(login.render(form));
		} else {
			session().clear();
			session("username", form.get().username);
			return redirect(routes.Application.index());
		}
	}
}