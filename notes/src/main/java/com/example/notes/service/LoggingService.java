package com.example.notes.service;


public class LoggingService {
    private static LoggingService instance;

    private LoggingService() {
        // Приватный конструктор предотвращает создание экземпляров
    }

    public static synchronized LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}
