package Handlers

import Builder.{QABuilder, ViewBuilder}
import Events.ActionEventListener
import Models.{Answer, Properties}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._

import scala.collection.mutable.ListBuffer

object SceneHandler {

  var selectedAnswers: ListBuffer[Answer] = ListBuffer()

  var currentScene = new Scene(400,150)
  var qIndex: Int = 0
  var cIndex: Int = 0
  var correctCount: Int = 0

  var showGraph: Boolean = false

  def build(dir: String): SceneHandler.type={
    QABuilder.buildQuestionaire(dir)
                .buildAnswerList(dir)
                    .buildSelectionList()
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
      QABuilder.listQuestions(qIndex).text, true, Properties.boldStyle, 800, 300
    )
    val answerLabel = ViewBuilder.createLabel("", false, Properties.boldStyle)
    val designerLabel = ViewBuilder.createLabel(Properties.footer, true, Properties.boldStyle)

    val sectionCbx = ViewBuilder.createChoiceBox[String](200,50, Pos.TopRight, QABuilder.listSectionIndex)
    val submit = ViewBuilder.createButton(Properties.submitTxt, Pos.BottomCenter )
    val next = ViewBuilder.createButton(Properties.nextTxt, Pos.BottomCenter, false)

    val checkBoxList: ListBuffer[CheckBox] = ListBuffer(
      ViewBuilder.createCheckBox(QABuilder.listQuestions(qIndex).options(0).text, 800, 200),
      ViewBuilder.createCheckBox(QABuilder.listQuestions(qIndex).options(1).text, 800, 200),
      ViewBuilder.createCheckBox(QABuilder.listQuestions(qIndex).options(2).text, 800, 200),
      ViewBuilder.createCheckBox(QABuilder.listQuestions(qIndex).options(3).text, 800, 200)
    )

    val radioBtnList: ListBuffer[RadioButton] = ListBuffer(
      ViewBuilder.createRadioButton(QABuilder.listQuestions(qIndex).options(0).text, 800, 200, toggleRadBtn),
      ViewBuilder.createRadioButton(QABuilder.listQuestions(qIndex).options(1).text, 800, 200, toggleRadBtn),
      ViewBuilder.createRadioButton(QABuilder.listQuestions(qIndex).options(2).text, 800, 200, toggleRadBtn),
      ViewBuilder.createRadioButton(QABuilder.listQuestions(qIndex).options(3).text, 800, 200, toggleRadBtn)
    )

    val footerVBox = ViewBuilder.createVBox(
      10, 20, Pos.BottomRight,
      Seq(designerLabel)
    )
    val vBox1 = ViewBuilder.createVBox(
      10, 20, Pos.Center,
      Seq(
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
      ))

    currentScene = new Scene(800,600)

    currentScene.setRoot(vBox1)

    sectionCbx.setOnAction((e)=>{
      qIndex = ActionEventListener.getSectionChoiceIndex(sectionCbx)
      cIndex = qIndex
      ActionEventListener.next(
        qIndex, sectionCbx, label, answerLabel, next, submit, vBox1, footerVBox, checkBoxList, radioBtnList
      )
    })

    submit.setOnAction((e)=>{
      ActionEventListener.submit(
        answerLabel,next,checkBoxList,toggleRadBtn
      )})

    next.setOnAction((e)=>{
      if( showGraph && QABuilder.listQuestions(qIndex+1).text.take(2).equals("1.") ){
        val bChart = ViewBuilder.createBarChartResult(5,12, Properties.barStyle, Properties.barGraphTitle, (correctCount.toDouble/(qIndex - cIndex)*100 ))
          ActionEventListener.showResult(vBox1, sectionCbx, next, bChart)
        correctCount = 0
        cIndex = qIndex
        showGraph = false
      }
      else{
        qIndex += 1
        ActionEventListener.next(
          qIndex, sectionCbx, label, answerLabel, next, submit, vBox1, footerVBox, checkBoxList, radioBtnList
        )
        if(!showGraph) showGraph = true
      }
    })

    this
  }
}
