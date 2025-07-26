package org.example.starter.errors;

import java.time.Instant;

public record ApiError(String code, String message, Instant timestamp) {}
