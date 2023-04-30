package Events

import Builder.QABuilder.{listAnswers, listQuestions, listSectionQuestion}
import Handlers.SceneHandler.{correctCount, currentScene, qIndex, selectedAnswers}
import Models.{Answer, Properties}
import scalafx.scene.chart.BarChart
import scalafx.scene.control._
import scalafx.scene.layout.VBox

import scala.collection.mutable.ListBuffer

object ActionEventListener {

  def getSectionChoiceIndex(choiceBox: ChoiceBox[String] ): Int = {
    val index = listSectionQuestion(choiceBox.getSelectionModel.getSelectedIndex).number -1
    selectedAnswers = ListBuffer()
    0 to (index-1) foreach((i)=>{ selectedAnswers.addOne(null)})
    index
  }

  def showResult(vBox1: VBox, sectionCbx: ChoiceBox[String], next: Button, bChart: BarChart[Number, String]): Unit ={
    vBox1.children = Seq(sectionCbx,bChart, next)
  }

  def submit(answerLabel: Label, next: Button, checkBoxList: ListBuffer[CheckBox], toggleRadBtn: ToggleGroup): Unit ={
    if(listQuestions(qIndex).text.toLowerCase().contains(Properties.selectTwo) || listAnswers(qIndex).choice.length.equals(2)){
      val filteredCBL = checkBoxList.filter(cbx => cbx.isSelected)
      if(filteredCBL.length != 2){
        answerLabel.setStyle(Properties.incorrectStyle)
        answerLabel.setText(Properties.selectOnlyTwoTxt)
        answerLabel.setVisible(true)
        return
      }
      var c = ""
      var cText = ""
      filteredCBL.foreach(cb =>{
        cText += cb.getText+"\n"
        c += cb.getText()(0)
      })
      selectedAnswers.addOne( Answer( c, cText) )

      var checkAns = true
      0 to filteredCBL.length foreach{ i => {
        if(!selectedAnswers(qIndex).choice.equals( listAnswers(qIndex).choice )) {
          answerLabel.setStyle(Properties.incorrectStyle)
          answerLabel.setText(s"Incorrect! Answer ${listAnswers(qIndex).choice(0)+","+listAnswers(qIndex).choice(1)}: ${listAnswers(qIndex).text}")
          checkAns = false
        }
      }}
      if(checkAns){
        answerLabel.setStyle(Properties.correctStyle)
        answerLabel.setText(s"Correct! Answer ${listAnswers(qIndex).choice(0)+","+listAnswers(qIndex).choice(1)}: ${listAnswers(qIndex).text}")
        correctCount += 1
      }
    }

    else if(!listQuestions(qIndex).text.toLowerCase().contains(Properties.selectTwo) && !listAnswers(qIndex).choice.length.equals(2)){
      val c = toggleRadBtn.getSelectedToggle.asInstanceOf[javafx.scene.control.RadioButton].getText
      selectedAnswers.addOne( Answer(c(0).toString, c) )

      if(selectedAnswers(qIndex).choice.equals(listAnswers(qIndex).choice)){
        answerLabel.setStyle(Properties.correctStyle)
        answerLabel.setText(s"Correct! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}" )
        correctCount += 1
      }else{
        answerLabel.setStyle(Properties.incorrectStyle)
        answerLabel.setText(s"Inorrect! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}" )
      }
    }

    answerLabel.setVisible(true)
    next.setVisible(true)
  }

  def next(qIndex: Int, sectionCbx: ChoiceBox[String], label: Label, answerLabel: Label, next: Button, submit: Button, vBox1: VBox, footerVBox: VBox, checkBoxList:ListBuffer[CheckBox], radioBtnList: ListBuffer[RadioButton] ): Unit={
    label.setText(listQuestions(qIndex).text)
    if(listQuestions(qIndex).text.toLowerCase().contains(Properties.selectTwo) || listAnswers(qIndex).choice.length.equals(2) ){
      0 to 3 foreach{ i => {
        checkBoxList(i).setText(listQuestions(qIndex).options(i).text)
        checkBoxList(i).setSelected(false)
      }}
    }
    else{
      0 to 3 foreach{ i => {
        radioBtnList(i).setText(listQuestions(qIndex).options(i).text)
        radioBtnList(i).setSelected(false)
      }}
    }


    answerLabel.setVisible(false)
    next.setVisible(false)
    if(listQuestions(qIndex).text.toLowerCase().contains(Properties.selectTwo) || listAnswers(qIndex).choice.length.equals(2)){
      vBox1.children = Seq(
        sectionCbx,
        label,
        checkBoxList(0),
        checkBoxList(1),
        checkBoxList(2),
        checkBoxList(3),
        submit,
        answerLabel,
        next,
        footerVBox
      )
    }else{
      vBox1.children = Seq(
        sectionCbx,
        label,
        radioBtnList(0),
        radioBtnList(1),
        radioBtnList(2),
        radioBtnList(3),
        submit,
        answerLabel,
        next,
        footerVBox
      )
    }
    currentScene.setRoot(vBox1)
  }

}
