package cart.service;

import cart.dao.h2Implement.MemberH2Dao;
import cart.dto.MemberResponse;
import cart.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberH2Dao memberH2Dao;

    public MemberService(MemberH2Dao memberH2Dao) {
        this.memberH2Dao = memberH2Dao;
    }


    public List<MemberResponse> selectAllMember() {
        List<Member> members = memberH2Dao.selectAll();
        return members.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
