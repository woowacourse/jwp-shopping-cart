package cart.service;

import cart.dao.item.ItemDao;
import cart.entity.ItemEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private ItemService itemService;

    @Test
    @DisplayName("모든 아이템 조회 테스트")
    void findAll() {
        given(itemDao.findAll()).willReturn(Optional.of(List.of(
                new ItemEntity(1L, "치킨", "url", 10000),
                new ItemEntity(2L, "치킨", "url", 10000)
        )));

        Assertions.assertThat(itemService.findAll()).hasSize(2);
    }
}
