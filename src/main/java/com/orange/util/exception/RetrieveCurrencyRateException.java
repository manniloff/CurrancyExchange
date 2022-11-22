package com.orange.util.exception;

public class RetrieveCurrencyRateException extends RuntimeException  {
    public RetrieveCurrencyRateException (String msg) {
       super(msg);
    }

    public RetrieveCurrencyRateException (String msg, Throwable cause) {
        super(msg, cause);
    }
}
