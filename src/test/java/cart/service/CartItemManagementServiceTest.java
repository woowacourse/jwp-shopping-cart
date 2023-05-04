package cart.service;

import cart.dao.JdbcCartItemDao;
import cart.dao.JdbcProductDao;
import cart.domain.entity.CartItemEntity;
import cart.domain.entity.ProductEntity;
import cart.dto.CartItemDetailsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemManagementServiceTest {

    @InjectMocks
    CartItemManagementService managementService;

    @Mock
    JdbcCartItemDao cartItemDao;

    @Mock
    JdbcProductDao productDao;

    @Nested
    @DisplayName("사용자 id로 장바구니에 담긴 상품 목록을 조회하는 findAllByMemberId 메서드 테스트")
    class FindAllByMemberIdTest {

        @DisplayName("장바구니 상품들을 가져와서 정보를 조합해 반환하는지 확인한다")
        @Test
        void successTest() {
            final List<CartItemEntity> cartItemData = List.of(
                    CartItemEntity.of(1L, 1L, 1L),
                    CartItemEntity.of(2L, 1L, 2L)
            );
            ProductEntity productEntity1 = ProductEntity.of(1L, "chicken", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000);
            ProductEntity productEntity2 = ProductEntity.of(2L, "pizza", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 20000);

            when(cartItemDao.selectAllByMemberId(anyLong())).thenReturn(cartItemData);
            when(productDao.selectById(1L)).thenReturn(productEntity1);
            when(productDao.selectById(2L)).thenReturn(productEntity2);

            List<CartItemDetailsDto> cartItemDetailsDtos = managementService.findAllByMemberId(1L);

            assertAll(
                    () -> assertThat(cartItemDetailsDtos.size()).isEqualTo(2),
                    () -> assertThat(cartItemDetailsDtos.get(0).getId()).isEqualTo(1L),
                    () -> assertThat(cartItemDetailsDtos.get(0).getName()).isEqualTo("chicken"),
                    () -> assertThat(cartItemDetailsDtos.get(0).getImage()).isEqualTo("https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg"),
                    () -> assertThat(cartItemDetailsDtos.get(0).getPrice()).isEqualTo(10000),
                    () -> assertThat(cartItemDetailsDtos.get(1).getId()).isEqualTo(2L),
                    () -> assertThat(cartItemDetailsDtos.get(1).getName()).isEqualTo("pizza"),
                    () -> assertThat(cartItemDetailsDtos.get(1).getImage()).isEqualTo("https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg"),
                    () -> assertThat(cartItemDetailsDtos.get(1).getPrice()).isEqualTo(20000)
            );
        }
    }

}
