package cart.service;

import cart.controller.exception.MemberNotFoundException;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberDto;
import cart.dto.response.MemberResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        final Optional<List<Member>> membersOptional = memberDao.findAll();
        if (membersOptional.isEmpty()) {
            return new ArrayList<>();
        }
        return membersOptional.get().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public Member find(final MemberDto memberDto) {
        return memberDao.findByEmail(memberDto.getEmail())
                .orElseThrow(MemberNotFoundException::new);
    }
}
