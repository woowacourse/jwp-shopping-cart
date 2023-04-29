package cart.dto;

public enum SuccessCode {

    CREATE_ITEM(201, "상품 추가 성공"),
    UPDATE_ITEM(200, "상품 업데이트 성공"),
    DELETE_ITEM(200, "상품 업데이트 성공"),
    GET_ITEMS(200, "상품 조회 성공");


    private final int status;
    private final String message;

    SuccessCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
