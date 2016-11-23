// Info copied from  http://bit.ly/2gLoU8f
name := "scalaz-demo"
 
version := "0.2"
 

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

/*
libraryDependencies ++= Seq(
  "com.typesafe.play"  %% "play-json"    % "2.3.4"
)
*/

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.3.4"
 
scalacOptions += "-deprecation"
 
initialCommands in console := """
    | import play.api.libs.json._
    |""".stripMargin

