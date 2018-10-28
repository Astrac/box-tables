package astrac.boxtables

import astrac.boxtables.string._
import astrac.boxtables.string.instances._
import cats.kernel.Eq
import cats.tests.CatsSuite
import cats.laws.discipline.ContravariantTests
import org.scalacheck.{Arbitrary, Gen}

class CellSpec extends CatsSuite {
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

  checkAll("Cell.ControvariantLaws",
           ContravariantTests[Cell].contravariant[Int, String, Boolean])
}
