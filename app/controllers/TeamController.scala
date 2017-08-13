package controllers

import anorm._
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

class TeamController extends Controller {

  def teamRanking(quizCategoryId: Int) = Action {
    DB.withConnection { implicit c =>
      val quizCategoryName = SQL("SELECT name FROM Quiz_Category WHERE id = {id};")
        .on('id -> quizCategoryId).as(SqlParser.str("name").single)

      SQL(
        """
          |SELECT
          |  Team.name AS team_name,
          |  SUM(Quiz_Result.point) AS quiz_category_point
          |FROM Team
          |LEFT OUTER JOIN Quiz ON Quiz.quiz_category_id = {quiz_category_id}
          |LEFT OUTER JOIN Quiz_Result ON Quiz_Result.team_id = Team.id AND Quiz_Result.quiz_id = Quiz.id
          |GROUP BY team_name
          |ORDER BY quiz_category_point DESC;
        """.stripMargin)
          .on('quiz_category_id -> quizCategoryId)
          .withResult(go(_, List.empty[(String, Int)])) match {
        case Left(throwables) => Ok(throwables.toString())
        case Right(list) => Ok(views.html.teamRankingOfQuizCategory(quizCategoryName, list))
      }
    }
  }

  @annotation.tailrec
  private def go(maybeCursor: Option[Cursor], list: List[(String, Int)]): List[(String, Int)] = maybeCursor match {
    case Some(cursor) => {
      if (list.size == 100) {
        list
      } else {
        go(cursor.next, list:+ (
          cursor.row[String]("team_name"),
          cursor.row[Option[Int]]("quiz_category_point").getOrElse(0)
        ))
      }
    }
    case _ => list
  }

}