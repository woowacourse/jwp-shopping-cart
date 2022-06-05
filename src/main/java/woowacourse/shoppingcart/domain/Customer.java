package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final boolean isAdmin;

    public Customer(String email, String password, String nickname) {
        this(null, email, password, nickname, false);
    }

    public Customer(String email, String password, String nickname, boolean isAdmin) {
        this(null, email, password, nickname, isAdmin);
    }

    public Customer(Long id, String email, String password, String nickname) {
        this(id, email, password, nickname, false);
    }

    public Customer(Long id, String email, String password, String nickname, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
