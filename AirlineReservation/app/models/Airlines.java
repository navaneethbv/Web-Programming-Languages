package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

/**
 * Entity Airlines
 * 
 * @author Ekal.Golas
 *
 */
@Entity
@Table(name = "Airlines")
public class Airlines extends Model {
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
	 * Airline name
	 */
	@Required
	@MaxLength(20)
	@MinLength(5)
	public String airline;

	/**
	 * Weekly schedule information
	 */
	@Required
	@MaxLength(20)
	public String week_schedule;

	/**
	 * Finder for this class
	 */
	public static Finder<String, Airlines> find = new Finder<String, Airlines>(
			String.class, Airlines.class);

	/**
	 * Parameterized constructor
	 * 
	 * @param flight_no
	 *            Flight number
	 * @param airline
	 *            Airline name
	 * @param week_schedule
	 *            Weekly schedule as comma separated values
	 */
	public Airlines(String flight_no, String airline, String week_schedule) {
		this.flight_no = flight_no;
		this.airline = airline;
		this.week_schedule = week_schedule;
	}
}