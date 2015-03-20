///*
// * Created by IntelliJ IDEA.
// * User: joshcough
// * Date: Mar 20, 2009
// * Time: 10:02:19 AM
// */
//package com.joshcough.miniscala.caseclasses
//
//trait Statement
//
//case class Sobject(name: String, extender: Extender, block: Option[Block]) extends Statement
//case class Sclass(name: String, params: FormalParams, extender: Extender, block: Option[Block]) extends Statement
//case class Method(name: String, params: FormalParams, returnTyp: Typ, exp: Expression) extends Statement
//
//
//////////////////extends code//////////////
//trait Extender
//case object ExtendsNothing extends Extender {
//  def +(ec: ExtendsClause) = new SingleExtender(ec)
//}
//case class SingleExtender(clause: ExtendsClause) extends Extender {
//  def +(wc: WithClause) = new MultiExtender(clause, wc)
//}
//case class MultiExtender(clause: ExtendsClause, withClauses: WithClause*) {
//  def +(wc: WithClause) = new MultiExtender(clause, (withClauses ++ Array(wc)): _*)
//}
//case class ExtendsClause(typ: Typ, expressions: Expression*)
//case class WithClause(typ: Typ)
////////////////////////////////////////////
//
//case class FormalParams(params: FormalParam*)
//case class FormalParam(name: String, t: Typ)
//
//case class Typ(name: String)
//object IntTyp extends Typ("Int")
//object BooleanTyp extends Typ("Boolean")
//object StringTyp extends Typ("String")
//
//case class ValDec(name: String, typ: Option[Typ], exp: Expression) extends Statement
//case class VarDec(name: String, typ: Option[Typ], exp: Option[Expression]) extends Statement
//
//trait Expression extends Statement
//object NoExpression extends Expression
//case class Block(statements: Statement*) extends Expression
//
//case class DotExpression(left: Expression, right: Expression) extends Expression
//case class ParamExpression(id: Id, expressions: Expression*) extends Expression
//case class IntConstant(i: Int) extends Expression
//case class Id(name: String) extends Expression
//
//class ASTTest {
//  @org.testng.annotations.Test def astTest = println(m)
//
//  @org.testng.annotations.Test def astClassTest = {
//    println(c)
//    println(c2)
//  }
//
//  val m = Method("m",
//    FormalParams(FormalParam("x", IntTyp)),
//    IntTyp,
//    Block(
//      ValDec("y", Some(IntTyp), IntConstant(5)),
//      DotExpression(Id("y"), ParamExpression(Id("+"), Id("x")))
//      )
//    )
//
//  val c = Sclass("Dood",
//    FormalParams(FormalParam("x", IntTyp)),
//    ExtendsNothing,
//    Some(Block(ValDec("y", Some(IntTyp), IntConstant(5))))
//    )
//
//  val c2 = Sclass("C2",
//    FormalParams(FormalParam("x", IntTyp)),
//    SingleExtender( ExtendsClause( Typ("C") )),
//    Some(Block(ValDec("y", Some(IntTyp), IntConstant(5))))
//    )
//}
////
////object H {
////  def i(x: Int) = 675
////}
////class A(x: Int)
////class B extends A(5 * 7 * 9)
////class C extends A({
////  class X {
////    val t = 7
////  }
////  H.i(42) * 42
////}
////  )
