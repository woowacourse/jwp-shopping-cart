package woowacourse.member.domain;

public class Member {

    private final Long id;
    private final Email email;
    private final Name name;
    private final Password password;

    private Member(Long id, Email email, Name name, Password password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static Member withEncrypt(String email, String name, String password) {
        return new Member(0L, new Email(email), new Name(name), Password.withEncrypt(password));
    }

    public static Member withoutEncrypt(Long id, String email, String name, String password) {
        return new Member(id, new Email(email), new Name(name), Password.withoutEncrypt(password));
    }

    public boolean isSameName(Name comparison) {
        return name.equals(comparison);
    }

    public boolean isSamePassword(Password comparison) {
        return password.equals(comparison);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
