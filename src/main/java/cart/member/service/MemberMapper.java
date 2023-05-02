package cart.member.service;

import cart.member.dto.MemberDto;
import cart.member.entity.MemberEntity;

public class MemberMapper {

    public static MemberDto toDto(MemberEntity member) {
        return new MemberDto(member.getEmail(), member.getPassword());
    }
}
