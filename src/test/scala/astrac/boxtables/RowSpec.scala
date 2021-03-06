package astrac.boxtables

import astrac.boxtables.string._
import astrac.boxtables.string.instances._
import cats.kernel.Eq
import cats.laws.discipline.ContravariantMonoidalTests
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSpecDiscipline

class RowSpec extends AnyFunSpec with Configuration with FunSpecDiscipline {
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
      .forall(
        e =>
          a.cells.map(_.content(e)) == b.cells.map(_.content(e)) &&
            a.cells.map(_.formatter) == b.cells.map(_.formatter)
      )
  }

  checkAll(
    "Row.ControvariantMonoidalLaws",
    ContravariantMonoidalTests[Row]
      .contravariantMonoidal[Int, String, Boolean]
  )
}
