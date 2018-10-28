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
import astrac.boxtables.string._
import astrac.boxtables.string.instances._
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
println(Tables.simple(users, Sizing.Weighted(80, List(2, 1, 1, 2)), Themes.doubleLineAscii))
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


 ╔═══════════════════════╦═════════════╦═════════════╦═══════════════════════╗
 ║                       ║             ║             ║                       ║
 ║ Kilgore Trout         ║ 30          ║ true        ║ Visits: 18            ║
 ║                       ║             ║             ║ Transfers: 35         ║
 ║                       ║             ║             ║                       ║
 ╠═══════════════════════╬═════════════╬═════════════╬═══════════════════════╣
 ║                       ║             ║             ║                       ║
 ║ Billy Pilgrim         ║ 20          ║ false       ║ Visits: 5             ║
 ║                       ║             ║             ║ Transfers: 7          ║
 ║                       ║             ║             ║                       ║
 ╠═══════════════════════╬═════════════╬═════════════╬═══════════════════════╣
 ║                       ║             ║             ║                       ║
 ║ Mandarax              ║ 3           ║ true        ║ Visits: 10            ║
 ║                       ║             ║             ║ Transfers: 0          ║
 ║                       ║             ║             ║                       ║
 ╚═══════════════════════╩═════════════╩═════════════╩═══════════════════════╝
```

## Dependencies

Box-tables depends on `shapeless` (for `Sized` and generic derivation) and on
`cats` for algebraic typeclasses as `Monoid`.

## Cells

Any cell value is valid as long as it implements the `Cell` typeclass:

```scala
package astrac.boxtables
package algebra

trait Cell[Primitive, Model] {
  def content(a: Model): Primitive
}
```

Primitive, datetime and option instances for `String` as a primitive type are
defined in `astrac.boxtables.string.instances` and are not available by default
so they must be explicitly imported.

## Rows

Any type can be represented as a row of a table as long as there is in scope an
instance of the `Row` typeclass:

```scala
package astrac.boxtables
package algebra

sealed trait Row[Primitive, Model] {
  def size: Int
  def toRow(in: Model): List[Primitive]
}
```

The `string` subpackage provides a `Row` alias that is specialised on `String`
as a primitive type; it is possible to create instances in several ways:

```scala
import astrac.boxtables.string.{AutoRow, Row}
import astrac.boxtables.string.primitives._

case class Foo(x: String, y: Int)

// Lifting a (implicit) `Cell` instance into a single-column `Row`
val stringRow: Row[String] = Row[String]

// Shapeless auto-derivation for case-classes
val shapelessRow: Row[Foo] = AutoRow[Foo]
```

`Row` is also a `cats.ContravariantMonoidal` and hence it is possible to use
functions like `contramap` and `contramapN` to create new instances from already
existing ones; for example:

```scala
// Uses also the imports in the example above
import cats.syntax.contravariant._
import cats.syntax.contravariantSemigroupal._

// Using contramap to create a `Row` instance for a value-class
case class Bar(x: String) extends AnyVal
val rowBar = Row[String].contramap[Bar](_.x)

// Using contramapN to create a `Row` instance for a case class with derived fields
val rowFoo: Row[Foo] =
  (Row[String], Row[Int], Row[Boolean]).contramapN[Foo] { foo =>
    (foo.x, foo.y, foo.y > 0)
  }
```

Finally it is also possible to enable full automatic derivation for tuples and
case classes:

```scala
import astarc.boxtables.string._
import astarc.boxtables.string.fullAuto._
import cats.implicits._

val data: List[SomeCaseClass] = ...

println(Tables.simple(data, Sizing.Equal(80), Themes.singleLineAscii))
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

## Algebra

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

Facades to the algebra are provided in the `astrac.boxtables.string` package
to facilitate the generation of string-backed tables.

## Formatters

This is the definition of a `Formatter`:

```scala
trait Formatter[Primitive] {
  def apply(w: Int)(s: Primitive): List[Primitive]
}
```

The purpose of the formatter is to adapt the values extracted from the `Row`
typeclass to what is needed by the table algebra when it creates a cell. The
`apply` function formats a single `Primitive` in a `List` of lines of the
desired width, opportunely padding the line if the content is shorter.

Formatters can be attached to any `Row` instance in several ways:

```
import astrac.boxtables.string._
import astrac.boxtables.string.instances._

// Apply the same formatter to all cells in a row
val row: Row[SomeType] = ...
val centered = row.format(Formatter.centerAlign)

// Derive an instance for a row with a provided formatter
val row: Row[SomeCaseClass] = AutoRow.formatted(Formatter.centerAlign)

// Apply formatters by index
// WARNING - non exaustive match can result in exceptions
val row: Row[SomeType] = ...
val formatted = row.formatByIndex {
  case 0 => Formatter.rightAlign
  case 1 => Formatter.centerAlign
  case _ => Formatter.leftAlign
}

// Use ContravariantMonoidal to build a row bottom-up with custom formatters
val row: Row[(String, String, String)] = (
    Row[String].format(Formatter.leftAlign),
    Row[String].format(Formatter.centerAlign),
    Row[String].format(Formatter.rightAlign)
  ).tupled
```

The default formatter when calling functions in the `Tables` object is `basic`;
the `basic` formatter won't do any word wrapping and will simply break the string
in chunks of the desired sizes, padding the last one when necessary.

This is an example of the various provided formatters:

```scala
import astrac.boxtables.string._
import astrac.boxtables.string.instances._
import cats.implicits._

val loremIpsum =
  """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
    |id molestie erat. Duis auctor vestibulum lacus quis ultrices.
    |""".stripMargin.replace("\n", " ")

implicit val row = (
    Row[String].format(Formatter.basic),
    Row[String].format(Formatter.leftAlign),
    Row[String].format(Formatter.centerAlign),
    Row[String].format(Formatter.rightAlign)
  ).tupled

println(
  Tables.simple(
    List(
      ("basic", "left", "center", "right"),
      (loremIpsum, loremIpsum, loremIpsum, loremIpsum)
    ),
    Sizing.Equal(80),
    Themes.blankCompact
  )
)
```

On the REPL:

```
// Exiting paste mode, now interpreting.


 basic               left                      center                     right

 Lorem ipsum dolor s Lorem ipsum dolor    Lorem ipsum dolor   Lorem ipsum dolor
 it amet, consectetu sit amet,                sit amet,               sit amet,
 r adipiscing elit.  consectetur             consectetur            consectetur
 Nullam id molestie  adipiscing elit.     adipiscing elit.     adipiscing elit.
 erat. Duis auctor v Nullam id molestie  Nullam id molestie  Nullam id molestie
 estibulum lacus qui erat. Duis auctor    erat. Duis auctor   erat. Duis auctor
 s ultrices.         vestibulum lacus     vestibulum lacus     vestibulum lacus
                     quis ultrices.        quis ultrices.        quis ultrices.
```

## The `string` package

The `astrac.boxtables.string` package defines specialised implementations of the
table components when the primitive type is a simple string. There are also aliases
to some components in the base package for ease of importing:

```
import astrac.boxtables.string._
import astrac.boxtables.string.fullAuto._
import cats.implicits._
```

These imports provide the types, the cell instances and full automatic
derivation for `Row` instances and should allow you to create tables from
your data types with zero additional costs.

The `Themes` object contains the definition of a few themes and the `Tables`
object exposes the following functions:

- `simple` - A table with no headers or footers
- `withHeader`
- `withFooter`
- `withHeaderAndFooter`
- `markdown`

When creating tables with headers and footers different themes can be defined
for each part of the table using the `TableConfig[Primitive]` case class; if
not specified, header and footer configurations will default to the main theme.

## Markdown tables generation

`Tables.markdown` will generate a markdown table from the provided data.
Please note that since content-based table sizing is not yet implemented it is
the user's responsibility to configure column sizing so that cells do not
overflow on a new line. By default the function will provide evenly distributed
columns for a 80 characters wide table. This is an example of usage:

```scala
import astrac.boxtables.string.Tables
import astrac.boxtables.string.fullAuto._
import cats.implicits._

case class Book(title: String, author: String)

println(Tables.markdown(
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
* `singleLineAscii` - See example at the top of the README
* `doubleLineAscii` - Same as `singleLineAscii` but using `║`, `═`, ...
* `doubleVSingleHAscii` - Mix of sigle and double lines (double for verticals)
* `singleVDoubleHAscii` - Mix of sigle and double lines (double for horizontals)
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
