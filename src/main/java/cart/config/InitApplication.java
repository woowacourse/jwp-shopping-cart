package cart.config;

import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplication implements CommandLineRunner {
    private final MemberDao memberDao;
    
    public InitApplication(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    @Override
    public void run(final String... args) {
        memberDao.save(new Member("a@a.com", "password1"));
        memberDao.save(new Member("b@b.com", "password2"));
    }
}
