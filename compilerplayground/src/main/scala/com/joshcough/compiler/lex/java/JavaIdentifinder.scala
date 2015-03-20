package com.joshcough.compiler.lex.java

/**
 * @author dood
 * Date: May 15, 2009
 * Time: 10:52:20 AM
 */

trait JavaIdentifinder extends Identifinder {

  def findIdentifier(chars: LineOfCode): Option[Lexeme] = Identifinder.findIdentifier(chars)

  object Identifinder extends CharConsumer
  {

    def findIdentifier(chars: LineOfCode): Option[Lexeme] = {
      chars.nextChar match {
        case None => None
        case Some(c) => {
          if (!(isValidIdenfitierStartChar(c))) None
          else Some(Identifier(getIdentifierValue(chars)))
        }
      }
    }

    def isValidIdenfitierStartChar(c: Char): Boolean =  c.isLetter || c == '_' || c == '$'

    def isValidIdenfitierChar(c: Char): Boolean = {
      c.isLetter || Character.isDigit(c) || c == '_' || c == '$'
    }

    def isLetterOrNumber(c: Char): Boolean = c.isLetter || Character.isDigit(c)

    def getIdentifierValue(chars: LineOfCode): String = {
      val (id, nextChar) = consumeWhile(chars, isValidIdenfitierChar _)
      id
    }
  }
}
