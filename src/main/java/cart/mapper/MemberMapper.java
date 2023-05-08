package cart.mapper;

import cart.dto.request.MemberRequest;
import cart.dto.response.MemberResponse;
import cart.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberEntity requestToEntity(MemberRequest request) {
        return new MemberEntity(null, request.getEmail(), request.getPassword(), request.getName(), request.getPhone(), null, null);
    }

    public MemberResponse entityToResponse(MemberEntity entity) {
        return new MemberResponse(entity.getId(), entity.getEmail(), entity.getPassword(), entity.getName(), entity.getPhone());
    }
}
