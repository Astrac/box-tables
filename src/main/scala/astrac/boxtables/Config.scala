package astrac.boxtables

case class RowsConfig[Primitive](theme: Theme[Primitive], sizing: Sizing)

case class TableConfig[Primitive](
    main: RowsConfig[Primitive],
    header: Option[RowsConfig[Primitive]] = None,
    footer: Option[RowsConfig[Primitive]] = None
) {
  lazy val safeHeader: RowsConfig[Primitive] = header.getOrElse(main)
  lazy val safeFooter: RowsConfig[Primitive] = footer.getOrElse(main)
}
