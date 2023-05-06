package cart.test;

import cart.dto.request.ProductRequest;
import java.util.List;

public class ProductRequestFixture {

    public static final ProductRequest request = new ProductRequest(
            "스플릿",
            "스프링",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequest updateRequest = new ProductRequest(
            "스플릿2",
            "스프링2",
            150000,
            "우아한테크코스2",
            List.of(3L, 4L)
    );
    public static final ProductRequest imageEmptyRequest = new ProductRequest(
            "name",
            null,
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequest overLengthNameRequest = new ProductRequest(
            "1".repeat(51),
            "imageUrl",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequest negativePriceRequest = new ProductRequest(
            "name",
            "imageUrl",
            -1,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequest categoryNullRequest = new ProductRequest(
            "name",
            "imageUrl",
            1000,
            "우아한테크코스",
            null
    );
}
