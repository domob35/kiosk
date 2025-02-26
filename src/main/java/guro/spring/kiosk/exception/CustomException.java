package guro.spring.kiosk.exception;

public class CustomException extends RuntimeException {
    private final CustomEEnum customEEnum;

    public CustomException(CustomEEnum customEEnum) {
        super(customEEnum.getMessage());
        this.customEEnum = customEEnum;
    }

    public CustomException(CustomEEnum customEEnum, String message) {
        super(message);
        this.customEEnum = customEEnum;
    }

    public CustomEEnum getCustomEEnum() {
        return customEEnum;
    }
}


