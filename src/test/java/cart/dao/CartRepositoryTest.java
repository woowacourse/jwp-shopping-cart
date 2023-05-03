package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.MemberRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.entity.ProductEntity;
import cart.domain.Member;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/schema.sql", "/data.sql", "/cart_data.sql"})
@JdbcTest
class CartRepositoryTest {

    private static final String MEMBER_NOT_FOUND_MESSAGE = "존재하지 않는 회원 정보입니다.";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "존재하지 않는 상품 정보입니다.";
    private static final String ALREADY_EXIST_MESSAGE = "장바구니에 해당 제품이 이미 존재합니다.";

    private CartRepository cartRepository;
    private MemberRequest memberRequest;
    private MemberRequest notExistMemberRequest;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        CartDao mySQLCartDao = new MySQLCartDao(jdbcTemplate);
        MemberDao mySQLMemberDao = new MySQLMemberDao(jdbcTemplate);
        ProductDao mySQLProductDao = new MySQLProductDao(jdbcTemplate);
        cartRepository = new CartRepository(mySQLCartDao, mySQLMemberDao, mySQLProductDao);
        memberRequest = new MemberRequest("songsy405@naver.com", "abcd");
        notExistMemberRequest = new MemberRequest("songsy405@naver.com", "1234");
    }

    @Test
    @DisplayName("getProducts() 메서드에 회원 정보를 인자로 넣어주면 처음 들어가있는 장바구니 제품 목록이 반환된다.")
    void getProducts() {
        //given, when
        List<ProductEntity> products = cartRepository.getProducts(memberRequest);
        int actual = products.size();
        ProductEntity product1 = new ProductEntity(1L, "피자", 13000,
            "https://searchad-phinf.pstatic.net/MjAyMjEyMjdfMTE1/MDAxNjcyMTAxNTI0Nzg4.WfiSlsy9fTUQJ6q2FTGOaaOVU0QpSB0U1LvplKZQXzIg.H4UgI0VbKUszP7mzC3qhwpSMe15DluJnxjxVGDq_QUgg.PNG/451708-1fa87663-02e3-4303-b8a9-d7eea3676018.png?type=f160_160");
        ProductEntity product2 = new ProductEntity(2L, "치킨", 27000,
            "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjdfMTI2%2FMDAxNjc5OTI1ODQ4NTgy.6RT9z-i5prsnwwc-6B9TaK6Q0Zcgsd3TeDGiUdqyDRIg.rW2kMtzBKNFhWWXyr_X2bZfR15AEPUOz-VJnqVaP0jEg.JPEG.koreasinju%2FIMG_3379.jpg&type=ff332_332");

        //then
        assertAll(
            () -> assertThat(actual).isEqualTo(2),
            () -> assertThat(products).contains(product1, product2)
        );
    }

    @Test
    @DisplayName("add() 메서드에 유효한 제품, 회원 정보를 인자로 넣어주면 정상적으로 동작한다.")
    void add_success() {
        assertThatCode(() -> cartRepository.add(3L, memberRequest)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 제품에 대해 add() 메서드를 호출하면 예외가 발생한다.")
    void add_fail_because_not_exist_product() {
        assertThatThrownBy(() -> cartRepository.add(4L, memberRequest))
            .isInstanceOf(NoSuchElementException.class).hasMessage(PRODUCT_NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("이미 존재하는 제품에 대해 add() 메서드를 호출하면 예외가 발생한다.")
    void add_fail_because_aleady_exist_product() {
        assertThatThrownBy(() -> cartRepository.add(1L, memberRequest))
            .isInstanceOf(DataIntegrityViolationException.class).hasMessage(ALREADY_EXIST_MESSAGE);
    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해 add() 메서드를 호출하면 예외가 발생한다.")
    void add_fail_because_not_exist_member() {
        assertThatThrownBy(() -> cartRepository.add(1L, notExistMemberRequest)).isInstanceOf(
            NoSuchElementException.class).hasMessage(MEMBER_NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("remove() 메서드에 유효한 제품, 회원 정보를 인자로 넣어주면 정상적으로 동작한다.")
    void remove() {
        assertThatCode(() -> cartRepository.remove(1L, memberRequest)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해 remove() 메서드를 호출하면 예외가 발생한다.")
    void remove_fail_because_not_exist_member() {
        assertThatThrownBy(() -> cartRepository.remove(1L, notExistMemberRequest)).isInstanceOf(
            NoSuchElementException.class).hasMessage(MEMBER_NOT_FOUND_MESSAGE);
    }
}