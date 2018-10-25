package astrac.boxtables

import shapeless.ops.hlist.{Fill, Tupler}
import shapeless.{HList, Nat}

trait TupleN[N <: Nat, A] {
  type Tuple
  def apply(a: A): Tuple
}

object TupleN {
  type Aux[N <: Nat, A, _Tuple] = TupleN[N, A] { type Tuple = _Tuple }

  implicit def instance[N <: Nat, A, H <: HList](
      implicit fill: Fill.Aux[N, A, H],
      tupler: Tupler[H]
  ) = new TupleN[N, A] {
    type Tuple = tupler.Out
    def apply(a: A) = tupler(fill(a))
  }
}
