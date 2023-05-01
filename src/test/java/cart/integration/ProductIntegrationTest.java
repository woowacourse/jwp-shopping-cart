package cart.integration;

import cart.config.IntegrationTestConfig;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.product.ProductRepository;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

class ProductIntegrationTest extends IntegrationTestConfig {
    private static final Product CHICKEN = new Product("치킨", 10000, "이미지-url");

    @Autowired
    ProductRepository repository;

    @Test
    void getProducts() {
        repository.save(CHICKEN);

        given(this.spec)
                .log().all()
                .filter(document(METHOD_NAME,
                        responseFields(
                                fieldWithPath("[].id").description("상품 번호"),
                                fieldWithPath("[].name").description("상품명"),
                                fieldWithPath("[].price").description("상품 가격"),
                                fieldWithPath("[].image").description("상품 이미지 url")
                        )))
                .contentType(APPLICATION_JSON_VALUE)

        .when()
                .get("/products")

        .then()
                .statusCode(OK.value());
    }

    @Test
    void createProduct() {
        final ProductCreateRequest request = new ProductCreateRequest("KIARA", 10000, "이미지-url");

        given(this.spec)
                .log().all()
                .filter(document(METHOD_NAME,
                        requestFields(
                                fieldWithPath("name").description("상품명"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("image").description("상품 이미지 url")
                        )))
                .contentType(APPLICATION_JSON_VALUE)
                .body(request)

        .when()
                .post("/products")

        .then()
                .statusCode(CREATED.value());
    }

    @Test
    void deleteProduct() {
        final ProductId saveId = repository.save(CHICKEN);

        given(this.spec)
                .log().all()
                .filter(document(METHOD_NAME))
                .contentType(APPLICATION_JSON_VALUE)
                .pathParam("id", saveId.getId())

        .when()
                .delete("/products/{id}")
        .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    void updateProduct() {
        final ProductId saveId = repository.save(CHICKEN);
        final ProductUpdateRequest request = new ProductUpdateRequest("HYENA", 3000, "이미지2-url");

        given(this.spec)
                .log().all()
                .filter(document(METHOD_NAME,
                        requestFields(
                                fieldWithPath("name").description("변경될 상품명"),
                                fieldWithPath("price").description("변경될 상품 가격"),
                                fieldWithPath("image").description("변경될 상품 이미지 url")
                        ),
                        responseFields(
                                fieldWithPath("id").description("변경된 상품 번호"),
                                fieldWithPath("name").description("변경된 상품명"),
                                fieldWithPath("price").description("변경된 상품 가격"),
                                fieldWithPath("image").description("변경된 상품 이미지 url")
                        )
                ))
                .contentType(APPLICATION_JSON_VALUE)
                .pathParam("id", saveId.getId())
                .body(request)

        .when()
                .patch("/products/{id}")
        .then()
                .statusCode(OK.value());
    }
}
