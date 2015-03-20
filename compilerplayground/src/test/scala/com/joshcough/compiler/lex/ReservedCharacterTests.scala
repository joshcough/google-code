/*
 * Created by IntelliJ IDEA.
 * User: joshcough
 * Date: May 14, 2009
 * Time: 4:49:38 PM
 */
package com.joshcough.compiler.lex

trait ReservedCharacterTests extends LexerTest {
  checkLine("(", 0 -> LeftParen)

  checkLine(")", 0 -> RightParen)

  checkLine("{", 0 -> LeftCurly)

  checkLine("}", 0 -> RightCurly)

  checkLine("[", 0 -> LeftBrace)

  checkLine("]", 0 -> RightBrace)

  checkLine("()", 0 -> LeftParen, 1 -> RightParen)

  checkLine("}({)[]",
    0 -> RightCurly,
    1 -> LeftParen,
    2 -> LeftCurly,
    3 -> RightParen,
    4 -> LeftBrace,
    5 -> RightBrace)

  checkLine("x,y",
    0 -> Identifier("x"),
    1 -> Comma,
    2 -> Identifier("y"))
}