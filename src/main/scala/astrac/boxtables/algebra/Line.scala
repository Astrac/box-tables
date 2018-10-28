package astrac.boxtables
package algebra

import cats.kernel.Monoid
import cats.instances.list._
import cats.syntax.apply._
import cats.syntax.monoid._
import cats.syntax.foldable._
import cats.syntax.traverse._
import Sizing._

trait Line[Primitive, Model] {
  implicit val PrimitiveSupport: PrimitiveSupport[Primitive]
  implicit def Primitive: Monoid[Primitive]
  implicit def Model: Row[Primitive, Model]

  import PrimitiveSupport._

  val sizing: Rows[Primitive, Sizing] = Rows.sizing
  val theme: Rows[Primitive, Theme[Primitive]] = Rows.theme

  def transpose(
      ls: List[List[Primitive]]): Rows[Primitive, List[List[Primitive]]] = {
    val w = ls.map(_.size).max
    ls.zipWithIndex
      .traverse {
        case (l, i) =>
          cellWidth(i).map(cw => l.padTo(w, Primitive.combineN(space, cw)))
      }
      .map(_.transpose)
  }

  def boundedSpace(w: Int): Rows[Primitive, Int] = theme.map { t =>
    math.max(0,
             w - 2 -
               (Model.columns - 1) -
               (t.padding.space.l * Model.columns) -
               (t.padding.space.r * Model.columns) -
               t.margins.space.l -
               t.margins.space.r)
  }

  val cellSpace: Rows[Primitive, Int] = sizing.flatMap {
    case Equal(t)       => boundedSpace(t)
    case Fixed(cs)      => Rows.pure(cs.sum)
    case Weighted(t, _) => boundedSpace(t)
  }

  def cellWidth(idx: Int): Rows[Primitive, Int] =
    (sizing, cellSpace).mapN[Int] { (sizing, cellSpace) =>
      val base = sizing match {
        case Equal(_)            => cellSpace / Model.columns
        case Fixed(cs)           => cs(idx)
        case w @ Weighted(_, ws) => cellSpace / w.sum * ws(idx)
      }

      val rounding = sizing match {
        case Equal(_) if idx < cellSpace % Model.columns   => 1
        case w @ Weighted(_, _) if idx < cellSpace % w.sum => 1
        case _                                             => 0
      }

      base + rounding
    }

  def cell(paddingL: Primitive, paddingR: Primitive, rowsDivider: Primitive)(
      content: Primitive,
      index: Int): Rows[Primitive, Primitive] =
    theme.map { t =>
      val body = Primitive.combineN(paddingL, t.padding.space.l) |+| content
      val pr = Primitive.combineN(paddingR, t.padding.space.r)

      if (index == Model.columns - 1) body |+| pr
      else body |+| pr |+| rowsDivider
    }

  def line(marginL: Primitive,
           borderL: Primitive,
           paddingL: Primitive,
           cells: List[Primitive],
           rowsDivider: Primitive,
           paddingR: Primitive,
           borderR: Primitive,
           marginR: Primitive): Rows[Primitive, Primitive] = theme.flatMap {
    t =>
      cells.zipWithIndex
        .foldMap((cell(paddingL, paddingR, rowsDivider) _).tupled)
        .map(
          body =>
            Primitive.combineN(marginL, t.margins.space.l) |+|
              borderL |+|
              body |+|
              borderR |+|
              Primitive.combineN(marginR, t.margins.space.r))
  }

  def fillCells(f: Primitive): Rows[Primitive, List[Primitive]] =
    (0 until Model.columns).toList
      .traverse(idx => cellWidth(idx).map(cw => Primitive.combineN(f, cw)))

  def marginLine(f: Primitive): Rows[Primitive, Primitive] =
    fillCells(f).flatMap(line(f, f, f, _, f, f, f, f))

  def borderLine(border: Primitive,
                 intersectL: Primitive,
                 intersectM: Primitive,
                 intersectR: Primitive): Rows[Primitive, Primitive] =
    (theme, fillCells(border))
      .mapN { (t, cs) =>
        line(t.margins.fill.l,
             intersectL,
             border,
             cs,
             intersectM,
             border,
             intersectR,
             t.margins.fill.r)
      }
      .flatMap(identity)

  def contentLine(contents: List[Primitive]): Rows[Primitive, Primitive] =
    theme.flatMap { t =>
      line(t.margins.fill.l,
           t.borders.l,
           t.padding.fill.l,
           contents,
           t.dividers.v.getOrElse(space),
           t.padding.fill.r,
           t.borders.r,
           t.margins.fill.r)
    }

  def paddingLine(p: Primitive): Rows[Primitive, Primitive] =
    for {
      t <- theme
      cs <- fillCells(p)
      l <- line(t.margins.fill.l,
                t.borders.l,
                p,
                cs,
                t.dividers.v.getOrElse(space),
                p,
                t.borders.r,
                t.margins.fill.r)
    } yield l
}
