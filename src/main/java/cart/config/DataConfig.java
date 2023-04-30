package cart.config;

import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DataConfig {

    @Component
    private static class InitDataInserter implements CommandLineRunner {

        private final MemberDao memberDao;

        private InitDataInserter(final MemberDao memberDao) {
            this.memberDao = memberDao;
        }

        @Override
        public void run(final String... args) {
            memberDao.save(new Member("a@a.com", "password1"));
            memberDao.save(new Member("b@b.com", "password2"));
        }
    }
}
