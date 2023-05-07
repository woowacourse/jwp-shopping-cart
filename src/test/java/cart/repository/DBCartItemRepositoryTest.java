package cart.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cart.dto.ProductRequest;
import cart.entity.CartItem;
import cart.entity.CartItemRepository;
import cart.entity.Member;

@JdbcTest
class DBCartItemRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private CartItemRepository cartItemRepository;
    private Long memberId1;
    private Long memberId2;
    private Long productId1;
    private Long productId2;
    private Long cartItemId1;
    private Long cartItemId2;
    private Long cartItemId3;
    private Long cartItemId4;

    @BeforeEach
    void setUp() {
        cartItemRepository = new DBCartItemRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE cart IF EXISTS");
        jdbcTemplate.execute("DROP TABLE product IF EXISTS");
        jdbcTemplate.execute("DROP TABLE member IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE member ("
            + "id          BIGINT          NOT NULL    AUTO_INCREMENT,"
            + "email       VARCHAR(255)    NOT NULL,"
            + "password    VARCHAR(255)    NOT NULL,"
            + "    PRIMARY KEY (id))");
        jdbcTemplate.execute("CREATE TABLE product ("
            + "    id      BIGINT            NOT NULL    AUTO_INCREMENT,"
            + "    name    VARCHAR(255)    NOT NULL,"
            + "    imgURL  VARCHAR(8000)    NOT NULL,"
            + "    price   INT             NOT NULL,"
            + "    PRIMARY KEY (id))");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cart\n"
            + "(\n"
            + "    id          BIGINT            NOT NULL    AUTO_INCREMENT,\n"
            + "    member_id   BIGINT            NOT NULL,\n"
            + "    product_id  BIGINT            NOT NULL,\n"
            + "    PRIMARY KEY (id),\n"
            + "    FOREIGN KEY (member_id) REFERENCES member(id),\n"
            + "    FOREIGN KEY (product_id) REFERENCES product(id)\n"
            + "    );");

        SimpleJdbcInsert simpleJdbcInsert1 = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("member")
            .usingGeneratedKeyColumns("id");

        Member member1 = Member.of(null, "email1@email.com", "password1");
        Member member2 = Member.of(null, "email2@email.com", "password2");

        Map<String, Object> map1 = Map.of(
            "email", member1.getEmail(),
            "password", member1.getPassword()
        );
        Map<String, Object> map2 = Map.of(
            "email", member2.getEmail(),
            "password", member2.getPassword()
        );

        this.memberId1 = simpleJdbcInsert1.executeAndReturnKey(map1).longValue();
        this.memberId2 = simpleJdbcInsert1.executeAndReturnKey(map2).longValue();

        SimpleJdbcInsert simpleJdbcInsert2 = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");

        ProductRequest product1 = new ProductRequest("name1", "url1", 1000);
        ProductRequest product2 = new ProductRequest("name2", "url2", 2000);

        Map<String, Object> map3 = Map.of(
            "name", product1.getName(),
            "imgurl", product1.getImgUrl(),
            "price", product1.getPrice()
        );
        Map<String, Object> map4 = Map.of(
            "name", product2.getName(),
            "imgurl", product2.getImgUrl(),
            "price", product2.getPrice()
        );

        this.productId1 = simpleJdbcInsert2.executeAndReturnKey(map3).longValue();
        this.productId2 = simpleJdbcInsert2.executeAndReturnKey(map4).longValue();

        SimpleJdbcInsert simpleJdbcInsert3 = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("cart")
            .usingGeneratedKeyColumns("id");

        Map<String, Object> map5 = Map.of(
            "member_id", memberId1,
            "product_id", productId1
        );
        Map<String, Object> map6 = Map.of(
            "member_id", memberId1,
            "product_id", productId2
        );

        Map<String, Object> map7 = Map.of(
            "member_id", memberId2,
            "product_id", productId1
        );
        Map<String, Object> map8 = Map.of(
            "member_id", memberId2,
            "product_id", productId2
        );

        this.cartItemId1 = simpleJdbcInsert3.executeAndReturnKey(map5).longValue();
        this.cartItemId2 = simpleJdbcInsert3.executeAndReturnKey(map6).longValue();
        this.cartItemId3 = simpleJdbcInsert3.executeAndReturnKey(map7).longValue();
        this.cartItemId4 = simpleJdbcInsert3.executeAndReturnKey(map8).longValue();
    }

    @Test
    @DisplayName("장바구니에 담긴 상품 정보를 DB에 저장한다.")
    void save() {
        CartItem cartItem = CartItem.of(null, memberId1, productId1);
        Long id3 = cartItemRepository.save(cartItem);

        String sql = "SELECT * FROM cart";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(5);

    }

    @Test
    @DisplayName("장바구니 id로 아이템을 조회한다.")
    void findById() {
        CartItem cartItem = cartItemRepository.findById(cartItemId1);
        assertAll(
            () -> assertThat(cartItem.getMemberId()).isEqualTo(memberId1),
            () -> assertThat(cartItem.getProductId()).isEqualTo(productId1)
        );

    }

    @Test
    @DisplayName("해당 유저의 장바구니 목록을 조회한다.")
    void findAll() {
        assertAll(
            () -> assertThat(cartItemRepository.findAll(memberId1).size()).isEqualTo(2),
            () -> assertThat(cartItemRepository.findAll(memberId2).size()).isEqualTo(2));
    }

    @Test
    @DisplayName("ID에 해당하는 장바구니 목록의 아이템을 제거한다.")
    void deleteById() {
        cartItemRepository.deleteById(cartItemId1);

        assertAll(
            () -> assertThat(cartItemRepository.findAll(memberId1).size()).isEqualTo(1),
            () -> assertThat(cartItemRepository.findAll(memberId2).size()).isEqualTo(2));
    }

    @Test
    @DisplayName("상품 ID에 해당하는 모든 상품 정보를 삭제한다.")
    void deleteByProductId() {
        cartItemRepository.deleteByProductID(productId1);

        assertAll(
            () -> assertThat(cartItemRepository.findAll(memberId1).size()).isEqualTo(1),
            () -> assertThat(cartItemRepository.findAll(memberId2).size()).isEqualTo(1));
    }
}
