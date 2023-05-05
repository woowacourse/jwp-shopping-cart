package cart.common.exception;

public enum PersistenceExceptionMessages {
    PRODUCT_NOTFOUND("id에 해당하는 Product를 찾지 못했습니다."),
    CART_DELETING_FAILED("삭제할 대상이 데이터베이스에 존재하지 않습니다."),
    CART_SAVING_WITH_INVALID_VALUES("존재하지 않는 Member 또는 Product로 Cart를 저장할 수 없습니다."),
    CART_DUPLICATED_SAVING("동일한 Member와 동일한 Product 정보가 담긴 Cart를 저장할 수 없습니다."),
    CART_SAVING_FAILED("Cart 정보를 저장하는 데에 실패했습니다."),
    MEMBER_NOTFOUND("주어진 id로 Member를 찾을 수 없습니다."),
    MEMBER_DUPLICATED_EMAIL("이미 등록된 email입니다."),
    MEMBER_SAVING_FAILED("Member 정보를 저장하는 데에 실패했습니다.");

    private final String message;

    PersistenceExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
