package controllers

import play.api.Play.current
import play.api._
import anorm._
import play.api.db.DB
import play.api.mvc._

class ElementarySchoolController extends Controller {

  def list = Action {
    DB.withConnection { implicit c =>
      SQL("SELECT id, name FROM Elementary_School;").withResult(go(_, List.empty[(Int, String)])) match {
        case Left(throwables) => Ok(throwables.toString())
        case Right(list) => Ok(views.html.elementarySchools(list))
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