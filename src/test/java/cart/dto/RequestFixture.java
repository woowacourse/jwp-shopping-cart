package cart.dto;


import cart.controller.dto.ProductRequest;

@SuppressWarnings("SpellCheckingInspection")
public class RequestFixture {

    public static final ProductRequest NUNU_REQUEST = new ProductRequest("누누", "naver.com", 1);

    private RequestFixture() {
    }
}
