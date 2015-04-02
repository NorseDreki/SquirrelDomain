package com.squirrel.android.domain;

/**
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 19.03.2015.
 */
public interface ILogger {
    /** Log a verbose message with optional format args. */
    void v(String message, Object... args);

    /** Log a verbose exception and a message with optional format args. */
    void v(Throwable t, String message, Object... args);

    /** Log a debug message with optional format args. */
    void d(String message, Object... args);

    /** Log a debug exception and a message with optional format args. */
    void d(Throwable t, String message, Object... args);

    /** Log an info message with optional format args. */
    void i(String message, Object... args);

    /** Log an info exception and a message with optional format args. */
    void i(Throwable t, String message, Object... args);

    /** Log a warning message with optional format args. */
    void w(String message, Object... args);

    /** Log a warning exception and a message with optional format args. */
    void w(Throwable t, String message, Object... args);

    /** Log an error message with optional format args. */
    void e(String message, Object... args);

    /** Log an error exception and a message with optional format args. */
    void e(Throwable t, String message, Object... args);
}