grammar Test;

options { output=AST; }

tokens {
	MINISCALA;
	INT='INT';
	ID='ID';
	BOOLEAN='BOOLEAN';
	STRING='STRING';
	EXPRESSION='EXPRESSION';
	PARAM_EXPRESSION='PARAM_EXPRESSION';
	DOT='.';
	PARAM;
	PARAMS;
	EQUALS='=';
	LEFT_PAREN='(';
	RIGHT_PAREN=')';
}

@header {package com.joshcough.miniscala; }
@lexer::header {package com.joshcough.miniscala;}

// Parser
start :expression*;
expression :primitiveExpression (DOT^ idExpression)*;

primitiveExpression: 
	primitive -> primitive
	|primitive params -> ^(PARAM_EXPRESSION primitive params);

idExpression: 
	Identifier -> ^(ID Identifier)
	|Identifier params -> ^(PARAM_EXPRESSION  ^(ID Identifier) params);

params: LEFT_PAREN (expression (',' expression)* )? RIGHT_PAREN;

primitive:
	DecimalLiteral -> ^(INT DecimalLiteral)
	|BooleanLiteral -> ^(BOOLEAN BooleanLiteral)
	|StringLiteral -> ^(STRING StringLiteral) 
	|Identifier -> ^(ID Identifier);
	
// Lexer
BooleanLiteral : 'true'|'false';
DecimalLiteral : ('0' | '1'..'9' '0'..'9'*);

StringLiteral:  '\'' Letter* '\'';
Identifier :   Letter+;

fragment
Letter 	:'a'..'z';

WS:(' '|'\r'|'\t'|'\u000C'|'\n');
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};
LINE_COMMENT: '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;};

