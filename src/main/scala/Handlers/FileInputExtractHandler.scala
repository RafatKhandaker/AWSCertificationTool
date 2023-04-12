package Handlers

import org.xml.sax.{Attributes, ContentHandler, Locator}

object FileInputExtractHandler extends ContentHandler {

  def characters(ch: Array[Char], start: Int, length: Int) {
    println(new String(ch))
  }

  def endDocument() {
  }

  def endElement(uri: String, localName: String, qName: String) {
  }

  def endPrefixMapping(prefix: String) {
  }

  def ignorableWhitespace(ch: Array[Char], start: Int, length: Int) {
  }

  def processingInstruction(target: String, data: String) {
  }

  def setDocumentLocator(locator: Locator) {
  }

  def skippedEntity(name: String) {
  }

  def startDocument() {
  }

  def startElement(uri: String, localName: String, qName: String, atts: Attributes) {
  }

  def startPrefixMapping(prefix: String, uri: String) {
  }


}
