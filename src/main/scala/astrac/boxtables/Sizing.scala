package astrac.boxtables

sealed trait Sizing
object Sizing {
  case class Equal(total: Int) extends Sizing
  case class Fixed(columns: List[Int]) extends Sizing
  case class Weighted(total: Int, weights: List[Int]) extends Sizing {
    val sum = weights.sum
  }
}
