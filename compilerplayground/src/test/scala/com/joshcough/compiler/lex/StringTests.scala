package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 17, 2009
 * Time: 8:30:46 PM
 */

trait StringTests extends LexerTest {

  checkLine("\"hey doods\"", 0 -> StringLiteral("hey doods"))
  checkLine("\"   \"", 0 -> StringLiteral("   "))

  
  checkErrors("\"res", SyntaxError("unclosed string literal", "res", 0))
  checkErrors("x") // No Errors
  checkErrors("\"", SyntaxError("unclosed string literal", "", 0))
}