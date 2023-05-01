package cart.entity;

import cart.dto.MemberRequest;

import java.sql.Timestamp;

public class MemberEntity {
    private final Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public MemberEntity(Long id, String email, String password, String name, String phone, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void replace(MemberRequest memberRequest) {
        this.name = memberRequest.getName();
        this.phone = memberRequest.getPhone();
        this.email = memberRequest.getEmail();
        // TODO : password 정책
        this.password = memberRequest.getPassword();
    }
}
