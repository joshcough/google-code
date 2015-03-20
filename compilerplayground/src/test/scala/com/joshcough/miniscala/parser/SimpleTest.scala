//package com.joshcough.miniscala
//
//import java.io.{File, FileReader}
//import org.antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
//import org.antlr.runtime.tree.{CommonTree, CommonTreeNodeStream}
//import Equalizer._
//import org.antlr.stringtemplate.language.AngleBracketTemplateLexer
//import org.testng.annotations.{Test, DataProvider}
//
///**
// * @author dood
// * Date: Jan 17, 2009
// * Time: 12:54:36 AM
// */
///*******************************
// * test driven compiler design *
// *******************************/
//
///**********************************************************************
//
//do i want to test the output of the lexer?
//maybe... it would be a bunch of tokens, but it might be worth testing
//todo: at least look at the output of the lexer
//
//do i want to test the parser independent of the lexer?
//if so, i need to be able to create tokens
//
//this probably wont work.
//
//what i probably want to do is, for each production in my grammar:
//give it test code and test the AST output of it.
//ideally, this would keep my productions very small.
//eventually though, the code input would have to be big
//to test productions like "classDecl"
//
//how should i structure the tests?
//one test class per production?
//one giant data provider?
//data provider per production?
//
//how should i structure the input data? and where should i put it?
//similarly - what about the expected AST data?
//
//this should get me started...but much more
//
// **********************************************************************/
//trait CompilerTest[P] extends ASTRep {
//  def parse(parser: P): { def getTree: Object }
//  def tests: Array[Array[String]]
//}
//
//abstract class MiniScalaCompilerTestRunner(ct: CompilerTest[MiniScalaParser]) extends
//  CompilerTestRunner[MiniScalaLexer, MiniScalaParser](ct) with
//      MiniScalaLexerFactory with MiniScalaParserFactory
//
//abstract class TestGrammarCompilerTestRunner(ct: CompilerTest[TestParser]) extends
//  CompilerTestRunner[TestLexer, TestParser](ct) with TestLexerFactory with TestParserFactory
//
//abstract class CompilerTestRunner[L, P](ct: CompilerTest[P]) extends ParserFactory[L, P]{
//
//  @DataProvider {val name = "tests"}
//  def tests_internal = ct.tests
//
//  @Test {val dataProvider = "tests"}
//  def initialGrammarTest(in: String, expectedAst: String) = {
//    ct.parse(parser(in)._2).getTree.asInstanceOf[CommonTree].toStringTree mustBe expectedAst
////    treeGrammarTest(in)
//  }
//
////  def treeGrammarTest(in: String) = {
////    val tg = getTreeGrammar(in)
////    val compile = tg.compile
////    val output = compile.getTemplate
////    println(output.toString)
////  }
//
//  def getTreeGrammar(in:String) = {
//    val (toks, p) = parser(in)
//    val nodes = new CommonTreeNodeStream(ct.parse(p).getTree)
//    nodes.setTokenStream(toks)
////    val tg = new MiniScalaTreeGrammar(nodes)
////    tg.setTemplateLib(getTemplates)
////    tg
//  }
//
//  def getTemplates = {
//    val templateFileName = "./src/main/antlr/com/joshcough/miniscala/MiniScala.stg";
//    new StringTemplateGroup(new FileReader(templateFileName))
//  }
//
//}
//
//object PimpedString {
//  trait Yielder {
//    def -->(ast: String): Array[String]
//  }
//  implicit def stringToYielder(s: String): Yielder = new Yielder {
//    def -->(ast: String) = Array(s, ast)
//  }
//}
//
//
//trait ASTRep{
//  def id(i: String) = i
//  def int(i: Integer) = i.toString
//  def boolean(b: Boolean) = b.toString
//  def string(s: String) = "\"" + s + "\""
//  def dot(left:String, right: String) = "(. "+ left + " " + right + ")"
//  def apply_exp(leftExp:String, params: String*) = "(APPLY "+ leftExp + " " + params.mkString(" ") + ")"
//
//  def typ( name: String ) = "(TYPE " + name + ")"
//  def valDec(id: String, exp: String) = "(val " + id + " " + exp + ")"
//  def valDec(id: String, typ: String, exp: String) = "(val "  + id + " " + typ + " " + exp + ")"
//  def varDec(id: String, exp: String) = "(var "  + id + " " + exp + ")"
//  def varDec(id: String, typ: String, exp: String) = "(var "  + id + " " + typ + " " + exp + ")"
//
//  def clas(name: String) = "(class " + name + ")"
//  def clas(name: String, params: String) = "(class "+name+" "+params+")"
//  def clas(name: String, params: String, block: String) = "(class "+name+" "+params+" (BODY "+block+"))"
//
//  def method(name: String, exp: String) = "(METHOD_DEC "+name+" "+exp+")"
//  def abstact_method(name: String, params: String) = "(METHOD_DEC "+name+" "+params+")"
//  def method(name: String, params: String, exp: String) = "(METHOD_DEC "+name+" "+params+" "+exp+")"
//
//  def apply_params(ps: String*) = "( " + ps.mkString(" , ") + (if(ps.size > 0) " )" else ")")
//  def params(ps: String*) = "(PARAMS " + ps.mkString(" ") + ")"
//  def param(name:String, typeName: String) = "(PARAM "+name+" " + typ(typeName) + ")"
//
//  def block(statements: String*) = "(BLOCK " + statements.mkString(" ") + ")"
//}
//
//
///**
//start :(construct)* EOF;
//construct: object|classDecl;
//classDecl: CLASS ID parameters? (EXTENDS ID expressions?)? block?-> ^( CLASS ID ^(PARAMS parameters) );
//object: OBJECT ID EXTENDS ID expressions? (block)?;
//block: LEFT_CURLY (statement|methodDecl|classDecl|object)* RIGHT_CURLY;
//methodDecl: DEF ID parameters? (COLON type)? EQUALS block;
//parameters: LEFT_PAREN (parameterDecl  (',' parameterDecl)*)? RIGHT_PAREN -> parameterDecl+;
//parameterDecl: ID ':' type;
//valDec: VAL ID (COLON t=type)? EQUALS expression -> ^(VAL ID $t? expression);
//varDec: VAR ID (COLON t=type)? EQUALS expression -> ^(VAR ID $t? expression);
//type: ID -> ^(TYPE ID);
//statement: valDec|varDec|expression;
//expression :terminal (methodCall)* |constructor (methodCall)*;
//terminal :DecimalLiteral|TRUE|FALSE|ID|THIS;
//methodCall: DOT ID e=expressions? -> ^('.' ID $e);
//constructor: NEW ID expressions?;
//expressions:
//LEFT_PAREN (expression ( ',' expression)* )? RIGHT_PAREN -> expression+;
// */
