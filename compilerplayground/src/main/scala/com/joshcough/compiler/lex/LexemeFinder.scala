package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 10, 2009
 * Time: 4:41:20 PM
 */

trait LexemeFinder {
  def finders: List[LexContext => Option[Lexeme]] = List()
}

trait CharConsumer {
  val continue = true
  val stop = false


  def consumeWhile(chars: LineOfCode, f: Char => Boolean): (String, Option[Char]) = {
    consumeWhile2(chars, (oc: Option[Char]) => oc match {
      case Some(c) => f(c)
      case _ => false
    })
  }

  def consumeWhile2(chars: LineOfCode, f: Option[Char] => Boolean): (String, Option[Char]) = {

    def consumeWhile(acc: List[Char], chars: LineOfCode,
                     f: Option[Char] => Boolean): (String, Option[Char]) = {
      if (f(chars.nextChar)) {
        consumeWhile(acc ::: List(chars.nextChar.get), chars.skip(1), f)
      } else {
        (acc.mkString(""), chars.nextChar)
      }
    }
    consumeWhile(List[Char](), chars, f)
  }

}

trait LetterOrNumberFinder {
  def isLetterOrNumber(c: Char): Boolean = c.isLetter || Character.isDigit(c)

  def isLetterOrNumber(c: Option[Char]): Boolean = c match {
    case None => false
    case Some(c) => isLetterOrNumber(c)
  }
}

object CharFinders {
  def apply(lexemes: CharLexeme*): List[LexContext => Option[Lexeme]] = lexemes.map(_.find _).toList
}

trait WhiteSpaceFinder extends LexemeFinder {
  override def finders = super.finders ::: List(findWhiteSpace _)

  def findWhiteSpace(context: LexContext): Option[Lexeme] = {
    context.restOfCurrentLine.nextChar match {
      case Some('\n') => Some(NewLine)
      case Some(' ') => Some(WhiteSpace(' '))
      case Some('\t') => Some(WhiteSpace('\t'))
      case _ => None
    }
  }
}