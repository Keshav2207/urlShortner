package org.kpl22.urlshortener.util;

import java.security.SecureRandom;

public final class ShortIdGenerator {

    private static final char[] BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    private static final SecureRandom RANDOM = new SecureRandom();

    private ShortIdGenerator() {
    }

    public static String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(BASE62[RANDOM.nextInt(BASE62.length)]);
        }
        return sb.toString();
    }
}
