package cart.persistence;

import cart.entity.Member;
import cart.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAllProductsByMemberId(Integer memberId) {
        String query = "SELECT p.* " +
                "FROM CART c " +
                "JOIN CART_PRODUCT cp ON c.id = cp.cart_id " +
                "JOIN PRODUCT p ON p.id = cp.product_id " +
                "WHERE c.member_id = ?";

        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String url = resultSet.getString("url");
            int price = resultSet.getInt("price");

            return new Product(id, name, url, price);
        }, memberId);
    }

    public Integer remove(Integer memberId) {
        final var query = "DELETE FROM CART WHERE member_id = ?";
        return jdbcTemplate.update(query, memberId);
    }

    public Optional<Member> findByEmail(String email) {
        final var query = "SELECT * FROM MEMBER WHERE email = ?";
        Member member = jdbcTemplate.queryForObject(query, getMemberRowMapper(), email);

        return Optional.of(member);
    }

    private RowMapper<Member> getMemberRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String mail = resultSet.getString("email");
            String password = resultSet.getString("password");

            return new Member(id, mail, password);
        };
    }
}
