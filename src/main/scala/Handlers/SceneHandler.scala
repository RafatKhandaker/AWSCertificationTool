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

  def ChangeScene(): SceneHandler.type= {
    val toggleRadBtn = new ToggleGroup()

    val label = ViewBuilder.createLabel(
      QABuilder.listQuestions(qIndex).text, true, Properties.boldStyle, 800, 300
    )
    val answerLabel = ViewBuilder.createLabel("", false, Properties.boldStyle)
    val designerLabel = ViewBuilder.createLabel(Properties.footer, true, Properties.boldStyle)

    val sectionCbx = ViewBuilder.createChoiceBox[String](200,50, Pos.TopRight, QABuilder.listSectionIndex)
    val submit = ViewBuilder.createButton(Properties.submitTxt, Pos.BottomCenter )
    val next = ViewBuilder.createButton(Properties.nextTxt, Pos.BottomCenter, false)

    val checkBoxList: ListBuffer[CheckBox] = ListBuffer()
    val radioBtnList: ListBuffer[RadioButton] = ListBuffer()

    ViewBuilder.createCheckBoxList(checkBoxList,qIndex)
    ViewBuilder.createRadioBoxList(radioBtnList,qIndex, toggleRadBtn)

    val footerVBox = ViewBuilder.createVBox(
      10, 20, Pos.BottomRight,
      Seq(designerLabel)
    )

    val childrenSeq: Seq[Region] = ViewBuilder.buildChildrenSequence(qIndex,sectionCbx,label,checkBoxList,radioBtnList,submit,answerLabel,next, footerVBox )
    val vBox1 = ViewBuilder.createVBox(10, 20, Pos.Center,childrenSeq)

    currentScene = new Scene(800,600)

    currentScene.setRoot(vBox1)

    sectionCbx.setOnAction((e)=>{
      qIndex = ActionEventListener.getSectionChoiceIndex(sectionCbx)
      cIndex = qIndex
      ActionEventListener.next(
        toggleRadBtn, qIndex, sectionCbx, label, answerLabel, next, submit, vBox1, footerVBox, checkBoxList, radioBtnList
      )
      submit.disable = false
    })

    submit.setOnAction((e)=>{
      ActionEventListener.submit(
        answerLabel,next,checkBoxList,toggleRadBtn
      )
      submit.disable = true
    })

    next.setOnAction((e)=>{
      submit.disable = false
      if( showGraph && QABuilder.listQuestions(qIndex+1).text.take(2).equals("1.") ){
        val bChart = ViewBuilder.createBarChartResult(5,12, Properties.barStyle, Properties.barGraphTitle, (correctCount.toDouble/(qIndex+1 - cIndex)*100 ))
        next.visible = false
        ActionEventListener.showResult(vBox1, sectionCbx, next, bChart)
        correctCount = 0
        cIndex = qIndex+1
        showGraph = false
      }
      else{
        qIndex += 1
        ActionEventListener.next(
          toggleRadBtn, qIndex, sectionCbx, label, answerLabel, next, submit, vBox1, footerVBox, checkBoxList, radioBtnList
        )
        if(!showGraph) showGraph = true
      }
    })

    this
  }
}
