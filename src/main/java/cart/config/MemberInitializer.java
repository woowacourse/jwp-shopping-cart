package cart.config;

import cart.entity.member.Member;
import cart.entity.member.MemberDao;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MemberInitializer {

    private final MemberDao memberDao;

    public MemberInitializer(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @PostConstruct
    public void init() {
        final Member gitchan = new Member("gitchan@wooteco.com", "password1");
        final Member boxster = new Member("boxster@wooteco.com", "password2");
        final Member irene = new Member("irene@wooteco.com", "password3");
        final Member hyun9mak = new Member("hyun9mak@wooteco.com", "password4");

        memberDao.save(gitchan);
        memberDao.save(boxster);
        memberDao.save(irene);
        memberDao.save(hyun9mak);
    }
}
