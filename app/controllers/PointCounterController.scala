package controllers

import anorm._
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._
import play.api.data._

/**
  * ポイントカウンター コントローラー
  */
class PointCounterController extends Controller {
  import TeamForm._

  def showSelectTeamForm = Action {
    DB.withConnection { implicit c =>
      val postUrl = routes.PointCounterController.showPointCounter()

      SQL("SELECT Team.id AS team_id, Team.name AS team_name FROM Team ORDER BY team_id;")
        .withResult(go(_, List.empty[(Int, String)])) match {
        case Left(throwables) => Ok(throwables.toString())
        case Right(teams) => Ok(views.html.selectTeam(teams, postUrl))
      }
    }
  }

  def showPointCounter = Action { implicit request: Request[AnyContent] =>
    DB.withConnection { implicit c =>
      val errorFunction = { formWithErrors: Form[Data] =>
        BadRequest(views.html.pointCounter(List.empty[(Int, String)]))
      }

      val successFunction = { data: Data =>
        SQL("SELECT Team.id AS team_id, Team.name AS team_name FROM Team ORDER BY team_id;")
          .withResult(go(_, List.empty[(Int, String)])) match {
          case Left(throwables) => Ok(throwables.toString())
          case Right(teams) => {
            val selectedTeam = teams.filter { team =>
              data.teamId.contains(team._1)
            }
            Ok(views.html.pointCounter(selectedTeam))
          }
        }
      }

      val formValidationResult = form.bindFromRequest
      formValidationResult.fold(errorFunction, successFunction)
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
