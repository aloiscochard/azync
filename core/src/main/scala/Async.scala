package matterhorn

import scala.concurrent.Future

import Concurrent._
import IO._
import RTS._

case class Async[A](threadId: ThreadId) extends AnyVal

object Async {
  def async[A](io: IO[A]): IO[Async[A]] = forkIO(io).map(Async(_))
  def await[A](async: Async[A]): IO[A] = IO[A](Exp.Wait(async.threadId) :: Nil)
  def cancel[A](ac: Async[A]): IO[Unit] = killThread(ac.threadId)
}
