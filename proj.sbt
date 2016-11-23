// Info copied from  http://bit.ly/2gLoU8f
name := "scalaz-demo"
 
version := "0.2"
 
// scalaVersion := "2.10.2"
 
libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.4"
 
scalacOptions += "-deprecation"
 
initialCommands in console := """
    |import scalaz._
    |import Scalaz._
    | import play.api.libs.json._
    |""".stripMargin
