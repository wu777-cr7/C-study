package com.example.snackshop.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNumberGenerator {
    private static final AtomicInteger counter = new AtomicInteger(0);
    public static String generate() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int seq = counter.incrementAndGet() % 10000;
        return "SN" + date + String.format("%04d", seq);
    }
}