//package com.joshcough.miniscala.primitiveTests
//
//
//import org.testng.annotations.{Test, DataProvider}
//import PimpedString._
//
///**
// * @author dood
// * Date: Jan 20, 2009
// * Time: 9:45:51 PM
// */
//
//object PrimitiveTests extends CompilerTest[MiniScalaParser] {
//
//  def parse(parser: MiniScalaParser) = new {
//    def getTree: Object = parser.primitive.getTree
//  }
//
//  def tests = Array(
//    "5"      --> int(5),
//    "true"   --> boolean(true),
//    "false"  --> boolean(false),
//    "mother" --> id("mother"),
//    "self"   --> id("self")
//  )
//}
//
//class PrimitiveTest extends MiniScalaCompilerTestRunner(PrimitiveTests)