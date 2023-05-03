package cart.exception;

import java.util.Arrays;

public enum ErrorCode {
    // Product
    NAME_NOT_BLANK("P001", "상품명 데이터 누락", "상품명은 필수 입력 값입니다."),
    IMG_URL_NOT_BLANK("P002", "이미지 Url 데이터 누락", "이미지 Url은 필수 입력 값입니다."),
    PRICE_NOT_BLANK("P003", "상품가격 데이터 누락", "상품가격은 필수 입력 값입니다."),
    PRICE_MIN_VALUE("P004", "최소 값 기준치 미달", "상품가격은 0 이상이어야 합니다."),

    // DataBase,
    ID_NOT_FOUND("D001", "데이터 조회 실패", "일치하는 ID가 없습니다."),

    UNKNOWN("A001", "정의되지 않은 오류", "정의되지 않은 오류입니다."),
    ;

    private final String code;
    private final String message;
    private final String detail;

    ErrorCode(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorCode findErrorCode(String detail) {
        return Arrays.stream(values())
                .filter(errorCode -> errorCode.detail.equals(detail))
                .findAny()
                .orElse(UNKNOWN);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }
}
