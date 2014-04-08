name := "EI"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.hibernate" % "hibernate-entitymanager" % "4.2.3.Final",
  "commons-io" % "commons-io" % "2.4",
  cache
)     


play.Project.playJavaSettings
