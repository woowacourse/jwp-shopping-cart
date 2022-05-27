package woowacourse.shoppingcart.domain;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;

    public Member(final Long id, final String email, final String password, final String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static Member createWithoutId(final String email, final String password, final String nickname) {
        return new Member(null, email, password, nickname);
    }

    public Long getId() {
        return id;
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
}
