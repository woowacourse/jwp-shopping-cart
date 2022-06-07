package woowacourse.shoppingcart.domain;

import java.util.function.Function;

public class Customer {

    private Long id;
    private LoginId loginId;
    private Name username;
    private Password password;

    private Customer(Long id, LoginId loginId, Name username,
        Password password) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    private Customer(LoginId loginId, Name username, Password password) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Customer(String loginId, String name, String password) {
        this(new LoginId(loginId),
            new Name(name),
            new Password(password));
    }

    public Customer(Long id, String loginId, String name, String password){
        this(id,
            new LoginId(loginId),
            new Name(name),
            new Password(password));
    }

    public Customer ofHashPassword(Function<String, String> hashing) {
        String hashedPassword = hashing.apply(password.getValue());

        return new Customer(this.loginId, this.username, new Password(hashedPassword));
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId.getValue();
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public boolean isSamePassword(String password) {
        return this.password.isSamePassword(password);
    }
}
