package cart.service;

import cart.dao.MemberDao;
import cart.dto.response.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerService {

    @Autowired
    private final MemberDao memberDao;

    public CustomerService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<MemberDto> findAll() {
        return memberDao.findAll().stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }
}
