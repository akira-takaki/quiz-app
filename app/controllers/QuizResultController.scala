package controllers

import anorm._
import play.api.i18n.Messages.Implicits._
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._
import play.api.data._

class QuizResultController extends Controller {
  import QuizResultForm._

  /**
    * ⭕登録用フォームの表示
    *
    * @param quizId
    */
  def showQuizResultMaruForm(quizId: Int) = Action { implicit request: Request[AnyContent] =>
    DB.withConnection { implicit c =>
      val postUrl = routes.QuizResultController.entryQuizResultMaru(quizId)

      val question = SQL("SELECT question FROM Quiz WHERE id = {id};")
        .on('id -> quizId).as(SqlParser.str("question").single)

      Ok(views.html.entryQuizResultMaru(quizId, question, form, postUrl))
    }
  }

  /**
    * ❌登録用フォームの表示
    *
    * @param quizId
    */
  def showQuizResultBatsuForm(quizId: Int) = Action { implicit request: Request[AnyContent] =>
    DB.withConnection { implicit c =>
      val postUrl = routes.QuizResultController.entryQuizResultBatsu(quizId)

      val question = SQL("SELECT question FROM Quiz WHERE id = {id};")
        .on('id -> quizId).as(SqlParser.str("question").single)

      Ok(views.html.entryQuizResultBatsu(quizId, question, form, postUrl))
    }
  }

  /**
    * ⭕登録処理
    *
    * @param quizId
    */
  def entryQuizResultMaru(quizId: Int) = Action { implicit request: Request[AnyContent] =>
    DB.withConnection { implicit c =>
      val postUrl = routes.QuizResultController.entryQuizResultMaru(quizId)

      val question = SQL("SELECT question FROM Quiz WHERE id = {id};")
        .on('id -> quizId).as(SqlParser.str("question").single)

      val errorFunction = { formWithErrors: Form[Data] =>
        BadRequest(views.html.entryQuizResultMaru(quizId, question, formWithErrors, postUrl))
      }

      val successFunction = { data: Data =>
        val answer = SQL("SELECT answer FROM Quiz WHERE id = {id};")
          .on('id -> quizId).as(SqlParser.str("answer").single)

        val point = if (answer.equals(data.answer)) { 1 } else { 0 }

        // TODO 二重登録を考慮して、すでに存在している場合は UPDATE にする
        val id: Option[Long] = SQL("INSERT INTO Quiz_Result VALUE({team_id}, {quiz_id}, {answer}, {point});")
          .on('team_id -> data.teamId, 'quiz_id -> data.quizId, 'answer -> data.answer, 'point -> point)
          .executeInsert()

        Redirect(routes.QuizResultController.entryQuizResultMaru(quizId)).flashing("info" -> "OK")
      }

      val formValidationResult = form.bindFromRequest
      formValidationResult.fold(errorFunction, successFunction)
    }
  }

  /**
    * ❌登録処理
    *
    * @param quizId
    */
  def entryQuizResultBatsu(quizId: Int) = Action { implicit request: Request[AnyContent] =>
    DB.withConnection { implicit c =>
      val postUrl = routes.QuizResultController.entryQuizResultBatsu(quizId)

      val question = SQL("SELECT question FROM Quiz WHERE id = {id};")
        .on('id -> quizId).as(SqlParser.str("question").single)

      val errorFunction = { formWithErrors: Form[Data] =>
        BadRequest(views.html.entryQuizResultBatsu(quizId, question, formWithErrors, postUrl))
      }

      val successFunction = { data: Data =>
        val answer = SQL("SELECT answer FROM Quiz WHERE id = {id};")
          .on('id -> quizId).as(SqlParser.str("answer").single)

        val point = if (answer.equals(data.answer)) { 1 } else { 0 }

        // TODO 二重登録を考慮して、すでに存在している場合は UPDATE にする
        val id: Option[Long] = SQL("INSERT INTO Quiz_Result VALUE({team_id}, {quiz_id}, {answer}, {point});")
          .on('team_id -> data.teamId, 'quiz_id -> data.quizId, 'answer -> data.answer, 'point -> point)
          .executeInsert()

        Redirect(routes.QuizResultController.entryQuizResultBatsu(quizId)).flashing("info" -> "OK")
      }

      val formValidationResult = form.bindFromRequest
      formValidationResult.fold(errorFunction, successFunction)
    }
  }

  def checkQuizResultEntry(quizId: Int) = Action {
    DB.withConnection { implicit c =>
      val question = SQL("SELECT question FROM Quiz WHERE id = {id};")
        .on('id -> quizId).as(SqlParser.str("question").single)

      SQL(
        """
          |SELECT
          |  Team.id AS team_id,
          |  Team.name AS team_name
          |FROM Team
          |LEFT OUTER JOIN Quiz_Result ON Quiz_Result.team_id = Team.id AND Quiz_Result.quiz_id = {quiz_id}
          |WHERE Quiz_Result.quiz_id IS NULL
          |ORDER BY team_name;
        """.stripMargin)
          .on('quiz_id -> quizId)
          .withResult(go(_, List.empty[(Int, String)])) match {
        case Left(throwables) => Ok(throwables.toString())
        case Right(list) => Ok(views.html.checkQuizResultEntry(question, list))
      }
    }
  }

  @annotation.tailrec
  private def go(maybeCursor: Option[Cursor], list: List[(Int, String)]): List[(Int, String)] = maybeCursor match {
    case Some(cursor) => {
      if (list.size == 100) {
        list
      } else {
        go(cursor.next, list:+ (cursor.row[Int]("team_id"), cursor.row[String]("team_name")))
      }
    }
    case _ => list
  }

}