package parser.ast.expressions;

import java.math.BigDecimal;

public record DecimalExpression(BigDecimal value) implements Expression { }
