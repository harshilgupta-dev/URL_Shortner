package com.harshil.gupta.url_shortner.util;

public class Base62Encoder {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = BASE62.length();
    private static final int MIN_SHORT_URL_LENGTH = 7;
    private static final char PAD_CHAR = '=';

    /**
     * Convert timestamp (milliseconds) to Base62 string
     * @param timestamp timestamp
     * @return encoded shortUrl
     */
    public static String encode(long timestamp) {
        StringBuilder shortUrl = new StringBuilder();

        // Base62 encoding process
        while (timestamp > 0) {
            shortUrl.insert(0, BASE62.charAt((int) (timestamp % BASE)));
            timestamp /= BASE;
        }

        // If the encoded string is shorter than 7 characters, pad it with '='
        while (shortUrl.length() < MIN_SHORT_URL_LENGTH) {
            shortUrl.insert(0, PAD_CHAR);  // Padding at the start
        }

        return shortUrl.toString();
    }
}

