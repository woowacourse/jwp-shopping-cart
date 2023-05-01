package cart;

import cart.repository.ProductRepository;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class ProductIntegrationTest {
    static final String METHOD_NAME = "{method-name}";

    @Autowired
    ProductRepository repository;

    @LocalServerPort
    int port;

    RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocs) {
        RestAssured.port = this.port;
        this.spec = new RequestSpecBuilder()
                .addFilter(document("{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .addFilter(documentationConfiguration(restDocs))
                .build();
    }

    @Test
    void getProducts() {
        repository.save(new ProductCreateRequest("HYENA", 10000, "이미지-url"));

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
        final long saveId = repository.save(new ProductCreateRequest("KIARA", 10000, "이미지-url"));

        given(this.spec)
                .log().all()
                .filter(document(METHOD_NAME))
                .contentType(APPLICATION_JSON_VALUE)
                .pathParam("id", saveId)

        .when()
                .delete("/products/{id}")
        .then()
                .statusCode(NO_CONTENT.value());
    }

    @Test
    void updateProduct() {
        final long saveId = repository.save(new ProductCreateRequest("KIARA", 10000, "이미지-url"));
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
                .pathParam("id", saveId)
                .body(request)

        .when()
                .patch("/products/{id}")
        .then()
                .statusCode(OK.value());
    }
}
