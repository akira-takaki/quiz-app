package controllers

import anorm._
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

class QuizController extends Controller {

  def list(categoryId: Int) = Action {
    DB.withConnection { implicit c =>
      val quizCategoryName = SQL("SELECT name FROM Quiz_Category WHERE id = {id};")
          .on('id -> categoryId).as(SqlParser.str("name").single)

      SQL("SELECT id, question FROM Quiz WHERE quiz_category_id = {quiz_category_id} ORDER BY seq;")
          .on('quiz_category_id -> categoryId)
          .withResult(go(_, List.empty[(Int, String)])) match {
        case Left(throwables) => Ok(throwables.toString())
        case Right(list) => Ok(views.html.quizzes(categoryId, quizCategoryName, list))
      }
    }
  }

  @annotation.tailrec
  private def go(maybeCursor: Option[Cursor], list: List[(Int, String)]): List[(Int, String)] = maybeCursor match {
    case Some(cursor) => {
      if (list.size == 100) {
        list
      } else {
        go(cursor.next, list:+ (cursor.row[Int]("id"), cursor.row[String]("question")))
      }
    }
    case _ => list
  }

}