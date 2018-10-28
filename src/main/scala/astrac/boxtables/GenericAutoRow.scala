package astrac.boxtables

import shapeless.{::, Generic, HList, HNil, Nat, Poly1}
import shapeless.ops.hlist.{Length, LiftAll, Mapper, ToList, Zip}
import shapeless.ops.nat.ToInt

trait GenericAutoRow[Primitive, Model] {
  def row: GenericRow[Primitive, Model]
}

object GenericAutoRow {

  object mapper extends Poly1 {
    implicit def instance[A, Idx <: Nat, P] = at[(A, GenericCell[P, A])] {
      case (a, ca) => ca.content(a)
    }
  }

  implicit def instance[Primitive,
                        Model,
                        Repr <: HList,
                        Cells <: HList,
                        Args <: HList,
                        Contents <: HList,
                        Size <: Nat](
      implicit generic: Generic.Aux[Model, Repr],
      liftCells: LiftAll.Aux[GenericCell[Primitive, ?], Repr, Cells],
      zipArgs: Zip.Aux[Repr :: Cells :: HNil, Args],
      contentMapper: Mapper.Aux[mapper.type, Args, Contents],
      toList: ToList[Contents, Primitive],
      lengthNat: Length.Aux[Contents, Size],
      length: ToInt[Size]): GenericAutoRow[Primitive, Model] =
    new GenericAutoRow[Primitive, Model] {
      val row = GenericRow.unsafe(length(), { model: Model =>
        discard(lengthNat)
        generic.to(model).zip(liftCells.instances).map(mapper).toList
      })
    }

  trait Instances {
    implicit def rowInstance[Primitive, Model](
        implicit r: GenericAutoRow[Primitive, Model])
      : GenericRow[Primitive, Model] =
      GenericAutoRow[Primitive, Model]
  }

  object instances extends Instances

  def apply[Primitive, Model](implicit r: GenericAutoRow[Primitive, Model])
    : GenericRow[Primitive, Model] = r.row

  def derive[Primitive, Model](implicit r: GenericAutoRow[Primitive, Model])
    : GenericRow[Primitive, Model] = apply
}
