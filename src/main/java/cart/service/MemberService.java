package cart.service;

import cart.controller.dto.response.MemberResponse;
import cart.dao.MemberDao;
import cart.dao.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberResponse> findAllMember() {
        List<MemberEntity> findMembers = memberDao.findAll();

        return findMembers.stream()
                .map(entity -> new MemberResponse(entity.getEmail(), entity.getPassword()))
                .collect(Collectors.toList());
    }

}
