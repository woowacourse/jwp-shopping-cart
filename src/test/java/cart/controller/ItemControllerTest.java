package cart.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.AddItemRequest;
import cart.controller.dto.ItemResponse;
import cart.model.Item;
import cart.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    ObjectMapper objectMapper = new ObjectMapper();


    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("상품 요청 이름에 공백이 입력되면 BAD REQUEST가 반횐된다.")
    void createItemRequestFailWithBlankName(String name) throws Exception {
        AddItemRequest addItemRequest = createItemRequest(name, "http://image.com", 15_000);
        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("상품 요청 이미지 주소에 공백이 입력되면 BAD REQUEST가 반횐된다.")
    void createItemRequestFailWithBlankUrl(String imageUrl) throws Exception {
        AddItemRequest addItemRequest = createItemRequest("맥북", imageUrl, 15_000);
        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("상품 요청 가격에 양수가 아니면 BAD REQUEST가 반횐된다.")
    void createItemRequestFailWithNonPositivePrice(int price) throws Exception {
        AddItemRequest addItemRequest = createItemRequest("맥북", "http://image.com", price);
        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("상품을 등록한다.")
    void createItemRequestSuccess() throws Exception {
        AddItemRequest addItemRequest = createItemRequest("맥북", "http://image.com", 1_500_000);
        ItemResponse itemResponse = createItemResponse();
        when(itemService.add(any())).thenReturn(itemResponse);

        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(itemResponse.getId()))
                .andExpect(jsonPath("$.name").value(itemResponse.getName()))
                .andExpect(jsonPath("$.imageUrl").value(itemResponse.getImageUrl()))
                .andExpect(jsonPath("$.price").value(itemResponse.getPrice()))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 전체를 조회한다.")
    void findAllItemRequestSuccess() throws Exception {
        ItemResponse itemResponse1 = createItemResponse();
        ItemResponse itemResponse2 = createItemResponse();
        when(itemService.findAll()).thenReturn(List.of(itemResponse1, itemResponse2));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andDo(print());
    }

    @Test
    @DisplayName("상품을 변경한다.")
    void updateItemRequestSuccess() throws Exception {
        AddItemRequest addItemRequest = createItemRequest("맥북", "http://image.com", 15_000);
        ItemResponse itemResponse = createItemResponse();
        when(itemService.update(any(), any())).thenReturn(itemResponse);

        mockMvc.perform(put("/items/{id}", 1)
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemResponse.getId()))
                .andExpect(jsonPath("$.name").value(itemResponse.getName()))
                .andExpect(jsonPath("$.imageUrl").value(itemResponse.getImageUrl()))
                .andExpect(jsonPath("$.price").value(itemResponse.getPrice()))
                .andDo(print());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteItemRequestSuccess() throws Exception{
        mockMvc.perform(delete("/items/{id}", 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    private static AddItemRequest createItemRequest(String name, String imageUrl, int price) {
        return new AddItemRequest(name, imageUrl, price);
    }

    private ItemResponse createItemResponse() {
        return ItemResponse.from(new Item(1L, "맥북", "http://image.com", 20_000));
    }
}
