package cart.data;

import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialData implements CommandLineRunner {
    private final MemberDao memberDao;

    public InitialData(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void run(String... args) throws Exception {
        memberDao.save(new Member("aa@woowa.com","password1"));
        memberDao.save(new Member("bb@woowa.com","password2"));
    }
}
