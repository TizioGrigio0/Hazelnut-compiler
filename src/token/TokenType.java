package token;

import java.util.HashMap;
import java.util.Map;

public enum TokenType {
    // Generic
    UNSET,
    IDENTIFIER,                                 // x, xxx, x1
    NUMBER,                                     // 123, 0x123, 0b123
    CHAR_LITERAL,                               // ' ... '
    STRING_LITERAL,                             // " ... "
    INVALID,
    EOF("\0"),
    LEFT_PARENTHESIS(Precedence.CALL_INDEX, "("), RIGHT_PARENTHESIS(Precedence.LOWEST, ")"), // Last parenthesis should be LOWEST
    LEFT_SQUARE(Precedence.CALL_INDEX, "["), RIGHT_SQUARE(Precedence.LOWEST, "]"),
    LEFT_BRACKET("{"),   RIGHT_BRACKET("}"),    // No precedence
    COMMA(","), DOT(Precedence.CALL_INDEX, "."), COLON(":"), SEMICOLON(";"),
    QUESTION_MARK("?"),

    // Literals
    BOOLEAN_LITERAL("true", "false", "TRUE", "FALSE"),
    NULL_LITERAL("null", "NULL"),
    MAX_32("MAX_32"),                 // 0xFFFF_FFFF
    MAX_64("MAX_64"),                 // 0xFFFF_FFFF_FFFF_FFFF

    // Keywords
    VAR("var"),
    CONST("const"),
    FINAL("final"),
    WHILE("while"),
    IF("if"), ELSE("else"),
    FOR("for"),
    BREAK("break"),
    CONTINUE("continue"),
    GOTO("goto"),
    RETURN("return"),

    // Types
    I16("short", "i16", "I16"),
    I32("int", "integer", "i32", "I32"),        // 32bit
    I64("long", "i64", "I64"),                  // 64bit
    F32("float", "f32", "F32"),                 // 32bit
    F64("double", "f64", "F64"),                // 64bit
    BOOL("bool", "boolean"),
    CHAR("char"),
    STRING("str", "string"),

    // Logical symbols
    EQUALS(Precedence.EQUALITY, "=="),
    NOT_EQUALS(Precedence.EQUALITY, "!="),
    GREATER(Precedence.COMPARISON, ">"),
    LESSER(Precedence.COMPARISON, "<"),
    GREATER_EQUAL(Precedence.COMPARISON, ">="),
    LESSER_EQUAL(Precedence.COMPARISON, "<="),

    // Arithmetical symbols
    ASSIGN(Precedence.ASSIGNMENT, "="),
    PLUS(Precedence.SUM, "+"),
    PLUS_ASSIGN(Precedence.ASSIGNMENT, "+="),
    PLUS_ONE(Precedence.POSTFIX, "++"),
    MINUS(Precedence.SUM,"-"),
    MINUS_ASSIGN(Precedence.ASSIGNMENT, "-="),
    MINUS_ONE(Precedence.POSTFIX, "--"),
    TIMES(Precedence.PRODUCT,"*"),
    TIMES_ASSIGN(Precedence.ASSIGNMENT, "*="),
    POWER(Precedence.EXPONENT, "**"),
    POWER_ASSIGN(Precedence.ASSIGNMENT, "**="),
    DIVIDE(Precedence.PRODUCT, "/"),
    DIVIDE_ASSIGN(Precedence.ASSIGNMENT, "/="),
    MODULO(Precedence.PRODUCT ,"%"), // "a * b % c" should evaluate as "(a * b) % c
    MODULO_ASSIGN(Precedence.ASSIGNMENT, "%="),
    LEFT_SHIFT(Precedence.BITWISE_SHIFT, "<<"),
    LEFT_SHIFT_ASSIGN(Precedence.ASSIGNMENT, "<<="),
    RIGHT_SHIFT(Precedence.BITWISE_SHIFT, ">>"),
    RIGHT_SHIFT_ASSIGN(Precedence.ASSIGNMENT, ">>="),

    // Logical operations
    AND(Precedence.LOGICAL_AND, "and", "AND", "&&"),
    NAND(Precedence.LOGICAL_AND, "nand", "NAND", "!&&"),
    OR(Precedence.LOGICAL_OR, "or", "OR", "||"),
    NOR(Precedence.LOGICAL_OR, "nor", "NOR", "!||"),
    NOT(Precedence.PREFIX, "not", "NOT", "!"),
    XOR(Precedence.LOGICAL_XOR, "xor", "XOR", "^^"),
    XNOR(Precedence.LOGICAL_XOR, "xnor", "XNOR", "xand", "XAND", "!^^"),

    // Bitwise operations
    BIT_AND(Precedence.BITWISE_AND, "&"),
    BIT_AND_ASSIGN(Precedence.ASSIGNMENT, "&="),
    BIT_OR(Precedence.BITWISE_OR, "|"),
    BIT_OR_ASSIGN(Precedence.ASSIGNMENT, "|="),
    BIT_NOT(Precedence.PREFIX, "~"),
    BIT_NOT_ASSIGN(Precedence.ASSIGNMENT, "~="),
    BIT_XOR(Precedence.BITWISE_XOR, "^"),
    BIT_XOR_ASSIGN(Precedence.ASSIGNMENT, "^=");

    private static final Map<String, TokenType> SYMBOLS = new HashMap<>();
    private final String[] aliases;
    private final Precedence precedence;

    // Only operators have different precedence, so we set that one manually
    TokenType(Precedence precedence, String... aliases) {
        this.precedence = precedence;
        this.aliases = aliases;
    }

    TokenType(String... aliases) {
        this.precedence = Precedence.LOWEST;
        this.aliases = aliases;
    }


    // Run once AFTER all the enums have been created
    static {

        // Save all the aliases from the constructor into the map
        for (TokenType type : TokenType.values()) {
            for (String alias : type.aliases) {
                SYMBOLS.put(alias, type);
            }
        }

    } // static closure

    // Given a string, return it's token type (if it's a known symbol)
    public static TokenType decode(String s) {
        return SYMBOLS.get(s);
    }
}
