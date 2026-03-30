package token.tokenscanner;

import lexer.SourceCursor;
import token.Token;

public class CommentScanner implements TokenScanner {

    public boolean canScan(char c) {
        return (c == '#');
    }

    public Token scan(SourceCursor source) {
        // Count the amount of adjacent #
        int counter = 0;
        while (source.peek() == '#') {
            source.advance();
            counter++;
        }

        // If it's only 1 #, then we manage it as a single line comment
        if (counter == 1) {
            // Keep eating everything until we reach the end of the line
            while(source.peek() != '\n' && source.peek() != 'r' && source.peek() != '\0') {
                source.advance();
            }
        }  // single line comment closure
        // If it's at least 2 #, then we manage it as a multi line comment
        else {
            counter = 0;
            while (source.peek() != '\0') { // Keep searching until we end the page
                if (source.peek() == '#') counter++; // If we found a #, count it
                else { // If we found something that isn't #, check if we have enough to stop, otherwise restart the counter
                    if (counter >= 2) return null;
                    counter = 0;
                }
                source.advance();
            } // while closure
        } // multi line comment closure

        return null;
    }
}
