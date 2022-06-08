package woowacourse.shoppingcart.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.function.Supplier;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.domain.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.Birthday;
import woowacourse.shoppingcart.domain.customer.Contact;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Gender;
import woowacourse.shoppingcart.domain.customer.Name;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.Terms;
import woowacourse.shoppingcart.dto.SignUpRequest;

public class CustomerFixture {

    public static final CustomerDto tommyDto = new CustomerDto("her0807@naver.com", "password1!",
            "example.com", "토미", "male", "1988-08-07",
            "12345678910", "a", "b", "12345", true);

    public static final CustomerDto connieDto = new CustomerDto("her0807@naver.com", "password1!",
            "example.com", "코니", "female", "1988-08-07",
            "12345678910",
            "a", "b", "12345", true);

    public static final Customer updatedTommyDto = new Customer(1L, new Email("her0807@naver.com"),
            new Password("password1!"), "example.com", new Name("토미"),
            Gender.MALE, new Birthday("1988-08-07"), new Contact("01987654321"),
            new FullAddress("d", "e", "54321"), new Terms(true));

    public static final Supplier<Customer> tommyCreator = () -> new Customer(1L, new Email("her0807@naver.com"),
            new Password("password1!"), "example.com", new Name("토미"), Gender.MALE,
            new Birthday("1988-08-07"), new Contact("01987654321"),
            new FullAddress("a", "b", "12345"), new Terms(true));

    public static final Customer tommy = tommyCreator.get();

    public static final String token = 회원가입_후_로그인_후_토큰_발급();


    private static SignUpRequest 회원_정보(String email, String password, String profileImageUrl, String name,
                                       String gender,
                                       String birthday, String contact, String address, String detailAddress,
                                       String zoneCode,
                                       boolean terms) {
        return new SignUpRequest(email, password, profileImageUrl, name, gender, birthday, contact, address,
                detailAddress, zoneCode, terms);
    }

    private static ExtractableResponse<Response> 회원_가입(SignUpRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/customers")
                .then()
                .log().all()
                .extract();
    }


    private static String 회원가입_후_로그인_후_토큰_발급() {
        회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                "희봉", "male", "1998-08-07", "12345678910",
                "address", "detailAddress", "12345", true));

        TokenRequest tokenRequest = new TokenRequest("example@example.com", "example123!");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
        return response.body()
                .jsonPath()
                .getObject("", TokenResponse.class).getAccessToken();
    }
}
