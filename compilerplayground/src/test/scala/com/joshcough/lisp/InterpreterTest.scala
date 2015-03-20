package com.joshcough.lisp

import org.scalatest.FunSuite

/**
 * 
 */
class InterpreterTest extends EvalAbstractPredicatesTest with
                              EvalAbstractSelectorsTest with
                              EvalAbstractCreatorsTest with
                              SubstitutionTest with ApplyTest with EvalTest

/**
 * 
 */
trait EvalTest extends
  EvalAbstractPredicatesTest with EvalAbstractSelectorsTest with EvalAbstractCreatorsTest with SubstitutionTest{

  testEval("a", "a")
  testEval("(a b)", Application("a", "b"))
  testEval("((lambda (x) a) b)", "a")
  testEval("((lambda (x) x) b)", "b")
  testEval("(((lambda (x) (lambda (y) x)) a) b)", "a")

  testEval(Application(constantFunction, "b"), "a")

  def testEval(code:String, expected: Any) {
    test("eval: " + code ) { new Interpreter().evalCodeString(code) mustBe expected }
  }

  def testEval(code:Any, expected: Any) {
    test("eval: " + code ) { new Interpreter().eval(code) mustBe expected }
  }
}

trait ApplyTest extends EvalBaseTest{

  testApply("f", "x", Application("f", "x"))
  testApply(constantFunction, "b", "a")

  def testApply(f:Any, a:Any, expected:Any){
    test("apply function: " + f + " arg: " + a ){ new Interpreter().apply(f,a) mustBe expected }
  }
}

trait SubstitutionTest extends EvalBaseTest{

  // atom substitutions
  testSubstitution("a", "x", "x", "a")
  testSubstitution("a", "x", "e", "e")  

  // application substitutions
  testSubstitution("a", "x", Application("f", "x"), Application("f", "a"))
  testSubstitution("a", "x", Application("x", "f"), Application("a", "f"))
  testSubstitution("b", "x", getBody(constantFunction), "a")

  // function substitutions
  // [x/x](lambda (x) a) -> (lambda (x) a)
  testSubstitution("x", "x", constantFunction, constantFunction)
  // [b/u](lambda (x) (x y)) -> (lambda (z0) (z0 b)) (renaming rule)
  testSubstitution("b", "y", parse("(lambda (x) (x y))"), parse("(lambda (z0) (z0 b))"))

  def testSubstitution(a:Any, x:Any, e:Any, expected:Any){
    test("substitute ["+a+"/"+x+"]" + e ){ new Interpreter().substitute(a,x,e) mustBe expected }
  }
}

/**
 *
 */
trait EvalBaseTest extends FunSuite with MustBeMatchers {
  import _root_.com.joshcough.compiler.CompilationUnit
  import _root_.com.joshcough.lisp.lex.SExprLexer
  import _root_.com.joshcough.lisp.parser.SExprParser

  val constantFunction = List("lambda", List("x"), "a")

  def evalTest(desc: String, a: Any, f: Interpreter => Any, expected: Any) {
    test(desc + ": " + a) {f(new Interpreter()) mustBe expected}
  }

  def evalTest(desc: String, a: Any, b: Any, f: Interpreter => Any, expected: Any) {
    test(desc + ": " + a + " " + b) {f(new Interpreter()) mustBe expected}
  }

  def Application(as: Any*) = List(as:_*)
  def getBody(a:Any) = new Interpreter().getBody(a)
  def createFunction(id:Any, body:Any) = new Interpreter().createFunction(id, body)

  def parse(code:String) = new SExprParser().parse(SExprLexer(CompilationUnit(code)).lex)
}

/**
 *
 */
trait EvalAbstractPredicatesTest extends EvalBaseTest {

  testIsAtom("a", true) // atom
  testIsAtom(List(), false) // empty list
  testIsAtom(List("a"), false) // list with atom
  testIsAtom(Application("a", "b"), false) // application
  testIsAtom(constantFunction, false) // function

  testIsFunction("a", false) // atom
  testIsFunction(List(), false) // empty list
  testIsFunction(List("a"), false) // list with atom
  testIsFunction(Application("a", "b"), false) // application
  testIsFunction(constantFunction, true) // function

  testIsApplication("a", false) // atom
  testIsApplication(List(), false) // empty list
  testIsApplication(List("a"), false) // list with atom
  testIsApplication(Application("a", "b"), true) // application
  testIsApplication(constantFunction, false) // function

  def testIsAtom(a: Any, expected: Boolean) = evalTest("is atom", a, _.isAtom(a), expected)
  def testIsFunction(a: Any, expected: Boolean) = evalTest("is function", a, _.isFunction(a), expected)
  def testIsApplication(a: Any, expected: Boolean) = evalTest("is application", a, _.isApplication(a), expected)
}

/**
 *
 */
trait EvalAbstractSelectorsTest extends EvalBaseTest {

  testGetFunction(Application("a", "b"), "a")
  testGetFunction(Application(constantFunction, "b"), constantFunction)
  testGetFunction(Application("a", constantFunction), "a")

  testGetArgument(Application("a", "b"), "b")
  testGetArgument(Application(constantFunction, "b"), "b")
  testGetArgument(Application("a", constantFunction), constantFunction)

  testGetId(constantFunction, "x")
  testGetId(List("lambda", List("i"), Application("+", "1", "i")), "i")

  testGetBody(constantFunction, "a")
  testGetBody(List("lambda", List("i"), Application("+", "1", "i")), Application("+", "1", "i"))

  def testGetFunction(a: Any, expected: Any) = evalTest("get function", a, _.getFunction(a), expected)
  def testGetArgument(a: Any, expected: Any) = evalTest("get argument", a, _.getArgument(a), expected)
  def testGetId(a: Any, expected: Any) = evalTest("get id", a, _.getId(a), expected)
  def testGetBody(a: Any, expected: Any) = evalTest("get body", a, _.getBody(a), expected)
}

/**
 *
 */
trait EvalAbstractCreatorsTest extends EvalBaseTest {

  testCreateFunction("a", "b", List("lambda", List("a"), "b"))

  testCreateApplication("a", "b", Application("a", "b"))

  def testCreateFunction(a: Any, b: Any, expected: Any) = {
    evalTest("create function", a, b, _.createFunction(a, b), expected)
  }

  def testCreateApplication(a: Any, b: Any, expected: Any) = {
    evalTest("create application", a, b, _.createApplication(a, b), expected)
  }
}