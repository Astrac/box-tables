package astrac

package object boxtables {
  def discard[T]: T => Unit = _ => ()
}
