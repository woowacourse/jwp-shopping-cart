package cart.service;

import static cart.fixture.RequestFactory.ADD_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.UPDATE_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.createUpdateItemRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.controller.dto.AddItemRequest;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.UpdateItemRequest;
import cart.dao.ItemDao;
import cart.exception.ItemException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
class ItemServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    ItemService itemService;

    @BeforeEach
    void setUp() {
        ItemDao itemDao = new ItemDao(jdbcTemplate);
        itemService = new ItemService(itemDao);
    }

    @Nested
    @DisplayName("ItemService CRUD 성공 테스트")
    class ItemServiceCRUDSuccessTest {

        @Test
        @DisplayName("상품을 저장한다.")
        void addItemSuccess() {
            AddItemRequest addItemRequest = ADD_MAC_BOOK_REQUEST;

            ItemResponse itemResponse = itemService.add(addItemRequest);

            assertAll(
                    () -> assertThat(itemResponse.getId()).isPositive(),
                    () -> assertThat(itemResponse.getName()).isEqualTo(addItemRequest.getName()),
                    () -> assertThat(itemResponse.getImageUrl()).isEqualTo(addItemRequest.getImageUrl()),
                    () -> assertThat(itemResponse.getPrice()).isEqualTo(addItemRequest.getPrice())
            );
        }

        @Nested
        @DisplayName("상품 RUD 성공 테스트")
        class ItemServiceRUDSuccessTest {

            private ItemResponse itemResponse;

            @BeforeEach
            void setUp() {
                itemResponse = itemService.add(ADD_MAC_BOOK_REQUEST);
            }

            @Test
            @DisplayName("모든 상품을 찾는다.")
            void findAllItemSuccess() {
                List<ItemResponse> itemResponses = itemService.findAll();

                assertThat(itemResponses).hasSizeGreaterThanOrEqualTo(1);
            }

            @Test
            @DisplayName("상품을 변경한다.")
            void updateItemSuccess() {
                UpdateItemRequest updateAddItemRequest = createUpdateItemRequest("자전거", "http://image.url", 1_500_000);

                ItemResponse updateItemResponse = itemService.update(itemResponse.getId(), updateAddItemRequest);

                assertAll(
                        () -> assertThat(updateItemResponse.getId()).isEqualTo(itemResponse.getId()),
                        () -> assertThat(updateItemResponse.getName()).isEqualTo(updateAddItemRequest.getName())
                );
            }

            @Test
            @DisplayName("상품을 삭제한다.")
            void deleteItemSuccess() {
                assertDoesNotThrow(() -> itemService.delete(itemResponse.getId()));
            }
        }
    }

    @Nested
    @DisplayName("상품 RUD 실패 테스트")
    @Sql("/truncate.sql")
    class ItemServiceRUDFailTest {

        @Test
        @DisplayName("존재하지 않는 상품 ID를 조회하면 예외가 발생한다.")
        void updateItemRequestFailWithNotExistID() {
            assertThatThrownBy(() -> itemService.update(1L, UPDATE_MAC_BOOK_REQUEST))
                    .isInstanceOf(ItemException.class)
                    .hasMessage("일치하는 상품을 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID를 삭제하면 예외가 발생한다.")
        void deleteItemRequestFailWithNotExistID() {
            assertThatThrownBy(() -> itemService.delete(1L))
                    .isInstanceOf(ItemException.class)
                    .hasMessage("일치하는 상품을 찾을 수 없습니다.");
        }
    }
}
