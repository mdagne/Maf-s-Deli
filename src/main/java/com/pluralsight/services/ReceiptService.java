package com.pluralsight.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Saves receipt text to timestamped files in receipts directory
public final class ReceiptService {
    private static final String RECEIPTS_DIR = "receipts";
    private static final String FILENAME_PATTERN = "yyyyMMdd-HHmmss";
    private static final String FILE_EXTENSION = ".txt";

    // Saves receipt content to timestamped file and returns file path
    public static String saveReceipt(LocalDateTime placedAt, String content) throws IOException {
        File dir = new File(RECEIPTS_DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create receipts directory");
            }
        }
        
        String filename = placedAt.format(DateTimeFormatter.ofPattern(FILENAME_PATTERN)) + FILE_EXTENSION;
        File file = new File(dir, filename);
        
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }
        
        return file.getAbsolutePath();
    }
}

