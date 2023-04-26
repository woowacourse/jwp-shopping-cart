package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.dao.ItemDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("상품을 저장한다.")
    void addItemSuccess() {
        ItemRequest itemRequest = createItemRequest();

        ItemResponse itemResponse = itemService.add(itemRequest);

        assertAll(
                () -> assertThat(itemResponse.getId()).isPositive(),
                () -> assertThat(itemResponse.getName()).isEqualTo(itemRequest.getName()),
                () -> assertThat(itemResponse.getImageUrl()).isEqualTo(itemRequest.getImageUrl()),
                () -> assertThat(itemResponse.getPrice()).isEqualTo(itemRequest.getPrice())
        );
    }

    @Test
    @DisplayName("모든 상품을 찾는다.")
    @Sql("/truncate.sql")
    void findAllItemSuccess() {
        ItemRequest itemRequest1 = createItemRequest();
        ItemRequest itemRequest2 = createItemRequest();
        itemService.add(itemRequest1);
        itemService.add(itemRequest2);

        List<ItemResponse> itemResponses = itemService.findAll();

        assertThat(itemResponses).hasSize(2);
    }

    @Test
    @DisplayName("상품을 변경한다.")
    void updateItemSuccess() {
        ItemRequest itemRequest = createItemRequest();
        ItemResponse itemResponse = itemService.add(itemRequest);

        ItemRequest updateItemRequest = new ItemRequest("자전거", "http://image.url", 1_500_000);
        ItemResponse updateItemResponse = itemService.update(itemResponse.getId(), updateItemRequest);

        assertAll(
                () -> assertThat(updateItemResponse.getId()).isEqualTo(itemResponse.getId()),
                () -> assertThat(updateItemResponse.getName()).isEqualTo(updateItemRequest.getName())
        );
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteItemSuccess() {
        ItemRequest itemRequest = createItemRequest();
        ItemResponse itemResponse = itemService.add(itemRequest);

        assertDoesNotThrow(() -> itemService.delete(itemResponse.getId()));
    }

    private static ItemRequest createItemRequest() {
        return new ItemRequest("맥북", "http://image.url", 1_500_000);
    }
}
