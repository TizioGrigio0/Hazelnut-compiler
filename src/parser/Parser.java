package parser;

import errorreporter.CompilerError;
import errorreporter.ErrorReporter;
import parser.ast.expressions.ErrorExpression;
import parser.ast.expressions.Expression;
import parser.ast.statements.ErrorStatement;
import parser.ast.statements.ExpressionStatement;
import parser.ast.statements.Statement;
import parser.infix_parselet.*;
import parser.seed_parselet.*;
import token.Precedence;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {

    private final TokenCursor source;
    private final ErrorReporter reporter;

    private HashMap<TokenType, SeedParselet> seedParselets = new HashMap<>();
    private HashMap<TokenType, InfixParselet> infixParselets = new HashMap<>();

    public Parser(List<Token> source, ErrorReporter reporter) {
        this.source = new TokenCursor(source);
        this.reporter = reporter;
        registerGrammar();
    }

    private void registerGrammar() {
        registerSeed(TokenType.NUMBER, new NumberParselet());
        registerSeed(TokenType.IDENTIFIER, new NameParselet());
        registerSeed(TokenType.MINUS, new PrefixOperatorParselet());

        registerInfix(TokenType.PLUS, new BinaryOperatorParselet(Precedence.SUM));
        registerInfix(TokenType.MINUS, new BinaryOperatorParselet(Precedence.SUM));
        registerInfix(TokenType.TIMES, new BinaryOperatorParselet(Precedence.PRODUCT));
        registerInfix(TokenType.DIVIDE, new BinaryOperatorParselet(Precedence.PRODUCT));

    }

    private void registerSeed(TokenType token, SeedParselet seedParselet) { seedParselets.put(token, seedParselet); }
    private void registerInfix(TokenType token, InfixParselet infixParselet) { infixParselets.put(token, infixParselet); }


    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        while (!source.isAtEnd()) {
            statements.add(parseStatement());
        }
        return statements;
    }


    private Statement parseStatement() {
        // KEYWORDS HERE TODO
        return parseExpressionStatement();
    }

    private Statement parseExpressionStatement() {
        Expression expression = parseExpression(Precedence.LOWEST);

        if (expression.isError()) {
            synchronize();
            return new ErrorStatement(expression);
        }

        Token next = source.peek();
        TokenType nextType;
        if (next == null) {
            nextType = TokenType.EOF;
        } else {
            nextType = next.getType();
        }

        if (nextType != TokenType.SEMICOLON) {
            Token previous = source.previous();
            reporter.pushError(
                    new CompilerError(
                            ParserErrorMessage.EXPECTED_TOKEN.format(";", previous.getValue()),
                            previous.getLine(),
                            previous.getPosition()+previous.getValue().length(),
                            CompilerError.CompilerPhase.PARSER,
                            ((nextType == TokenType.EOF) ? CompilerError.ErrorType.WARNING : CompilerError.ErrorType.ERROR),
                            previous.getLineContent()
                    )
            );
            return new ExpressionStatement(expression);
        } else {
            source.advance();
        }
        return new ExpressionStatement(expression);
    }

    // Starts eating all tokens until a semicolon. This is because otherwise there are a lot of errors in a row. We call this after the first error.
    private void synchronize() {
        while (!source.isAtEnd()) {
            Token current = source.peek();
            source.advance();
            if (current.getType() == TokenType.SEMICOLON) return;
        }
    }

    public Expression parseExpression(Precedence precedence) {
        Token token = source.peek(); source.advance();
        SeedParselet seedParselet = seedParselets.get(token.getType());

        if (seedParselet == null) {
            reporter.pushError(
                    new CompilerError(
                            ParserErrorMessage.EXPECTED_EXPRESSION.format(token.getValue()),
                            token.getLine(),
                            token.getPosition(),
                            CompilerError.CompilerPhase.PARSER,
                            CompilerError.ErrorType.ERROR,
                            token.getLineContent()
                            )
            );
            return new ErrorExpression(token);
        } // error closure

        Expression left = seedParselet.parse(this, token);

        while(precedence.ordinal() < getNextPrecedence().ordinal()) {
            // Consume the next token
            Token next = source.peek(); source.advance();
            InfixParselet infixParselet = infixParselets.get(next.getType());

            left = infixParselet.parse(this, left, next);
        }

        return left;
    }

    private Precedence getNextPrecedence() {
        Token nextToken = source.peek();

        if (nextToken == null) return Precedence.LOWEST;

        InfixParselet parser = infixParselets.get(nextToken.getType());

        if (parser == null) {
            // If there is nothing else, then we finished
            return Precedence.LOWEST;
        } // error closure

        return parser.precedence();
    }


}
