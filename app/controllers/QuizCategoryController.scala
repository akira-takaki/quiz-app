package controllers

import anorm._
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

class QuizCategoryController extends Controller {

  def list = Action {
    DB.withConnection { implicit c =>
      SQL("SELECT id, name FROM Quiz_Category ORDER BY seq;").withResult(go(_, List.empty[(Int, String)])) match {
        case Left(throwables) => Ok(throwables.toString())
        case Right(list) => Ok(views.html.quizCategories(list))
      }
    }
  }

  @annotation.tailrec
  private def go(maybeCursor: Option[Cursor], list: List[(Int, String)]): List[(Int, String)] = maybeCursor match {
    case Some(cursor) => {
      if (list.size == 100) {
        list
      } else {
        go(cursor.next, list:+ (cursor.row[Int]("id"), cursor.row[String]("name")))
      }
    }
    case _ => list
  }

}