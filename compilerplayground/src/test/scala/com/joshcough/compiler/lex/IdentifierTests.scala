package com.joshcough.compiler.lex


import scala.{CaseClass, CaseObject}

/**
 * @author dood
 * Date: May 14, 2009
 * Time: 4:43:41 PM
 */

trait IdentifierTests extends LexerTest {

  //test("ident") {
  checkLine("case", 0 -> Identifier("case"))
  checkLine("fwef_^&*", 0 -> Identifier("fwef_^&*"))
  checkLine("::", 0 -> Identifier("::"))
  checkLine("_x_", 0 -> Identifier("_x_"))

  checkLine("case class ^^^{}",
    0 -> CaseClass,
    11 -> Identifier("^^^"),
    14 -> LeftCurly,
    15 -> RightCurly)

  checkLine("case class    ^^^{}",
    0 -> CaseClass,
    14 -> Identifier("^^^"),
    17 -> LeftCurly,
    18 -> RightCurly)

  checkLine("case object    ^^^{}",
    0 -> CaseObject,
    15 -> Identifier("^^^"),
    18 -> LeftCurly,
    19 -> RightCurly)

  // this one is a weird case...but the test is correct
  // there should be a space between :: and :String
  //test("check weird : cases") {

  checkLine("def :::String",
    0 -> Identifier("def"),
    4 -> Identifier(":::"),
    7 -> Identifier("String"))

  checkLine("def :: :String",
    0 -> Identifier("def"),
    4 -> Identifier("::"),
    7 -> Colon,
    8 -> Identifier("String"))

  checkLine("def ::={}",
    0 -> Identifier("def"),
    4 -> Identifier("::="),
    7 -> LeftCurly,
    8 -> RightCurly)

  checkLine("def :: ={}",
    0 -> Identifier("def"),
    4 -> Identifier("::"),
    7 -> Eq,
    8 -> LeftCurly,
    9 -> RightCurly)

  //test("crazy methods") {
  checkLine("x ^:-> y",
    0 -> Identifier("x"),
    2 -> Identifier("^:->"),
    7 -> Identifier("y"))

  checkLine("x.^:->(y)",
    0 -> Identifier("x"),
    1 -> Dot,
    2 -> Identifier("^:->"),
    6 -> LeftParen,
    7 -> Identifier("y"),
    8 -> RightParen)

  checkLine("def --> :String",
    0 -> Identifier("def"),
    4 -> Identifier("-->"),
    8 -> Colon,
    9 -> Identifier("String"))


  //  test("identifiers") {
  checkLine("qwidjqw_qwoidjqd_qdqwd", 0 -> Identifier("qwidjqw_qwoidjqd_qdqwd"))
  checkLine("qwidjqw_123_qdqwd", 0 -> Identifier("qwidjqw_123_qdqwd"))
  checkLine("qwidjqw_xx123xx_qdqwd", 0 -> Identifier("qwidjqw_xx123xx_qdqwd"))
  checkLine("qwidjqw_^*&", 0 -> Identifier("qwidjqw_^*&"))
  checkLine("-->", 0 -> Identifier("-->"))
  //check("qwidjqw_^$&", 0 -> Identifier("qwidjqw_^"))

  //test("ids starting with underscores") {
  checkLine("_1", 0 -> Identifier("_1"))
  checkLine("_ab", 0 -> Identifier("_ab"))
  checkLine("_1_ab", 0 -> Identifier("_1_ab"))
  checkLine("_1_ab_^^", 0 -> Identifier("_1_ab_^^"))


}