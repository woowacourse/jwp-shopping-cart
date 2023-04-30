package cart.controller;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import cart.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @MockBean
    ItemService mockItemService;

    @MockBean
    ItemController mockController;

    @Mock
    ItemService itemService;

    @InjectMocks
    ItemController itemController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("POST /items 요청 시 addItem 메서드가 호출된다")
    @Test
    void addItemMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new ItemRequest("레드북", 50000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        when(mockItemService.saveItem(any())).thenReturn(1L);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(value));
        //then
        verify(mockController, times(1)).addItem(any());
    }

    @DisplayName("GET /items 요청 시 loadAllItem 메서드가 호출된다")
    @Test
    void loadAllItemMappingURL() throws Exception {
        //given
        when(mockItemService.loadAllItem()).thenReturn(Collections.emptyList());
        //when
        mockMvc.perform(MockMvcRequestBuilders
                .get("/items"));
        //then
        verify(mockController, times(1)).loadAllItem();
    }

    @DisplayName("GET /items 요청 시 loadAllItem 메서드가 호출된다")
    @Test
    void loadItemMappingURL() throws Exception {
        //given
        when(mockItemService.loadItem(1L)).thenReturn(ItemResponse.from(new Item.Builder().id(1L)
                                                                                          .name(new Name("레드북"))
                                                                                          .imageUrl(new ImageUrl("https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"))
                                                                                          .price(new Price(50000))
                                                                                          .build()));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/items/1"));
        //then
        verify(mockController, times(1)).loadItem(anyLong());
    }

    @DisplayName("PUT /items/{itemsId} 요청 시 updateItem 메서드가 호출된다")
    @Test
    void updateItemMappingURL() throws Exception {
        //given
        String value = objectMapper.writeValueAsString(new ItemRequest("레드북", 50000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        doNothing().when(mockItemService)
                   .updateItem(anyLong(), any());
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/items/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(value));
        //then
        verify(mockController, times(1)).updateItem(anyLong(), any());
    }

    @DisplayName("DELETE /items/{itemsId} 요청 시 deleteItem 메서드가 호출된다")
    @Test
    void deleteItemMappingURL() throws Exception {
        //given
        doNothing().when(mockItemService)
                   .deleteItem(anyLong());
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/1"));
        //then
        verify(mockController, times(1)).deleteItem(anyLong());
    }

    @DisplayName("ItemRequest를 입력받아 아이템 추가 시 ResponseEntity를 응답한다")
    @Test
    void addItem() {
        //given
        ItemRequest value = new ItemRequest("레드북", 50000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg");
        when(itemService.saveItem(any())).thenReturn(1L);
        //then
        ResponseEntity<Void> responseEntity = itemController.addItem(value);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/"))
        );
    }

    @DisplayName("아이템 조회시  ResponseEntity<List<ItemResponse>>를 응답한다")
    @Test
    void loadAllItem() {
        //given
        List<ItemResponse> itemResponses = List.of(
                ItemResponse.from(new Item.Builder()
                        .id(1L)
                        .name(new Name("1번"))
                        .imageUrl(new ImageUrl("https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"))
                        .price(new Price(1234))
                        .build()
                ));
        when(itemService.loadAllItem()).thenReturn(itemResponses);
        //then
        ResponseEntity<List<ItemResponse>> listResponseEntity = itemController.loadAllItem();
        Assertions.assertAll(
                () -> assertThat(listResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(listResponseEntity.getBody()).contains(itemResponses.get(0))
        );
    }

    @DisplayName("아이템 조회시 ResponseEntity<ItemResponse>를 응답한다")
    @Test
    void loadItem() {
        //given
        ItemResponse itemResponse = ItemResponse.from(new Item.Builder()
                .id(1L)
                .name(new Name("1번"))
                .imageUrl(new ImageUrl("https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"))
                .price(new Price(1234))
                .build()
        );
        when(itemService.loadItem(1L)).thenReturn(itemResponse);
        //then
        ResponseEntity<ItemResponse> itemResponseResponseEntity = itemController.loadItem(1L);
        Assertions.assertAll(
                () -> assertThat(itemResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(itemResponseResponseEntity.getBody()).isEqualTo(itemResponse)
        );
    }

    @DisplayName("ItemRequest를 입력받아 아이템 수정 시 ResponseEntity를 응답한다")
    @Test
    void updateItem() {
        //given
        ItemRequest value = new ItemRequest("레드북", 50000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg");
        Long itemId = 1L;
        doNothing().when(itemService).updateItem(anyLong(), any());
        //then
        ResponseEntity<Void> responseEntity = itemController.updateItem(itemId, value);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/"))
        );
    }

    @DisplayName("itemId를 입력받아 아이템 삭제 시 ResponseEntity를 응답한다")
    @Test
    void deleteItem() {
        //given
        Long itemId = 1L;
        doNothing().when(itemService)
                   .deleteItem(anyLong());
        //then
        ResponseEntity<Void> responseEntity = itemController.deleteItem(itemId);
        Assertions.assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getHeaders()
                                               .getLocation()).isEqualTo(URI.create("/"))
        );
    }
}
