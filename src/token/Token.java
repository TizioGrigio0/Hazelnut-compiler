package token;


public class Token {
    private final TokenType type;       // The identifier of the type of the token
    private String value;               // Value inside the token
    private final int index;            // Token position in the whole text
    private final int line;             // Token line
    private final int position;         // Token position in the line

    private final String lineContent;   // The whole content of the line (used for errors)

    public Token(TokenType type, String value, int index, int line, int position, String lineContent) {
        this.type = type;
        this.value = value;
        this.index = index;
        this.line = line;
        this.position = position;
        this.lineContent = lineContent;
    }

    public boolean isInvalid() {
        return (this.type == TokenType.INVALID);
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }
    public String getValue() { return this.value; }

    public TokenType getType() { return this.type; }
    public int getLine() { return line; }
    public int getIndex() { return index; }
    public int getPosition() { return position; }

    public String getLineContent() { return lineContent; }

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
