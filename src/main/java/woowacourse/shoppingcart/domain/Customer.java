package woowacourse.shoppingcart.domain;

public class Customer {

    private Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;

    public Customer(Long id, String email, String password, String name, String phone, String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Customer(String email, String pw, String name, String phone, String address) {
        this(null, email, pw, name, phone, address);
    }
}
