package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberAuthDto;
import cart.dto.response.MemberResponse;
import cart.entity.MemberEntity;
import cart.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findMembers() {
        return memberDao.findAll()
                .stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberEntity findMember(final MemberAuthDto memberAuthDto) {
        return memberDao.findByEmailAndPassword(memberAuthDto.getEmail(), memberAuthDto.getPassword())
                .orElseThrow(() -> new NotFoundException("등록되지 않은 회원입니다."));
    }
}
