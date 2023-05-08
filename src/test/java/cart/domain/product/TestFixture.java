package cart.domain.product;

import cart.product.domain.ImageUrl;
import cart.product.domain.Product;
import cart.product.domain.ProductCategory;
import cart.product.domain.ProductId;
import cart.product.domain.ProductName;
import cart.product.domain.ProductPrice;
import cart.user.domain.CartUser;
import cart.user.domain.UserEmail;

public class TestFixture {
    public static final Product PIZZA = new Product(
            ProductName.from("Pizza"),
            ProductPrice.from(18_000),
            ProductCategory.FOOD,
            ImageUrl.from("www.domino-pizza.com"),
            ProductId.from(1L)
    );

    public static final Product CHICKEN = new Product(
            ProductName.from("Chicken"),
            ProductPrice.from(23_000),
            ProductCategory.FOOD,
            ImageUrl.from("www.kyochon.com"),
            ProductId.from(2L)
    );

    public static final CartUser CART_USER_A = new CartUser(
            UserEmail.from("a@a.com"),
            "password1"
    );

    public static final CartUser CART_USER_B = new CartUser(
            UserEmail.from("b@b.com"),
            "password2"
    );
}
