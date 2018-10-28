package astrac.boxtables

import cats.syntax.contravariant._
import shapeless.ops.hlist.{At, ZipConst, ZipWithIndex}
import shapeless.{Generic, HList, Nat, Poly1}
import shapeless.ops.hlist.{LiftAll, Mapper, ToList}

trait GenericAutoRow[Primitive, Model] {
  def row: GenericRow[Primitive, Model]
}

object GenericAutoRow {

  object mapper extends Poly1 {
    implicit def instance[Primitive,
                          Content,
                          Model,
                          Index <: Nat,
                          Repr <: HList](
        implicit hlistAt: At.Aux[Repr, Index, Content]) =
      at[((GenericCell[Primitive, Content], Index), Generic.Aux[Model, Repr])] {
        case ((cell, _), generic) =>
          cell.contramap[Model](m => generic.to(m).at[Index])
      }
  }

  implicit def instance[Primitive,
                        Model,
                        Repr <: HList,
                        Cells <: HList,
                        IndexedCells <: HList,
                        Args <: HList,
                        ContramappedCells <: HList](
      implicit generic: Generic.Aux[Model, Repr],
      liftCells: LiftAll.Aux[GenericCell[Primitive, ?], Repr, Cells],
      indexed: ZipWithIndex.Aux[Cells, IndexedCells],
      withGeneric: ZipConst.Aux[Generic.Aux[Model, Repr], IndexedCells, Args],
      cellsContramapper: Mapper.Aux[mapper.type, Args, ContramappedCells],
      toList: ToList[ContramappedCells, GenericCell[Primitive, Model]]
  ): GenericAutoRow[Primitive, Model] =
    new GenericAutoRow[Primitive, Model] {
      override val row = GenericRow.instance(
        liftCells.instances.zipWithIndex.zipConst(generic).map(mapper).toList)
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

  def formatted[Primitive, Model](f: GenericFormatter[Primitive])(
      implicit r: GenericAutoRow[Primitive, Model])
    : GenericRow[Primitive, Model] = derive[Primitive, Model].format(f)
}
