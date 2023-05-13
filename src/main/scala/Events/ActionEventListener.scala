package Events

import Builder.QABuilder.{listAnswers, listQuestions, listSectionQuestion}
import Builder.ViewBuilder
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
    if(listAnswers(qIndex).choice.length > 1){
      val filteredCBL = checkBoxList.filter(cbx => cbx.isSelected)
      var c = ""
      var cText = ""
      filteredCBL.foreach(cb =>{
        cText += cb.getText+"\n"
        c += cb.getText()(0)
      })
      selectedAnswers.addOne( Answer( c, cText) )

      if(!selectedAnswers(qIndex).choice.equals( listAnswers(qIndex).choice )) {
        answerLabel.setStyle(Properties.incorrectStyle)
        answerLabel.setText(s"Incorrect! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}")
      }else{
        answerLabel.setStyle(Properties.correctStyle)
        answerLabel.setText(s"Correct! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}")
        correctCount += 1
      }
    }

    else{
      val c = toggleRadBtn.getSelectedToggle.asInstanceOf[javafx.scene.control.RadioButton].getText
      selectedAnswers.addOne( Answer(c(0).toString, c) )

      if(selectedAnswers(qIndex).choice.equals(listAnswers(qIndex).choice)){
        answerLabel.setStyle(Properties.correctStyle)
        answerLabel.setText(s"Correct! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}" )
        correctCount += 1
      }else{
        answerLabel.setStyle(Properties.incorrectStyle)
        answerLabel.setText(s"Incorrect! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}" )
      }
    }

    answerLabel.setVisible(true)
    next.setVisible(true)
  }

  def next(toggleRadBtn: ToggleGroup, qIndex: Int, sectionCbx: ChoiceBox[String], label: Label, answerLabel: Label, next: Button, submit: Button, vBox1: VBox, footerVBox: VBox, checkBoxList:ListBuffer[CheckBox], radioBtnList: ListBuffer[RadioButton] ): Unit={
    label.setText(listQuestions(qIndex).text)
    checkBoxList.clear()
    radioBtnList.clear()

    ViewBuilder.createCheckBoxList(checkBoxList,qIndex)
    ViewBuilder.createRadioBoxList(radioBtnList,qIndex, toggleRadBtn)

    if(listAnswers(qIndex).choice.length > 1 ){
      listQuestions(qIndex).options.zipWithIndex foreach{ case(o,i) => {
        checkBoxList(i).setText(o.text)
      }}}
    else{
      listQuestions(qIndex).options.zipWithIndex foreach{ case(o,i) => {
        radioBtnList(i).setText(o.text)
      }}}

    answerLabel.setVisible(false)
    next.setVisible(false)
    vBox1.children = ViewBuilder.buildChildrenSequence(
      qIndex,sectionCbx,label,checkBoxList,radioBtnList,submit,answerLabel,next, footerVBox
    )
    currentScene.setRoot(vBox1)
  }

}
