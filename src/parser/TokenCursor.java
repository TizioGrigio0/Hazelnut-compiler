package parser;

import token.Token;
import token.TokenType;

import java.util.List;

public class TokenCursor {

    // Attributes
    private final List<Token> tokens;
    private final int length;
    private int currentIndex = 0;

    // Constructor
    TokenCursor(List<Token> tokens) {
        this.tokens = tokens;
        length = tokens.size();
    }

    // Methods
    /// Advances the list
    public void advance() {
        currentIndex++;
    }

    public boolean isAtEnd() {
        // N.B: can't use peek in here, otherwise it's an infinite loop and goes into stack overflow
        return currentIndex >= length || this.tokens.get(currentIndex).getType() == TokenType.EOF;
    }
    /// Returns the token at the currentIndex
    public Token peek() {
        if (isAtEnd()) return null;
        return tokens.get(currentIndex);
    }

    public Token previous() {
        if (currentIndex - 1 >= 0 && currentIndex - 1 < length) return tokens.get(currentIndex - 1);
        else return null;
    }

}
