package astrac.boxtables

object Examples {

  def apply(name: String) = {
    examples(name)
  }

  private lazy val examples = Map(
    "/auto.example" -> auto,
    "/markdown.example" -> markdown,
    "/test-theme-equal-sizing.example" -> equalSizing,
    "/test-theme-weighted-sizing.example" -> weightedSizing,
    "/test-theme-fixed-sizing.example" -> fixedSizing
  )

  val loremIpsum =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
      |id molestie erat. Duis auctor vestibulum lacus quis ultrices.
      |Vestibulum et dapibus ligula. Fusce lacinia nisl id tincidunt
      |egestas.""".stripMargin.replace("\n", " ")

  val loremIpsumShort =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
      |id molestie erat. Duis auctor vestibulum lacus quis ultrices.
      |""".stripMargin.replace("\n", " ")

  val tableWordBoundariesFormatter = List(
    "                                                            ",
    " ┌─────────────┬──────────────────┬───────────────────────┐ ",
    " │             │                  │                       │ ",
    " │ Lorem ipsum │   Lorem ipsum    │ Lorem ipsum dolor sit │ ",
    " │ dolor sit   │ dolor sit amet,  │     amet, consectetur │ ",
    " │ amet,       │   consectetur    │      adipiscing elit. │ ",
    " │ consectetur │ adipiscing elit. │    Nullam id molestie │ ",
    " │ adipiscing  │    Nullam id     │     erat. Duis auctor │ ",
    " │ elit.       │  molestie erat.  │ vestibulum lacus quis │ ",
    " │ Nullam id   │   Duis auctor    │  ultrices. Vestibulum │ ",
    " │ molestie    │ vestibulum lacus │    et dapibus ligula. │ ",
    " │ erat. Duis  │  quis ultrices.  │ Fusce lacinia nisl id │ ",
    " │ auctor      │  Vestibulum et   │    tincidunt egestas. │ ",
    " │ vestibulum  │ dapibus ligula.  │                       │ ",
    " │ lacus quis  │  Fusce lacinia   │                       │ ",
    " │ ultrices.   │     nisl id      │                       │ ",
    " │             │    tincidunt     │                       │ ",
    " │             │     egestas.     │                       │ ",
    " │             │                  │                       │ ",
    " ├─────────────┼──────────────────┼───────────────────────┤ ",
    " │             │                  │                       │ ",
    " │ Lorem ipsum │   Lorem ipsum    │ Lorem ipsum dolor sit │ ",
    " │ dolor sit   │ dolor sit amet,  │     amet, consectetur │ ",
    " │ amet,       │   consectetur    │      adipiscing elit. │ ",
    " │ consectetur │ adipiscing elit. │    Nullam id molestie │ ",
    " │ adipiscing  │    Nullam id     │     erat. Duis auctor │ ",
    " │ elit.       │  molestie erat.  │ vestibulum lacus quis │ ",
    " │ Nullam id   │   Duis auctor    │  ultrices. Vestibulum │ ",
    " │ molestie    │ vestibulum lacus │    et dapibus ligula. │ ",
    " │ erat. Duis  │  quis ultrices.  │ Fusce lacinia nisl id │ ",
    " │ auctor      │  Vestibulum et   │    tincidunt egestas. │ ",
    " │ vestibulum  │ dapibus ligula.  │                       │ ",
    " │ lacus quis  │  Fusce lacinia   │                       │ ",
    " │ ultrices.   │     nisl id      │                       │ ",
    " │             │    tincidunt     │                       │ ",
    " │             │     egestas.     │                       │ ",
    " │             │                  │                       │ ",
    " └─────────────┴──────────────────┴───────────────────────┘ ",
    "                                                            "
  ).mkString("\n")

  val auto =
    ("""The Three Body Problem, Cixin Liu
      |The Stars My Destination, Alfred Bester
      |Foundation, Isaac Asimov""".stripMargin,
     """|| Title                                 | Author                               |
       ||---------------------------------------|--------------------------------------|
       || The Three Body Problem                | Cixin Liu                            |
       || The Stars My Destination              | Alfred Bester                        |
       || Foundation                            | Isaac Asimov                         |""".stripMargin)

  val markdown = (
    """Kilgore Trout, 30, true, 18, 35
      |Billy Pilgrim, 20, false, 5, 7
      |Mandarax, 3, true, 10, 0""".stripMargin,
    """|| Name                     | Age                     | Active                  |
       ||--------------------------|-------------------------|-------------------------|
       || Kilgore Trout            | 30                      | true                    |
       || Billy Pilgrim            | 20                      | false                   |
       || Mandarax                 | 3                       | true                    |""".stripMargin
  )

  val equalSizing = (
    """Kilgore Trout, 30, true, 18, 35
      |Billy Pilgrim, 20, false, 5, 7
      |Mandarax, 3, true, 10, 0""".stripMargin,
    """^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvv|vvvvvvvvv|vvvvvvvv|vvvvvvvv←>>
      |<<<<→>>>>Kil<<|>>>>30 <<|>>>>tr<<|>>>>Vi<<←>>
      |<<<<→>>>>gor<<|>>>>   <<|>>>>ue<<|>>>>si<<←>>
      |<<<<→>>>>e T<<|>>>>   <<|>>>>  <<|>>>>ts<<←>>
      |<<<<→>>>>rou<<|>>>>   <<|>>>>  <<|>>>>: <<←>>
      |<<<<→>>>>t  <<|>>>>   <<|>>>>  <<|>>>>18<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>Tr<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>an<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>sf<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>er<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>s:<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>> 3<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>5 <<←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<↦---------↺---------↺--------↺--------↤>>
      |<<<<→vvvvvvvvv|vvvvvvvvv|vvvvvvvv|vvvvvvvv←>>
      |<<<<→>>>>Bil<<|>>>>20 <<|>>>>fa<<|>>>>Vi<<←>>
      |<<<<→>>>>ly <<|>>>>   <<|>>>>ls<<|>>>>si<<←>>
      |<<<<→>>>>Pil<<|>>>>   <<|>>>>e <<|>>>>ts<<←>>
      |<<<<→>>>>gri<<|>>>>   <<|>>>>  <<|>>>>: <<←>>
      |<<<<→>>>>m  <<|>>>>   <<|>>>>  <<|>>>>5 <<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>Tr<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>an<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>sf<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>er<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>s:<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>> 7<<←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<↦---------↺---------↺--------↺--------↤>>
      |<<<<→vvvvvvvvv|vvvvvvvvv|vvvvvvvv|vvvvvvvv←>>
      |<<<<→>>>>Man<<|>>>>3  <<|>>>>tr<<|>>>>Vi<<←>>
      |<<<<→>>>>dar<<|>>>>   <<|>>>>ue<<|>>>>si<<←>>
      |<<<<→>>>>ax <<|>>>>   <<|>>>>  <<|>>>>ts<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>: <<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>10<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>Tr<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>an<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>sf<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>er<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>>s:<<←>>
      |<<<<→>>>>   <<|>>>>   <<|>>>>  <<|>>>> 0<<←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<→^^^^^^^^^|^^^^^^^^^|^^^^^^^^|^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |
      |^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Kilgore Trou<<|>>>>30         <<|>>>>true       <<|>>>>Visits: 18 <<←>>
      |<<<<→>>>>t           <<|>>>>           <<|>>>>           <<|>>>>Transfers: <<←>>
      |<<<<→>>>>            <<|>>>>           <<|>>>>           <<|>>>>35         <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<↦------------------↺-----------------↺-----------------↺-----------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Billy Pilgri<<|>>>>20         <<|>>>>false      <<|>>>>Visits: 5  <<←>>
      |<<<<→>>>>m           <<|>>>>           <<|>>>>           <<|>>>>Transfers: <<←>>
      |<<<<→>>>>            <<|>>>>           <<|>>>>           <<|>>>>7          <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<↦------------------↺-----------------↺-----------------↺-----------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Mandarax    <<|>>>>3          <<|>>>>true       <<|>>>>Visits: 10 <<←>>
      |<<<<→>>>>            <<|>>>>           <<|>>>>           <<|>>>>Transfers: <<←>>
      |<<<<→>>>>            <<|>>>>           <<|>>>>           <<|>>>>0          <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |
      |^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Kilgore Trout         <<|>>>>30                   <<|>>>>true                 <<|>>>>Visits: 18           <<←>>
      |<<<<→>>>>                      <<|>>>>                     <<|>>>>                     <<|>>>>Transfers: 35        <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦----------------------------↺---------------------------↺---------------------------↺---------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Billy Pilgrim         <<|>>>>20                   <<|>>>>false                <<|>>>>Visits: 5            <<←>>
      |<<<<→>>>>                      <<|>>>>                     <<|>>>>                     <<|>>>>Transfers: 7         <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦----------------------------↺---------------------------↺---------------------------↺---------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Mandarax              <<|>>>>3                    <<|>>>>true                 <<|>>>>Visits: 10           <<←>>
      |<<<<→>>>>                      <<|>>>>                     <<|>>>>                     <<|>>>>Transfers: 0         <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv""".stripMargin
  )

  val fixedSizing = (
    """Kilgore Trout, 30, true, 18, 35
      |Billy Pilgrim, 20, false, 5, 7
      |Mandarax, 3, true, 10, 0""".stripMargin,
    """^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvv|vvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Kilgore Trout       <<|>>>>30 <<|>>>>tru<<|>>>>Visits: 18                    <<←>>
      |<<<<→>>>>                    <<|>>>>   <<|>>>>e  <<|>>>>Transfers: 35                 <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦--------------------------↺---------↺---------↺------------------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvv|vvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Billy Pilgrim       <<|>>>>20 <<|>>>>fal<<|>>>>Visits: 5                     <<←>>
      |<<<<→>>>>                    <<|>>>>   <<|>>>>se <<|>>>>Transfers: 7                  <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦--------------------------↺---------↺---------↺------------------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvv|vvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Mandarax            <<|>>>>3  <<|>>>>tru<<|>>>>Visits: 10                    <<←>>
      |<<<<→>>>>                    <<|>>>>   <<|>>>>e  <<|>>>>Transfers: 0                  <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^|^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |
      |^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvv|vvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Kilgore Trout  <<|>>>>30<<|>>>>true <<|>>>>Visits: 18          <<←>>
      |<<<<→>>>>               <<|>>>>  <<|>>>>     <<|>>>>Transfers: 35       <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦---------------------↺--------↺-----------↺--------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvv|vvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Billy Pilgrim  <<|>>>>20<<|>>>>false<<|>>>>Visits: 5           <<←>>
      |<<<<→>>>>               <<|>>>>  <<|>>>>     <<|>>>>Transfers: 7        <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦---------------------↺--------↺-----------↺--------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvv|vvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Mandarax       <<|>>>>3 <<|>>>>true <<|>>>>Visits: 10          <<←>>
      |<<<<→>>>>               <<|>>>>  <<|>>>>     <<|>>>>Transfers: 0        <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv""".stripMargin
  )

  val weightedSizing = (
    """Kilgore Trout, 30, true, 18, 35
      |Billy Pilgrim, 20, false, 5, 7
      |Mandarax, 3, true, 10, 0""".stripMargin,
    """^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvv|vvvvvvv|vvvvvvv|vvvvvvvvvv←>>
      |<<<<→>>>>Kilg<<|>>>>3<<|>>>>t<<|>>>>Visi<<←>>
      |<<<<→>>>>ore <<|>>>>0<<|>>>>r<<|>>>>ts: <<←>>
      |<<<<→>>>>Trou<<|>>>> <<|>>>>u<<|>>>>18  <<←>>
      |<<<<→>>>>t   <<|>>>> <<|>>>>e<<|>>>>Tran<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>> <<|>>>>sfer<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>> <<|>>>>s: 3<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>> <<|>>>>5   <<←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<↦----------↺-------↺-------↺----------↤>>
      |<<<<→vvvvvvvvvv|vvvvvvv|vvvvvvv|vvvvvvvvvv←>>
      |<<<<→>>>>Bill<<|>>>>2<<|>>>>f<<|>>>>Visi<<←>>
      |<<<<→>>>>y Pi<<|>>>>0<<|>>>>a<<|>>>>ts: <<←>>
      |<<<<→>>>>lgri<<|>>>> <<|>>>>l<<|>>>>5   <<←>>
      |<<<<→>>>>m   <<|>>>> <<|>>>>s<<|>>>>Tran<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>>e<<|>>>>sfer<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>> <<|>>>>s: 7<<←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<↦----------↺-------↺-------↺----------↤>>
      |<<<<→vvvvvvvvvv|vvvvvvv|vvvvvvv|vvvvvvvvvv←>>
      |<<<<→>>>>Mand<<|>>>>3<<|>>>>t<<|>>>>Visi<<←>>
      |<<<<→>>>>arax<<|>>>> <<|>>>>r<<|>>>>ts: <<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>>u<<|>>>>10  <<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>>e<<|>>>>Tran<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>> <<|>>>>sfer<<←>>
      |<<<<→>>>>    <<|>>>> <<|>>>> <<|>>>>s: 0<<←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^|^^^^^^^|^^^^^^^|^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |
      |^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Kilgore Trout  <<|>>>>30   <<|>>>>true <<|>>>>Visits: 18          <<←>>
      |<<<<→>>>>               <<|>>>>     <<|>>>>     <<|>>>>Transfers: 35       <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦---------------------↺-----------↺-----------↺--------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Billy Pilgrim  <<|>>>>20   <<|>>>>false<<|>>>>Visits: 5           <<←>>
      |<<<<→>>>>               <<|>>>>     <<|>>>>     <<|>>>>Transfers: 7        <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦---------------------↺-----------↺-----------↺--------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Mandarax       <<|>>>>3    <<|>>>>true <<|>>>>Visits: 10          <<←>>
      |<<<<→>>>>               <<|>>>>     <<|>>>>     <<|>>>>Transfers: 0        <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |
      |^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
      |<<<<↘↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↧↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↙>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Kilgore Trout               <<|>>>>30        <<|>>>>true      <<|>>>>Visits: 18                           <<←>>
      |<<<<→>>>>                            <<|>>>>          <<|>>>>          <<|>>>>Transfers: 35                        <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦----------------------------------↺----------------↺----------------↺-------------------------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Billy Pilgrim               <<|>>>>20        <<|>>>>false     <<|>>>>Visits: 5                            <<←>>
      |<<<<→>>>>                            <<|>>>>          <<|>>>>          <<|>>>>Transfers: 7                         <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↦----------------------------------↺----------------↺----------------↺-------------------------------------------↤>>
      |<<<<→vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvv|vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv←>>
      |<<<<→>>>>Mandarax                    <<|>>>>3         <<|>>>>true      <<|>>>>Visits: 10                           <<←>>
      |<<<<→>>>>                            <<|>>>>          <<|>>>>          <<|>>>>Transfers: 0                         <<←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<→^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^←>>
      |<<<<↗↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↥↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↖>>
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      |vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv""".stripMargin
  )

}
