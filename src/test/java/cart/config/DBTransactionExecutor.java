package cart.config;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;

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
