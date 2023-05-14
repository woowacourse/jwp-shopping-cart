package cart;

import cart.cart.dao.JdbcCartDao;
import cart.cart_product.dao.JdbcCartProductDao;
import cart.member.dao.JdbcMemberDao;
import cart.product.dao.JdbcProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@Import({
        JdbcCartProductDao.class,
        JdbcCartDao.class,
        JdbcMemberDao.class,
        JdbcProductDao.class
})
@Sql("/reset-cart_product-data.sql")
@JdbcTest
public abstract class DaoTest {

    @Autowired
    protected JdbcCartDao cartDao;

    @Autowired
    protected JdbcCartProductDao cartProductDao;

    @Autowired
    protected JdbcMemberDao memberDao;

    @Autowired
    protected JdbcProductDao productDao;
}
