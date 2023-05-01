package cart.service;

import cart.dao.cart.CartDao;
import cart.entity.ItemEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;


    @Test
    @DisplayName("모든 장바구니 아이템 조회 테스트")
    void findAll() {
        given(cartDao.findAll("admin@naver.com")).willReturn(List.of(
                new ItemEntity(1L, "치킨", "url", 10000),
                new ItemEntity(2L, "치킨", "url", 10000)
        ));

        Assertions.assertThat(cartService.findAll("admin@naver.com")).hasSize(2);
    }
}
