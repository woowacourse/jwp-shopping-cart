package cart.dao;

import cart.domain.entity.CartItem;
import cart.domain.entity.Member;
import cart.domain.entity.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcCartItemDao implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CartItem> cartItemEntityRowMapper = (resultSet, rowNum) -> {
        Member member = Member.of(
                resultSet.getLong("member_id"),
                resultSet.getString("member_email"),
                resultSet.getString("member_password")
        );
        Product product = Product.of(
                resultSet.getLong("product_id"),
                resultSet.getString("product_name"),
                resultSet.getString("product_image"),
                resultSet.getInt("product_price")
        );
        return CartItem.of(
                resultSet.getLong("id"),
                member,
                product
        );
    };

    @Override
    public List<CartItem> selectAllByMemberId(final Long memberId) {
        String sql = "SELECT ci.id, member_id, email AS member_email, password AS member_password, product_id, name AS product_name, image AS product_image, price AS product_price " +
                "FROM cart_item AS ci " +
                "LEFT JOIN product AS p ON ci.product_id =p.id " +
                "LEFT JOIN member AS m ON ci.member_id = m.id " +
                "WHERE member_id = ? ";
        return jdbcTemplate.query(sql, cartItemEntityRowMapper, memberId);
    }

    @Override
    public long insert(final CartItem cartItem) {
        try {
            String sql = "INSERT INTO cart_item (member_id, product_id) values (?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setLong(1, cartItem.getMember().getId());
                pst.setLong(2, cartItem.getProduct().getId());
                return pst;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException("장바구니에 담을 수 없습니다");
        }
    }

    @Override
    public int deleteByIdAndMemberId(final long id, final long memberId) {
        String sql = "DELETE FROM cart_item WHERE id = ? AND member_id = ?";
        return jdbcTemplate.update(sql, id, memberId);
    }
}
