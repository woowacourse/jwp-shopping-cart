package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;

@JdbcTest
class H2CartDaoTest {

  private long memberId;
  private long productId;
  private CartEntity cartEntity;

  private H2CartDao h2CartDao;
  private SimpleJdbcInsert simpleJdbcInsert;
  @Autowired
  private NamedParameterJdbcTemplate namedParameterjdbcTemplate;
  @BeforeEach
  void setUp() {
    simpleJdbcInsert = new SimpleJdbcInsert(namedParameterjdbcTemplate.getJdbcTemplate())
        .withTableName("cart")
        .usingGeneratedKeyColumns("id");
    h2CartDao = new H2CartDao(namedParameterjdbcTemplate);
    memberId = saveMember();
    productId = saveProduct();
    cartEntity = new CartEntity(memberId, productId);
  }

  @Test
  void save() {
    //when
    h2CartDao.save(cartEntity);

    //then
    final List<CartEntity> carts = getCarts();
    final CartEntity findEntity = carts.get(0);
    assertThat(carts.size()).isEqualTo(1);
    assertThat(findEntity)
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(cartEntity);
  }

  private long saveMember() {
    final String sql = "insert into member(email, password) values(:email, :password)";
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new MemberEntity("testEmail", "testPassword"));
    final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterjdbcTemplate.update(sql, parameterSource, keyHolder);
    return keyHolder.getKey().longValue();
  }

  private long saveProduct() {
    final String sql = "insert into product(name, image_url, price) values(:name, :imageUrl, :price)";
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new ProductEntity("치킨", "testUrl", 10000));
    final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterjdbcTemplate.update(sql, parameterSource, keyHolder);
    return keyHolder.getKey().longValue();
  }

  private List<CartEntity> getCarts() {
    String sql = "select * from cart";
    return namedParameterjdbcTemplate.getJdbcTemplate()
        .query(sql, (resultSet, count) -> new CartEntity(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("member_id"),
            resultSet.getInt("cart_count")));
  }

  @Test
  void findCartByMemberId() {
    //given
    saveCart(cartEntity);

    //when
    final List<CartEntity> carts = h2CartDao.findCartByMemberId(memberId);

    //then
    assertThat(carts).hasSize(1);
  }

  private long saveCart(CartEntity cartEntity) {
    final BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartEntity);
    return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
  }

  @Test
  void deleteByMemberIdAndProductId() {
    //given
    saveCart(cartEntity);
    final List<CartEntity> firstCarts = getCarts();
    assertThat(firstCarts).hasSize(1);

    //when
    h2CartDao.deleteByMemberIdAndProductId(memberId, productId);

    //then
    final List<CartEntity> lastCarts = getCarts();
    assertThat(lastCarts).isEmpty();
  }

  @Test
  void findCartByMemberIdAndProductId() {
    //given
    saveCart(cartEntity);

    //when
    final CartEntity findEntity = h2CartDao.findCartByMemberIdAndProductId(memberId, productId).get();

    //then
    assertThat(findEntity).isNotNull();
  }

  @Test
  void addCartCount() {
    //given
    saveCart(cartEntity);

    //when
    h2CartDao.addCartCount(cartEntity.getCartCount()+1, memberId, productId);

    //then
    final List<CartEntity> carts = getCarts();
    assertThat(carts.get(0).getCartCount()).isEqualTo(cartEntity.getCartCount() + 1);
  }
}
