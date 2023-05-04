package cart.dao;

import cart.domain.Member;
import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM member";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Member(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        ));
    }

    public List<Product> findProductByEmail(final String email) {
        String sql = "SELECT product.id, name, image_url, price FROM cart JOIN product ON cart.product_id = product.id WHERE member_id = (SELECT id FROM member WHERE email LIKE ?)";
        return jdbcTemplate.query(sql,
                ps -> ps.setString(1, email),
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("image_url"),
                        rs.getInt("price")
                ));
    }
}
