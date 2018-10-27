package astrac.boxtables

case class Spacing(t: Int = 0, r: Int = 0, b: Int = 0, l: Int = 0)

object Spacing {
  lazy val zero = Spacing()
  def all(v: Int) = Spacing(v, v, v, v)
  def hv(h: Int, v: Int) = Spacing(v, h, v, h)
}

case class Sides[Primitive](t: Primitive,
                            r: Primitive,
                            b: Primitive,
                            l: Primitive)

object Sides {
  def all[Primitive](v: Primitive) = Sides(v, v, v, v)
  def hv[Primitive](h: Primitive, v: Primitive) = Sides(v, h, v, h)
}

case class Intersections[Primitive](t: Primitive,
                                    r: Primitive,
                                    b: Primitive,
                                    l: Primitive,
                                    c: Primitive)

object Intersections {
  def all[Primitive](v: Primitive) = Intersections(v, v, v, v, v)
  def hvc[Primitive](h: Primitive, v: Primitive, c: Primitive) =
    Intersections(v, h, v, h, c)
}

case class Corners[Primitive](tl: Primitive,
                              tr: Primitive,
                              br: Primitive,
                              bl: Primitive)

object Corners {
  def all[Primitive](v: Primitive) = Corners(v, v, v, v)
}

case class Dividers[Primitive](h: Option[Primitive], v: Option[Primitive])
object Dividers {
  def hv[Primitive](h: Primitive, v: Primitive) = Dividers(Some(h), Some(v))
  lazy val none = Dividers(None, None)
}

case class Padding[Primitive](
    space: Spacing,
    fill: Sides[Primitive]
)

case class Margins[Primitive](
    space: Spacing,
    fill: Sides[Primitive]
)

case class Theme[Primitive](
    borders: Sides[Primitive],
    corners: Corners[Primitive],
    dividers: Dividers[Primitive],
    padding: Padding[Primitive],
    margins: Margins[Primitive],
    intersections: Intersections[Primitive]
)
