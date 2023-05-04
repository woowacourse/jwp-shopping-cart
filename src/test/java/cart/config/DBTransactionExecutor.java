package cart.config;

import cart.cart.dao.CartDao;
import cart.cart.dao.CartMemoryDao;
import cart.member.dao.MemberDao;
import cart.member.dao.MemberMemoryDao;
import cart.product.dao.ProductDao;
import cart.product.dao.ProductMemoryDao;
import org.junit.jupiter.api.extension.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static cart.constant.TestConstant.*;

public class DBTransactionExecutor implements BeforeEachCallback, AfterEachCallback {
    private final JdbcTemplate jdbcTemplate;
    
    public DBTransactionExecutor(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void beforeEach(final ExtensionContext context) {
        jdbcTemplate.execute(CART_DELETE_ALL_SQL);
        jdbcTemplate.execute(PRODUCT_DELETE_ALL_SQL);
        jdbcTemplate.execute(MEMBER_DELETE_ALL_SQL);
        
        jdbcTemplate.execute(CART_ID_INIT_SQL);
        jdbcTemplate.execute(PRODUCT_ID_INIT_SQL);
        jdbcTemplate.execute(MEMBER_ID_INIT_SQL);
    }
    
    @Override
    public void afterEach(final ExtensionContext context) {
        jdbcTemplate.execute(CART_DELETE_ALL_SQL);
        jdbcTemplate.execute(PRODUCT_DELETE_ALL_SQL);
        jdbcTemplate.execute(MEMBER_DELETE_ALL_SQL);
        
        jdbcTemplate.execute(CART_ID_INIT_SQL);
        jdbcTemplate.execute(PRODUCT_ID_INIT_SQL);
        jdbcTemplate.execute(MEMBER_ID_INIT_SQL);
    }
}
