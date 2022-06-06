package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final UserId userId;
    private final Nickname nickname;
    private final Password password;

    private Customer(final Long id, final String userId, final String nickname, final Password password) {
        this.id = id;
        this.userId = new UserId(userId);
        this.nickname = new Nickname(nickname);
        this.password = password;
    }

    private Customer(final Long id, final String userId, final String nickname, final String password) {
        this(id, userId, nickname, new Password(password));
    }

    public static Customer from(final Long id, final String userId, final String nickname, final String password) {
        return new Customer(id, userId, nickname, password);
    }

    public void validateMatchingOriginalPassword(final String other) {
        password.validateMatchingOriginalPassword(other);
    }

    public void validateMatchingLoginPassword(final String other) {
        password.validateMatchingLoginPassword(other);
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
