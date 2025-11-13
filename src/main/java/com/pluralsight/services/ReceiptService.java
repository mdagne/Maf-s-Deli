package com.pluralsight.services;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Saves receipts to receipts/ directory. Filename format: yyyyMMdd-HHmmss.txt
public final class ReceiptService {
    private static final String RECEIPTS_DIR = "receipts";

    public static String saveReceipt(LocalDateTime placedAt, String content) throws IOException {
        File dir = new File(RECEIPTS_DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Could not create receipts directory");
            }
        }
        String filename = placedAt.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".txt";
        File file = new File(dir, filename);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }
        return file.getAbsolutePath();
    }
}

