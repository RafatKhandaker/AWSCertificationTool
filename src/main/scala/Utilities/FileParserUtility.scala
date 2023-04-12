package Utilities

import org.apache.tika.metadata._
import org.apache.tika.parser._
import org.apache.tika.sax.BodyContentHandler

import java.io._

object FileParserUtility {

  def parse (file: String): String = {
    var result: String = ""
    val parser: Parser = new AutoDetectParser();
    val stream: InputStream = new FileInputStream( new File(file) )
    val handler: BodyContentHandler = new BodyContentHandler()
    val metadata: Metadata = new Metadata()
    val context: ParseContext = new ParseContext()

    try {
      parser.parse(stream, handler, metadata, context)
      result = handler.toString
    }finally stream.close()
     result
  }

}
