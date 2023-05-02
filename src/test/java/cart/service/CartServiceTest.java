package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.request.ProductRequestDto;
import cart.entity.CartEntity;
import cart.entity.customer.CustomerEntity;
import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @DisplayName("상품과 고객의 Id를 통해 카트에 상품을 추가한다.")
    @Test
    void add() {
        //given
        final Long registeredProductID = productService.register(
            new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );
        final List<CustomerEntity> customers = customerService.findAll();
        final Long firstCustomerId = customers.get(0).getId();

        //when
        cartService.add(new CartEntity(firstCustomerId, registeredProductID));

        //then
        final List<ProductEntity> products = cartService.findAllById(firstCustomerId);
        assertAll(
            () -> assertThat(products).hasSize(1),
            () -> assertThat(Objects.requireNonNull(products).get(0).getId()).isEqualTo(registeredProductID)
        );
    }

    @DisplayName("카트의 Id로 카트 데이터를 삭제한다.")
    @Test
    void delete() {
        //given
        final Long registeredProductID = productService.register(
            new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );
        final List<CustomerEntity> customers = customerService.findAll();
        final Long firstCustomerId = customers.get(0).getId();
        final Long cartId = cartService.add(new CartEntity(firstCustomerId, registeredProductID));

        //when
        cartService.delete(cartId);

        //then
        final List<ProductEntity> products = cartService.findAllById(firstCustomerId);
        assertThat(products).hasSize(0);
    }

    @DisplayName("고객 Id와 상품 Id로 카트 데이터를 삭제한다.")
    @Test
    void deleteByCustomerIdAndProductId() {
        //given
        final Long registeredProductID = productService.register(
            new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "description",
                List.of(1L)
            )
        );
        final List<CustomerEntity> customers = customerService.findAll();
        final Long firstCustomerId = customers.get(0).getId();
        cartService.add(new CartEntity(firstCustomerId, registeredProductID));

        //when
        cartService.deleteByCustomerIdAndProductId(firstCustomerId, registeredProductID);

        //then
        final List<ProductEntity> products = cartService.findAllById(firstCustomerId);
        assertThat(products).hasSize(0);
    }
}
