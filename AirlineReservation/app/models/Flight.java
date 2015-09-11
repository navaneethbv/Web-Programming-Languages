package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

/**
 * Entity Flight
 * 
 * @author Ekal.Golas
 *
 */
@Entity
@Table(name = "Flight")
public class Flight extends Model {
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
	 * Origination airport
	 */
	@Required
	@MaxLength(15)
	public String origin;

	/**
	 * Destination airport
	 */
	@Required
	@MaxLength(15)
	public String destination;

	/**
	 * Departure time
	 */
	@Required
	public String departure_time;

	/**
	 * Duration of the flight
	 */
	@Required
	public String duration;

	/**
	 * Finder for this class
	 */
	public static Finder<String, Flight> find = new Finder<String, Flight>(
			String.class, Flight.class);

	/**
	 * Parameterized constructor
	 * 
	 * @param flight_no
	 *            Flight number
	 * @param origin
	 *            From
	 * @param destination
	 *            To
	 * @param departure_time
	 *            Departure from origin
	 * @param duration
	 *            Flight duration
	 */
	public Flight(String flight_no, String origin, String destination,
			String departure_time, String duration) {
		this.flight_no = flight_no;
		this.origin = origin;
		this.destination = destination;
		this.departure_time = departure_time;
		this.duration = duration;
	}
}