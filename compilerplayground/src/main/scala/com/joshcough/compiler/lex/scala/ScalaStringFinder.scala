package com.joshcough.compiler.lex.scala

/**
 * @author dood
 * Date: May 15, 2009
 * Time: 3:22:59 PM
 */

trait ScalaStringFinder extends LexemeFinder {
  override def finders = super.finders ::: List(StringFinder.findString _)

  object StringFinder extends CharConsumer
  {
    def findString(lexContext: LexContext): Option[Lexeme] = {

      def definitelyFindStringOrUnclosedString: Option[Lexeme] = {
        val (s, nextChar) = consumeWhile(lexContext.restOfCurrentLine.skip(1),
          (c: Char) => c match {
            case '"' | '\n' => stop
            case _ => continue
          })
        nextChar match {
          case Some('"') => Some(StringLiteral(s))
          case _ => {
            lexContext.syntaxError("unclosed string literal", 0)
            Some(UnclosedStringLiteral(s))
          }
        }
      }

      lexContext.nextChar match {
        case None => None
        case Some(c) => {
          c match {
            case '"' => definitelyFindStringOrUnclosedString
            case _ => None
          }
        }
      }
    }
  }
}
