package Handlers

import Utilities.FileParserUtility

import java.nio.file.{FileSystems, Files}
import scala.collection.mutable.ListBuffer

object FileHandler {

  var directory: String = ""
  var files: Array[AnyRef] = Array()
  var parsedFiles: ListBuffer[String] = ListBuffer()

  def addDirectory(d: String): FileHandler.type ={
    directory = d
    this
  }
  def setQuestionPath(): FileHandler.type ={
    directory += "\\questions"
    this
  }
  def setAnswersPath(): FileHandler.type ={
    directory += "\\answers"
    this
  }

  def setBookPath(): FileHandler.type ={
    directory += "\\books"
    this
  }

  def getFiles(): FileHandler.type={
    files = Files.list(FileSystems.getDefault.getPath(directory)).toArray
    this
  }
  def parseFiles():FileHandler.type ={
    parsedFiles = ListBuffer()
    files.foreach(f=>{
      parsedFiles += FileParserUtility.parse(f.toString)
    })

    this
  }

}
