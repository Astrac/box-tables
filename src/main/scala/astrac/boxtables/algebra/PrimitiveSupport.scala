package astrac.boxtables
package algebra

trait PrimitiveSupport[Primitive] {
  def space: Primitive
}

object PrimitiveSupport {
  def instance[Primitive](s: Primitive): PrimitiveSupport[Primitive] =
    new PrimitiveSupport[Primitive] {
      override lazy val space = s
    }

  implicit val stringSupport: PrimitiveSupport[String] =
    instance(" ")
}
