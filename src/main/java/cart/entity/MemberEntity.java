package cart.entity;

import cart.dto.request.MemberRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MemberEntity {
    private final Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private final Timestamp createdAt;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void replace(MemberRequest memberRequest) {
        this.name = memberRequest.getName();
        this.phone = memberRequest.getPhone();
        this.email = memberRequest.getEmail();
        this.password = memberRequest.getPassword();
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
