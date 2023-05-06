package cart.persistence;

import cart.domain.member.Member;
import cart.domain.member.MemberRepository;
import cart.dto.LoginDto;
import cart.persistence.dao.MemberDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberDao memberDao;

    public MemberRepositoryImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public boolean contains(Member member) {
        return memberDao.contains(member);
    }

    @Override
    public Optional<Member> findByEmailAndPassword(LoginDto loginDto) {
        return memberDao.findByEmailAndPassword(loginDto);
    }
}
