name := "AirlineReservation"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.33",
  cache,
  filters
)

play.Project.playJavaSettings
