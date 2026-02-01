package io.github.lucasgb.transaction_scheduler_api.domain.exceptions;

public class SameAccountTransferException extends RuntimeException {
    public SameAccountTransferException() {
        super("Source and target accounts must be different");
    }
}