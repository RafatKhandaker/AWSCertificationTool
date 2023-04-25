package Handlers

import Models.{Answer, Question}
import scalafx.geometry.{Insets, Pos}
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
    val designerLabel = Label("Created By: Rafat Khandaker\nEmail: rafat.Khandaker.developer@gmail.com")
    designerLabel.setStyle("-fx-font-weight: bold");
    currentScene.root = new VBox {
      spacing = 10
      padding = Insets(20)
      alignment = Pos.Center
      children = Seq(cbx,
        new VBox{
          padding = Insets(20)
          spacing = 10
          alignment = Pos.BottomCenter
          children = Seq( designerLabel )
        })}
    this
  }

  def Next(): SceneHandler.type= {
    val toggleRadBtn = new ToggleGroup()
    val toggleChkBx = new ToggleGroup()

    val label = Label(listQuestions(qIndex).text)
    label.setMaxWidth(800)
    label.setMaxHeight(300)
    val answerLabel = Label("")
    val designerLabel = Label("Created By: Rafat Khandaker\nEmail: rafat.Khandaker.developer@gmail.com")
    designerLabel.setStyle("-fx-font-weight: bold");
    val submit = new Button{
      text= "Submit"
      alignment = Pos.BottomCenter
    }
    val next = new Button{
      text= "Next"
      alignment = Pos.BottomCenter
      visible = false
    }
    answerLabel.setVisible(false)
    label.setStyle("-fx-font-weight: bold");

    val checkBoxList: ListBuffer[CheckBox] = ListBuffer(
      new CheckBox{
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(0).text
        selected = false
      },
      new CheckBox{
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(1).text
        selected = false
      },
      new CheckBox{
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(2).text
        selected = false
      },
      new CheckBox{
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(3).text
        selected = false
      }
    )

    val radioBtnList: ListBuffer[RadioButton] = ListBuffer(
      new RadioButton {
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(0).text
        selected = false
        toggleGroup = toggleRadBtn
      },
      new RadioButton {
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(1).text
        selected = false
        toggleGroup = toggleRadBtn
      },
      new RadioButton {
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(2).text
        selected = false
        toggleGroup = toggleRadBtn
      },
      new RadioButton {
        maxWidth = 800
        maxHeight = 200
        text = listQuestions(qIndex).options(3).text
        selected = false
        toggleGroup = toggleRadBtn
      }
    )
    val vBox1 = new VBox{
      padding = Insets(20)
      spacing = 10
      alignment = Pos.Center
    }
    vBox1.children = Seq(
      label,
      radioBtnList(0),
      radioBtnList(1),
      radioBtnList(2),
      radioBtnList(3),
      submit,
      answerLabel,
      next,
      new VBox {
        padding = Insets(20)
        spacing = 10
        alignment = Pos.BottomRight
        children = Seq( designerLabel )
      })

    submit.setOnAction((e)=>{
      if( listQuestions(qIndex).text.toLowerCase().contains("select two.")){
        val filteredCBL = checkBoxList.filter(cbx => cbx.isSelected)
        if(filteredCBL.length != 2){
          answerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red")
          answerLabel.setText("!!! Select only 2 values !!!")
          answerLabel.setVisible(true)
          return this
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
          if( !selectedAnswers(qIndex).choice.equals( listAnswers(qIndex).choice ) ) {
            answerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red")
            answerLabel.setText(s"Incorrect! Answer ${listAnswers(qIndex).choice(0)+","+listAnswers(qIndex).choice(1)}: ${listAnswers(qIndex).text}")
            checkAns = false
          }
        }}
        if(checkAns){
          answerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: green")
          answerLabel.setText(s"Correct! Answer ${listAnswers(qIndex).choice(0)+","+listAnswers(qIndex).choice(1)}: ${listAnswers(qIndex).text}")
        }
      }

      else if( !listQuestions(qIndex).text.toLowerCase().contains("select two.")){
        val c = toggleRadBtn.getSelectedToggle.asInstanceOf[javafx.scene.control.RadioButton].getText
        selectedAnswers.addOne( Answer(c(0).toString, c) )

        if(selectedAnswers(qIndex).choice.equals(listAnswers(qIndex).choice)){
          answerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: green")
          answerLabel.setText(s"Correct! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}" )

        }else{
          answerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red")
          answerLabel.setText(s"Inorrect! Answer ${listAnswers(qIndex).choice}: ${listAnswers(qIndex).text}" )
        }
      }

      answerLabel.setVisible(true)
      next.setVisible(true)
    })

    next.setOnAction((e)=>{
      qIndex += 1
      label.setText(listQuestions(qIndex).text)
      if( listQuestions(qIndex).text.toLowerCase().contains("select two.") ){
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
      if(listQuestions(qIndex).text.toLowerCase().contains("select two.")){
        vBox1.children = Seq(
          label,
          checkBoxList(0),
          checkBoxList(1),
          checkBoxList(2),
          checkBoxList(3),
          submit,
          answerLabel,
          next,
          new VBox {
            padding = Insets(20)
            spacing = 10
            alignment = Pos.BottomRight
            children = Seq( designerLabel )
          })
      }else{
        vBox1.children = Seq(
          label,
          radioBtnList(0),
          radioBtnList(1),
          radioBtnList(2),
          radioBtnList(3),
          submit,
          answerLabel,
          next,
          new VBox {
            padding = Insets(20)
            spacing = 10
            alignment = Pos.BottomRight
            children = Seq( designerLabel )
          })
      }
      currentScene.setRoot(vBox1)
    })

    currentScene = new Scene(800,600)

    currentScene.setRoot(vBox1)

    this
  }
}
