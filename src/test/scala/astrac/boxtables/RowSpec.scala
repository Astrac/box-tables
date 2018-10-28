package astrac.boxtables

import astrac.boxtables.string._
import astrac.boxtables.string.instances._
import cats.kernel.Eq
import cats.tests.CatsSuite
import cats.laws.discipline.ContravariantMonoidalTests
import org.scalacheck.{Arbitrary, Gen}

class RowSpec extends CatsSuite {
  val equalitySamples = 100

  implicit def arbitraryRow[A: Row]: Arbitrary[Row[A]] =
    Arbitrary(Gen.const(Row[A]))

  implicit def eqRow[A: Arbitrary]: Eq[Row[A]] = Eq.instance { (a, b) =>
    Iterator
      .continually(Arbitrary.arbitrary[A].sample)
      .collect {
        case Some(v) => v
      }
      .take(equalitySamples)
      .forall(e => a.toRow(e) == b.toRow(e))
  }

  checkAll("Row.ControvariantMonoidalLaws",
           ContravariantMonoidalTests[Row]
             .contravariantMonoidal[Int, String, Boolean])
}
