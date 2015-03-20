package com.joshcough.lisp.parser

import compiler.lex.{RightParen, LeftParen, Token}
import lisp.lex.Atom

/**
 * examples:
 *
 * a
 * (a)
 * ((b))
 * (a (b))
 * ((a) b)
 * (a (b) c)
 * (a (b) (c)) 
 * ...
 */
case class SExprParser() {
  def parse(tokens: List[Token]): Any = {

    def parseSExprInner(tokens: List[Token]): (Any, List[Token]) = {
      tokens match {
        case x :: xs => {
          x match {
            case Token(LeftParen, _, _) => {
              val (list, remainingTokens) = parseSExprList(xs)
              remainingTokens match {
                case Token(RightParen, _, _) :: xs => (list, xs)
                case Nil => throw new IllegalArgumentException // no trailing RightParen
              }
            }
            case Token(Atom(data), _, _) => (data, xs)
            case _ => throw new IllegalArgumentException // something other than an Atom or LeftParen
          }
        }
        case Nil => throw new IllegalArgumentException // no tokens at all!
      }
    }

    /**
     * examples:
     *
     * a
     * (b)
     * a (b)
     * (a) b
     * a (b) c
     * a (b) (c)
     * ...
     */
    def parseSExprList(tokens: List[Token]): (List[Any], List[Token]) = {
      def parseSExprList(tokens: List[Token], acc: List[Any]): (List[Any], List[Token]) = {
        tokens match {
          case Token(RightParen, _, _) :: xs => (acc, tokens)
          case Token(_, _, _) :: xs => {
            val (sexpr, remainingTokens) = parseSExprInner(tokens)
            parseSExprList(remainingTokens, acc ::: List(sexpr))
          }
          case _ => throw new IllegalArgumentException
        }
      }
      parseSExprList(tokens, Nil)
    }

    parseSExprInner(tokens)._1
  }
}