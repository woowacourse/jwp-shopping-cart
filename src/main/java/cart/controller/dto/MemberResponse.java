package cart.controller.dto;

import cart.dao.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;

    private MemberResponse(Long id, String email, String password) {
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

    public static MemberResponse from(MemberEntity memberEntity) {
        return new MemberResponse(memberEntity.getId(), memberEntity.getEmail(),
            memberEntity.getPassword());
    }

    public static List<MemberResponse> from(List<MemberEntity> memberEntities) {
        return memberEntities.stream()
            .map(entity -> new MemberResponse(entity.getId(), entity.getEmail(),
                entity.getPassword()))
            .collect(Collectors.toList());
    }
}
