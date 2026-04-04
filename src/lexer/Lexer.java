package lexer;

import errorreporter.ErrorReporter;
import token.tokenscanner.*;
import token.TokenType;
import token.Token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    // ----- Attributes -----
    private final SourceCursor source;
    private final List<TokenScanner> scanners;
    private final ErrorReporter reporter;

    // ----- Constructors -----
    public Lexer(String input, ErrorReporter reporter) {
        this.source = new SourceCursor(input.trim());
        this.reporter = reporter;
        this.scanners = List.of(
                new CommentScanner(),
                new CharScanner(),
                new StringScanner(),
                new NumberScanner(),
                new IdentifierScanner(),
                new SymbolScanner()
        );
    }

    // ----- Methods -----

    /// Tokenizes the whole content of the input given at creation
    public List<Token> tokenize() {

        List<Token> tokens = new ArrayList<>();

        while (!(this.source.isAtEnd())) {
            // Manage whitespaces
            while (Character.isWhitespace(this.source.peek())) this.source.advance();
            if (this.source.isAtEnd()) break;
            // Get the token and add it to the list of tokens
            this.source.setTokenStart();
            Token scannedToken = scanToken(); // scanToken() will do source.advance() as needed
            if (scannedToken != null) tokens.add( scannedToken );

            //System.out.println(tokens.getLast());
        } // while closure

        // EOF at the end of the input
        tokens.add(this.source.generateTokenHere(TokenType.EOF));
        return tokens;
    } // tokenize closure

    // Strategy pattern
    private Token scanToken() {
        final char firstChar = this.source.peek();

        for (TokenScanner scanner : scanners) {
            if (scanner.canScan(firstChar)) return scanner.scan(source);
        }

        throw new RuntimeException("No scanner found for character: " + firstChar);
    } // scanToken() closure

}
