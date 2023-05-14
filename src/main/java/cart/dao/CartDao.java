package cart.dao;

import cart.entity.CartEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CartDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public CartDao(final DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addProduct(final CartEntity cartEntity) throws DataIntegrityViolationException {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("member_id", cartEntity.getMemberId());
        params.addValue("product_id", cartEntity.getProductId());
        simpleJdbcInsert.executeAndReturnKey(params);
    }

    public void deleteProduct(final CartEntity cartEntity) {
        final String sql = "delete from cart where member_id = ? and product_id = ?";
        jdbcTemplate.update(sql, cartEntity.getMemberId(), cartEntity.getProductId());
    }

    public List<CartWithProduct> cartWithProducts(final int memberId) {
        final String sql = "select cart.member_id, product.id, product.name, product.image, product.price" +
                " from cart inner join product"
                + " on cart.product_id = product.id"
                + " where cart.member_id = ?";

        return jdbcTemplate.query(sql,
                (rs, cn) -> new CartWithProduct(
                        rs.getInt("cart.member_id"),
                        rs.getInt("product.id"),
                        rs.getString("product.name"),
                        rs.getString("product.image"),
                        rs.getInt("product.price")
                ), memberId
        );
    }

    public void deleteProducts(List<CartEntity> cartEntities) {
        final String sql = "delete from cart where member_id = ? and product_id = ?";
        jdbcTemplate.batchUpdate(
                sql,
                cartEntities,
                optimizeBatchSize(cartEntities.size()),
                (ps, argument) -> {
                    ps.setInt(1, argument.getMemberId());
                    ps.setInt(2, argument.getProductId());
                }
        );
    }

    public void insertProducts(List<CartEntity> cartEntities) {
        final String sql = "insert into cart (member_id, product_id) values(?, ?) ";
        jdbcTemplate.batchUpdate(
                sql,
                cartEntities,
                optimizeBatchSize(cartEntities.size()),
                (ps, argument) -> {
                    ps.setInt(1, argument.getMemberId());
                    ps.setInt(2, argument.getProductId());
                }
        );
    }

    private int optimizeBatchSize(final int size) {
        if (size > 100) {
            return 100;
        }
        return size;
    }
}
