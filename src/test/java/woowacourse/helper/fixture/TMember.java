package woowacourse.helper.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.member.domain.Member;
import woowacourse.member.dto.MemberRegisterRequest;

public enum TMember {

    MARU("maru@gmai.com", "Maru1234!", "송채원"),
    HUNI("huni@gmail.com", "Huni1234!", "최재훈");

    private final String email;
    private final String password;
    private final String name;

    TMember(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public ExtractableResponse<Response> register() {
        return request("/api/members", new MemberRegisterRequest(email, password, name));
    }

    private ExtractableResponse<Response> request(String url, Object params) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url, params)
                .then().log().all()
                .extract();
    }

    public MemberRegisterRequest toMemberRegisterRequest() {
        return new MemberRegisterRequest(email, password, name);
    }

    public Member toEntity() {
        return new Member(email, password, name);
    }
}
