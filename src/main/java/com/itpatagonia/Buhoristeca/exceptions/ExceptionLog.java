package com.itpatagonia.Buhoristeca.exceptions;

import com.itpatagonia.Buhoristeca.util.LogWriter;

public class ExceptionLog extends RuntimeException {

    public ExceptionLog(String message) {
        super(message);

        LogWriter logWriter = new LogWriter();
        logWriter.write(message);
    }
}
