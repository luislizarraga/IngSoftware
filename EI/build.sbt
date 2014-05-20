name := "EI"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.hibernate" % "hibernate-entitymanager" % "4.2.3.Final",
  "commons-io" % "commons-io" % "2.4",
  "com.typesafe" %% "play-plugins-mailer" % "2.2.0",
  "pdf" % "pdf_2.10" % "0.5" exclude("org.scala-stm", "scala-stm_2.10.0"),
  cache
)     


play.Project.playJavaSettings

resolvers += Resolver.url("Violas Play Modules", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns)
