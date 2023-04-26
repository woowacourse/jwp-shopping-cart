package cart.service;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.dao.ItemDao;
import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// TODO: 2023/04/26 서비스 테스트가 할게 없음 -> Mockito를 쓰는게 맞을까?
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private ItemService itemService;

    @DisplayName("모든 아이템을 불러온다")
    @Test
    void loadAllItem() {
        //given
        Item item1 = new Item.Builder().id(1L).name(new Name("1번")).price(new Price(123)).imageUrl(new ImageUrl("1번URL")).build();
        Item item2 = new Item.Builder().id(3L).name(new Name("3번")).price(new Price(123)).imageUrl(new ImageUrl("3번URL")).build();
        Item item3 = new Item.Builder().id(2L).name(new Name("2번")).price(new Price(123)).imageUrl(new ImageUrl("2번URL")).build();
        when(itemDao.findAll()).thenReturn(List.of(item1, item2, item3));
        //when
        List<ItemResponse> itemResponses = itemService.loadAllItem();
        //then
        assertThat(itemResponses).contains(ItemResponse.from(item1), ItemResponse.from(item2), ItemResponse.from(item3));
    }

    @DisplayName("아이템을 저장한다")
    @Test
    void saveItem() {
        //given
        Item item1 = new Item.Builder().name(new Name("1번")).price(new Price(123)).imageUrl(new ImageUrl("1번URL")).build();
        when(itemDao.save(item1)).thenReturn(1L);
        //when
        Long id = itemService.saveItem(new ItemRequest("1번", 123, "1번URL"));
        //then
        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("아이템을 수정한다")
    @Test
    void updateItem() {
        //given
        Item item1 = new Item.Builder().id(1L).name(new Name("1번")).price(new Price(123)).imageUrl(new ImageUrl("1번URL")).build();
        when(itemDao.findBy(1L)).thenReturn(Optional.of(item1));
        doNothing().when(itemDao).update(item1);
        //when
        itemService.updateItem(1L, new ItemRequest("1번", 123, "1번URL"));
        //then
        verify(itemDao, times(1)).update(item1);
    }

    @DisplayName("아이템을 삭제한다.")
    @Test
    void deleteItem() {
        //given
        Item item1 = new Item.Builder().id(1L).name(new Name("1번")).price(new Price(123)).imageUrl(new ImageUrl("1번URL")).build();
        when(itemDao.findBy(1L)).thenReturn(Optional.of(item1));
        doNothing().when(itemDao).deleteBy(1L);
        //when
        itemService.deleteItem(1L);
        //then
        verify(itemDao, times(1)).deleteBy(1L);
    }

    @DisplayName("아이템을 조회한다.")
    @Test
    void loadItem() {
        //given
        Item item1 = new Item.Builder().id(1L).name(new Name("1번")).price(new Price(123)).imageUrl(new ImageUrl("1번URL")).build();
        when(itemDao.findBy(1L)).thenReturn(Optional.of(item1));
        //when
        ItemResponse itemResponse = itemService.loadItem(1L);
        //then
        assertThat(itemResponse).isEqualTo(ItemResponse.from(item1));
    }
}
