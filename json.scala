import scala.io.Source

def GetUrl = {
  val echoService = "http://scooterlabs.com/echo.json"
  Source.fromURL(echoService) mkString
}
Json.parse(GetUrl)
