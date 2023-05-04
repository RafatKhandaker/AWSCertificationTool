package Builder

import Handlers.FileHandler
import Models.{Answer, Options, Question}
import scalafx.collections.ObservableBuffer

import scala.collection.mutable.ListBuffer

object QABuilder {
  var p1 = "\\d+\\. ([A-Za-z&-._\\n*$\\/(A-Za-z0-9)“”,’—]+( [A-Za-z&-._\\n$“”,’\\/(A-Za-z0-9)—]*)+)\\?* (\\n*\\S* *\\n*\\S*)\\n".r
  var p2 = "[A-E]{1}\\. ([A-Za-z&-._\\/\\n$(A-Za-z0-9)*“”,’—;:]+( [A-Za-z&-._“”,’\\/\\n$(A-Za-z0-9)—;:]*)+)\\n*".r

  var listQuestions: ListBuffer[Question] = new ListBuffer()
  var listOptions: ListBuffer[Options] = new ListBuffer()
  var listAnswers: ListBuffer[Answer] = new ListBuffer()
  var listSectionQuestion: ListBuffer[Question] = new ListBuffer()
  var listSectionIndex: ObservableBuffer[String] = new ObservableBuffer()

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
        val pMatchArray = pMatch.toString().split("\\n\\n").zipWithIndex
        pMatchArray foreach{ case(s,i)=>{
            if(s.contains("A.")) listOptions.addOne(Options('A', s))

            else if(s.contains("B.")) listOptions.addOne(Options('B', s))

            else if(s.contains("C.")) listOptions.addOne(Options('C', s))

            else if(s.contains("D.")){
              listOptions.addOne(Options('D', s))
              if( (i+1) <= pMatchArray.length -1 && pMatchArray(i+1)._1.contains("E."))
                listOptions.addOne(Options('E', pMatchArray(i+1)._1))
              listQuestions.addOne( Question((qIndex+1),lquestions(qIndex),listOptions.toList))
              listOptions = new ListBuffer()
              qIndex += 1
            }
        }}}
    })
    this
  }

  def buildAnswerList(d:String): QABuilder.type ={
    FileHandler.addDirectory(d).setAnswersPath().getFiles().parseFiles().parsedFiles.foreach(f=>{
      f.split("\\d+\\.[^\\n]").foreach(a=> {
        val s1 = (("[A-Z,\\s]*\\. ").r.findFirstIn(a)).fold("")(_.toString)
        var s2 = ""
        ("[A-Z]").r.findAllMatchIn(s1).foreach(s => { s2 += s })
        if(!s2.isEmpty) listAnswers.addOne(Answer(s2, a.substring(a.indexOf('.')+1 )))
      })})
    this
  }

  def buildSelectionList(): QABuilder.type ={
    listSectionQuestion = listQuestions.filter(q=> q.text.take(2).equals("1."))
    0 to listSectionQuestion.length foreach((i)=>{
      listSectionIndex.addOne(s"Section ${i}")
    })
    this
  }
}

