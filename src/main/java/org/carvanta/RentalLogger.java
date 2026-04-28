package org.carvanta;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalLogger {

    private final File logFile;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RentalLogger(String logFileName) {
        this.logFile = new File(logFileName);
    }

    /**
     * Write a rental transaction to the log file
     * Format: timestamp | customerId | customerName | carId | carDetails | action | daysRented
     */
    public void logRentalTransaction(Customer customer, Car car, String action, int days) {
        String timestamp = LocalDateTime.now().format(dateFormatter);
        String logEntry = String.format("%s | Customer: %s (ID: %d) | Car: %s %s (ID: %d) | Action: %s | Days: %d",
                timestamp, customer.getName(), customer.getId(),
                car.getBrand(), car.getModel(), car.getCarId(),
                action, days);

        try {
            // Append to file (not overwrite)
            try (FileWriter fw = new FileWriter(logFile, true);
                 BufferedWriter writer = new BufferedWriter(fw)) {
                writer.write(logEntry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to rental log: " + e.getMessage());
        }
    }

    /**
     * Read and display all rental transactions from the log file
     */
    public void displayRentalHistory() {
        if (!logFile.exists()) {
            System.out.println("No rental history available.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            System.out.println("\n===== RENTAL HISTORY =====");
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    System.out.println(line);
                    count++;
                }
            }

            if (count == 0) {
                System.out.println("No rental transactions recorded.");
            } else {
                System.out.println("\nTotal Transactions: " + count);
            }
        } catch (IOException e) {
            System.out.println("Error reading rental log: " + e.getMessage());
        }
    }

    /**
     * Get rental history as a List
     */
    public List<String> getRentalHistory() {
        List<String> history = new ArrayList<>();

        if (!logFile.exists()) {
            return history;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    history.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading rental log: " + e.getMessage());
        }

        return history;
    }

    /**
     * Clear the rental history log
     */
    public void clearHistory() {
        try {
            try (FileWriter fw = new FileWriter(logFile)) {
                // This overwrites the file, effectively clearing it
            }
            System.out.println("Rental history cleared.");
        } catch (IOException e) {
            System.out.println("Error clearing rental log: " + e.getMessage());
        }
    }

    /**
     * Export rental history to a formatted CSV file
     */
    public void exportToCSV(String csvFileName) {
        List<String> history = getRentalHistory();

        if (history.isEmpty()) {
            System.out.println("No history to export.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName))) {
            // Write CSV header
            writer.write("Timestamp,Customer Name,Customer ID,Car Brand,Car Model,Car ID,Action,Days");
            writer.newLine();

            // Parse and write each transaction
            for (String transaction : history) {
                // Simple parsing - extract fields from the formatted string
                String csvLine = transaction.replace(" | ", ",")
                        .replace("Customer: ", "")
                        .replace(" (ID: ", ",")
                        .replace(")", "")
                        .replace("Car: ", "")
                        .replace("Action: ", "")
                        .replace("Days: ", "");

                writer.write(csvLine);
                writer.newLine();
            }

            System.out.println("Rental history exported to: " + csvFileName);
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }
}
