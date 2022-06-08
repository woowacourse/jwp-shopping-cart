package woowacourse.shoppingcart.exception;

public enum ExceptionMessage {
    CODE_1001(1001, "존재하지 않는 URL입니다"),
    CODE_1002(1002, "토큰의 유효 기간이 만료되었습니다"),
    CODE_1003(1003, "토큰이 유효하지 않습니다"),
    CODE_1004(1004, "인증이 필요한 접근입니다"),

    CODE_2101(2101, "이메일 형식이 맞지 않습니다"),
    CODE_2102(2102, "닉네임 형식이 맞지 않습니다"),
    CODE_2103(2103, "비밀번호 형식이 맞지 않습니다"),
    CODE_2001(2001, "이미 존재하는 이메일입니다"),
    CODE_2201(2201, "이메일 혹은 비밀번호가 일치하지 않습니다"),
    CODE_2202(2202, "입력된 비밀번호가 현재 비밀번호와 일치하지 않습니다"),

    CODE_3001(3001, "상품 목록에서 요청하신 상품이 존재하지 않습니다"),

    CODE_4101(4101, "수량 형식이 맞지 않습니다"),
    CODE_4001(4101, "해당 상품이 장바구니에 존재하지 않습니다"),

    CODE_5001(4101, "존재하지 않는 주문입니다"),
    ;

    private int code;
    private String message;

    ExceptionMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
