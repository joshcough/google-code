tree grammar MiniScalaTreeGrammar;

options{
	tokenVocab=MiniScala;
	ASTLabelType=CommonTree;
	output=template;
}

@header { package com.joshcough.miniscala; }

compile:e1=expression-> {$e1.st} | v=var-> {$v.st} | v2=val-> {$v2.st} ;

expression:	DOT e1=expression e2=expression -> dot_exp(left={$e1.st}, right={$e2.st})
	|	APPLY -> apply_exp()
	|	INT_LIT -> int_lit(x={$INT_LIT.text})
	|	STRING_LIT -> string_lit(x={$STRING_LIT.text})
	|	BOOLEAN -> boolean_lit(x={$BOOLEAN.text})
	|	ID -> id(x={$ID.text})
	;
	
var	:	VAR ID t=type? e=expression -> var_dec(name={$ID.text}, typ={$t.st}, exp={$e.st});
val	:	VAL ID t=type? e=expression ->  val_dec(name={$ID.text}, typ={$t.st}, exp={$e.st});
type	:	TYPE ID -> typ_lit(t={$ID.text});
