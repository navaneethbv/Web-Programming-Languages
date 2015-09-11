package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

/**
 * Entity Cost
 * 
 * @author Ekal.Golas
 *
 */
@Entity
@Table(name = "Cost")
public class Cost extends Model {
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Flight number
	 */
	@Required
	@Id
	@MaxLength(5)
	public String flight_no;

	/**
	 * Cost for the ticket
	 */
	@Required
	public float price;

	/**
	 * Class of travel
	 */
	@Required
	@MaxLength(1)
	public String Class;

	/**
	 * Finder for this class
	 */
	public static Finder<String, Cost> find = new Finder<String, Cost>(
			String.class, Cost.class);

	/**
	 * Parameterized constructor
	 * 
	 * @param flight_no
	 *            Flight number
	 * @param price
	 *            Cost in dollars
	 * @param class1
	 *            Class of this travel
	 */
	public Cost(String flight_no, float price, String class1) {
		this.flight_no = flight_no;
		this.price = price;
		Class = class1;
	}
}