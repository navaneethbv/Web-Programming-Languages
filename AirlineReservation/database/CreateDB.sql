-- Create fresh database
DROP DATABASE IF EXISTS Flight_Management;
CREATE DATABASE Flight_Management;
USE Flight_Management;

-- Create an administrator for this DB
GRANT ALL ON `Flight_Management`.* TO 'flight_admin'@'localhost' IDENTIFIED BY 'Admin';