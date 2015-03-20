package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 11, 2009
 * Time: 11:56:09 PM
 */

object TwoPartIdentifinder {
  def apply(lex: IdentifierOnlyLexer,
            w1: String,
            w2: String,
            eme: Lexeme): TwoPartIdentifinder = new TwoPartIdentifinder {
    val lexer: IdentifierOnlyLexer = lex
    val word1: String = w1
    val word2: String = w2
    val lexeme: Lexeme = eme
  }
}


trait TwoPartIdentifinder {
  
  val lexer: IdentifierOnlyLexer
  val word1: String
  val word2: String
  val lexeme: Lexeme

  def find(lexContext:LexContext): Option[Lexeme] = {

    //println(this + "(" + lexer + ") trying to find: " + word1 + " " + word2 + " in " + chars)

    if (lexContext.restOfCurrentLine.size < (word1.length + word2.length + 1)) return None

    lexer.lexing(CompilationUnit(lexContext.restOfCurrentLine))

    val (tok1, tok2) = (lexer.nextToken, lexer.nextToken)

    //println("tok1: " + tok1 + " tok2: " + tok2)

    (tok1, tok2) match {
      case (Token(Id(w1), _, _), Token(Id(w2), _, _)) => {
        if (w1 == word1 && w2 == word2) Some(lexeme) else None
      }
      case _ => None
    }
  }
}

trait IdentifierOnlyLexer extends Lexer with WhiteSpaceFinder with Identifinder


