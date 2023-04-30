package Handlers

import Builder.ViewBuilder
import Events.ActionEventListener
import Models.{Answer, Properties, Question}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._

import scala.collection.mutable.ListBuffer

object SceneHandler {

  var listQuestions: List[Question] = List()
  var listAnswers: List[Answer] = List()
  var selectedAnswers: ListBuffer[Answer] = ListBuffer()

  var currentScene = new Scene(400,150)
  var qIndex: Int = 0

  def addQuestionsList(lQuestions: List[Question]): SceneHandler.type ={
    listQuestions = lQuestions
    this
  }

  def addAnswersList(lAnswers: List[Answer]): SceneHandler.type ={
    listAnswers = lAnswers
    this
  }
  def createInitialScene(cbx: ChoiceBox[String]): SceneHandler.type={
    currentScene.root = ViewBuilder.createVBox(
      10,20, Pos.Center,
      Seq(cbx,
        ViewBuilder.createVBox(
          10,20, Pos.BottomCenter,
          Seq( ViewBuilder.createLabel(Properties.footer, true, Properties.boldStyle) )
        )))
    this
  }

  def Next(): SceneHandler.type= {
    val toggleRadBtn = new ToggleGroup()

    val label = ViewBuilder.createLabel(
      listQuestions(qIndex).text, true, Properties.boldStyle, 800, 300
    )
    val answerLabel = ViewBuilder.createLabel("", false, Properties.boldStyle)
    val designerLabel = ViewBuilder.createLabel(Properties.footer, true, Properties.boldStyle)

    val submit = ViewBuilder.createButton(Properties.submitTxt, Pos.BottomCenter )
    val next = ViewBuilder.createButton(Properties.nextTxt, Pos.BottomCenter, false)

    val checkBoxList: ListBuffer[CheckBox] = ListBuffer(
      ViewBuilder.createCheckBox(listQuestions(qIndex).options(0).text, 800, 200),
      ViewBuilder.createCheckBox(listQuestions(qIndex).options(1).text, 800, 200),
      ViewBuilder.createCheckBox(listQuestions(qIndex).options(2).text, 800, 200),
      ViewBuilder.createCheckBox(listQuestions(qIndex).options(3).text, 800, 200)
    )

    val radioBtnList: ListBuffer[RadioButton] = ListBuffer(
      ViewBuilder.createRadioButton(listQuestions(qIndex).options(0).text, 800, 200, toggleRadBtn),
      ViewBuilder.createRadioButton(listQuestions(qIndex).options(1).text, 800, 200, toggleRadBtn),
      ViewBuilder.createRadioButton(listQuestions(qIndex).options(2).text, 800, 200, toggleRadBtn),
      ViewBuilder.createRadioButton(listQuestions(qIndex).options(3).text, 800, 200, toggleRadBtn)
    )

    val footerVBox = ViewBuilder.createVBox(
      10, 20, Pos.BottomRight,
      Seq(designerLabel)
    )
    val vBox1 = ViewBuilder.createVBox(
      10, 20, Pos.Center,
      Seq(
        label,
        radioBtnList(0),
        radioBtnList(1),
        radioBtnList(2),
        radioBtnList(3),
        submit,
        answerLabel,
        next,
        footerVBox
      ))



    submit.setOnAction((e)=>{
      ActionEventListener.clickSubmit(
        answerLabel,next,checkBoxList,toggleRadBtn
      )})

    next.setOnAction((e)=>{
      qIndex += 1
      ActionEventListener.clickNext(
        qIndex, label, answerLabel, next, submit, vBox1, footerVBox, checkBoxList, radioBtnList
      )})

    currentScene = new Scene(800,600)

    currentScene.setRoot(vBox1)

    this
  }
}
