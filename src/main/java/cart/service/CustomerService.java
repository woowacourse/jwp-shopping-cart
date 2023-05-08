package cart.service;

import cart.dao.MemberDao;
import cart.dto.response.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final MemberDao memberDao;

    public CustomerService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberDto> findAll() {
        return memberDao.findAll().stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }
}
