package cart.dto;

import cart.repository.entity.MemberEntity;

public class MemberDto {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    private MemberDto(final Long id, final String name, final String email, final String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static MemberDto from(final MemberEntity memberEntity) {
        return new MemberDto(
                memberEntity.getId(),
                memberEntity.getName(),
                memberEntity.getEmail(),
                memberEntity.getPassword());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
