/*
 * Created by IntelliJ IDEA.
 * User: joshcough
 * Date: May 14, 2009
 * Time: 4:35:20 PM
 */
package com.joshcough.compiler.lex

trait SyntaxErrorTests extends LexerTest {
  checkErrors("'``'",
    SyntaxError("unrecognized character", "'``'", 0),
    SyntaxError("unrecognized character", "'``'", 1),
    SyntaxError("unrecognized character", "'``'", 2),
    SyntaxError("unrecognized character", "'``'", 3))
}