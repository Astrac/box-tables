package astrac.boxtables

import astrac.boxtables.string.{Cell => SCell, Row => SRow}
import astrac.boxtables.string.instances._
import cats.kernel.Eq
import cats.tests.CatsSuite
import cats.laws.discipline.ContravariantMonoidalTests
import org.scalacheck.{Arbitrary, Gen}

class RowSpec extends CatsSuite {
  val equalitySamples = 100

  implicit def arbitraryRowFromCell[A: SCell]: Arbitrary[SRow[A]] =
    Arbitrary(Gen.const(SRow.cell[A]))

  implicit def eqRow[A: Arbitrary]: Eq[SRow[A]] = Eq.instance { (a, b) =>
    Iterator
      .continually(Arbitrary.arbitrary[A].sample)
      .collect {
        case Some(v) => v
      }
      .take(equalitySamples)
      .forall(e => a.toRow(e) == b.toRow(e))
  }

  checkAll("SRow.ControvariantMonoidalLaws",
           ContravariantMonoidalTests[SRow]
             .contravariantMonoidal[Int, String, Boolean])
}
