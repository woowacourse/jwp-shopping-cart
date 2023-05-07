package cart.persistence;

import cart.domain.cart.Item;
import cart.domain.member.Member;
import cart.domain.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class H2CartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public H2CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveItem(Item item) {
        String sql = "INSERT INTO CART(member_id, product_id) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, item.getMemberId());
            ps.setLong(2, item.getProductId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Item> findAllItemsByMemberId(Long memberId) {
        String sql = "SELECT * " +
                "FROM (SELECT * FROM CART WHERE member_id=?) AS FILTERED_CART " +
                "JOIN MEMBER ON FILTERED_CART.member_id = MEMBER.id " +
                "JOIN PRODUCT ON FILTERED_CART.product_id = PRODUCT.id";


        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Item(
                resultSet.getLong("id"),
                new Member(
                        resultSet.getLong("MEMBER.id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ),
                new Product(
                        resultSet.getLong("Product.id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url")
                )
        ), memberId);
    }

    @Override
    public void deleteItemById(Long id) {
        String sql = "DELETE FROM CART WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
