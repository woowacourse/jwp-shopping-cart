package cart.service;

import cart.dto.MemberDto;
import cart.entity.MemberEntity;

public class MemberMapper {

    public static MemberDto toDto(MemberEntity member) {
        return new MemberDto(member.getEmail(), member.getPassword());
    }
}
