package ca.sfu.cmpt276.utils.enums;

public enum PaymentError {

    INSUFFICIENT_BALANCE("Insufficient balance to buy this plant.", 400);

    private final String message;
    private final int statusCode;

    PaymentError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
