package astrac.boxtables

case class Spacing(t: Int = 0, r: Int = 0, b: Int = 0, l: Int = 0)

object Spacing {
  lazy val zero = Spacing()
  def all(v: Int) = Spacing(v, v, v, v)
  def hv(h: Int, v: Int) = Spacing(v, h, v, h)
}

case class Sides[T](t: T, r: T, b: T, l: T)

object Sides {
  def all[T](v: T) = Sides(v, v, v, v)
  def hv[T](h: T, v: T) = Sides(v, h, v, h)
}

case class Intersections[T](t: T, r: T, b: T, l: T, c: T)

object Intersections {
  def all[T](v: T) = Intersections(v, v, v, v, v)
  def hvc[T](h: T, v: T, c: T) = Intersections(v, h, v, h, c)
}

case class Corners[T](tl: T, tr: T, br: T, bl: T)

object Corners {
  def all[T](v: T) = Corners(v, v, v, v)
}

case class Dividers[T](v: T, h: T)

case class Padding[T](
    space: Spacing,
    fill: Sides[T]
)

case class Margins[T](
    space: Spacing,
    fill: Sides[T]
)

case class Theme[T](
    borders: Sides[T],
    corners: Corners[T],
    dividers: Dividers[T],
    padding: Padding[T],
    margins: Margins[T],
    intersections: Intersections[T]
)
