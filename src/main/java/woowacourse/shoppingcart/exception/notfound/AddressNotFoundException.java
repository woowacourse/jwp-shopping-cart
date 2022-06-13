package woowacourse.shoppingcart.exception.notfound;

public class AddressNotFoundException extends CustomerNotFoundException {
    public AddressNotFoundException() {
        super("유저의 주소 정보를 찾을 수 없습니다.");
    }
}
