package cart.repository;

import cart.entity.Cart;
import cart.repository.exception.CartPersistanceFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCartRepository implements CartRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCartRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Cart save(Cart cart) throws CartPersistanceFailedException {
        String sql = "insert into CART (member_email, product_id) values (:memberEmail, :productId)";

        try {
            int updateCount = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(cart));
            if (updateCount == 0) {
                throw new CartPersistanceFailedException("장바구니 정보를 저장하는 데에 실패했습니다.");
            }
        } catch (DuplicateKeyException exception) {
            throw new CartPersistanceFailedException("동일한 회원이 동일한 상품을 중복해서 장바구니에 담을 수 없습니다.");
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw new CartPersistanceFailedException("존재하지 않는 멤버 또는 상품으로 장바구니에 담을 수 없습니다.", exception);
        }

        return cart;
    }

    @Override
    public List<Cart> findAllByMemberEmail(String memberEmail) {
        String sql = "select * from CART where member_email = :memberEmail";
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("memberEmail", memberEmail),
                (rs, rowNum) -> new Cart(rs.getString("member_email"), rs.getLong("product_id"))
        );
    }
}
