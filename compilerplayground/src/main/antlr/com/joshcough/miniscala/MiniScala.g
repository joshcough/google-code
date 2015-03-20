grammar MiniScala;

options {
	output=AST;
}

tokens {
	MINISCALA;
	APPLY;
	CLASS='class';
	OBJECT='object';
	VAL='val';
	VAR='var';
	ARGS='ARGS';
	METHOD_DEC='METHOD_DEC';
	METHOD_CALL='METHOD_CALL';
	BLOCK='BLOCK';
	BODY='BODY';
	EXPRESSION='EXPRESSION';
	EXTENDS='extends';
	WHILE='while';
	TYPE='type';
	IF='if';
	ELSE='else';
	RETURN='return';
	NOT='!';
	NEW='new';
	THIS='this';
	DEF='def';
	DOT='.';
	PARAM;
	PARAMS;
	EQUALS='=';
	SEMI=';';
	LEFT_CURLY='{';
	LEFT_PAREN='(';
	LEFT_BRACKET='[';
	RIGHT_CURLY='}';
	RIGHT_PAREN=')';
	RIGHT_BRACKET=']';
	QUESTION='?';
	AND='&';
	STAR='*';
	SLASH='/';
	PLUS='+';
	MINUS='-';
	GT='>';
	LT='<';
	BACKSLASH='\\';
	PERCENT='%';
	CARROT='^';
	TILDE='~';
	COLON=':';
}

@header {package com.joshcough.miniscala; }
@lexer::header {package com.joshcough.miniscala;}

///////////////////////////////////////
// Parser
///////////////////////////////////////
start :expression;
compile	: (classDecl | objectDecl)*;

// statements
statement: valDec | varDec | classDecl | objectDecl | methodDecl | expression;
objectDecl: OBJECT ID extender block?;
classDecl: CLASS ID formalParams? extender? classBody? -> ^(CLASS ID formalParams? extender? classBody?);
classBody:block -> ^(BODY block);     
extender: EXTENDS ID params? -> ^(EXTENDS ID params*);

methodDecl: DEF ID formalParams? (COLON t=type)? EQUALS (e=expression) -> ^(METHOD_DEC ID formalParams? $t? $e);

block: LEFT_CURLY (statement (('\n'|';') statement)*)? RIGHT_CURLY -> ^(BLOCK statement+);
formalParams: LEFT_PAREN (formalParam  (',' formalParam)*)? RIGHT_PAREN -> ^(PARAMS formalParam+);
formalParam: ID ':' type -> ^(PARAM ID type);
valDec: VAL valOrVarDecRest -> ^(VAL valOrVarDecRest);
varDec: VAR valOrVarDecRest -> ^(VAR valOrVarDecRest);
valOrVarDecRest: ID (COLON t=type)? EQUALS e=expression -> ID $t? $e;
type: ID -> ^(TYPE ID);

// expressions
expression: block | dotExpression;
dotExpression: applyExpression (DOT^ applyExpression)*;
applyExpression: primitive | primitive (params)+ -> ^(APPLY primitive params+);
params: LEFT_PAREN (expression (',' expression)* )? RIGHT_PAREN;
primitive: INT_LIT | BOOLEAN | STRING_LIT | ID;

////////////////////////////////////////    
// Lexer
////////////////////////////////////////
BOOLEAN : 'true'|'false';
INT_LIT : ('0' | '1'..'9' '0'..'9'*);
STRING_LIT:  '"' LETTER* '"';
ID :   LETTER+;
fragment
LETTER 	:'a'..'z'|'A'..'Z';
WS:(' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};
LINE_COMMENT: '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;};
