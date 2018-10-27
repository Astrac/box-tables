package astrac.boxtables

import shapeless.{::, Generic, HList, HNil, Nat, Poly1}
import shapeless.ops.hlist.{Length, LiftAll, Mapper, ToList, Zip}
import shapeless.ops.nat.ToInt

trait AutoRow[Model] {
  def row: Row[Model]
}

object AutoRow {

  object mapper extends Poly1 {
    implicit def instance[A, Idx <: Nat] = at[(A, Cell[A])] {
      case (a, ca) => ca.content(a)
    }
  }

  implicit def instance[Model,
                        Repr <: HList,
                        Cells <: HList,
                        Args <: HList,
                        Contents <: HList,
                        Size <: Nat](
      implicit generic: Generic.Aux[Model, Repr],
      liftCells: LiftAll.Aux[Cell, Repr, Cells],
      zipArgs: Zip.Aux[Repr :: Cells :: HNil, Args],
      contentMapper: Mapper.Aux[mapper.type, Args, Contents],
      toList: ToList[Contents, String],
      lengthNat: Length.Aux[Contents, Size],
      length: ToInt[Size]): AutoRow[Model] =
    new AutoRow[Model] {
      val row = Row.unsafe(length(), { model: Model =>
        discard(lengthNat)
        generic.to(model).zip(liftCells.instances).map(mapper).toList
      })
    }

  object instances {
    implicit def rowInstance[Model: AutoRow]: Row[Model] = AutoRow[Model]
  }

  def apply[Model](implicit r: AutoRow[Model]): Row[Model] = r.row

  def derive[Model: AutoRow]: Row[Model] = apply
}
