package woowacourse.member.dto;

public class MemberRegisterRequest {

    private String email;
    private String password;
    private String name;

    private MemberRegisterRequest() {
    }

    public MemberRegisterRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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
