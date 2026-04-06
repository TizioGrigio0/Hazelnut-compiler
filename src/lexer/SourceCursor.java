package lexer;

import token.*;

import java.util.ArrayList;

public class SourceCursor {

    // Attributes
    private final String input;
    private final int length;
    private int currentIndex = 0;
    private int currentPositionInLine = 0;
    private int firstPositionInLine = 0;
    private int lineStart = 0;
    private int currentLine = 1;
    private int tokenStart = 0;
    private int tokenStartLine = 0;

    private ArrayList<Integer> lineFirstIndex = new ArrayList<Integer>();

    // Constructors
    SourceCursor(String input) {
        // Pre-process the input
        this.input = input.replace("\r\n", "\n").replace("\r", "\n");
        this.length = this.input.length();
        this.lineFirstIndex.add(0);
    }

    // Getters
    public int getLine() {
        return currentLine;
    }
    public int getTokensFirstLine() { return tokenStartLine; }
    public int getColumn() {
        return firstPositionInLine;
    }

    // Methods
    /// Returns the remaining length, including the currentIndex
    public int getRemainingLength() {
        return length-currentIndex;
    }
    /// Returns the current character, then goes to the next index
    public void advance() {
        if (isAtEnd()) { return; }
        char foundChar = input.charAt(currentIndex);
        this.currentIndex++; currentPositionInLine++;
        checkNewline(foundChar);
    }

    /// Go back one character, doesn't account for newlines
    public void fallback() {
        currentIndex--;
        currentPositionInLine--;
    }

    /// Get the current character without consuming it
    public char peek() {
        if (isAtEnd()) return '\0';
        return this.input.charAt(currentIndex);
    }

    /// Sets the token's first index to the currentIndex
    public void setTokenStart() {
        this.tokenStart = this.currentIndex;
        this.firstPositionInLine = this.currentPositionInLine;
        this.tokenStartLine = this.currentLine;
    }

    /// Creates the token based on collected information
    public Token generateTokenHere(TokenType type) {
        if (type == TokenType.EOF) setTokenStart(); // If it's EOF token, then it shouldn't have any content
        return new Token(type, this.input.substring(this.tokenStart, this.currentIndex), this.tokenStart, this.currentLine, this.firstPositionInLine);
    }

    /// Returns whether the cursor is over the end of the input or not
    public boolean isAtEnd() {
        return currentIndex >= length;
    }

    ///  Checks if the passed character is a newLine and if the next character in the input composes \r\n,
    ///  eventually skipping a char to not count it twice
    private void checkNewline(char c) {
        if (c == '\n') {
            this.currentLine++;
            this.currentPositionInLine = 0;
            this.lineStart = currentIndex;
            this.lineFirstIndex.add(currentIndex);
        }
    }

    /// Returns the string from the start of the token up to the current index (included)
    public String extractTokenString() {
        return input.substring(tokenStart, currentIndex);
    }

    /// Returns the content of the current line
    public String getLineContent() {
        if (isAtEnd()) return input.substring(lineStart, currentIndex);
        int tempIndex = currentIndex;
        while (input.charAt(tempIndex) != '\n' && input.charAt(tempIndex) != '\0') tempIndex++;
        return input.substring(lineStart, tempIndex);
    }

    /// Given the line number (1...), returns the whole content of that line
    public String getLineContent(int line) {
        if (line >= lineFirstIndex.toArray().length) return getLineContent();
        return this.input.substring(lineFirstIndex.get(line-1), lineFirstIndex.get(line)-1);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SourceCursor{");
        sb.append("input='").append(input).append('\'');
        sb.append(", length=").append(length);
        sb.append(", currentIndex=").append(currentIndex);
        sb.append(", currentPositionInLine=").append(currentPositionInLine);
        sb.append(", currentLine=").append(currentLine);
        sb.append(", tokenStart=").append(tokenStart);
        sb.append('}');
        return sb.toString();
    }
}
