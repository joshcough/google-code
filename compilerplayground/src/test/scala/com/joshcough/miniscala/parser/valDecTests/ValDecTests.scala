//package com.joshcough.miniscala.valDecTests
//
//import org.testng.annotations.{Test, DataProvider}
//import PimpedString._
//
///**
// * @author dood
// * Date: Jan 18, 2009
// * Time: 12:35:08 AM
// */
//class ValDeclTest extends MiniScalaCompilerTestRunner(ValDecTests)
//class VarDeclTest extends MiniScalaCompilerTestRunner(VarDecTests)
//
//
//object ValDecTests extends CompilerTest[MiniScalaParser] {
//
//  def parse(parser: MiniScalaParser) = new {
//    def getTree: Object = parser.valDec.getTree
//  }
//
//  def tests = Array(
//    // without types
//    "val a = 5"      --> valDec("a", int(5)),
//    "val b = false"  --> valDec("b", boolean(false)),
//    "val c = true"   --> valDec("c", boolean(true)),
//    "val d = self"   --> valDec("d", id("self")),
//    "val e = randomId"   --> valDec("e", id("randomId")),
//
//    // with types
//    "val x : Int = 5"         --> valDec("x", typ("Int"), int(5)),
//    "val x : Boolean = false" --> valDec("x", typ("Boolean"), boolean(false)),
//    "val x : Boolean = true"  --> valDec("x", typ("Boolean"), boolean(true)),
//    "val d : SomeType = self" --> valDec("d", typ("SomeType"), id("self")),
//    "val e : RandomType = randomId"   --> valDec("e", typ("RandomType"), id("randomId"))
//  )
//}
//
//object VarDecTests extends CompilerTest[MiniScalaParser] {
//
//  def parse(parser: MiniScalaParser) = new {
//    def getTree: Object = parser.varDec.getTree
//  }
//
//  def tests = Array(
//    // without types
//    "var a = 5"      --> varDec("a", int(5)),
//    "var b = false"  --> varDec("b", boolean(false)),
//    "var c = true"   --> varDec("c", boolean(true)),
//    "var d = self"   --> varDec("d", id("self")),
//    "var e = randomId"   --> varDec("e", id("randomId")),
//
//    // with types
//    "var x : Int = 5"         --> varDec("x", typ("Int"), int(5)),
//    "var x : Boolean = false" --> varDec("x", typ("Boolean"), boolean(false)),
//    "var x : Boolean = true"  --> varDec("x", typ("Boolean"), boolean(true)),
//    "var d : SomeType = self" --> varDec("d", typ("SomeType"), id("self")),
//    "var e : RandomType = randomId"   --> varDec("e", typ("RandomType"), id("randomId"))
//  )
//}
//
