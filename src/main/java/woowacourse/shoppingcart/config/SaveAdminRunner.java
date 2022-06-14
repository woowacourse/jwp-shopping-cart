package woowacourse.shoppingcart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.AccountDao;
import woowacourse.shoppingcart.domain.Account;

@Component
public class SaveAdminRunner implements ApplicationRunner {

    @Value("${shopping.admin.email}")
    private String adminEmail;

    @Value("${shopping.admin.nickname}")
    private String adminNickname;

    @Value("${shopping.admin.password}")
    private String adminPassword;

    private final AccountDao accountDao;

    public SaveAdminRunner(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        accountDao
            .save(new Account(adminEmail, PasswordEncoder.encrypt(adminPassword), adminNickname, true));
    }
}
