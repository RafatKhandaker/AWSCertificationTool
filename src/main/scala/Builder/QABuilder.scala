package Builder

import Handlers.FileHandler
import Models.{Answer, Options, Question}

import scala.collection.mutable.ListBuffer

object QABuilder {
  var p1 = "\\d+\\. ([A-Za-z&-._\\n*“”,’—]+( [A-Za-z&-._\\n“”,’(0-9)—]*)+)\\?".r
  var p2 = "[A-D]{1}\\. ([A-Za-z&-._\\n*“”,’—]+( [A-Za-z&-._“”,’\\n(0-9)—;]*)+)\\n{1}".r

  var listQuestions: ListBuffer[Question] = new ListBuffer()
  var listOptions: ListBuffer[Options] = new ListBuffer()
  var listAnswers: ListBuffer[Answer] = new ListBuffer()

  var qIndex: Int = 0
  var aIndex: Int = 0

  def buildQuestionaire(d:String): QABuilder.type ={
    var sFile = ""
    val lquestions: ListBuffer[String] = new ListBuffer()

    FileHandler.addDirectory(d).setQuestionPath().getFiles().parseFiles().parsedFiles.foreach(f=>{
        sFile = f
        for( pMatch <- p1.findAllMatchIn(f)) {
          lquestions.addOne(pMatch.toString())
          sFile = sFile.replaceFirst(pMatch.toString(),"")
        }

        for( pMatch <- p2.findAllMatchIn(sFile)) {
          pMatch.toString().split("\\n\\n").foreach(s=>{
            if(s.contains("A.")) listOptions.addOne(Options('A', s))
            else if(s.contains("B.")) listOptions.addOne(Options('B', s))
            else if(s.contains("C.")) listOptions.addOne(Options('C', s))
            else if(s.contains("D.")){
              listOptions.addOne(Options('D', s))
              listQuestions.addOne( Question((qIndex+1),lquestions(qIndex),listOptions.toList))
              listOptions = new ListBuffer()
              qIndex += 1
            }})}
    })
    this
  }

  def buildAnswerList(d:String): QABuilder.type ={
    FileHandler.addDirectory(d).setAnswersPath().getFiles().parseFiles().parsedFiles.foreach(f=>{
        f.split("\\d+\\. ").foreach(a=>{
          if(a(0).isUpper && a(1).equals('.')){
            listAnswers.addOne(Answer( a(0), a.substring(2)))
          }})})
    this
  }

}

