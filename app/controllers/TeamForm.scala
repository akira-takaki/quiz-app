package controllers

object TeamForm {
  import play.api.data.Forms._
  import play.api.data.Form

  case class Data(teamId: List[Int])

  val form = Form(
    mapping(
      "teamId" -> list(number)
    )(Data.apply)(Data.unapply)
  )

}
