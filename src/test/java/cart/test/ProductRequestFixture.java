package cart.test;

import cart.dto.request.ProductRequestDto;

import java.util.List;

public class ProductRequestFixture {

    public static final ProductRequestDto request = new ProductRequestDto(
            "스플릿",
            "스프링",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequestDto updateRequest = new ProductRequestDto(
            "스플릿2",
            "스프링2",
            150000,
            "우아한테크코스2",
            List.of(3L, 4L)
    );
    public static final ProductRequestDto imageEmptyRequest = new ProductRequestDto(
            "name",
            null,
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequestDto overLengthNameRequest = new ProductRequestDto(
            "1".repeat(51),
            "imageUrl",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequestDto negativePriceRequest = new ProductRequestDto(
            "name",
            "imageUrl",
            -1,
            "우아한테크코스",
            List.of(1L, 2L)
    );
    public static final ProductRequestDto categoryNullRequest = new ProductRequestDto(
            "name",
            "imageUrl",
            1000,
            "우아한테크코스",
            null
    );
}
