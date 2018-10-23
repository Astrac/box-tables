package astrac.boxtables

import astrac.boxtables.instances.all._
import cats.kernel.Eq
import cats.tests.CatsSuite
import cats.laws.discipline.ContravariantMonoidalTests
import org.scalacheck.{Arbitrary, Gen}

class RowSpec extends CatsSuite {
  val equalitySamples = 100

  implicit def arbitraryRowFromCell[A: Cell]: Arbitrary[Row[A]] =
    Arbitrary(Gen.const(Row.cell[A]))

  implicit def eqRow[A: Arbitrary]: Eq[Row[A]] = Eq.instance { (a, b) =>
    Iterator
      .continually(Arbitrary.arbitrary[A].sample)
      .collect {
        case Some(v) => v
      }
      .take(equalitySamples)
      .forall(e => a.toRow(e) == b.toRow(e))
  }

  checkAll(
    "Row.ControvariantMonoidalLaws",
    ContravariantMonoidalTests[Row].contravariantMonoidal[Int, String, Boolean])
}
