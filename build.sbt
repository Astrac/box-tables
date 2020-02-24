import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val boxTables = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(
    onLoad in Global := { Command.process("project root", _: State) } compose (onLoad in Global).value,
    organization := "astrac",
    name := "box-tables",
    description := "A library to create box-drawing tables",
    licenses += ("Apache-2.0", url(
      "https://opensource.org/licenses/Apache-2.0"
    )),
    crossScalaVersions := Seq("2.12.10", "2.13.1"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full
    ),
    libraryDependencies ++= Seq(
      "com.chuusai" %%% "shapeless" % "2.3.3",
      "org.typelevel" %%% "cats-core" % "2.1.0",
      "org.typelevel" %%% "cats-laws" % "2.1.0" % Test,
      "org.typelevel" %% "discipline-scalatest" % "1.0.1",
      "org.scalacheck" %%% "scalacheck" % "1.14.3" % Test
    ),
    scalacOptions := Seq(
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-encoding",
      "utf-8", // Specify character encoding used by source files.
      "-explaintypes", // Explain type errors in more detail.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly.
      "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
      "-language:experimental.macros", // Allow macro definition (besides implementation and application)
      "-language:higherKinds", // Allow higher-kinded types
      "-language:implicitConversions", // Allow definition of implicit functions called views
      "-unchecked", // Enable additional warnings where generated code depends on assumptions.
      "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
      "-Xfatal-warnings", // Fail the compilation if there are any warnings.
      "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
      "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
      "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
      "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
      "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
      "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
      "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
      "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
      "-Xlint:option-implicit", // Option.apply used implicit view.
      "-Xlint:package-object-classes", // Class or object defined in package object.
      "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
      "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
      "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
      "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
      "-Ywarn-dead-code", // Warn when dead code is identified.
      "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
      "-Ywarn-numeric-widen", // Warn when numerics are widened.
      "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
      "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
      "-Ywarn-unused:params", // Warn if a value parameter is unused.
      "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
      "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
    ) ++ (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, scalaMajor)) if scalaMajor == 12 =>
        Seq(
          "-Ypartial-unification",
          "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
          "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
          "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
          "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
          "-Xlint:unsound-match", // Pattern match may not be typesafe.
          "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
          "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
          "-Xfuture" // Turn on future language features.
        )
      case _ => Nil
    }),
    scalacOptions in (Compile, console) ~= (_.filterNot(
      Set(
        "-Ywarn-unused:imports",
        "-Xfatal-warnings"
      )
    ).filterNot(_.contains("paradise"))),
    scalacOptions in (Test, console) ~= (_.filterNot(
      Set(
        "-Ywarn-unused:imports",
        "-Xfatal-warnings"
      )
    ).filterNot(_.contains("paradise")))
  )
  .jsSettings(
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC3"
  )

lazy val boxTablesJS = boxTables.js
lazy val boxTablesJVM = boxTables.jvm

lazy val root = project
  .settings(
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .aggregate(boxTablesJS, boxTablesJVM)
