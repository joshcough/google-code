package com.joshcough.compiler.lex.java

/**
 * @author dood
 * Date: May 15, 2009
 * Time: 10:51:23 AM
 */

trait JavaLexer extends Lexer with JavaFinders with AddToCompilationUnitSyntaxErrorHander

trait JavaFinders extends JavaCharFinders with NumberFinder with JavaIdentifinder with WhiteSpaceFinder

trait JavaCharFinders extends LexemeFinder {
  override def finders = super.finders ::: CharFinders(
      Underscore, Comma, Dot, Eq, Semi, Colon, LeftParen,
      RightParen, LeftCurly, RightCurly, LeftBrace, RightBrace, Question
  )
}
