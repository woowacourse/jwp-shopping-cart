package cart.service;

import cart.dao.MemberDao;
import cart.dto.entity.MemberEntity;
import cart.dto.response.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAll() {
        List<MemberEntity> members = memberDao.findAll();

        System.out.println();

        return members.stream()
                .map(memberEntity -> new MemberResponse(
                        memberEntity.getEmail(),
                        memberEntity.getPassword())
                )
                .collect(Collectors.toList());
    }
}
