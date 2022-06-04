package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcInsertHelper {

    public static SimpleJdbcInsert createWithTableName(JdbcTemplate jdbcTemplate, String tableName) {
        return new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(tableName)
            .usingGeneratedKeyColumns("id");
    }

}
