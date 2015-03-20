package com.joshcough.compiler.lex.scala

/**
 * @author dood
 * Date: May 14, 2009
 * Time: 8:59:05 PM
 */


trait ScalaIdentifinder extends Identifinder {

  def findIdentifier(lexContext:LexContext): Option[Lexeme] = {
    Identifinder.findIdentifier(lexContext)
  }

  object Identifinder extends CharConsumer
  {
    val opChars = List('!', '~', '%', '^', '&', '*', '>', '<', '+',
      '/', '\\', '|', '?', '=', ':', '#', '-')

    def findIdentifier(lexContext:LexContext): Option[Lexeme] = {
      lexContext.nextChar match {
        case None => None
        case Some(c) => {
          if (!(isValidIdenfitierStartChar(c))) None
          else if (c == '_') findIdentifierStartingWithUnderscore(lexContext.restOfCurrentLine)
          else Some(internalFindIdent(lexContext.restOfCurrentLine))
        }
      }
    }

    def isValidIdenfitierStartChar(c: Char): Boolean = {
      c == '_' || c.isLetter || isOpChar(c)
    }

    def isValidIdenfitierChar(c: Char): Boolean = {
      c.isLetter || Character.isDigit(c) || isOpChar(c)
    }

    def isLetterOrNumber(c: Char): Boolean = {
      c.isLetter || Character.isDigit(c)
    }

    def isOpChar(c: Char): Boolean = opChars.contains(c)

    def isOpCharOption(o: Option[Char]): Boolean = o match {
      case None => false
      case Some(c) => opChars.contains(c)
    }

    def internalFindIdent(chars: LineOfCode): Lexeme = {
      if (isOpCharOption(chars.nextChar)) getOperator(chars)
      else Identifier(getIdentifierValue(chars))
    }

    def getOperator(chars: LineOfCode): Lexeme = {
      val op = getOperatorString(chars)
      if (op == "=>") Rocket else Identifier(op)
    }

    def getOperatorString(chars: LineOfCode): String = {
      val (op:String, nextChar:Option[Char]) = consumeWhile(chars, isOpChar _)
      op
    }

    def getIdentifierValue(chars: LineOfCode): String = {
      val (id, nextChar) = consumeWhile(chars, isValidIdenfitierChar _)
      nextChar match {
        case Some('_') => {
          val nextChars = chars.skip(id.length + 1)
          if (isOpCharOption(nextChars.nextChar)) id + "_" + getOperatorString(nextChars)
          else id + "_" + getIdentifierValue(nextChars)
        }
        case None => id
      }
    }

    def findIdentifierStartingWithUnderscore(chars: LineOfCode): Option[Lexeme] = {
      chars.skip(1).nextChar match {
        case Some(c) => {
          if (isLetterOrNumber(c)) Some(Identifier("_" + getIdentifierValue(chars skip 1)))
          else None
        }
        case _ => None
      }
    }
  }
}
