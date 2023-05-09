package cart.auth;

import cart.domain.member.Member;

public class TestMemberResponse {

    private final long id;
    private final String email;
    private final String password;
    private final String name;

    public TestMemberResponse(final long id, final String email, final String password, final String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static TestMemberResponse from(final Member member) {
        return new TestMemberResponse(member.getId(), member.getEmail(), member.getPassword(), member.getName());
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
