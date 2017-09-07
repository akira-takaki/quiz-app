package daos

import anorm._

trait QuizDao {

  def question(quizId: Int)(implicit c: java.sql.Connection): String = {
    SQL("SELECT question FROM Quiz WHERE id = {id};")
      .on('id -> quizId).as(SqlParser.str("question").single)
  }

  def answer(quizId: Int)(implicit c: java.sql.Connection): String = {
    SQL("SELECT answer FROM Quiz WHERE id = {id};")
      .on('id -> quizId).as(SqlParser.str("answer").single)
  }

}
