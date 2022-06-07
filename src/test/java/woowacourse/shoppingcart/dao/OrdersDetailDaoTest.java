package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.dao.entity.OrdersDetailEntity;
import woowacourse.shoppingcart.dao.entity.ProductEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters"})
class OrdersDetailDaoTest {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrdersDetailDao ordersDetailDao;

    public OrdersDetailDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.ordersDetailDao = new OrdersDetailDao(jdbcTemplate);
    }

    @Test
    void 주문_정보_생성() {
        //given
        Long ordersId = 주문_등록(고객_등록());
        Long productId = 상품_등록();

        //when
        Long orderDetailId = ordersDetailDao.save(new OrdersDetailEntity(null, ordersId, productId, 5));

        //then
        assertThat(orderDetailId).isNotNull();
    }

    @Test
    void 주문_id로_주문_정보_조회() {
        //given
        Long productId = 상품_등록();
        Long ordersId = 주문_등록(고객_등록());

        final int insertCount = 3;
        for (int i = 0; i < insertCount; i++) {
            ordersDetailDao.save(new OrdersDetailEntity(null, ordersId, productId, 3));
        }

        //when
        final List<OrdersDetailEntity> ordersDetailsByOrderId = ordersDetailDao.findByOrderId(ordersId);

        //then
        assertThat(ordersDetailsByOrderId).hasSize(insertCount);
    }

    private Long 고객_등록() {
        String sql = "INSERT INTO customer (account, nickname, password, address, phone_number) "
                + "VALUES (:account, :nickname, :password, :address, :phoneNumber)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(
                new CustomerEntity(null, "yeonlog", "연로그", "aA!12345", "ㅇ", "01011112222"));

        jdbcTemplate.update(sql, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private Long 상품_등록() {
        String query = "INSERT INTO product (name, price, image_url) VALUES (:name, :price, :imageUrl)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(new ProductEntity("name", 1000, "imageUrl"));

        jdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private Long 주문_등록(Long customerId) {
        String sql = "INSERT INTO orders (customer_id) VALUES (:id)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource("id", customerId);

        jdbcTemplate.update(sql, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
