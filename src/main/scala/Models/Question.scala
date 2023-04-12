package Models

case class Question(number:Int, text:String, options: List[Options], selected: Char = ' ' )
