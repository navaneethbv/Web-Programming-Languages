package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import play.data.format.*;

import javax.persistence.*;

/**
 * Entity Booking
 * 
 * @author Ekal.Golas
 *
 */
@Entity
@Table(name = "Booking")
public class Booking extends Model {
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Booking ID
	 */
	@Required
	@Id
	public int ID;

	/**
	 * Flight number
	 */
	@Required
	@MaxLength(5)
	public String flight_no;

	/**
	 * Seat number
	 */
	@Required
	@MaxLength(5)
	public String seat_no;

	/**
	 * User name
	 */
	@Required
	@MaxLength(25)
	@MinLength(5)
	@Pattern(value = "^\\w*$", message = "Not a valid username")
	public String username;

	/**
	 * Date this booking was created
	 */
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public String date_created;

	/**
	 * Date this booking was last modified
	 */
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public String date_modified;

	/**
	 * Finder for this class
	 */
	public static Finder<String, Booking> find = new Finder<String, Booking>(
			String.class, Booking.class);

	/**
	 * Parameterized constructor
	 * 
	 * @param id
	 *            Booking ID
	 * @param flight_no
	 *            Flight number
	 * @param seat_no
	 *            Seat number
	 * @param username
	 *            User name
	 * @param date_created
	 *            Date booking was created
	 * @param date_modified
	 *            Date booking was last modified
	 */
	public Booking(int id, String flight_no, String seat_no, String username,
			String date_created, String date_modified) {
		this.ID = id;
		this.flight_no = flight_no;
		this.seat_no = seat_no;
		this.username = username;
		this.date_created = date_created;
		this.date_modified = date_modified;
	}
}