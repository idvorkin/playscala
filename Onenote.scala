// Dispatch
import dispatch.Defaults._
import dispatch._
import play.api.libs.json._

case class Notebook(
                     val name: String
                   )

case class NotebookList(
                         val value: List[Notebook]
                       )

case class Link(
                 val href: String
               )

case class PageLinks(
                      val oneNoteClientUrl: Link,
                      val oneNoteWebUrl: Link

                    )

case class Page(
                 val title: String,
                 val links: PageLinks,
                 val contentUrl: String
               )

case class PageList(
                     val value: List[Page]
                   )

// Huh, why don't these be in scope in LiveAuth??
object OneNoteAppId
{
	val ClientId = "0000000048130833"
	val Secret = "40lRVb3d17e0AsQh3n0oFMXr3q-nkjPw"
}


// dunno what these format things are.

object OneNote
{
  implicit val __link1 = Json.format[Link];
  implicit val __pageLinks = Json.format[PageLinks];
  implicit val __page = Json.format[Page];
  implicit val __pageList = Json.format[PageList];
  implicit val notebookFormat = Json.format[Notebook];
  implicit val notebookListFormat = Json.format[NotebookList];

  def notebooksRequest() = {
    baseRequest() / "me" / "notes" / "notebooks"
  }

  def searchPages(title: String) = {
    val r = Http(searchPagesRequest(title))
    val r1 = r()
    var body = r1.getResponseBody()
    val json = Json.parse(body)
    Json.fromJson[PageList](json) get
  }

  def searchPagesRequest(title: String) = {
    var filter = s"title eq '$title'"
    baseRequest() / "me" / "notes" / "pages" <<? Map("filter" -> filter)
  }

  def baseRequest(authToken: String = "") = {
    addHeaders(url("https://www.onenote.com/api/v1.0"))
  }

  def addHeaders(request: Req) = {
    val path = "C:/Users/idvor/AppData/Local/Temp/saveOneNoteAccessToken_9353"
    // hardcoded for now from linqpad.
    val token = scala.io.Source.fromFile(path).mkString

    val headers = Map(
      "Authorization" -> s"Bearer $token",
      "Content-Type" -> "application/json"
    )
    println(s"Using Token:$token")

    request <:< headers
  }

}

// OneNote Auth: https://msdn.microsoft.com/en-us/office/office365/howto/onenote-auth#sign-in-msa
object LiveAuth { 

	def CreateClientAccessCodeUrl() = 
	{
        val clientId = "0000000048130833"
        val secret = "40lRVb3d17e0AsQh3n0oFMXr3q-nkjPw" // Bizarre - not sure why can't acess this  off OneNoteAppId
		val authorizeBase = "https://login.live.com/oauth20_authorize.srf";
	    val wlCallBackUri = "https://login.live.com/oauth20_desktop.srf";
	    val scopes = "wl.signin Office.onenote_create"
        val scopesEscaped = ""
        val queryParams = Map(
          "client_id" -> clientId,
          "redirect_uri" -> wlCallBackUri,
          "response_type" -> "code",
          "scope" -> scopes
          )

    url(authorizeBase) <<? queryParams
	}


      // var t = client.GetStringAsync("/me/notes/pages/0-aedc66b0713504be326f0894e8f70748!1-922579950926BF9E!1760/content");
}
