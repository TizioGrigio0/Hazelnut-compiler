package util;

public abstract class AnsiTextHandler {

    public static String getReset() { return "\u001B[0m"; }
    public static void resetEverything() {
        System.out.print("\u001B[0m");
    }

    public static String getDefaultColor() { return "\u001B[39m"; }
    public static void setDefaultColor() {
        System.out.print("\u001B[39m");
    }

    public static String getRed() { return "\u001B[31m"; }
    public static void setRed() {
        System.out.print("\u001B[31m");
    }

    public static String getBlue() { return "\u001B[34m"; }
    public static void setBlue() {
        System.out.print("\u001B[34m");
    }

    public static String getGreen() { return "\u001B[32m"; }
    public static void setGreen() {
        System.out.print("\u001B[32m");
    }

    public static String getYellow() { return "\u001B[33m"; }
    public static void setYellow() {
        System.out.print("\u001B[33m");
    }

    public static String getBold() { return "\u001B[1m"; }
    public static void setBold() { System.out.print("\u001B[1m"); }
    public static String getNoBold() { return "\u001B[21m"; }
    public static void setNoBold() { System.out.print("\u001B[21m"); }

    public static String getItalic() { return "\u001B[3m"; }
    public static void setItalic() { System.out.print("\u001B[3m"); }
    public static String getNoItalic() { return "\u001B[23m"; }
    public static void setNoItalic() { System.out.print("\u001B[23m"); }

    public static String getUnderlined() { return "\u001B[4m"; }
    public static void setUnderlined() { System.out.print("\u001B[4m"); }
    public static String getNoUnderlined() { return "\u001B[24m"; }
    public static void setNoUnderlined() { System.out.print("\u001B[24m"); }

}
