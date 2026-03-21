package Lexer;


public class Token {
    private final TokenType type;       // The identifier of the type of the token
    private final String value;         // Value inside the token, might get converted later
    private final int index;            // Token position in the whole text
    private final int line;             // Token line
    private final int position;         // Token position in the line

    public Token(TokenType type, String value, int index, int line, int position) {
        this.type = type;
        this.value = value;
        this.index = index;
        this.line = line;
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token{");
        sb.append("type=").append(type);
        sb.append(", value='").append(value).append('\'');
        sb.append(", index=").append(index);
        sb.append(", line=").append(line);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }

}
