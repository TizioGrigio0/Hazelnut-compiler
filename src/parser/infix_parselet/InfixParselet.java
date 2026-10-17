package parser.infix_parselet;

import parser.ast.expressions.Expression;
import parser.Parser;
import token.Precedence;
import token.Token;

// Connect expressions
public interface InfixParselet {

    Expression parse(Parser parser, Expression left, Token token);

    Precedence precedence();

}
