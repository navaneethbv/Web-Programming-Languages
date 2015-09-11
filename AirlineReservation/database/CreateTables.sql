-- Drop tables
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS BookingInfo;
DROP TABLE IF EXISTS Cost;
DROP TABLE IF EXISTS Flight;
DROP TABLE IF EXISTS Airlines;
DROP TABLE IF EXISTS UserInfo;

-- Create user table
CREATE TABLE UserInfo (
	UserName		VARCHAR(25) PRIMARY KEY,	-- Username for login
	Name_user		VARCHAR(50) NOT NULL,		-- Name of the user
	Password_MD5	VARCHAR(50) NOT NULL,		-- Password for the user in encrypted format
	Email			VARCHAR(25),				-- Email ID of the user
	Phone			VARCHAR(15),				-- Phone number of the user
	Date_created	DATETIME NOT NULL,			-- Date the account was created
	Date_modified	DATETIME NOT NULL,			-- Date the account was last modified
	Active			BOOLEAN NOT NULL,			-- Is the account active?
	CONSTRAINT `date_check` CHECK (Date_created <= Date_modified)
);

-- Create airlines table
CREATE TABLE Airlines (
	Flight_No		VARCHAR(5) PRIMARY KEY,		-- Flight number of the airline
	Airline			VARCHAR(20) NOT NULL,		-- Airline name
	Week_Schedule	VARCHAR(20) NOT NULL		-- Comma separated values of week days for which the flight is operational
);

-- Create Booking table
CREATE TABLE Flight (
	Flight_No		VARCHAR(5) PRIMARY KEY,					-- Flight number of the airline
	Origin			VARCHAR(15) NOT NULL,					-- Source airport name
	Destination		VARCHAR(15) NOT NULL,					-- Destination airport name
	Departure_time	TIME NOT NULL,							-- Departure time from the source
	Duration		TIME NOT NULL,							-- Duration of the journey
	FOREIGN KEY (Flight_No) REFERENCES Airlines(Flight_No)	-- Record linked to airlines	
);

-- Create table cost
CREATE TABLE Cost (
	Flight_No		VARCHAR(5),								-- Flight number of the airline
	Price			FLOAT NOT NULL,							-- Price for the journey from source to destination
	Class			VARCHAR(1) NOT NULL,					-- Class: B for business, E for economy
	FOREIGN KEY (Flight_No) REFERENCES Airlines(Flight_No),	-- Record linked to airlines
	CONSTRAINT `class_check` CHECK (Class IN (`B`, `E`))	-- Restrict class values
);

-- Create booking info table
CREATE TABLE BookingInfo (
	Booking_ID		INTEGER PRIMARY KEY AUTO_INCREMENT,			-- Booking ID referenced from booking table
	No_Of_Adults	INTEGER NOT NULL,							-- Number of adults
	No_Of_Children	INTEGER DEFAULT 0							-- Number of children, default 0
);
	
-- Create Booking table
CREATE TABLE Booking (
	ID				INTEGER,								-- Record linked to Booking ID
	Flight_No		VARCHAR(5),								-- Flight number of the airline
	Seat_No			VARCHAR(5) NOT NULL,
	UserName		VARCHAR(25),
	Date_created	DATETIME NOT NULL,						-- Date the booking was created
	Date_modified	DATETIME NOT NULL,						-- Date the booking was last modified
	FOREIGN KEY (ID) REFERENCES BookingInfo(Booking_ID),		-- Record linked to booking info
	FOREIGN KEY (Flight_No) REFERENCES Airlines(Flight_No),	-- Record linked to airlines
	CONSTRAINT `booking_date_check` CHECK (Date_created <= Date_modified)
);