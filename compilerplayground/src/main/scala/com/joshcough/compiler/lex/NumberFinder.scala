package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 12, 2009
 * Time: 3:30:26 PM
 */

trait NumberFinder extends LexemeFinder {
  override def finders = super.finders ::: List(NumberFinder.findIdent _)
}

object NumberFinder extends CharConsumer{
  def findIdent(lexContext: LexContext): Option[Lexeme] = {
    lexContext.nextChar match {
      case None => None
      case Some(c) => if (!(isValidNumberStartChar(c))) None else Some(internalFindNumber(lexContext))
    }
  }

  def isValidNumberStartChar(c: Char): Boolean = Character.isDigit(c)

  def isValidNumberChar(c: Char): Boolean = Character.isDigit(c)

  def internalFindNumber(lexContext: LexContext): Number = {
    val (num, nextChar) = consumeWhile(lexContext.restOfCurrentLine, isValidNumberChar _)
    nextChar match {
      case Some('.') => {
        Number( num + "." + consumeWhile(lexContext.restOfCurrentLine.skip(num.length + 1), isValidNumberChar _)._1)
      }
      case _ => Number(num)
    }
  }
}
