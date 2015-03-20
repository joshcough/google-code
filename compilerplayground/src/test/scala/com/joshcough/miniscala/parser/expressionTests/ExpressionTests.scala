//package com.joshcough.miniscala.expressionTests
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
//object MiniScalaExpressionTests extends CompilerTest[MiniScalaParser] {
//  def parse(parser: MiniScalaParser) = new {
//    def getTree: Object = parser.expression.getTree
//  }
//
//  def tests = Array(
//    "5" --> int(5),
//    "x" --> id("x"),
//    "\"y\"" --> string("y"),
//    "x.y" --> dot(id("x"), id("y")),
//    "x()" --> apply_exp(id("x"), apply_params()),
//    "x()()" --> apply_exp(id("x"), apply_params(), apply_params()),
//    "x.y.z" --> dot( dot(id("x"), id("y")), id("z") ),
//    "x.y.z()" --> dot( dot(id("x"), id("y")), apply_exp(id("z"), apply_params()) ),
//    "x.y.z(x,y)" --> dot( dot(id("x"), id("y")), apply_exp(id("z"), apply_params(id("x"), id("y"))))
//    )
//}
//
//object TestGrammarExpressionTests extends CompilerTest[TestParser] {
//  def parse(parser: TestParser) = new {
//    def getTree: Object = parser.expression.getTree
//  }
//
//  def tests = Array(
////    "5" --> int(5),
////    "x" --> id("x"),
////    "'y'" --> "(STRING 'y')",
////    "5.x" --> "(. (INT 5) (ID x))",
////    "x.y" --> "(. (ID x) (ID y))",
////    "'x'.y.z" --> "(. (. (STRING 'x') (ID y)) (ID z))",
////    "x.y.z()" --> dot( dot(id("x"), id("y")), apply_exp(id("z")) ),
////    "x.y.z(x,y)" --> dot( dot(id("x"), id("y")), apply_exp(id("z"), id("x"), id("y")) )
//    )
//}
//
//
//class MiniScalaExpressionTest extends MiniScalaCompilerTestRunner(MiniScalaExpressionTests)
//class TestGrammarExpressionTest extends TestGrammarCompilerTestRunner(TestGrammarExpressionTests)
