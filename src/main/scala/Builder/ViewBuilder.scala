package Builder

import Builder.QABuilder.listAnswers
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}
import scalafx.scene.control._
import scalafx.scene.layout.{Region, VBox}

import scala.Double.NaN
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

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

  def createCheckBoxList(refObject: ListBuffer[CheckBox], qIndex:Int, toggleRadBtn:ToggleGroup = null): Unit = {
    QABuilder.listQuestions(qIndex).options.foreach(o=> {
      refObject.addOne(this.createCheckBox(o.text, 800, 200))
    })
  }

  def createRadioBoxList(refObject: ListBuffer[RadioButton], qIndex:Int, toggleRadBtn:ToggleGroup = null): Unit = {
    QABuilder.listQuestions(qIndex).options.foreach(o=> {
      refObject.addOne(this.createRadioButton(o.text, 800, 200, toggleRadBtn))
    })
  }

  def createBarChartResult(bGap: Double, cGap: Double, style: String, titleTxt: String, score: Double): BarChart[Number, String]={
    val label = s"${score} %"

    val yAxis = new CategoryAxis {
      label = "Score"
    }

    val xAxis = new NumberAxis {
      label = "Total"
      tickLabelFormatter = NumberAxis.DefaultFormatter(this, "%", "")
    }

    val series = new XYChart.Series[Number, String] {
      name = s"User Score Result ${score}"
      data() += XYChart.Data[Number, String](score, label)
    }

    val series2 = new XYChart.Series[Number, String] {
      name = s"Max Score ${100}"
      data() += XYChart.Data[Number, String](100, label)
    }

    def xyData(xs: Seq[Number]) = ObservableBuffer(xs zip label map (xy => XYChart.Data(xy._1, xy._2)))

    new BarChart(xAxis, yAxis) {
      barGap = bGap
      categoryGap = cGap
      title = titleTxt+s" - ${score}%"
      data() ++= Seq(series, series2)
    }
  }

  def buildChildrenSequence(qIndex:Int, sectionCbx:ChoiceBox[String], label:Label, checkBoxList:ListBuffer[CheckBox], radioBtnList: ListBuffer[RadioButton], submit:Button, answerLabel:Label, next:Button, footerVBox:VBox): Seq[Region]={
    var childrenSeq: Seq[Region] = null
    val buffer: mutable.Buffer[Region] = mutable.Buffer()
    buffer.addOne(sectionCbx)
    buffer.addOne(label)
    if(listAnswers(qIndex).choice.length > 1) checkBoxList.foreach(c=>{ buffer.addOne(c)})
    else radioBtnList.foreach(r=>{ buffer.addOne(r)})
    buffer.addOne(submit)
    buffer.addOne(answerLabel)
    buffer.addOne(next)
    buffer.addOne(footerVBox)
    childrenSeq = buffer.toSeq

    childrenSeq
  }
}
