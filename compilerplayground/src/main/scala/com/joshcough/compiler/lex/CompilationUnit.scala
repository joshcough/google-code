package com.joshcough.compiler.lex

import _root_.java.io.File
import io.Source


trait CompilationUnitWalkerInterface {
  def skipChar: Unit

  def skipChars(i: Int): Unit

  def skipLine: Unit

  def restOfCurrentLine: LineOfCode

  def currentLineNumber: Int

  def currentOffset: Int

  def eofReached_? : Boolean
}

case class CompilationUnitWalker(unit: CompilationUnit) extends CompilationUnitWalkerInterface {
  assert(unit != null)

  private val lines: Iterator[LineOfCode] = unit.code.elements
  private var finishedVar = false
  private var currentLineVar = lines.next
  private var currentLineRestVar = currentLineVar
  private var currentLineNumberVar = 0
  private var currentIndexVar = 0

  def skipChar = skipChars(1)

  def skipChars(i: Int) {
    assert(!eofReached_?)
    if (restOfCurrentLine.size > i) {
      currentIndexVar += i;
      currentLineRestVar = currentLineRestVar skip i
    }
    else if (lines.hasNext) skipLine
    else finishedVar = true
  }

  def skipLine {
    assert(!eofReached_?)
    if (lines.hasNext) {
      currentLineRestVar = lines.next
      currentIndexVar = 0
      currentLineNumberVar += 1
    }
    else finishedVar = true
  }

  def currentLine: String = currentLineVar.toString

  def restOfCurrentLine: LineOfCode = currentLineRestVar

  def currentLineNumber: Int = currentLineNumberVar

  def currentOffset: Int = currentIndexVar

  def eofReached_? : Boolean = finishedVar
}
