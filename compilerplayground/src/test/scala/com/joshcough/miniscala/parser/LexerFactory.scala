//package com.joshcough.miniscala
//
//import java.io.{File, ByteArrayInputStream, InputStream, FileInputStream}
//import org.antlr.runtime.debug.BlankDebugEventListener
//import org.antlr.runtime.tree.CommonTree
//import org.antlr.runtime.{ANTLRFileStream, ANTLRInputStream, CommonTokenStream}
//import org.testng.annotations.Test
//
///**
// *
// * @author dood
// * Date: Jan 17, 2009
// * Time: 12:05:39 AM
// */
//trait LexerFactory[L] {
//  def lexer(in: InputStream): L
//  def lexer(file: File): L
//  implicit def fileToStream( file: File ) = new ANTLRFileStream(file.getAbsolutePath)
//}
//
//trait ParserFactory[L, P] extends LexerFactory[L] {
//  def parser(lex: L): (CommonTokenStream, P)
//  def parser(in: String): (CommonTokenStream, P) = parser(lexer(new ByteArrayInputStream(in.getBytes)))
//  def parser(file: File): (CommonTokenStream, P) = parser(lexer(file))
//}
//
//trait MiniScalaLexerFactory extends LexerFactory[MiniScalaLexer]{
//  def lexer(in: InputStream) = new MiniScalaLexer(new ANTLRInputStream(in))
//  def lexer(file: File) = new MiniScalaLexer(fileToStream(file))
//}
//
//trait MiniScalaParserFactory extends ParserFactory[MiniScalaLexer, MiniScalaParser] {
//  def parser(lex: MiniScalaLexer) = {
//    val toks = new CommonTokenStream(lex)
//    (toks, new MiniScalaParser(toks))
//  }
//}
//
//trait TestLexerFactory extends LexerFactory[TestLexer]{
//  def lexer(in: InputStream) = new TestLexer(new ANTLRInputStream(in))
//  def lexer(file: File) = new TestLexer(fileToStream(file))
//}
//
//trait TestParserFactory extends ParserFactory[TestLexer, TestParser] {
//  def parser(lex: TestLexer) = {
//    val toks = new CommonTokenStream(lex)
//    (toks, new TestParser(toks))
//  }
//}