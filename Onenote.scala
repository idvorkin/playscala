// Dispatch

// Do a continous compile w/ ~compile.
import dispatch.Defaults._
import dispatch._
import play.api.libs.json._

object OneNoteAuth {
  def addHeaders(request: Req): Req = {
    val path = "C:/Users/idvor/AppData/Local/Temp/saveOneNoteAccessToken_9352"
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

// Huh, why don't these be in scope in LiveAuth??
object OneNoteAppId
{
	val ClientId = "0000000048130833"
	val Secret = "40lRVb3d17e0AsQh3n0oFMXr3q-nkjPw"
}


// dunno what these format things are.

object OneNote {
  implicit val __link1 = Json.format[Link]
  implicit val __pageLinks = Json.format[PageLinks]
  implicit val __page = Json.format[Page]
  implicit val __pageList = Json.format[PageList]
  implicit val notebookFormat = Json.format[Notebook]
  implicit val notebookListFormat = Json.format[NotebookList]
  implicit val __copyBody = Json.format[CopyBody]

  def notebooksRequest() = {
    baseRequest() / "me" / "notes" / "notebooks"
  }

  def searchPages(title: String) = {
    val r = Http(searchPagesRequest(title))
    val r1 = r()
    var body = r1.getResponseBody()
    val json = Json.parse(body)
    Json.fromJson[PageList](json) get
   /// implicit val residentReads = Json.reads[Resident]
  }


  def searchPagesRequest(title: String) = {
    var filter = s"title eq '$title'"
    baseRequest() / "me" / "notes" / "pages" <<? Map("filter" -> filter)
  }

  def baseRequest(authToken: String = "") = {
    OneNoteAuth.addHeaders(url("https://www.onenote.com/api/v1.0"))
  }

  def pageContent(page: Page) = {
    val r = Http(OneNoteAuth.addHeaders(url(page.contentUrl)))
    r().getResponseBody()
  }

  def copyPage(page: Page) = {
    // https://msdn.microsoft.com/en-us/office/office365/howto/onenote-copy

    val section = "87eb8b50-7edc-45dc-9163-2fd4556d4114"

    // Create a post
    val copyUrl = url (page.self) / "copyToSection"
    var copyParams = Json.stringify(Json.toJson(CopyBody(id=section, renameAs="CopiedIgor")))
    // val residentJson: JsValue = Json.toJson(resident)

    val post = OneNoteAuth.addHeaders(copyUrl) << copyParams
    val r = Http(OneNoteAuth.addHeaders(post))
    r().getResponseBody()
  }
}

// OneNote Auth: https://msdn.microsoft.com/en-us/office/office365/howto/onenote-auth#sign-in-msa
object LiveAuth {

  def CreateClientAccessCodeUrl() = {
    val clientId = "0000000048130833"
    val secret = "40lRVb3d17e0AsQh3n0oFMXr3q-nkjPw"
    // Bizarre - not sure why can't acess this  off OneNoteAppId
    val authorizeBase = "https://login.live.com/oauth20_authorize.srf"
    val wlCallBackUri = "https://login.live.com/oauth20_desktop.srf"
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
