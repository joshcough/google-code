package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 14, 2009
 * Time: 9:08:19 PM
 */

trait Identifinder extends LexemeFinder{
  override def finders = super.finders ::: List(findIdentifier _)

  def findIdentifier(lexContext: LexContext): Option[Lexeme]
}