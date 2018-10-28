package astrac.boxtables
package string

object Formatter {
  val wordWrap: Formatter = algebra.Formatter.instance { (s, w) =>
    def breakLine(w: Int)(s: String) = {
      val (lines, last) = s
        .split(" ")
        .toList
        .flatMap(s => if (s.size > w) s.grouped(w) else List(s))
        .foldLeft((List.empty[String], "")) { (state, s) =>
          val (lines, current) = state
          if (current.size + s.size + 1 <= w && !current.isEmpty)
            (lines, s"$current $s")
          else if (current.size + s.size + 1 <= w) (lines, s)
          else (current :: lines, s)
        }

      (last :: lines).reverse
    }

    if (w == 0) Nil
    else
      s.split("\n")
        .toList
        .flatMap(breakLine(w))
        .filter(!_.isEmpty)
        .map(_.padTo(w, ' '))
  }

  val leftAlign: Formatter = wordWrap

  val centerAlign: Formatter = algebra.Formatter.instance { (s, w) =>
    wordWrap(s)(w).map { s =>
      val trimmed = s.trim
      val space = w - trimmed.size
      val firstPad = trimmed.size + (space / 2)

      trimmed.reverse.padTo(firstPad, ' ').reverse.padTo(w, ' ')
    }
  }

  val rightAlign: Formatter = algebra.Formatter.instance { (s, w) =>
    wordWrap(s)(w).map(_.trim().reverse.padTo(w, ' ').reverse)
  }

  val basic: Formatter = algebra.Formatter.instance { (s, w) =>
    if (w == 0) Nil
    else
      s.split("\n")
        .toList
        .flatMap(_.grouped(w).toList.map(_.padTo(w, ' ')))
  }
}
