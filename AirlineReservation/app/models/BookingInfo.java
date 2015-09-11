package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

/**
 * Entity Booking Info
 * 
 * @author Ekal.Golas
 *
 */
@Entity
@Table(name = "BookingInfo")
public class BookingInfo extends Model {
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Booking ID
	 */
	@Id
	public int booking_id;

	/**
	 * Number of adults
	 */
	@Required
	public int no_of_adults;

	/**
	 * Number of children
	 */
	public int no_of_children;

	/**
	 * Finder for this class
	 */
	public static Finder<String, BookingInfo> find = new Finder<String, BookingInfo>(
			String.class, BookingInfo.class);

	/**
	 * Parameterized constructor
	 * 
	 * @param no_of_adults
	 *            Number of adults
	 * @param no_of_children
	 *            Number of children
	 */
	public BookingInfo(int no_of_adults, int no_of_children) {
		this.no_of_adults = no_of_adults;
		this.no_of_children = no_of_children;
	}
}