// Dispatch

// Do a continous compile w/ ~compile.
import dispatch.Defaults._
import dispatch._
import play.api.libs.json._

case class CopyBody(id:String, renameAs:String)

case class Notebook(
                     name: String
                   )

case class NotebookList(
                         value: List[Notebook]
                       )

case class Link(
                 href: String
               )

case class PageLinks(
                      oneNoteClientUrl: Link,
                      oneNoteWebUrl: Link
                    )

case class Page(
                 id: String,
                 self: String,
                 title: String,
                 links: PageLinks,
                 contentUrl: String)

case class PageList(
                     value: List[Page]
                   )

