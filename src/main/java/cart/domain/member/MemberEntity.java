package cart.domain.member;

public class MemberEntity {

    private final MemberId id;
    private final Member member;

    public MemberEntity(final long id, final String username, final String password) {
        this.id = new MemberId(id);
        this.member = new Member(username, password);
    }

    public long getId() {
        return id.getValue();
    }

    public String getUsername() {
        return member.getUsername();
    }

    public String getPassword() {
        return member.getPassword();
    }
}
