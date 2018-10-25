package astrac.boxtables

case class RowsConfig[T](theme: Theme[T], sizing: Sizing)

case class TableConfig[T](
    main: RowsConfig[T],
    header: Option[RowsConfig[T]] = None,
    footer: Option[RowsConfig[T]] = None
) {
  lazy val safeHeader: RowsConfig[T] = header.getOrElse(main)
  lazy val safeFooter: RowsConfig[T] = footer.getOrElse(main)
}
