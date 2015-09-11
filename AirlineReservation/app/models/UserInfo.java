package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

/**
 * Entity User Info
 * 
 * @author Ekal.Golas
 *
 */
@Entity
@Table(name = "UserInfo")
public class UserInfo extends Model {
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * User name
	 */
	@Required
	@Id
	@MaxLength(25)
	@MinLength(5)
	@Pattern(value = "^\\w*$", message = "Not a valid username")
	public String username;

	/**
	 * Password for the user
	 */
	@Required
	@MaxLength(15)
	@MinLength(5)
	public String password_md5;

	/**
	 * Name of the user
	 */
	@Required
	@MaxLength(50)
	public String name_user;

	/**
	 * Email of the user
	 */
	@Email
	@MaxLength(25)
	public String email;

	/**
	 * Phone number of the user
	 */
	@MaxLength(15)
	public String phone;

	/**
	 * Date this user was created
	 */
	public String date_created;

	/**
	 * Date this user was last modified
	 */
	public String date_modified;

	/**
	 * Is the user currently active?
	 */
	public boolean active;

	/**
	 * Finder for this class
	 */
	public static Finder<String, UserInfo> find = new Finder<String, UserInfo>(
			String.class, UserInfo.class);

	/**
	 * Parameterized constructor
	 * 
	 * @param username
	 *            User name
	 * @param password
	 *            password
	 * @param name
	 *            Name of user
	 * @param email
	 *            Email of user
	 * @param phone
	 *            Phone number
	 * @param dateCreated
	 *            Date user was created
	 * @param dateModified
	 *            Date user was last modified
	 * @param active
	 *            Is the user active
	 */
	public UserInfo(String username, String password, String name,
			String email, String phone, String dateCreated,
			String dateModified, boolean active) {
		this.username = username;
		this.password_md5 = password;
		this.name_user = name;
		this.email = email;
		this.phone = phone;
		this.date_created = dateCreated;
		this.date_modified = dateModified;
		this.active = active;
	}

	/**
	 * Method to authenticate user
	 * 
	 * @param username
	 *            User name of user
	 * @param password
	 *            Password for the user
	 * @return
	 */
	public static UserInfo authenticate(String username, String password) {
		return find.where().eq("username", username)
				.eq("password_md5", password).findUnique();
	}
}