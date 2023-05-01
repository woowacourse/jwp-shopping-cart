package cart.mapper;

import cart.domain.Member;
import cart.dto.MemberRequest;
import cart.dto.MemberResponse;
import cart.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member requestToMember(MemberRequest request) {
        return new Member(request.getEmail(), request.getPassword(), request.getName(), request.getPhone());
    }

    public MemberResponse entityToResponse(MemberEntity entity) {
        return new MemberResponse(entity.getId(), entity.getEmail(), entity.getPassword(), entity.getName(), entity.getPhone());
    }
}
