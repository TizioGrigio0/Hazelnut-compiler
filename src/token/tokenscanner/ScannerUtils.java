package token.tokenscanner;

public class ScannerUtils {

    static boolean isIdentifiersChar(char c) {
        return  (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9')
                || c == '_';
    }

    static boolean isNumberChar(char c) {
        return (c >= '0' && c <= '9') || (c == '.') || (c=='_');
    }

}
