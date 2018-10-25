package astrac.boxtables

import shapeless.{::, Generic => SGeneric, HList, HNil, Nat, Poly1}
import shapeless.ops.hlist.{Length, LiftAll, Mapper, ToList, Zip}
import shapeless.ops.nat.ToInt

trait AutoRowTupleN[N <: Nat, A] {
  type Tuple
  def row: Row[Tuple]
}

object AutoRowTupleN {
  implicit def instance[N <: Nat, A, _Tuple](
      implicit tn: TupleN.Aux[N, A, _Tuple],
      autoRow: AutoRow[_Tuple]
  ): AutoRowTupleN[N, A] =
    new AutoRowTupleN[N, A] {
      println(tn)
      type Tuple = _Tuple
      val row = autoRow.row
    }
}

trait AutoRow[A] {
  def row: Row[A]
}

object AutoRow {

  object mapper extends Poly1 {
    implicit def instance[A, Idx <: Nat] = at[(A, Cell[A])] {
      case (a, ca) => ca.content(a)
    }
  }

  implicit def instance[A,
                        L <: HList,
                        R <: HList,
                        LR <: HList,
                        S <: HList,
                        N <: Nat](implicit g: SGeneric.Aux[A, L],
                                  c: LiftAll.Aux[Cell, L, R],
                                  z: Zip.Aux[L :: R :: HNil, LR],
                                  m: Mapper.Aux[mapper.type, LR, S],
                                  l: ToList[S, String],
                                  s: Length.Aux[S, N],
                                  i: ToInt[N]): AutoRow[A] =
    new AutoRow[A] {
      val row = Row.unsafe(i(), { a =>
        discard(s)
        g.to(a).zip(c.instances).map(mapper).toList
      })
    }

  object instances {
    implicit def rowInstance[A: AutoRow]: Row[A] = AutoRow[A]
  }

  def apply[A](implicit r: AutoRow[A]): Row[A] = r.row

  def derive[A: AutoRow]: Row[A] = apply
}
