package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse findOne(Long id) {
        MemberEntity memberEntity = memberDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 존재하지 않습니다."));

        return MemberResponse.from(memberEntity);
    }

    public MemberResponse findByEmailAndPassword(String email, String password) {
        MemberEntity memberEntity = memberDao.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 존재하지 않습니다."));

        return MemberResponse.from(memberEntity);
    }

    public List<MemberResponse> findAll() {
        return memberDao.findAll()
                .stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}
