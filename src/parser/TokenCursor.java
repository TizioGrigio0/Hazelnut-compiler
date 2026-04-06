package parser;

import token.Token;
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
        return currentIndex >= length;
    }
    /// Returns the token at the currentIndex
    public Token peek() {
        if (isAtEnd()) return null;
        return tokens.get(currentIndex);
    }

}
