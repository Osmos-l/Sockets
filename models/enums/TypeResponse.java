package models.enums;

public enum TypeResponse {
    CREATED("DONE"),
    FOUND("GOOD"),
    NOT_FOUND("BAD"),
    ERROR("ERROR");

    private String message;

    TypeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}