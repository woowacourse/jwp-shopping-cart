package woowacourse.shoppingcart.domain;

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

    public void comparePasswordFrom(final String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("아아디 또는 비밀번호를 확인하여주세요.");
        }
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
