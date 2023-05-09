package cart.controller.dto;

import cart.dao.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;

    public static List<MemberResponse> from(List<MemberEntity> entities) {
        return entities.stream().map(
                entity -> new MemberResponse(entity.getId(), entity.getEmail(), entity.getPassword()))
            .collect(Collectors.toList());
    }

    public MemberResponse(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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
