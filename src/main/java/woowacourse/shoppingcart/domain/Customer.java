package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final String email;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public Customer(String email, String password, String address, String phoneNumber) {
        this(null, email, password, address, phoneNumber);
    }

    public Customer(Long id, String email, String password, String address, String phoneNumber) {
        this.id = id;
        this.email = Objects.requireNonNull(email, "email은 필수 입력 사항입니다.");
        this.password = Objects.requireNonNull(password, "password는 필수 입력 사항압니다.");
        this.address = Objects.requireNonNull(address, "address는 필수 입력 사항압니다.");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber는 필수 입력 사항압니다.");
    }
}
