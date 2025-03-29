package org.george.chess.util;

public class Logger<T> {
    private final StringBuilder prefix;

    private Logger(final Class<T> clazz) {
        this.prefix = new StringBuilder();
        prefix.append("[")
                .append(clazz.getSimpleName())
                .append("]: ");
    }

    public static <T> Logger<T> of(final Class<T> clazz) {
        return new Logger<T>(clazz);
    }

    public void log(final String s) {
        System.out.print(prefix.toString());
        System.out.println(s);
    }

    public void logln(final String s) {
        System.out.println(prefix.toString());
        System.out.println(s);
    }

    public void logBitBoard(final String title, final long board) {
        log(title);
        final StringBuilder builder = new StringBuilder();
        final StringBuilder binary = new StringBuilder(Long.toBinaryString(board));
        final StringBuilder paddedBinary = new StringBuilder();
        for (int i = 0; i < 64 - binary.length(); i++) {
            paddedBinary.append(0);
        }
        paddedBinary.append(binary);

        for (int i = 0; i < 64; i += 8) {
            builder.append(paddedBinary.substring(i, i + 8));
            builder.append("\n");
        }
        builder.deleteCharAt(builder.length() - 1);

        logln(builder.toString());

    }
}
