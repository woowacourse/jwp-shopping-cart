package cart.service;

import cart.dao.ItemDao;
import cart.dto.ItemRequest;
import cart.dto.ItemResponse;
import cart.entity.CreateItem;
import cart.exception.ServiceIllegalArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Sql({"classpath:test_init.sql"})
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemDao.save(new CreateItem("치킨", "a", 10000));
        itemDao.save(new CreateItem("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void saveSuccess() {
        ItemRequest itemRequest = new ItemRequest("햄버거", "c", 2000);
        ItemResponse itemResponseExpected = new ItemResponse(3L, "햄버거", "c", 2000);

        List<ItemResponse> itemResponsesExpected = new ArrayList<>();
        itemResponsesExpected.add(new ItemResponse(1L, "치킨", "a", 10000));
        itemResponsesExpected.add(new ItemResponse(2L, "피자", "b", 20000));
        itemResponsesExpected.add(itemResponseExpected);

        itemService.save(itemRequest);

        List<ItemResponse> items = itemService.findAll();

        assertAll(
                () -> assertThat(items)
                        .usingRecursiveComparison()
                        .isEqualTo(itemResponsesExpected),

                () -> assertThat(items.get(2))
                        .usingRecursiveComparison()
                        .isEqualTo(itemResponseExpected) // toString 재정의 시 예외 검증 로직이 변경되기에 내부 값도 검증
        );
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다.")
    void findAllSuccessTest() {
        List<ItemResponse> itemResponses = itemService.findAll();
        ItemResponse itemResponseExpected = new ItemResponse(2L, "피자", "b", 20000);

        List<ItemResponse> itemResponsesExpected = new ArrayList<>();
        itemResponsesExpected.add(new ItemResponse(1L, "치킨", "a", 10000));
        itemResponsesExpected.add(itemResponseExpected);

        assertAll(
                () -> assertThat(itemResponses)
                        .usingRecursiveComparison()
                        .isEqualTo(itemResponsesExpected),

                () -> assertThat(itemResponses.get(1))
                        .usingRecursiveComparison()
                        .isEqualTo(itemResponseExpected) // toString 재정의 시 예외 검증 로직이 변경되기에 내부 값도 검증
        );
    }


    @Test
    @DisplayName("상품을 업데이트할 수 있다.")
    void updateItemSuccessTest() {
        ItemRequest itemRequest = new ItemRequest("국밥", "c", 30000);
        ItemResponse itemResponseExpected = new ItemResponse(2L, "국밥", "c", 30000);
        Long itemId = 2L;

        assertDoesNotThrow(() -> itemService.updateItem(itemId, itemRequest));

        List<ItemResponse> items = itemService.findAll();

        assertAll(
                () -> assertThat(items.get(1))
                        .usingRecursiveComparison()
                        .isEqualTo(itemResponseExpected)
        );
    }

    @Test
    @DisplayName("상품을 삭제할 수 있다.")
    void deleteItemSuccessTest() {
        Long itemId = 2L;

        itemService.deleteItem(itemId);
        assertThat(itemService.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("존재하지 않는 상품 번호를 업데이트할 경우 예외가 발생한다.")
    void updateInvalidItemIdFailTest() {
        ItemRequest itemRequest = new ItemRequest("국밥", "c", 30000);
        Long itemId = 3L;
        String exceptionMessage = "잘못된 상품 번호를 입력하셨습니다.";

        assertThatThrownBy(() -> itemService.updateItem(itemId, itemRequest))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }

    @Test
    @DisplayName("존재하지 않는 상품 번호를 삭제할 경우 예외가 발생한다.")
    void deleteInvalidItemIdFailTest() {
        Long itemId = 3L;
        String exceptionMessage = "잘못된 상품 번호를 입력하셨습니다.";

        assertThatThrownBy(() -> itemService.deleteItem(itemId))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessageContaining(exceptionMessage);
    }
}
