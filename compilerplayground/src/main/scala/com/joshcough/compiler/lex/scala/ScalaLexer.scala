package com.joshcough.compiler.lex.scala

/**
 * @author dood
 * Date: May 14, 2009
 * Time: 8:51:21 PM
 */
trait ScalaLexer extends Lexer with ScalaFinders with AddToCompilationUnitSyntaxErrorHander

trait ScalaFinders extends
    ScalaCharFinders with NumberFinder with ScalaIdentifinder with
    WhiteSpaceFinder with CaseClassFinder with CaseObjectFinder with ScalaStringFinder

trait ScalaCharFinders extends LexemeFinder {
  override def finders = super.finders ::: CharFinders(
    Underscore, Comma, Dot, Eq, Semi, Colon, LeftParen,
    RightParen, LeftCurly, RightCurly, LeftBrace, RightBrace
    )
}

object ScalaTwoPartIdentifinder {
  def apply(w1: String, w2: String, eme: Lexeme) = {
    TwoPartIdentifinder(new SimpleLexer with IdentifierOnlyLexer with ScalaIdentifinder, w1, w2, eme)
  }
}

case object CaseClass extends SimpleLexeme("case class")
case object CaseObject extends SimpleLexeme("case object")


trait CaseClassFinder extends LexemeFinder {
  override def finders =
    super.finders ::: List(ScalaTwoPartIdentifinder("case", "class", CaseClass).find _)
}

trait CaseObjectFinder extends LexemeFinder {
  override def finders =
    super.finders ::: List(ScalaTwoPartIdentifinder("case", "object", CaseObject).find _)
}