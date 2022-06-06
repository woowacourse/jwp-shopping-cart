package woowacourse.shoppingcart.entity;

public class Customer {

    private final Long id;
    private final String userId;
    private final String nickname;
    private final String password;

    public Customer(final String userId, final String nickname, final String password) {
        this(null, userId, nickname, password);
    }

    public Customer(final Long id, final String userId, final String nickname, final String password) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }

    public boolean hasSamePassword(final String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
