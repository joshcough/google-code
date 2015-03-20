/*
 * Created by IntelliJ IDEA.
 * User: joshcough
 * Date: May 10, 2009
 * Time: 12:11:43 PM
 */
package com.joshcough.compiler.lex

import _root_.java.io.File
import org.scalatest.FunSuite
import org.scalatest.matchers.MustMatchers
import scala.ScalaLexer

trait ScalaLexerTester extends LexerTest{
  def lexer(cu: CompilationUnit) = new SimpleLexer(cu) with ScalaLexer
}

trait LexerTest extends FunSuite with MustMatchers {

  def lexer(cu: CompilationUnit): Lexer

  def checkLine(code: String, expectedToks: (Int, Lexeme)*) = test(code) {
    privateCheckCode(code, expectedToks.map(x => {val (pos, lexeme) = x; ((0, pos), lexeme)}): _*)
  }

  def checkCode(code: String, expectedToks: ((Int, Int), Lexeme)*) = test(code) {
    privateCheckCode(code, expectedToks: _*)
  }

  def checkCode(code: File, expectedToks: ((Int, Int), Lexeme)*) = test(code.getName) {
    privateCheckCode(code, expectedToks: _*)
  }


  def checkErrors(code: String, expectedErrors: SyntaxError*) = test("test errors in: " + code) {
    val (compUnit, lexer) = compUnitAndLexer(code)

    var tok = lexer.nextToken
    // case statement here maybe?
    while (tok != EOF(compUnit.loc - 1)) {tok = lexer.nextToken}

    // loop through expected errors here, checking them with actual errors.
    println("syntax errors: " + compUnit.syntaxErrors)
    expectedErrors.toList must equal(compUnit.syntaxErrors)
  }

  private def privateCheckCode(code: String,
                               expectedToks: ((Int, Int), Lexeme)*): CompilationUnit = {
    val (compUnit, lexer) = compUnitAndLexer(code)
    privateCheckCode(compUnit, lexer, expectedToks: _*)
  }

  private def privateCheckCode(code: File,
                               expectedToks: ((Int, Int), Lexeme)*): CompilationUnit = {
    val (compUnit, lexer) = compUnitAndLexer(code)
    privateCheckCode(compUnit, lexer, expectedToks: _*)
  }

  private def privateCheckCode(compUnit: CompilationUnit,
                               lexer: Lexer,
                               expectedToks: ((Int, Int), Lexeme)*): CompilationUnit = {
    expectedToks.foreach {
      x => {
        val nextToken = lexer.nextToken
        //println( "expecting: " + x + " got: " + nextToken)
        val ((ln, pos), lexeme) = x
        nextToken must equal(Token(lexeme, ln, pos))
      }
    }
    if (expectedToks.size > 0) lexer.nextToken must equal(EOF(expectedToks.reverse(0)._1._1))
    compUnit
  }


  private def compUnitAndLexer(code: String): (CompilationUnit, Lexer) = {
    compUnitAndLexer(CompilationUnit(code))
  }

  private def compUnitAndLexer(code: File): (CompilationUnit, Lexer) = {
    compUnitAndLexer(CompilationUnit(code))
  }

  private def compUnitAndLexer(cu: CompilationUnit): (CompilationUnit, Lexer) = (cu, lexer(cu))
}

