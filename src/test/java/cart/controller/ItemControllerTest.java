package cart.controller;

import static cart.fixture.ItemDtoFactory.createItemDto;
import static cart.fixture.RequestFactory.ADD_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.UPDATE_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.createAddItemRequest;
import static cart.fixture.ResponseFactory.MAC_BOOK_RESPONSE;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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
import cart.controller.dto.UpdateItemRequest;
import cart.exception.GlobalControllerAdvice;
import cart.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @MockBean
    ItemService itemService;

    @Autowired
    ItemController itemController;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setControllerAdvice(new GlobalControllerAdvice())
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void createItemRequestSuccess() throws Exception {
        when(itemService.add(any(String.class), any(String.class), anyInt())).thenReturn(createItemDto());

        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(ADD_MAC_BOOK_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(MAC_BOOK_RESPONSE.getId()))
                .andExpect(jsonPath("$.name").value(MAC_BOOK_RESPONSE.getName()))
                .andExpect(jsonPath("$.imageUrl").value(MAC_BOOK_RESPONSE.getImageUrl()))
                .andExpect(jsonPath("$.price").value(MAC_BOOK_RESPONSE.getPrice()));
    }

    @Test
    @DisplayName("상품 전체를 조회한다.")
    void findAllItemRequestSuccess() throws Exception {
        when(itemService.findAll()).thenReturn(List.of(createItemDto(), createItemDto()));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @DisplayName("상품을 변경한다.")
    void updateItemRequestSuccess() throws Exception {
        when(itemService.update(anyLong(), any(UpdateItemRequest.class))).thenReturn(createItemDto());

        mockMvc.perform(put("/items/{id}", 1)
                        .content(objectMapper.writeValueAsString(UPDATE_MAC_BOOK_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(MAC_BOOK_RESPONSE.getId()))
                .andExpect(jsonPath("$.name").value(MAC_BOOK_RESPONSE.getName()))
                .andExpect(jsonPath("$.imageUrl").value(MAC_BOOK_RESPONSE.getImageUrl()))
                .andExpect(jsonPath("$.price").value(MAC_BOOK_RESPONSE.getPrice()));
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteItemRequestSuccess() throws Exception {
        mockMvc.perform(delete("/items/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("상품 요청 이름에 공백이 입력되면 BAD REQUEST가 반횐된다.")
    void createItemRequestFailWithBlankName(String name) throws Exception {
        AddItemRequest addItemRequest = createAddItemRequest(name, "https://image.com", 15_000);

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
        AddItemRequest addItemRequest = createAddItemRequest("맥북", imageUrl, 15_000);

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
        AddItemRequest addItemRequest = createAddItemRequest("맥북", "https://image.com", price);

        mockMvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(addItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 시 @PathVariable에 공백을 입력하면 BAD REQUEST가 반환된다.")
    void updateItemRequestFailWithBlankPathVariable() throws Exception {
        mockMvc.perform(put("/items/{id}", "   ")
                        .content(objectMapper.writeValueAsString(UPDATE_MAC_BOOK_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 시 @PathVariable에 문자열을 입력하면 BAD REQUEST가 반환된다.")
    void updateItemRequestFailWithStringPathVariable() throws Exception {
        mockMvc.perform(delete("/items/{id}", "abc")
                        .content(objectMapper.writeValueAsString(UPDATE_MAC_BOOK_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 삭제 시 @PathVariable에 공백을 입력하면 BAD REQUEST가 반환된다.")
    void deleteItemRequestFailWithBlankPathVariable() throws Exception {
        mockMvc.perform(delete("/items/{id}", "   "))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 시 @PathVariable에 문자열을 입력하면 BAD REQUEST가 반환된다.")
    void deleteItemRequestFailWithStringPathVariable() throws Exception {
        mockMvc.perform(put("/items/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }
}
