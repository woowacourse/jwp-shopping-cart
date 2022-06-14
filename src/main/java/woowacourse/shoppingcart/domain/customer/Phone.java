package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Phone {

    private static final String PHONE_FORMAT = "^010-[0-9]{4}-[0-9]{4}$";

    private final String phone;

    public Phone(final String phone) {
        validatePhoneNumber(phone);
        this.phone = phone;
    }

    public static void validatePhoneNumber(final String phone) {
        if (phone.isBlank() || !phone.matches(PHONE_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 전화번호 형식입니다.");
        }
    }

    public String getValue() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone1 = (Phone) o;
        return Objects.equals(phone, phone1.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
