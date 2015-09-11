package controllers;

import play.Logger;
import play.libs.F.Option;
import play.libs.OAuth;
import play.libs.OAuth.ConsumerKey;
import play.libs.OAuth.RequestToken;
import play.libs.OAuth.ServiceInfo;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.common.base.Strings;

/**
 * Controller to implement Twitter Single Sign On
 * 
 * @author Ekal.Golas
 *
 */
public class Twitter extends Controller {
	/**
	 * Key provided by twitter
	 */
	static final ConsumerKey KEY = new ConsumerKey("dOff0rSOab75OThpZUKwLax2Q",
			"0ebuqi00uZx4SEFUnUsLhYaxbqP0kknR8RUdXRnwQ7J8fIUgAx");

	/**
	 * Service information to access twitter API
	 */
	private static final ServiceInfo SERVICE_INFO = new ServiceInfo(
			"https://api.twitter.com/oauth/request_token",
			"https://api.twitter.com/oauth/access_token",
			"https://api.twitter.com/oauth/authorize", KEY);

	/**
	 * Authorization token
	 */
	private static final OAuth TWITTER = new OAuth(SERVICE_INFO);

	/**
	 * AJAX action for twitter authentication
	 * 
	 * @return redirect to home page if authenticated
	 */
	public static Result auth() {
		// Request token if not available, else get token and begin session
		String verifier = request().getQueryString("oauth_verifier");
		if (Strings.isNullOrEmpty(verifier)) {
			String url = routes.Twitter.auth().absoluteURL(request());
			RequestToken requestToken = TWITTER.retrieveRequestToken(url);
			saveSessionTokenPair(requestToken);

			return redirect(TWITTER.redirectUrl(requestToken.token));
		} else {
			Logger.info("Authenticating from twitter....");
			RequestToken requestToken = getSessionTokenPair().get();
			RequestToken accessToken = TWITTER.retrieveAccessToken(
					requestToken, verifier);
			saveSessionTokenPair(accessToken);

			return redirect(routes.Application.index());
		}
	}

	/**
	 * Method to save session parameters
	 * 
	 * @param requestToken
	 *            Request token
	 */
	private static void saveSessionTokenPair(RequestToken requestToken) {
		session("token", requestToken.token);
		session("secret", requestToken.secret);
		session("username", "Twitter User");
	}

	/**
	 * Method to get session token
	 * 
	 * @return Session token if present, else null
	 */
	private static Option<RequestToken> getSessionTokenPair() {
		if (session().containsKey("token")) {
			return Option.Some(new RequestToken(session("token"),
					session("secret")));
		}

		return Option.None();
	}
}