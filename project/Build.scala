import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
  val appName         = "hatena-api-sample"
  val appVersion      = "1.0-SNAPSHOT"
  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.apache.abdera" % "abdera-bundle" % "1.1.3"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )
}
