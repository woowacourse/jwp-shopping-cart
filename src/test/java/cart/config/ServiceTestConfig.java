package cart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public abstract class ServiceTestConfig {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
}
