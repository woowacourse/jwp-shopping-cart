package woowacourse.member.dto;

public class MemberDeleteRequest {

    private String password;

    private MemberDeleteRequest() {
    }

    public MemberDeleteRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
