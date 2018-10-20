package astrac.boxtables

import cats.Foldable
import cats.kernel.Monoid
import cats.instances.list._
import cats.syntax.monoid._
import cats.syntax.foldable._
import cats.syntax.list._
import Sizing._

class TableAlgebra[A, T](config: Theme[T], sizing: Sizing)(
    implicit T: Monoid[T],
    F: Formatter[T],
    R: Row[A]) {

  import config._

  private def transpose(ls: List[List[T]]): List[List[T]] = {
    val w = ls.map(_.size).max
    ls.zipWithIndex.map {
      case (l, i) =>
        l.padTo(w, T.combineN(F.space, cellWidth(i)))
    }.transpose
  }

  private def boundedSpace(w: Int) =
    math.max(0,
             w - 2 -
               (R.size - 1) -
               (padding.space.l * R.size) -
               (padding.space.r * R.size) -
               margins.space.l -
               margins.space.r)

  private val cellSpace = sizing match {
    case Equal(t)       => boundedSpace(t)
    case Fixed(cs)      => cs.sum
    case Weighted(t, _) => boundedSpace(t)
  }

  private def cellWidth(idx: Int): Int = {
    val base = sizing match {
      case Equal(_)            => cellSpace / R.size
      case Fixed(cs)           => cs(idx)
      case w @ Weighted(_, ws) => cellSpace / w.sum * ws(idx)
    }

    val rounding = sizing match {
      case Equal(_) if idx < cellSpace % R.size          => 1
      case w @ Weighted(_, _) if idx < cellSpace % w.sum => 1
      case _                                             => 0
    }

    base + rounding
  }

  private def cell(paddingL: T, paddingR: T, divider: T)(content: T,
                                                         index: Int): T = {
    val body = T.combineN(paddingL, padding.space.l) |+| content
    val pr = T.combineN(paddingR, padding.space.r)

    if (index == R.size - 1) body |+| pr
    else body |+| pr |+| divider
  }

  private def line(marginL: T,
                   borderL: T,
                   paddingL: T,
                   cells: List[T],
                   divider: T,
                   paddingR: T,
                   borderR: T,
                   marginR: T): T = {
    val body =
      cells.zipWithIndex.foldMap((cell(paddingL, paddingR, divider) _).tupled)

    T.combineN(marginL, margins.space.l) |+|
      borderL |+|
      body |+|
      borderR |+|
      T.combineN(marginR, margins.space.r)
  }

  private def fillCells(f: T): List[T] = {
    (0 until R.size)
      .map(idx => T.combineN(f, cellWidth(idx)))
      .toList
  }

  private def marginLine(f: T): T =
    line(f, f, f, fillCells(f), f, f, f, f)

  private def borderLine(border: T,
                         intersectL: T,
                         intersectM: T,
                         intersectR: T): T =
    line(margins.fill.l,
         intersectL,
         border,
         fillCells(border),
         intersectM,
         border,
         intersectR,
         margins.fill.r)

  private def contentLine(contents: List[T]) =
    line(margins.fill.l,
         borders.l,
         padding.fill.l,
         contents,
         dividers.v,
         padding.fill.r,
         borders.r,
         margins.fill.r)

  private def paddingLine(p: T): T =
    line(margins.fill.l,
         borders.l,
         p,
         fillCells(p),
         dividers.v,
         p,
         borders.r,
         margins.fill.r)

  val topMargin: List[T] =
    List.fill(margins.space.t)(marginLine(margins.fill.t))

  val topBorder: T =
    borderLine(borders.t, corners.tl, intersections.t, corners.tr)

  val divider: T =
    borderLine(dividers.h, intersections.l, intersections.c, intersections.r)

  val bottomBorder: T =
    borderLine(borders.b, corners.bl, intersections.b, corners.br)

  val bottomMargin: List[T] =
    List.fill(margins.space.b)(marginLine(margins.fill.b))

  val paddingTop: List[T] =
    List.fill(padding.space.t)(paddingLine(padding.fill.t))

  val paddingBottom = List.fill(padding.space.b)(paddingLine(padding.fill.b))

  def row(a: A): List[T] = {
    val contents = R.toRow(a)

    val lifted = transpose(contents.zipWithIndex.map {
      case (c, i) => F(cellWidth(i))(c)
    })

    paddingTop |+| lifted.map(contentLine) |+| paddingBottom
  }

  def table[F[_]: Foldable](as: F[A]): List[T] = {
    val body = as
      .foldMap(a => row(a) :+ divider)
      .toNel
      .fold(List.empty[T])(_.init)

    topMargin |+|
      List(topBorder) |+|
      body |+|
      List(bottomBorder) |+|
      bottomMargin
  }
}
