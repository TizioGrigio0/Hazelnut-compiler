package parser.seed_parselet;

import parser.Parser;
import parser.ast.expressions.DecimalExpression;
import parser.ast.expressions.Expression;
import parser.ast.expressions.IntegerExpression;
import token.Token;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberParselet implements SeedParselet {

    @Override
    public Expression parse(Parser parser, Token token) {
        String value = token.getValue().toLowerCase();

        // Manage prefixes
        if (value.startsWith("0x")) return new IntegerExpression( new BigInteger( value.substring(2), 16) );
        if (value.startsWith("0b")) return new IntegerExpression( new BigInteger( value.substring(2), 2) );
        if (value.startsWith("0o")) return new IntegerExpression( new BigInteger( value.substring(2), 8) );
        if (value.startsWith("0d")) return new IntegerExpression( new BigInteger( value.substring(2), 10) );

        // Process decimal number
        if (value.contains(".")) return new DecimalExpression( new BigDecimal(value) );
        else return new IntegerExpression( new BigInteger(value, 10));
    }
}
