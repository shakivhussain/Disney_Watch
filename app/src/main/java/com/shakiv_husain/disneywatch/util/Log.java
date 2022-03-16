package com.shakiv_husain.disneywatch.util;

public final class Log {


    public static void i(final String tag, final String message) {
        android.util.Log.i(tag, message);
    }

    public static void d(final String tag, final String message) {
        android.util.Log.d(tag, message);
    }

    public static void e(final String tag, final String message) {
        android.util.Log.e(tag, message);
    }
}
