package parser.ast.expressions;

import java.math.BigInteger;

public record IntegerExpression(BigInteger value) implements Expression { }
