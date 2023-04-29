package cart;

import cart.domain.Member;
import cart.repository.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("local")
public class TestDataInit {
    @Autowired
    MemberDao memberDao;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        memberDao.save(new Member("glen@naver.com", "123456"));
    }
}
