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
    LEFT_PARENTHESIS("("), RIGHT_PARENTHESIS(")"),
    LEFT_SQUARE("["), RIGHT_SQUARE("]"),
    LEFT_BRACKET("{"),   RIGHT_BRACKET("}"),
    COMMA(","), DOT("."), COLON(":"), SEMICOLON(";"),

    // Literals
    BOOLEAN_LITERAL("true", "false", "TRUE", "FALSE"),
    NULL_LITERAL("null", "NULL"),
    MAX_32("MAX_32"),                 // 0xFFFF_FFFF
    MAX_64("MAX_64"),                 // 0xFFFF_FFFF_FFFF_FFFF

    // Keywords
    WHILE("while"),
    IF("if"), ELSE("else"),
    FOR("for"),
    BREAK("break"),
    CONTINUE("continue"),
    GOTO("goto"),
    RETURN("return"),

    // Types
    VOID("void"),
    INT("int", "integer"),      // 32bit
    LONG("long"),               // 64bit
    FLOAT("float"),             // 32bit
    DOUBLE("double"),           // 64bit
    BOOL("bool", "boolean"),
    CHAR("char"),
    STRING("str", "string"),

    // Logical symbols
    EQUALS("=="),
    NOT_EQUALS("!="),
    GREATER(">"),
    LESSER("<"),
    GREATER_EQUAL(">="),
    LESSER_EQUAL("<="),

    // Arithmetical symbols
    ASSIGN("="),
    PLUS("+"),
    PLUS_ASSIGN("+="),
    PLUS_ONE("++"),
    MINUS("-"),
    MINUS_ASSIGN("-="),
    MINUS_ONE("--"),
    TIMES("*"),
    TIMES_ASSIGN("*="),
    POWER("**"),
    POWER_ASSIGN("**="),
    DIVIDE("/"),
    DIVIDE_ASSIGN("/="),
    MODULO("%"),
    MODULO_ASSIGN("%="),
    LEFT_SHIFT("<<"),
    LEFT_SHIFT_ASSIGN("<<="),
    RIGHT_SHIFT(">>"),
    RIGHT_SHIFT_ASSIGN(">>="),

    // Logical operations
    AND("and", "AND", "&&"),
    NAND("nand", "NAND", "!&&"),
    OR("or", "OR", "||"),
    NOR("nor", "NOR", "!||"),
    NOT("not", "NOT", "!"),
    XOR("xor", "XOR", "^^"),
    XNOR("xnor", "XNOR", "xand", "XAND", "!^^"),

    // Bitwise operations
    BIT_AND("&"),
    BIT_AND_ASSIGN("&="),
    BIT_OR("|"),
    BIT_OR_ASSIGN("|="),
    BIT_NOT("~"),
    BIT_NOT_ASSIGN("~="),
    BIT_XOR("^"),
    BIT_XOR_ASSIGN("^=");

    private static final Map<String, TokenType> SYMBOLS = new HashMap<>();
    private final String[] aliases;

    TokenType() {
        this.aliases = new String[0];
    }

    // Varargs, admits infinite parameters and puts them into an array
    TokenType(String... aliases) {
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
