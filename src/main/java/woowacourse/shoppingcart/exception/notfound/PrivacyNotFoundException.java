package woowacourse.shoppingcart.exception.notfound;

public class PrivacyNotFoundException extends CustomerNotFoundException {
    public PrivacyNotFoundException() {
        super("유저의 개인정보를 찾을 수 없습니다.");
    }
}
