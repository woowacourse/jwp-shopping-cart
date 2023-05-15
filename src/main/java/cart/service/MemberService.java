package cart.service;

import static java.util.stream.Collectors.toUnmodifiableList;

import cart.dao.MemberJdbcDao;
import cart.dto.MembetDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    private final MemberJdbcDao memberDao;

    public MemberService(final MemberJdbcDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MembetDto> findAll() {
        return memberDao.findAll().stream()
                .map(MembetDto::from)
                .collect(toUnmodifiableList());
    }
}
