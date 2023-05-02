package cart.dao.dummyData;

import cart.dao.MemberDao;
import cart.entity.MemberEntity;
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
        final MemberEntity memberEntity1 = MemberEntity.of("irene@email.com", "password1");
        final MemberEntity memberEntity2 = MemberEntity.of("abcd@email.com", "password2");

        memberDao.insert(memberEntity1);
        memberDao.insert(memberEntity2);
    }
}
