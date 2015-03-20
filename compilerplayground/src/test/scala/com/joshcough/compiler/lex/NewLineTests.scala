/*
 * Created by IntelliJ IDEA.
 * User: joshcough
 * Date: May 14, 2009
 * Time: 4:33:31 PM
 */
package com.joshcough.compiler.lex

trait NewLineTests extends LexerTest {
  checkCode("x,\ny",
    (0, 0) -> Identifier("x"),
    (0, 1) -> Comma,
    (1, 0) -> Identifier("y"))

  checkCode("""x,
    y,
      z""",
    (0, 0) -> Identifier("x"),
    (0, 1) -> Comma,
    (1, 4) -> Identifier("y"),
    (1, 5) -> Comma,
    (2, 6) -> Identifier("z"))

}