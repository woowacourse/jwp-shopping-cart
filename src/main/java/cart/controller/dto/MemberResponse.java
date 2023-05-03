package cart.controller.dto;

import cart.dao.entity.MemberEntity;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;

    private MemberResponse(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static MemberResponse from(MemberEntity memberEntity) {
        return new MemberResponse(memberEntity.getId(),
                memberEntity.getEmail(),
                memberEntity.getPassword(),
                memberEntity.getName());
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
}
