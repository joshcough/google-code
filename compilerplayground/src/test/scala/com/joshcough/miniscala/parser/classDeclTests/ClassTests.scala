//package com.joshcough.miniscala.classDeclTests
//
//import org.testng.annotations.{Test, DataProvider}
//import PimpedString._
//
///**
// * @author dood
// * Date: Jan 18, 2009
// * Time: 12:40:36 AM
// */
//
//object ClassTests extends CompilerTest[MiniScalaParser] {
//
//  def parse(parser: MiniScalaParser) = new {
//    def getTree: Object = parser.classDecl.getTree
//  }
//
//  def tests = Array(
//    "class X"     --> clas("X"),
//    "class Y(s:String)" --> clas( "Y", params(param("s", "String"))),
//    "class Y(s:String){ 5 }" --> clas( "Y",
//                                   params(param("s", "String")),
//                                   block(int(5))
//                                 )
//    )
//}
//
//class ClassTest extends MiniScalaCompilerTestRunner(ClassTests)