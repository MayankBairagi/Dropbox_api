name := "DropBox_Plugin"

version := "1.0"

scalaVersion := "2.9.2"

libraryDependencies ++= {
Seq(
"net.liftweb" %% "lift-json" % "2.5-M3",
"net.databinder.dispatch" %% "dispatch-core" % "0.9.5",
"net.databinder" %% "dispatch-oauth" % "0.8.8",
"com.novocode" % "junit-interface" % "0.10-M1" % "test",
"org.scalatest" %% "scalatest" % "1.6.1" % "test",
"org.specs2"        		%% "specs2"             		% "1.12.1"           		% "test",
"org.skife.com.typesafe.config" % "typesafe-config" % "0.3.0"
)
}

