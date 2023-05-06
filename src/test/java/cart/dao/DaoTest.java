package cart.dao;

import cart.dao.cart.JdbcCartDao;
import cart.dao.member.JdbcMemberDao;
import cart.dao.product.JdbcProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@Import({
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
    protected JdbcMemberDao memberDao;

    @Autowired
    protected JdbcProductDao productDao;
}
