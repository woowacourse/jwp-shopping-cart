package woowacourse.shoppingcart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Component
public class SaveAdminRunner implements ApplicationRunner {

    @Value("${shopping.admin.email}")
    private String adminEmail;

    @Value("${shopping.admin.nickname}")
    private String adminNickname;

    @Value("${shopping.admin.password}")
    private String adminPassword;

    private final CustomerDao customerDao;

    public SaveAdminRunner(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        customerDao.save(new Customer(adminEmail, PasswordEncoder.encrypt(adminPassword), adminNickname, true));
    }
}
