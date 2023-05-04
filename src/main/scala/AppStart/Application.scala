package AppStart

import Builder.ViewBuilder
import Handlers.SceneHandler
import Models.Properties
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
    courseItems.addOne(Properties.defaultCourse)

    val directoryList = Files.list(
      FileSystems.getDefault.getPath(Paths.get("").toAbsolutePath+Properties.defaultPath)
    ).toArray

    directoryList.foreach(x => courseItems.addOne(x.toString.substring(x.toString.lastIndexOf("\\")+1)) )

    stage =  new PrimaryStage {title = Properties.programTitle}

    val cbx = ViewBuilder.createChoiceBox[String](200,50, Pos.Center, courseItems)

    stage.scene = SceneHandler.createInitialScene(cbx).currentScene

    cbx.setOnAction((e:ActionEvent)=>{
      stage.scene = SceneHandler.build(
        directoryList.find( x => x.toString.contains(cbx.getSelectionModel.getSelectedItem) ).get.toString
      ).ChangeScene().currentScene
    })

  }




}
