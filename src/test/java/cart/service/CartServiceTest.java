package cart.service;

import cart.controller.dto.CartDto;
import cart.persistence.entity.MemberProductEntity;
import cart.persistence.repository.MemberProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CartService.class)
class CartServiceTest {

    @MockBean
    private MemberProductRepository memberProductRepository;

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("특정 사용자의 장바구니에 물건을 추가한다.")
    void addCart() {
        // given
        when(memberProductRepository.save(any(), any())).thenReturn(1L);

        // when
        long savedCartId = cartService.addCart("journey@gmail.com", 1L);

        // then
        assertThat(savedCartId).isSameAs(1L);
    }

    @Test
    @DisplayName("특정 사용자의 장바구니에 담긴 상품 리스트를 조회한다.")
    void getProductsByMemberId() {
        // given
        final MemberProductEntity chickenProduct = new MemberProductEntity(1L, 1L, 1L,
                "치킨", "chicken_image_url", 20000, "KOREAN");
        final MemberProductEntity steakProductEntity = new MemberProductEntity(2L, 1L, 2L,
                "스테이크", "steakUrl", 40000, "WESTERN");
        when(memberProductRepository.findByMemberEmail(any())).thenReturn(List.of(chickenProduct, steakProductEntity));

        // when
        final List<CartDto> cartDtos = cartService.getProductsByMemberEmail("journey@gmail.com");

        // then
        assertThat(cartDtos).hasSize(2);
        assertThat(cartDtos)
                .extracting("memberId", "productId", "productName",
                        "productImageUrl", "productPrice", "productCategory")
                .containsExactly(
                        tuple(1L, 1L, "치킨", "chicken_image_url", 20000, "KOREAN"),
                        tuple(1L, 2L, "스테이크", "steakUrl", 40000, "WESTERN"));
    }
}
