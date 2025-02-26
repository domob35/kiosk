package guro.spring.kiosk.exception;

public enum CustomEEnum {
    BRANCH_NOT_FOUND(404, "지점을 찾을 수 없습니다."),
    VALIDATION_ERROR(400, "유효성 검사 오류"),
    PRODUCT_EMPTY_IN_CATEGORY(404, "카테고리에 상품이 없습니다."),
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    PHONE_NUMBER_REQUIRED(400, "전화번호가 필요합니다."),
    MEMBER_ID_REQUIRED(400, "회원 아이디가 필요합니다."),
    PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
    OPTION_NOT_FOUND(404, "옵션을 찾을 수 없습니다."),
    USE_COUPON_OR_MEMBERSHIP_POINT_WITHOUT_MEMBER(400, "쿠폰 또는 멤버십 포인트를 사용할 때는 회원이 필요합니다."),
    INVALID_PAYMENT_METHOD(400, "유효하지 않은 결제 수단입니다."),
    INGREDIENT_NOT_FOUND(404, "재료를 찾을 수 없습니다."),
    INGREDIENT_NOT_ENOUGH(400, "재료가 부족합니다."),
    MEMBER_POINT_NOT_ENOUGH(400, "회원 포인트가 부족합니다."),
    COUPON_NOT_FOUND(404, "쿠폰을 찾을 수 없습니다."),
    COUPON_ALREADY_USED(400, "쿠폰이 이미 사용되었습니다."),
    ;

    private final String message;
    private final int statusCode;


    CustomEEnum(int statusCode, String message) {
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
