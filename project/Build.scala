import sbt._
import Keys._

//import com.typesafe.sbt.pgp.PgpKeys._

import scala.scalajs.sbtplugin.env.nodejs.NodeJSEnv
import scala.scalajs.sbtplugin.env.phantomjs.PhantomJSEnv
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

object ScalajsSnapSVG extends Build {

  val Scala211 = "2.11.2"

  type PE = Project => Project

  def commonSettings: PE =
    _.settings(scalaJSSettings: _*)
      .settings(
        // Note: 'React' had "com.github.japgolly.scalajs-react" but there's no need for the project name here. AK100814
        //
        organization       := "com.github.akauppi",
        
        // TBD: Should we version individually (React does) or mirror the mainstream (JavaScript library's) version
        //      and simply treat 'scala.js' versioning as packaging? AK100814
        //
        version            := "0.1-SNAPSHOT",     // note: could be "0.3.0-1...n"
        homepage           := Some(url("https://github.com/akauppi/scalajs-snapsvg")),
        
        // TBD: License still open (maybe MIT, maybe EPFL)
        //
        //licenses           += ("MIT", url("http://opensource.org/licenses/MIT")),

        scalaVersion       := Scala211,
        crossScalaVersions := Seq( /*"2.10.4",*/ Scala211),    // not testing with Scala 2.10 (TBD: should we?)
        scalacOptions     ++= Seq("-deprecation", "-unchecked", "-language:_"))

  def preventPublication: PE =
    _.settings(
      publishArtifact := false,
      // Note: 'publishLocalSigned' and 'publishSigned' give "not found: value ..." 
      //      AK100814
      //
      //publishLocalSigned := (),       // doesn't work
      //publishSigned := (),            // doesn't work
      packagedArtifacts := Map.empty) // doesn't work - https://github.com/sbt/sbt-pgp/issues/42

  def publicationSettings: PE =
    _.settings(
      publishTo := {
        val nexus = "https://oss.sonatype.org/"
        if (version.value.trim endsWith "SNAPSHOT")
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases"  at nexus + "service/local/staging/deploy/maven2")
      },
      pomExtra :=
        <scm>
          <connection>scm:git:github.com/akauppi/scalajs-snapsvg</connection>
          <developerConnection>scm:git:git@github.com:akauppi/scalajs-snapsvg.git</developerConnection>
          <url>github.com:akauppi/scalajs-snapsvg.git</url>
        </scm>
        <developers>
          <developer>
            <id>akauppi</id>
            <name>Asko Kauppi</name>
          </developer>
        </developers>)

  // Upgraded "0.1.8" -> "0.2.3" for Scala.js 0.5.4 (see http://www.scala-js.org/news/2014/08/29/announcing-scalajs-0.5.4/)
  //    NOTE: also 'project/plugins.scala' needs to have the same version number!!! AK300814
  //
  def utestSettings: PE =
    _.settings(utest.jsrunner.Plugin.utestJsSettings: _*)
      .configure(useSnap("test"))
      .settings(
        libraryDependencies += "com.lihaoyi" %%% "utest" % "0.2.3" % "test",
        requiresDOM := true,    // Note tbd: Scala.js 0.5.4 introduces dependency on 'RuntimeDOM' that is supposed to be used instead of this
                                //       e.g. "jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM"
        jsEnv in Test := customPhantomJSEnv)

  def customPhantomJSEnv: PhantomJSEnv = {
    import scala.scalajs.sbtplugin.env.ExternalJSEnv.RunJSArgs
    import scala.scalajs.tools.io._
    new PhantomJSEnv {
      override protected def initFiles(args: RunJSArgs): Seq[VirtualJSFile] = Seq(
        new MemVirtualJSFile("bindPolyfill.js").withContent(Polyfills.functionBind3)
      )
    }
  }

  def useSnap(scope: String = "compile"): PE =
    _.settings(
      // React has it like this; see 'scala.js' 0.5.3 release notes: http://www.scala-js.org/news/2014/07/30/announcing-scalajs-0.5.3/
      //jsDependencies += "org.webjars" % "react" % "0.11.1" % scope / "react-with-addons.js" commonJSName "React",

      //jsDependencies += "org.webjars" % "Snap.svg" % "0.3.0" % scope / "snap.svg.js" commonJSName "Snap",
      jsDependencies += "org.webjars" % "Snap.svg" % "0.3.0" % scope / "snap.svg.js",

      skip in packageJSDependencies := false)

  lazy val root = Project("root", file("."))
    .aggregate(core, test, scalaesque, example)
    .configure(commonSettings, preventPublication)

  lazy val core = project
    .configure(commonSettings, publicationSettings)
    .settings(
      name := "core",
      libraryDependencies ++= Seq(
        //"org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
        //"com.scalatags" %%% "scalatags" % "0.3.5"
      )
    )

  lazy val scalaesque = project
    .configure(commonSettings, publicationSettings)
    .dependsOn(core)
    .settings(name := "scalaesque")

  lazy val test = project
    .configure(commonSettings, publicationSettings, utestSettings)
    .dependsOn(core)
    .settings(name := "test")

  lazy val example = project
    .configure(commonSettings, useSnap(), preventPublication)
    .dependsOn(core)
    .settings(
      name := "example",
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"
      )
    )
}
