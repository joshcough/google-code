//package com.joshcough.miniscala.methodDeclTests
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
//object MethodDeclTests extends CompilerTest[MiniScalaParser] {
//
//  def parse(parser: MiniScalaParser) = new {
//    def getTree: Object = parser.methodDecl.getTree
//  }
//
//  def tests = Array(
//    "def m = 5"     --> method("m", int(5)),
//    "def m = { 5 }" --> method("m", block(int(5))),
//    "def m = { s() \n x() }" -->
//        method("m", block(apply_exp(id("s"),apply_params()), apply_exp(id("x"),apply_params()))),
//    "def m = { s(); x() }" -->
//        method("m", block(apply_exp(id("s"),apply_params()), apply_exp(id("x"),apply_params()))),
//    "def m( s: String ) = s" --> method( "m",
//                                   params(param("s", "String")),
//                                   id( "s" ))
//    //"def m = { s(\n) \n x(\n) }" --> method("m", block(apply_exp(id("s")), apply_exp(id("x")))),
//    )
//}
//
//class MethodDeclTest extends MiniScalaCompilerTestRunner(MethodDeclTests)