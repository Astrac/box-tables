[![Build Status](https://travis-ci.org/Astrac/box-tables.svg?branch=master)](https://travis-ci.org/Astrac/box-tables) [![Download](https://api.bintray.com/packages/astrac/maven/box-tables/images/download.svg)](https://bintray.com/astrac/maven/box-tables/_latestVersion)

# Box Tables

A scala library to build customisable, flexible box-drawing tables.

## Getting started

This is the dependency to get version 0.x from bintray (see badge at the top
for the latest version):

```scala
resolvers += Resolvers.bintray("astrac", "maven")
libraryDependencies += "astrac" %% "box-tables" % "0.x.y"
```

## Simple example

```scala
import astrac.boxtables.{AutoRow, Cell, Row, Sizing}
import astrac.boxtables.string.{Tables, Themes}
import astrac.boxtables.instances.all._
import cats.instances.list._
import cats.instances.string._

case class Counters(visits: Int, transfers: Int)
case class User(name: String, age: Int, active: Boolean, counters: Counters)

implicit val permissionCell: Cell[Counters] =
  Cell.instance(p => s"Visits: ${p.visits}\nTransfers: ${p.transfers}")

implicit val userRow: Row[User] = AutoRow[User]

val users = List(
  User("Kilgore Trout", 30, true, Counters(18, 35)),
  User("Billy Pilgrim", 20, false, Counters(5, 7)),
  User("Mandarax", 3, true, Counters(10, 0))
)

println(Tables.simple(users, Sizing.Equal(80), Themes.singleLineAscii))
```

```
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

Box-tables depends on `shapeless` (for `Sized` and generic derivation) and on
`cats` for algebraic typeclasses as `Monoid`.

## Cells

Any cell value is valid as long as it implements the `Cell` typeclass:

```scala
package astrac.boxtables

trait Cell[A] {
  def content(a: A): String
}
```

Primitive, datetime and option instances are defined in the package
`astrac.boxtables.instances` and are not available by default so they must be
explicitly imported.

## Rows

Any type can be represented as a row of a table as long as there is in scope an
instance of the `Row` typeclass:

```scala
sealed trait Row[A] {
  def size: Int
  def toRow(in: A): List[String]
}
```

It is possible to create instances in several ways:

```scala
import astrac.boxtables.{AutoRow, Row}
import astrac.boxtables.instances.primitive._
import shapeless.Sized

case class Foo(x: String, y: Int)

// Sized list instance (uses shapeless.Sized to ensure safety)
val rowFoo: Row[Foo] = Row.instance(foo => Sized[List](foo.x, foo.y.toString))

// Shapeless auto-derivation for case-classes
val rowFoo: Row[Foo] = AutoRow[Row]

// Lifting a (implicit) `Cell` instance into a single-column `Row`
val rowString: Row[String] = Row.cell
```

`Row` is also a `cats.ContravariantMonoidal` and hence it is possible to use
functions like `contramap` and `contramapN` to create new instances from already
existing ones; for example:

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

Finally it is also possible to enable full automatic derivation for tuples and
case classes:

```scala
import astarc.boxtables.AutoRow.instances._

val data: List[SomeCaseClass] = ...

println(StringTables.simple(data, Sizing.Equal(80), Themes.singleLineAscii))
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

The algebra that implement the table creation is not bound to `String` but can
work with any type that defines a `Monoid`; this is its definition:

```scala
trait TableAlgebra[Model, Primitive] {
  implicit def Primitive: Monoid[Primitive]
  implicit def R: Row[Model]
  ...
}
```

This exposes functions that allow to create components from the table or a table
altogether (`algebra.table(data)`); all these functions are not specifically
bound work on `String` but any type could be used as long as there is a `Monoid`
available for that type (e.g. a `String` with additional styling information).

## Formatters

This is the definition of a `Formatter`:

```scala
trait Formatter[Primitive] {
  def space: Primitive
  def apply(w: Int)(s: String): List[Primitive]
}
```

The purpose of the formatter is to bridge from the `String` values extracted by
the `Row` typeclass and the internal representation of the algebra. The `space`
function returns a single blank space and `apply` formats a single `String` in a
`List` of the target representation. This is where contents that are too long to
fit in one cell are split into multiple lines.

Formatters are provided when running algebra operations and when calling
functions in the `Tables` object; two formatters have been implemented so
far and they can only be applied to the table as a whole:

* `Formatter.basic` - Simply breaks the string so that it fits the space
* `Formatter.withWordBoundaries` - Simple algorithm that preserves words

## The `string` package

The `astrac.boxtables.string` package defines specialised implementations of the
table components when the primitive type is a simple string. The `Themes` object
contains the definition of a few themes and the `Tables` object exposes the
following functions:

- `simple` - A table with no headers or footers
- `withHeader`
- `withFooter`
- `withHeaderAndFooter`
- `markdown`

When creating tables with headers and footers different themes can be defined
for each part of the table using the `TableConfig[Primitive]` case class; if
not specified, header and footer configurations will default to the main theme.

## Markdown tables generation

`StringTables.markdown` will generate a markdown table from the provided data.
Please note that since content-based table sizing is not yet implemented it is
the user's responsibility to configure column sizing so that cells do not
overflow on a new line. By default the function will provide evenly distributed
columns for a 80 characters wide table. This is an example of usage:

```scala
import astrac.boxtables.{AutoRow, StringTables}
import astrac.boxtables.instances.all._
import AutoRow.instances._
import cats.instances.list._
import cats.instances.string._

case class Book(title: String, author: String)

println(StringTables.markdown(
  ("Title", "Author"),
  List(
    Book("The Three Body Problem", "Cixin Liu"),
    Book("Foundation", "Isaac Asimov"))))
```

```
// Exiting paste mode, now interpreting.

| Title                                 | Author                               |
|---------------------------------------|--------------------------------------|
| The Three Body Problem                | Cixin Liu                            |
| Foundation                            | Isaac Asimov                         |
```

Which renders on GitHub as:




| Title                                 | Author                               |
|---------------------------------------|--------------------------------------|
| The Three Body Problem                | Cixin Liu                            |
| Foundation                            | Isaac Asimov                         |

## Themes

A few themes are available in the `astrac.boxtables.Themes` object:

* `blank` - No borders, single character paddings and margins
* `blankCompact` - Same as `blank` with no paddings/margins
* `singleLineAscii` - See above
* `unicodeFrame` - Uses nicer characters and adds a light shade in the margins
* `simple` - Uses only `+`, `|`, and `-`, no paddings/margins
* `markdownHeader` - Used for the header cells and divider in markdown tables
* `markdownMain` - Used for the body of markdown tables

Themes are fully customisable, this is an example of theme definition:

```scala
  val unicodeFrame = Theme[String](
    borders = Sides.hv(h = "┃", v = "━"),
    corners = Corners(tl = "╆", tr = "╅", bl = "╄", br = "╃"),
    dividers = Dividers.hv(v = "┃", h = "━"),
    padding = Padding(space = Spacing.all(1), fill = Sides.all(" ")),
    margins = Margins(space = Spacing.all(1), fill = Sides.all("░")),
    intersections = Intersections(l = "╊", r = "╉", b = "╇", t = "╈", c = "╋")
  )
```
