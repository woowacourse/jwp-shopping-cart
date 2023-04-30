package cart.member.dto;

import cart.member.domain.Member;

public class DtoMapper {

    private DtoMapper() {
    }

    public static MemberDto toMemberDto(Member member) {
        return new MemberDto(member.getEmail(), member.getPassword(), member.getPhoneNumber());
    }

    public static Member toMember(MemberAddRequest memberAddRequest) {
        return new Member(memberAddRequest.getEmail(), memberAddRequest.getPassword(), memberAddRequest.getPhoneNumber());
    }
}
