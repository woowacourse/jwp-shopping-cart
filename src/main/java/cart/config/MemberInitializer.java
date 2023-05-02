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
        final Member memberA = new Member("a@a.com", "password1");
        final Member memberB = new Member("b@b.com", "password2");

        memberDao.save(memberA);
        memberDao.save(memberB);
    }
}
