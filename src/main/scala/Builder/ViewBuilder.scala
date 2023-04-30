package Builder

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.scene.control.{Button, CheckBox, ChoiceBox, Label, RadioButton, ToggleGroup}
import scalafx.scene.layout.VBox

import scala.Double.NaN

object ViewBuilder {

  def createLabel( text: String,
                   vis: Boolean = true,
                   style: String = "",
                   mWidth: Double = NaN,
                   mHeight: Double = NaN,
                 ): Label={
    val label = Label(text)
    if(!style.isEmpty) label.setStyle(style)
    if(!mWidth.isNaN) label.setMaxWidth(mWidth)
    if(!mHeight.isNaN) label.setMaxHeight(mHeight)
    label.setVisible(vis)
    label
  }

  def createVBox( space: Double,
                  pad: Double,
                  pos:Pos,
                  children: Seq[Node] = null
  ): VBox={
    val vBoxResult = new VBox{
      spacing = space
      padding = Insets(pad)
      alignment = pos
    }
    if(children.nonEmpty) vBoxResult.children = children
    vBoxResult
  }

  def createButton(txt: String, pos: Pos, vis: Boolean = true): Button={
    val buttonResult = new Button{
      text = txt
      alignment = pos
      visible = vis
    }
    buttonResult
  }

  def createCheckBox(txt: String, mWidth: Double, mHeight: Double, select: Boolean = false): CheckBox={
    val checkBoxResult = new CheckBox{
      text = txt
      maxWidth = mWidth
      maxHeight = mHeight
      selected = select
    }
    checkBoxResult
  }

  def createRadioButton(txt: String, mWidth: Double, mHeight: Double, tog: ToggleGroup, select: Boolean = false): RadioButton={
    val radioButtonResult = new RadioButton{
      text = txt
      maxWidth = mWidth
      maxHeight = mHeight
      toggleGroup = tog
      selected = select
    }
    radioButtonResult
  }

  def createChoiceBox[T](mWidth: Double, mHeight: Double, pos: Pos, itemOptions: ObservableBuffer[T] ): ChoiceBox[T]={
    new ChoiceBox[T]{
      maxWidth = mWidth
      maxHeight = mHeight
      alignmentInParent = pos
      items = itemOptions
      selectionModel().selectFirst()
    }
  }
}
