package cart.dummydata;

import cart.dao.MemberDao;
import cart.domain.entity.Member;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class MemberInitializer implements ApplicationRunner {

    private final MemberDao memberDao;

    public MemberInitializer(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        Member member1 = Member.of("irene@email.com", "password1");
        Member member2 = Member.of("abcd@email.com", "password2");

        memberDao.insert(member1);
        memberDao.insert(member2);
    }
}
