[![Build Status](https://travis-ci.org/Astrac/box-tables.svg?branch=master)](https://travis-ci.org/Astrac/box-tables)

# Box Tables

A scala library to build customisable, flexible box-drawing tables.

## Getting started

TODO: bintray

## Simple example

```
import astrac.boxtables.{BoxTable, Cell, Generic, Row, Sizing, Themes}
import astrac.boxtables.instances.all._
import cats.instances.list._

case class Counters(visits: Int, transfers: Int)
case class User(name: String, age: Int, active: Boolean, counters: Counters)

implicit val permissionCell: Cell[Counters] =
  Cell.instance(p => s"Visits: ${p.visits}\nTransfers: ${p.transfers}")

implicit val userRow: Row[User] = Generic[User].derive

val users = List(
  User("Kilgore Trout", 30, true, Counters(18, 35)),
  User("Billy Pilgrim", 20, false, Counters(5, 7)),
  User("Mandarax", 3, true, Counters(10, 0))
)

println(BoxTable.makeString(users, Sizing.Equal(80), Themes.singleLineAscii))

// Exiting paste mode, now interpreting.


 ┌───────────────────┬──────────────────┬──────────────────┬──────────────────┐
 │                   │                  │                  │                  │
 │ Kilgore Trout     │ 30               │ true             │ Visits: 18       │
 │                   │                  │                  │ Transfers: 35    │
 │                   │                  │                  │                  │
 ├───────────────────┼──────────────────┼──────────────────┼──────────────────┤
 │                   │                  │                  │                  │
 │ Billy Pilgrim     │ 20               │ false            │ Visits: 5        │
 │                   │                  │                  │ Transfers: 7     │
 │                   │                  │                  │                  │
 ├───────────────────┼──────────────────┼──────────────────┼──────────────────┤
 │                   │                  │                  │                  │
 │ Mandarax          │ 3                │ true             │ Visits: 10       │
 │                   │                  │                  │ Transfers: 0     │
 │                   │                  │                  │                  │
 └───────────────────┴──────────────────┴──────────────────┴──────────────────┘
```

## Dependencies

Box-tables depends on `shapeless` (for `Sized` and generic derivation) and on `cats`
for algebraic typeclasses as `Monoid`.

## Cells

Any cell value is valid as long as it implements the `Cell` typeclass:

```scala
package astrac.boxtables

trait Cell[A] {
  def content(a: A): String
}
```

Primitive, datetime and option instances are defined in the package `astrac.boxtables.instances`
and are not available by default so they must be explicitly imported.

## Rows

Any type can be represented as a row of a table as long as there is in scope an instance of the `Row` typeclass:

```scala
sealed trait Row[A] {
  def size: Int
  def toRow(in: A): List[String]
}
```

It is possible to create instances in several ways:

```scala
import astrac.boxtables.{Generic, Row}
import astrac.boxtables.instances.primitive._
import shapeless.Sized

case class Foo(x: String, y: Int)

// Sized list instance (uses shapeless.Sized to ensure safety)
val rowFoo: Row[Foo] = Row.instance(foo => Sized[List](foo.x, foo.y.toString))

// Shapeless auto-derivation for case-classes
val rowFoo: Row[Foo] = Generic[Row].derive

// Lifting a (implicit) `Cell` instance into a single-column `Row`
val rowString: Row[String] = Row.cell
```

`Row` is also a `cats.ContravariantMonoidal` and hence it is possible to use functions like `contramap`
and `contramapN` to create new instances from already existing ones; for example:

```scala
// Using contramap to create a `Row` instance for a value-class
case class Bar(x: String) extends AnyVal
val rowBar = Row.cell[String].contramap[Bar](_.x)

// Using contramapN to create a `Row` instance for a case class with derived fields
val rowFoo: Row[Foo] =
  (Row.cell[String], Row.cell[Int], Row.cell[Boolean]).contramapN[Foo] { foo =>
    (foo.x, foo.y, foo.y > 0)
  }
```

## Sizing

Table size can be specified with three strategies at the moment:

```scala
import astrac.boxtables.Sizing

// Equally spread columns, 80 characters table including margins
val size = Sizing.Equal(80)

// Weighted columns, 80 characters table including margins
val size = Sizing.Weighted(80, List(1, 1, 3, 5))

// Fixed size columns, the table will take the size it needs
val size = Sizing.Fixed(List(20, 60, 10))
```

## Algebra and Formatters

The algebra that implement the table creation is not bound to `String` but can work with any type that defines a `Monoid`;
this is its definition:

```scala
class TableAlgebra[A, T](config: Theme[T], sizing: Sizing)(
    implicit T: Monoid[T],
    F: Formatter[T],
    R: Row[A]) {
```

This exposes functions that allow to create components from the table or a table altogether (`algebra.table(data)`);
all these functions are not specifically bound work on `String` but any type could be used as long as there are a `Monoid`
and a `Formatter` available for that type (e.g. a `String` with additional styling information).

This is the definition of a `Formatter`:

```scala
trait Formatter[T] {
  def space: T
  def apply(w: Int)(s: String): List[T]
}
```

The purpose of the formatter is to bridge from the `String` values extracted by the `Row` typeclass and the internal
representation of the algebra. The `space` function returns a single blank space and `apply` formats a single `String`
in a `List` of the target representation. This is where contents that are too long to fit in one cell are split into
multiple lines.

## Themes

A few themes are available in the `astrac.boxtables.Themes` object:

* `blank` - No borders, single character paddings and margins
* `blankCompact` - Same as `blank` with no paddings/margins
* `singleLineAscii` - See above
* `unicodeFrame` - Uses nicer characters and adds a light shade in the margins
* `simple` - Uses only `+`, `|`, and `-`, no paddings/margins

Themes are fully customisable, this is an example of theme definition:

```scala
  val unicodeFrame = Theme[String](
    borders = Sides.hv(h = "┃", v = "━"),
    corners = Corners(tl = "╆", tr = "╅", bl = "╄", br = "╃"),
    dividers = Dividers(v = "┃", h = "━"),
    padding = Padding(space = Spacing.all(1), fill = Sides.all(" ")),
    margins = Margins(space = Spacing.all(1), fill = Sides.all("░")),
    intersections = Intersections(l = "╊", r = "╉", b = "╇", t = "╈", c = "╋")
  )
```
