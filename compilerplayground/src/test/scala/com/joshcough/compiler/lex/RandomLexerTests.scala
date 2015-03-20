package com.joshcough.compiler.lex


import scala.CaseClass

/**
 * @author dood
 * Date: May 14, 2009
 * Time: 4:38:08 PM
 */

class AllLexerTests extends RandomLexerTests with StringTests with
    NewLineTests with SyntaxErrorTests with
    IdentifierTests with ReservedCharacterTests with ScalaLexerTester

trait RandomLexerTests extends LexerTest {

  test("white space") {
    val lexer = new SimpleLexer(CompilationUnit(" ")) with WhiteSpaceFinder
    lexer.nextToken must equal(EOF(0))
  }

  checkLine("object x",
    0 -> Identifier("object"),
    7 -> Identifier("x"))

  checkLine("case class Xyz{}",
    0 -> CaseClass,
    11 -> Identifier("Xyz"),
    14 -> LeftCurly,
    15 -> RightCurly)

  checkLine("case class", 0 -> CaseClass)

  checkLine("trait X extends Y[_]",
    0 -> Identifier("trait"),
    6 -> Identifier("X"),
    8 -> Identifier("extends"),
    16 -> Identifier("Y"),
    17 -> LeftBrace,
    18 -> Underscore,
    19 -> RightBrace)

  // match statement
  checkLine("x match { case _ => x }",
    0 -> Identifier("x"),
    2 -> Identifier("match"),
    8 -> LeftCurly,
    10 -> Identifier("case"),
    15 -> Underscore,
    17 -> Rocket,
    20 -> Identifier("x"),
    22 -> RightCurly)

  // numbers
  checkLine("12345", 0 -> Number("12345"))
  checkLine("123 + 456",
    0 -> Number("123"),
    4 -> Identifier("+"),
    6 -> Number("456"))
  checkLine("123.45",
    0 -> Number("123.45"))
}