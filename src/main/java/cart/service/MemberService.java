package cart.service;

import cart.entity.Member;
import cart.repository.JdbcMemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final JdbcMemberRepository memberRepository;

    public MemberService(final JdbcMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }
}
