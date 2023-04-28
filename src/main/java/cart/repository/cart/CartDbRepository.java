package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.cart.CartDbResponseDto;
import cart.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartDbRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public CartDbRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Cart> findAllByMember(final Member member) {
        String loadingCartSql = "SELECT id, member_id, product_id FROM cart WHERE member_id = :memberId";
        List<CartDbResponseDto> cartDbResponses = namedParameterJdbcTemplate.query(loadingCartSql, new MapSqlParameterSource("memberId", member.getId()), getCartRowMapper());

        List<Long> productIds = cartDbResponses.stream()
                .map(CartDbResponseDto::getProductId)
                .collect(Collectors.toList());

        if (productIds.isEmpty()) {
            return new ArrayList<>();
        }

        return getCarts(member, cartDbResponses, productIds);
    }

    private List<Cart> getCarts(final Member member, final List<CartDbResponseDto> cartDbResponses, final List<Long> productIds) {
        String loadingProductSql = "SELECT id, name, price, img_url FROM product WHERE id IN (:productIds)";
        List<Product> products = namedParameterJdbcTemplate.query(loadingProductSql, new MapSqlParameterSource("productIds", productIds), getProductRowMapper());

        return cartDbResponses.stream()
                .map(cartDbResponse -> {
                    Long cartId = cartDbResponse.getId();
                    Long productId = cartDbResponse.getProductId();
                    Product foundProduct = products.stream()
                            .filter(product -> product.isSameId(productId))
                            .findAny()
                            .orElseThrow(ProductNotFoundException::new);
                    return Cart.from(cartId, member, foundProduct);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void save(final Cart cart) {
        CartDbResponseDto cartDbResponseDto = CartDbResponseDto.from(cart.getId(), cart.getMember().getId(), cart.getProduct().getId());
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(cartDbResponseDto);
        KeyHolder keyHolder = simpleJdbcInsert.executeAndReturnKeyHolder(source);
        Long generatedId = keyHolder.getKeyAs(Long.class);
        cart.setId(generatedId);
    }

    @Override
    public void delete(final Member member, final Product product) {
        String sql = "DELETE FROM cart WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, member.getId(), product.getId());
    }

    private RowMapper<CartDbResponseDto> getCartRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long member_id = rs.getLong("member_id");
            Long product_id = rs.getLong("product_id");

            return CartDbResponseDto.from(id, member_id, product_id);
        };
    }

    private RowMapper<Product> getProductRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imgUrl = rs.getString("img_url");

            return Product.from(id, name, imgUrl, price);
        };
    }
}
