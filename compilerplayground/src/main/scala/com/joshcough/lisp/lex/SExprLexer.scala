package com.joshcough.lisp.lex

import compiler.CompilationUnit
import compiler.lex._

case class SExprLexer(cu:CompilationUnit) extends SimpleLexer(cu)
                                             with Lexer with SExprFinders with AddToCompilationUnitSyntaxErrorHander

trait SExprFinders extends SExprCharFinders with WhiteSpaceFinder with AtomFinder

trait SExprCharFinders extends LexemeFinder {
  override def finders = super.finders ::: CharFinders(LeftParen, RightParen)
}

case class Atom(data: String) extends Lexeme

trait AtomFinder extends LexemeFinder with CharConsumer with LetterOrNumberFinder {
  override def finders = super.finders ::: List((lc: LexContext) => {
    lc.nextChar match {
      case None => None
      case Some(c) => {
        if (!(isLetterOrNumber(c))) None
        else Some(Atom(consumeWhile(lc.restOfCurrentLine, isLetterOrNumber _)._1))
      }
    }
  })
}