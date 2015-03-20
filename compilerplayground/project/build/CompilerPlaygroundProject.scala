import sbt._

class CompilerPlaygroundProject(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {
  val st = "org.scala-tools.testing" % "scalatest" % "0.9.5" % "test->default"
  override def parallelExecution = true
  override def crossScalaVersions = Set("2.7.5")
  val sxr = compilerPlugin("org.scala-tools.sxr" %% "sxr" % "0.2.1")
  //override def includeTest(name: String) = name startsWith "com.joshcough.compiler.lex.sexpr"

  /*
 override def includeTest( name: String ) = {
   def exclude(names:String*): Boolean = ! names.find(name startsWith _ ).isDefined

   exclude("org.scalatest.testng.example",
           "org.scalatest.testng.testpackage",
           "org.scalatest.tools",
           "org.scalatest.junit.helpers",
           "org.scalatest.PackageAccess")
 } */
}
