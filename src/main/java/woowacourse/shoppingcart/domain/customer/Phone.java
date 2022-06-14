package woowacourse.shoppingcart.domain.customer;

public class Phone {

    private static final String PHONE_FORMAT = "^010-[0-9]{4}-[0-9]{4}$";

    private final String phone;

    public Phone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    private static void validatePhone(String phone) {
        if (phone.isBlank() || !phone.matches(PHONE_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 전화번호 형식입니다.");
        }
    }

    public String getValue() {
        return phone;
    }
}
