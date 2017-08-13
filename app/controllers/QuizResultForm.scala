package controllers

object QuizResultForm {
  import play.api.data.Forms._
  import play.api.data.Form

  case class Data(teamId: Int, quizId: Int, answer: String)

  val form = Form(
    mapping(
      "teamId" -> number,
      "quizId" -> number,
      "answer" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

}
