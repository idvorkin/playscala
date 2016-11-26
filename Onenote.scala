// Dispatch
import dispatch._, Defaults._

// Huh, why don't these be in scope in LiveAuth??
object OneNoteAppId
{
	val ClientId = "0000000048130833"
	val Secret = "40lRVb3d17e0AsQh3n0oFMXr3q-nkjPw"
}

object OneNote
{
	def baseRequest(authToken:String="") = 
	{
      val path = "C:/Users/idvor/AppData/Local/Temp/saveOneNoteAccessToken_9352" // hardcoded for now from linqpad.
      val token = if (authToken.isEmpty) 
      {
        scala.io.Source.fromFile(path).mkString
      }
      else
      {
        authToken
      }

        val headers = Map(
          "Authorization" -> s"Bearer $token",
          "Content-Type" -> "application/json"
          ) 

        // TBD do I need to set Accepted Media Types, or will it work by default?
    	// var t = client.GetStringAsync("/me/notes/pages/0-aedc66b0713504be326f0894e8f70748!1-922579950926BF9E!1760/content");
        url("https://www.onenote.com/api/v1.0") <:< headers
	}
    def notebooks () = 
    {
      baseRequest()  / "me" / "notes"/ "notebooks"
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
