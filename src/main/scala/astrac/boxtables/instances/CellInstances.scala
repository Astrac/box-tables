package astrac.boxtables.instances

import astrac.boxtables.Cell
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

trait Primitives {
  implicit val bigDecimalCell: Cell[BigDecimal] = Cell.instance(_.toString)
  implicit val booleanCell: Cell[Boolean] = Cell.instance(_.toString)
  implicit val charCell: Cell[Char] = Cell.instance(_.toString)
  implicit val doubleCell: Cell[Double] = Cell.instance(_.toString)
  implicit val floatCell: Cell[Float] = Cell.instance(_.toString)
  implicit val intCell: Cell[Int] = Cell.instance(_.toString)
  implicit val longCell: Cell[Long] = Cell.instance(_.toString)
  implicit val stringCell: Cell[String] = Cell.instance(identity)
}

trait Containers {
  implicit def optionCell[A: Cell]: Cell[Option[A]] =
    Cell.instance(_.fold("")(Cell[A].content))
}

trait Temporal {
  private val defaultFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

  implicit def temporalCell[T <: TemporalAccessor]: Cell[T] =
    Temporal.instance[T](defaultFormatter)
}

object Temporal {
  def instance[T <: TemporalAccessor](f: DateTimeFormatter): Cell[T] =
    Cell.instance(f.format)
}

trait All extends Primitives with Containers with Temporal
