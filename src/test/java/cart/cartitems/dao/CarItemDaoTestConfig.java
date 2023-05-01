package cart.cartitems.dao;

import cart.member.dao.MemberDao;
import cart.product.dao.H2ProductDao;
import cart.product.dao.ProductDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static cart.TestFixture.*;

public class CarItemDaoTestConfig {

    private final ProductDao productDao;
    private final MemberDao memberDao;

    CarItemDaoTestConfig(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productDao = new H2ProductDao(namedParameterJdbcTemplate);
        this.memberDao = new MemberDao(namedParameterJdbcTemplate);
    }

    void beforeEach() {
        memberDao.save(NO_ID_MEMBER1);
        memberDao.save(NO_ID_MEMBER2);
        productDao.save(NO_ID_PRODUCT1);
        productDao.save(NO_ID_PRODUCT2);
        productDao.save(NO_ID_PRODUCT3);
    }
}
