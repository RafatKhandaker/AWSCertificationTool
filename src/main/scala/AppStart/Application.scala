package AppStart

import Builder.QABuilder
import Handlers.SceneHandler
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control._

import java.nio.file.{FileSystems, Files, Paths}


object Application extends JFXApp3{

  override def start(): Unit = {
    val courseItems : ObservableBuffer[String] = ObservableBuffer()
    courseItems.addOne("Select Course")

    val directoryList = Files.list(
      FileSystems.getDefault.getPath(Paths.get("").toAbsolutePath+"/project/input")
    ).toArray

    directoryList.foreach(x => courseItems.addOne(x.toString.substring(x.toString.lastIndexOf("\\")+1)) )

    stage =  new PrimaryStage {title = "AWS Certification Tool"}

    val cbx = new ChoiceBox[String] {
      maxWidth = 200
      maxHeight = 50
      alignmentInParent = Pos.Center
      items = courseItems
      selectionModel().selectFirst()
    }
    stage.scene = SceneHandler.createInitialScene(cbx).currentScene

    cbx.setOnAction((e:ActionEvent)=>{
      val dir = directoryList.find( x => x.toString.contains(cbx.getSelectionModel.getSelectedItem) ).get.toString

      stage.scene = SceneHandler.addQuestionsList(QABuilder.buildQuestionaire(dir).listQuestions.toList)
                                    .addAnswersList(QABuilder.buildAnswerList(dir).listAnswers.toList)
                                        .Next().currentScene;
    })

  }




}
