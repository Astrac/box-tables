package astrac.boxtables

import astrac.boxtables.string._
import astrac.boxtables.string.instances._
import cats.kernel.Eq
import cats.laws.discipline.ContravariantTests
import org.scalacheck.{Arbitrary, Gen}
import org.typelevel.discipline.scalatest.FunSpecDiscipline
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.prop.Configuration

class CellSpec extends AnyFunSpec with Configuration with FunSpecDiscipline {
  val equalitySamples = 100

  implicit def arbitraryCell[A: Cell]: Arbitrary[Cell[A]] =
    Arbitrary(Gen.const(Cell[A]))

  implicit def eqCell[A: Arbitrary]: Eq[Cell[A]] = Eq.instance { (a, b) =>
    Iterator
      .continually(Arbitrary.arbitrary[A].sample)
      .collect {
        case Some(v) => v
      }
      .take(equalitySamples)
      .forall(e => a.content(e) == b.content(e))
  }

  checkAll(
    "Cell.ControvariantLaws",
    ContravariantTests[Cell].contravariant[Int, String, Boolean]
  )
}
