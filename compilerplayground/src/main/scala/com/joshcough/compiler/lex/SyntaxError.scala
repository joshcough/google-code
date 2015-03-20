package com.joshcough.compiler.lex

/**
 * @author dood
 * Date: May 12, 2009
 * Time: 7:49:28 PM
 */

trait SyntaxErrorHander{
  def syntaxError(message: String, unit: CompilationUnit, line: String, offset: Int):Unit = {
    //println("Syntax Error(" + message + " " + unit + ", line:" + line + ", offset:" + offset + ")")
  }
}

trait AddToCompilationUnitSyntaxErrorHander extends SyntaxErrorHander{
  override def syntaxError(message: String, unit: CompilationUnit, line: String, offset: Int):Unit = {
    super.syntaxError(message, unit, line, offset)
    unit.syntaxError(message, line, offset)
  }
}
