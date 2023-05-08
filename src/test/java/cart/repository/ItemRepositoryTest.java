package cart.repository;

import static cart.fixture.DomainFactory.MAC_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

import cart.domain.item.Item;
import cart.exception.item.ItemNotFoundException;
import cart.repository.dao.ItemDao;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

    @Mock
    ItemDao itemDao;

    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepository(itemDao);
    }

    @Test
    @DisplayName("상품을 조회한다.")
    void findByIdSuccess() {
        given(itemDao.findById(anyLong())).willReturn(Optional.of(MAC_BOOK));

        Optional<Item> actual = itemRepository.findById(1L);

        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void findAllSuccess() {
        given(itemDao.findAll()).willReturn(Collections.emptyList());

         List<Item> actual = itemRepository.findAll();

         assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("상품을 변경한다.")
    void updateSuccess() {
        given(itemDao.update(any(Item.class))).willReturn(1);

        assertDoesNotThrow(() -> itemRepository.update(MAC_BOOK));
    }

    @Test
    @DisplayName("존재하지 않는 상품을 변경하는 경우 예외가 발생한다.")
    void updateFailWithNotExistsItem() {
        given(itemDao.update(any(Item.class))).willReturn(0);

        assertThatThrownBy(() -> itemRepository.update(MAC_BOOK))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("일치하는 상품을 찾을 수 없습니다.");
    }
}
