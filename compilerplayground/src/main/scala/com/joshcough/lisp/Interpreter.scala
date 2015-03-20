package com.joshcough.lisp

import lex.SExprLexer
import parser.SExprParser
import compiler.CompilationUnit

class Interpreter{

  def evalCodeString(code: String): Any = {
    eval(new SExprParser().parse(SExprLexer(CompilationUnit(code)).lex))
  }

  def eval(e: Any): Any = {
    if( isAtom(e) ) e
    else if( isApplication(e) ) apply(getFunction(e), getArgument(e))
    else if( isFunction(e) ) createFunction(getId(e), eval(getBody(e)))
    else {
      // got something funny, like (a) not a atom, function, or app.
      // not sure what to do just yet, but returning the whole thing
      // doesnt seem too terrible. other option is to bomb...
      e
    }
  }

  // normal order
  def apply(f:Any, a:Any): Any = {
    if( isAtom(f) ) createApplication(f, eval(a))
    else if( isApplication(f) ) apply(eval(f), a)
    else eval(substitute(a, getId(f), getBody(f))) // isFunction
  }

  // applicative order
  def applicativeOrderApply(f:Any, a:Any): Any = {
    if( isAtom(f) ) createApplication(f, eval(a))
    else if( isApplication(f) ) applicativeOrderApply( eval(f), eval(a) )
    else eval(substitute(eval(a), getId(f), getBody(f))) // isFunction
  }

  def substitute(a:Any, x:Any, e:Any): Any = {
    if( isAtom(e) ) if( e == x ) a else e
    else if( isApplication(e) ) {
      createApplication(substitute(a,x,getFunction(e)), substitute(a,x,getArgument(e)))
    }
    else { // isFunction
      if(getId(e) == x) e
      else{
        val z = newId
        createFunction(z, substitute(a,x,substitute(z,getId(e),getBody(e))))
      }
    }
  }

  // newId for renaming rule support. this is garbage. but, i'll clean it up later. 
  private var count = -1
  def newId: String = { count = count + 1; "z" + count }

  // abstract predicates
  def isAtom(a:Any) = a match {
    case l:List[Any] => false
    case _ => true
  }

  def isFunction(a:Any) = a match {
    case l:List[Any] => l match {
      case  "lambda" :: (l:List[Any]) :: (a:Any) :: xs => true
      case _ => false
    }
    case _ => false
  }

  def isApplication(a:Any) = (! (isAtom(a) || isFunction(a))) && a.asInstanceOf[List[Any]].size == 2

  // abstract selectors
  def getFunction(a:Any) = a.asInstanceOf[List[Any]].head
  def getArgument(a:Any) = a.asInstanceOf[List[Any]].tail.head
  def getId(a:Any) = a.asInstanceOf[List[Any]].tail.head.asInstanceOf[List[Any]].head
  def getBody(a:Any) = a.asInstanceOf[List[Any]].tail.tail.head

  // abstract creators
  def createFunction(id:Any, body:Any) = List("lambda", List(id), body)
  def createApplication(a:Any, b:Any) = List(a, b)
}
