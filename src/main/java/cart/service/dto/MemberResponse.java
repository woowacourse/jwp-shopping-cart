package cart.service.dto;

public class MemberResponse {

    private final Long id;
    private final String role;
    private final String email;
    private final String password;
    private final String nickname;
    private final String telephone;

    public MemberResponse(Long id, String role, String email, String password, String nickname,
                          String telephone) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
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

    public String getTelephone() {
        return telephone;
    }
}
