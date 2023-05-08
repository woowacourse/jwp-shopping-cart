package cart.member.service;

import cart.member.dto.MemberResponse;

import java.util.List;

public interface MemberService {
    List<MemberResponse> findAll();
    
    MemberResponse findByEmailAndPassword(final String email, final String password);
}
