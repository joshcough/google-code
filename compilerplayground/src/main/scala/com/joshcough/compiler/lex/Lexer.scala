package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 9, 2009
 * Time: 3:55:54 PM
 */
abstract class SimpleLexer(cu: CompilationUnit) extends Lexer {
  lexing(cu)
  def this() = this (CompilationUnit())
  def lexing(s: String): Lexer = {lexing(CompilationUnit(s))}
}

trait LexContext {
  val restOfCurrentLine: LineOfCode
  val currentLine: String

  // so far all these things should be private!
  val errorHandler: SyntaxErrorHander
  val unit: CompilationUnit
  val currentOffset: Int

  def nextChar = restOfCurrentLine.nextChar

  def syntaxError(message: String, offsetInCurrentLine: Int) = {
    errorHandler.syntaxError(message, unit, currentLine, currentOffset + offsetInCurrentLine)
  }
}

trait Lexer extends SyntaxErrorHander {

  var unit: CompilationUnit = null
  var walker: CompilationUnitWalker = null

  def finders: List[LexContext => Option[Lexeme]]

  def nextToken: Token = {

    // if we've already lexed the whole file, get the f out.
    if (walker.eofReached_?) return EOF(walker.currentLineNumber)

    // find all possible Lexemes
    val matches = finders.map(f => f(currentContext)).filter(_.isDefined).map(_.get)

    // if we've found no lexemes, syntax error! adjust and try again.
    if (matches.isEmpty) {
      syntaxError("unrecognized character", unit, walker.currentLine, walker.currentOffset)
      walker.skipChar
      return nextToken
    }

    // the longest match found should be on top...i think...
    val longestMatch = matches.sort(_.size >= _.size).head

    // deal with the best lexeme
    handleLexeme(longestMatch)
  }

  def handleLexeme(lex: Lexeme) = lex match {
    case NewLine => {
      walker.skipLine
      nextToken
    }
    case WhiteSpace(_) => {
      walker.skipChar
      nextToken
    }
    case lex => {
      val indexOfLexeme = walker.currentOffset
      walker.skipChars(lex.size)
      Token(lex, walker.currentLineNumber, indexOfLexeme)
    }
  }

  def lexing(unit: CompilationUnit): Lexer = {
    walker = new CompilationUnitWalker(unit)
    this
  }

  def currentContext = new LexContext {
    val errorHandler: SyntaxErrorHander = Lexer.this
    val unit: CompilationUnit = Lexer.this.unit
    val restOfCurrentLine: LineOfCode = walker.restOfCurrentLine
    val currentLine: String = walker.currentLine
    val currentOffset: Int = walker.currentOffset
    val currentLineNumber: Int = walker.currentLineNumber
    val finished_? :Boolean = walker.eofReached_?
  }

  def lex: List[Token] = {
    def lex( acc: List[Token] ): List[Token]= if( currentContext.finished_? ) acc else lex(acc ::: List(nextToken))
    lex(Nil)
  }
}
