package woowacourse.shoppingcart.domain;

import java.util.function.Function;

public class Customer {

    private Long id;
    private String loginId;
    private String username;
    private String password;

    public Customer(Long id, String loginId, String username, String password) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Customer(String loginId, String username, String password) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Customer ofHashPassword(Function<String, String> hashing) {
        String hashedPassword = hashing.apply(password);

        return new Customer(this.loginId, this.username, hashedPassword);
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
