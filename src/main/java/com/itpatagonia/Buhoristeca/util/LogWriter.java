package com.itpatagonia.Buhoristeca.util;

import java.io.FileWriter;

public class LogWriter {

    private final String logFileName = "log.txt";

    public void write(String message) {
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            writer.append(message).append("\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
