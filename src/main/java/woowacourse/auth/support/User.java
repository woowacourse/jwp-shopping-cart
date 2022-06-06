package woowacourse.auth.support;

public class User {

    public static final String MEMBER = "member";
    public static final String NON_MEMBER = "anonymous";

    private final Long id;
    private final String authority;

    public User(String authority) {
        this(null, authority);
    }

    public User(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public boolean isMember() {
        return authority.equals(MEMBER);
    }

    public boolean isNonMember() {
        return authority.equals(NON_MEMBER);
    }

    public Long getId() {
        return id;
    }
}
