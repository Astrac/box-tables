package astrac.boxtables

import shapeless.{::, Generic => SGeneric, HList, HNil, Nat, Poly1}
import shapeless.ops.hlist.{Length, LiftAll, Mapper, ToList, Zip}
import shapeless.ops.nat.ToInt

object Generic {

  def discard[T]: T => Unit = _ => ()

  object mapper extends Poly1 {
    implicit def instance[A, Idx <: Nat] = at[(A, Cell[A])] {
      case (a, ca) => ca.content(a)
    }
  }

  class Builder[A] {
    def derive[L <: HList, R <: HList, LR <: HList, S <: HList, N <: Nat](
        implicit g: SGeneric.Aux[A, L],
        c: LiftAll.Aux[Cell, L, R],
        z: Zip.Aux[L :: R :: HNil, LR],
        m: Mapper.Aux[mapper.type, LR, S],
        l: ToList[S, String],
        s: Length.Aux[S, N],
        i: ToInt[N]): Row[A] =
      Row.unsafe(i(), { a =>
        discard(s)
        g.to(a).zip(c.instances).map(mapper).toList
      })
  }

  def apply[A]: Builder[A] = new Builder[A]

  def tupleN[N <: Nat, A](implicit tn: TupleN[N, A]) = new Builder[tn.Tuple]
}
