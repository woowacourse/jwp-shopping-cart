package cart.dto;

import cart.dao.entity.UserEntity;

public class ResponseUserDto {

    private final Long id;
    private final String email;
    private final String password;

    public ResponseUserDto(final UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
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
}
