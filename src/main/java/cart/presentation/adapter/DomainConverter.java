package cart.presentation.adapter;

import cart.business.domain.cart.CartItem;
import cart.business.domain.cart.CartItemId;
import cart.business.domain.member.Member;
import cart.business.domain.member.MemberEmail;
import cart.business.domain.member.MemberId;
import cart.business.domain.member.MemberPassword;
import cart.business.domain.product.Product;
import cart.business.domain.product.ProductId;
import cart.business.domain.product.ProductImage;
import cart.business.domain.product.ProductName;
import cart.business.domain.product.ProductPrice;
import cart.presentation.dto.AuthInfo;
import cart.presentation.dto.CartItemDto;
import cart.presentation.dto.ProductDto;

public class DomainConverter {

    private static final Integer NULL_ID = null;

    public static Product toProductWithoutId(ProductDto productDto) {
        return new Product(
                new ProductId(NULL_ID),
                new ProductName(productDto.getName()),
                new ProductImage(productDto.getUrl()),
                new ProductPrice(productDto.getPrice())
        );
    }

    public static Product toProductWithId(ProductDto productDto) {
        return new Product(
                new ProductId(productDto.getId()),
                new ProductName(productDto.getName()),
                new ProductImage(productDto.getUrl()),
                new ProductPrice(productDto.getPrice())
        );
    }

    public static CartItem toCartItemWithoutId(Integer productId, Integer memberId) {
        return new CartItem(new CartItemId(NULL_ID), new ProductId(productId), new MemberId(memberId));
    }

    public static Member toMemberWithoutId(AuthInfo authInfo) {
        return new Member(new MemberId(NULL_ID), new MemberEmail(authInfo.getEmail()),
                new MemberPassword(authInfo.getPassword())
        );
    }

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(),
                product.getUrl(), product.getPrice());
    }

    public static CartItemDto toCartItemDto(Product product, Integer cartItemId) {
        return new CartItemDto(cartItemId, product.getName(),
                product.getUrl(), product.getPrice());
    }
}
