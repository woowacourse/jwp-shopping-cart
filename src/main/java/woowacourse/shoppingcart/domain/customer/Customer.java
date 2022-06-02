package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final String email;
    private String nickname;
    private String password;

    public Customer(final Long id, final String email, final String nickname, final String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer(final String email, final String nickname, final String password) {
        this(null, email, nickname, password);
    }

    public void update(final String nickname, final String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public boolean equalsPassword(final String password) {
        return password.equals(this.password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
