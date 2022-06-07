package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.ThumbnailImage;

@Repository
public class ThumbnailImageDao {
    private final JdbcTemplate jdbcTemplate;

    public ThumbnailImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final ThumbnailImage thumbnailImage, final Long productId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("thumbnail_image")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("product_id", productId);
        params.put("url", thumbnailImage.getUrl());
        params.put("alt", thumbnailImage.getAlt());

        simpleJdbcInsert.execute(params);
    }
}
