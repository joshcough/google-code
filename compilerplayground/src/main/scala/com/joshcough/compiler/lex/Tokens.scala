package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 10, 2009
 * Time: 3:43:15 PM
 */
case class Token(lex: Lexeme, lineNumber: Int, offset: Int){
  def this(lex: Lexeme) = this(lex, 0, 0)
}

case class EOF(line: Int) extends Token( EOFLex, line, 0 )
