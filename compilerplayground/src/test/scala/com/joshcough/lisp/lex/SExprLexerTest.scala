package com.joshcough.lisp.lex

import compiler.CompilationUnit
import compiler.lex.{RightParen, LeftParen, LexerTest}

class SExprLexerTester extends LexerTest {
  def lexer(cu: CompilationUnit) = SExprLexer(cu)

  checkLine("case", 0 -> Atom("case"))
  checkLine("(", 0 -> LeftParen)
  checkLine(")", 0 -> RightParen)
  checkLine("()",   0 -> LeftParen, 1 -> RightParen)
  checkLine("( )",  0 -> LeftParen, 2 -> RightParen)
  checkLine("(  )", 0 -> LeftParen, 3 -> RightParen)

  checkLine("lambda", 0 -> Atom("lambda"))

  checkLine("(case)",
           0 -> LeftParen,
           1 -> Atom("case"),
           5 -> RightParen)

  checkLine("(case class)",
           0  -> LeftParen,
           1  -> Atom("case"),
           6  -> Atom("class"),
           11 -> RightParen)

  checkLine("(lambda (x) (f x))",
           0  -> LeftParen,
           1  -> Atom("lambda"),
           8  -> LeftParen,
           9  -> Atom("x"),
           10 -> RightParen,
           12 -> LeftParen,
           13 -> Atom("f"),
           15 -> Atom("x"),
           16 -> RightParen,
           17 -> RightParen)
}