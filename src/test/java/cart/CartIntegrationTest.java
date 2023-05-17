package cart;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.domain.cart.CartId;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.CartRepository;
import cart.repository.MemberRepository;
import cart.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {
	@Autowired
	CartRepository repository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ProductRepository productRepository;
	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void findAllByMemberEmail() {
		final Member member = new Member("kiara", "a@a.com", "password1");
		final Product product = new Product("사과", 1000, "사과이미지");
		final MemberId memberId = memberRepository.insert(member);
		final ProductId productId = productRepository.insert(product);
		repository.insert(memberId, productId);

		final ExtractableResponse<Response> result = RestAssured.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.auth().preemptive().basic("a@a.com", "password1")
			.when().log().all()
			.get("/cart/items")
			.then().log().all()
			.extract();

		assertAll(
			() -> assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(result.jsonPath().getString("name")).isEqualTo("[사과]"),
			() -> assertThat(result.jsonPath().getString("image")).isEqualTo("[사과이미지]")
		);
	}

	@Test
	public void add() {
		final Member member = new Member("kiara", "c@c.com", "password1");
		final Product product = new Product("사과", 1000, "사과이미지");
		final MemberId memberId = memberRepository.insert(member);
		final ProductId productId = productRepository.insert(product);
		repository.insert(memberId, productId);

		final ExtractableResponse<Response> result = RestAssured.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.auth().preemptive().basic("c@c.com", "password1")
			.when().log().all()
			.post("/cart/" + productId.getId())
			.then().log().all()
			.extract();

		assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void delete() {
		final Member member = new Member("kiara", "c@c.com", "password1");
		final Product product = new Product("사과", 1000, "사과이미지");
		final MemberId memberId = memberRepository.insert(member);
		final ProductId productId = productRepository.insert(product);
		final CartId cartId = repository.insert(memberId, productId);

		final ExtractableResponse<Response> result = RestAssured.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.auth().preemptive().basic("c@c.com", "password1")
			.when().log().all()
			.delete("/cart/" + cartId.getId())
			.then().log().all()
			.extract();

		assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
