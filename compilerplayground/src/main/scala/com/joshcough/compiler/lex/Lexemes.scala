package com.joshcough.compiler.lex


import symtab.{Keyword, Keywords}

/**
 * @author dood
 * Date: May 10, 2009
 * Time: 4:48:37 PM
 */

trait Lexeme {
  val data: String
  def size = data.size
}

object Lexeme {
  def apply(data: String) = new Lexeme {
    val data: String = data
  }
}

case class Id(val data: String) extends Lexeme

object Identifier{
  import symtab.Keywords
  def apply(word: String): Lexeme = Keywords.keywordOrIdentifier(word, new Id(word))
}

case class Number(data: String) extends Lexeme

case class StringLiteral(data: String) extends Lexeme{
  override def size = data.size + 2 // starts and ends with a quote
}

case class UnclosedStringLiteral(data: String) extends Lexeme{
  override def size = data.size + 1 // starts with a quote
}

class SimpleLexeme(val data: String) extends Lexeme

case object ClassKeyword extends SimpleLexeme("class") with Keyword
case object ObjectKeyword extends SimpleLexeme("object") with Keyword

case object Case    extends SimpleLexeme("case") with Keyword
case object ClassDef   extends SimpleLexeme("class") with Keyword
case object Catch   extends SimpleLexeme("catch") with Keyword
case object Extends extends SimpleLexeme("extends") with Keyword
case object Import  extends SimpleLexeme("import") with Keyword
case object New     extends SimpleLexeme("new") with Keyword
case object Package extends SimpleLexeme("package") with Keyword
case object Throw   extends SimpleLexeme("throw") with Keyword
case object Try     extends SimpleLexeme("try") with Keyword
case object Val     extends SimpleLexeme("val") with Keyword

case object Rocket extends SimpleLexeme("=>")
case object EOFLex extends SimpleLexeme("EOF")

case class WhiteSpace(ws: Char) extends CharLexeme(ws)
case object NewLine extends CharLexeme('\n')

case object Underscore extends CharLexeme('_')
case object Question extends CharLexeme('?')
case object Dot extends CharLexeme('.')
case object Eq extends CharLexeme('=')
case object Comma extends CharLexeme(',')
case object Semi extends CharLexeme(';')
case object Colon extends CharLexeme(':')
case object LeftParen extends CharLexeme('(')
case object RightParen extends CharLexeme(')')
case object LeftCurly extends CharLexeme('{')
case object RightCurly extends CharLexeme('}')
case object LeftBrace extends CharLexeme('[')
case object RightBrace extends CharLexeme(']')

abstract class CharLexeme(val c: Char) extends Lexeme {
  val data = c.toString

  def find(lexContext: LexContext): Option[Lexeme] = {
    lexContext.nextChar match {
      case Some(ch) => if (ch == c) Some(this) else None
      case _ => None
    }
  }
}
