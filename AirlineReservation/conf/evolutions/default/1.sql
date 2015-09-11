# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Airlines (
  flight_no                 varchar(255) not null,
  airline                   varchar(255),
  week_schedule             varchar(255),
  constraint pk_Airlines primary key (flight_no))
;

create table Booking (
  id                        integer auto_increment not null,
  flight_no                 varchar(255),
  seat_no                   varchar(255),
  username                  varchar(255),
  date_created              varchar(255),
  date_modified             varchar(255),
  constraint pk_Booking primary key (id))
;

create table BookingInfo (
  booking_id                integer auto_increment not null,
  no_of_adults              integer,
  no_of_children            integer,
  constraint pk_BookingInfo primary key (booking_id))
;

create table Cost (
  flight_no                 varchar(255) not null,
  price                     float,
  class                     varchar(255),
  constraint pk_Cost primary key (flight_no))
;

create table Flight (
  flight_no                 varchar(255) not null,
  origin                    varchar(255),
  destination               varchar(255),
  departure_time            varchar(255),
  duration                  varchar(255),
  constraint pk_Flight primary key (flight_no))
;

create table UserInfo (
  username                  varchar(255) not null,
  password_md5              varchar(255),
  name_user                 varchar(255),
  email                     varchar(255),
  phone                     varchar(255),
  date_created              varchar(255),
  date_modified             varchar(255),
  active                    tinyint(1) default 0,
  constraint pk_UserInfo primary key (username))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table Airlines;

drop table Booking;

drop table BookingInfo;

drop table Cost;

drop table Flight;

drop table UserInfo;

SET FOREIGN_KEY_CHECKS=1;

