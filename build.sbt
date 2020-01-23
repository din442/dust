val ScalatraVersion = "2.7.0-RC1"

organization := "com.jigsawstudio"
name := "dust"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.12.10"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.19.v20190610" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc"       % "3.4.0",
      "com.h2database"  %  "h2"                % "1.4.200",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.10.1",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.10.1",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.1",
      "org.postgresql"  %  "postgresql"        % "42.2.9",
      "org.scalatest"   %% "scalatest"         % "3.1.0"    % "test",      
)
enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
