package cart.repository;

import cart.dao.CartDao;
import cart.dao.joinrequest.CartWithProduct;
import cart.domain.Cart;
import cart.domain.Product;
import cart.entity.CartEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {

    @Captor
    ArgumentCaptor<List<CartEntity>> cartCaptor;
    @InjectMocks
    private CartRepository cartRepository;
    @Mock
    private CartDao cartDao;

    @DisplayName("유저 Id 에 해당하는 카트를 생성한다")
    @Test
    void getCartOf() {
        //given
        final String firstProductName = "name";
        final String firstProductImage = "image";
        final int firstProductPrice = 1;
        given(cartDao.cartWithProducts(anyInt()))
                .willReturn(List.of(
                                new CartWithProduct(1, 1, firstProductName, firstProductImage, firstProductPrice),
                                new CartWithProduct(1, 2, "name2", "image2", 2)
                        )
                );

        //when
        final Cart cart = cartRepository.getCartOf(1);
        final Product firstProduct = cart.getProducts().get(0);

        //then
        assertAll(
                () -> assertThat(cart.getMemberId()).isEqualTo(1),
                () -> assertThat(cart.getProducts()).hasSize(2),
                () -> assertThat(firstProduct.getId()).isEqualTo(1),
                () -> assertThat(firstProduct.getName()).isEqualTo(firstProductName),
                () -> assertThat(firstProduct.getImage()).isEqualTo(firstProductImage),
                () -> assertThat(firstProduct.getPrice()).isEqualTo(firstProductPrice)
        );
    }

    @DisplayName("save 메서드는 기존 cart 에 존재하지 않는 product 를 저장한다")
    @Test
    void save_insertProducts() {
        //given
        final int memberId = 1;

        final int exitingProductId = 1;
        final String exitingName = "name";
        final String exitingImage = "image";
        final int exitingPrice = 1;
        given(cartDao.cartWithProducts(anyInt()))
                .willReturn(List.of(
                                new CartWithProduct(memberId, exitingProductId, exitingName, exitingImage, exitingPrice)
                        )
                );


        final int newProductId = 4;

        final Cart newItemAddedCart = new Cart(memberId, List.of(
                new Product(exitingProductId, exitingName, exitingImage, exitingPrice),
                new Product(newProductId, "newName", "newImage", 2))
        );

        //when
        cartRepository.save(newItemAddedCart);

        //then
        verify(cartDao).insertProducts(cartCaptor.capture());
        final List<CartEntity> addedCartEntities = cartCaptor.getValue();
        final CartEntity addedEntity = addedCartEntities.get(0);

        assertAll(
                () -> assertThat(addedCartEntities).hasSize(1),
                () -> assertThat(addedEntity.getProductId()).isEqualTo(newProductId),
                () -> assertThat(addedEntity.getMemberId()).isEqualTo(memberId)
        );
    }

    @DisplayName("save 메서드는 기존 cart 에 존재하던 아이템이 없으면 삭제한다")
    @Test
    void save_deleteProducts() {
        //given
        final int memberId = 1;

        final int exitedProductId = 1;
        final String exitedName = "name";
        final String exitedImage = "image";
        final int exitedPrice = 1;
        given(cartDao.cartWithProducts(anyInt()))
                .willReturn(List.of(
                                new CartWithProduct(memberId, exitedProductId, exitedName, exitedImage, exitedPrice)
                        )
                );


        final Cart newItemAddedCart = new Cart(memberId, Collections.emptyList());

        //when
        cartRepository.save(newItemAddedCart);

        //then
        verify(cartDao).deleteProducts(cartCaptor.capture());
        final List<CartEntity> addedCartEntities = cartCaptor.getValue();
        final CartEntity deletedEntities = addedCartEntities.get(0);

        assertAll(
                () -> assertThat(addedCartEntities).hasSize(1),
                () -> assertThat(deletedEntities.getProductId()).isEqualTo(exitedProductId),
                () -> assertThat(deletedEntities.getMemberId()).isEqualTo(memberId)
        );
    }
}
