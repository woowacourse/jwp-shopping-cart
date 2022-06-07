package woowacourse.member.domain;

public class Member {

    private final long id;
    private final Email email;
    private final Name name;
    private final Password password;

    private Member(long id, Email email, Name name, Password password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Member(String email, String name, Password password) {
        this(0L, new Email(email), new Name(name), password);
    }

    public Member(long id, String email, String name, Password password) {
        this(id, new Email(email), new Name(name), password);
    }

    public boolean isSameName(Name comparison) {
        return name.equals(comparison);
    }

    public boolean isSamePassword(Password comparison) {
        return password.equals(comparison);
    }

    public long getId() {
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
