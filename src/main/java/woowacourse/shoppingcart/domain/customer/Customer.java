package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final UserId userId;
    private final Nickname nickname;
    private final Password password;

    public Customer(final Long id, final String userId, final String nickname, final String password) {
        this.id = id;
        this.userId = new UserId(userId);
        this.nickname = new Nickname(nickname);
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
