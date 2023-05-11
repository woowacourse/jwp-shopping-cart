package cart.domain;

import cart.domain.product.*;
import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.User;

public class TestFixture {
    public static final Product PIZZA = new Product(
            ProductName.from("Pizza"),
            ProductPrice.from(18_000),
            ProductCategory.FOOD,
            ImageUrl.from("www.domino-pizza.com"),
            1L
    );

    public static final Product CHICKEN = new Product(
            ProductName.from("Chicken"),
            ProductPrice.from(23_000),
            ProductCategory.FOOD,
            ImageUrl.from("www.kyochon.com"),
            2L
    );

    public static final User ZUNY = new User(
            Email.from("zuny@naver.com"),
            Password.from("zuny1234"),
            1L
    );

    public static final User ADMIN = new User(
            Email.from("amdin@naver.com"),
            Password.from("adminadmin"),
            2L
    );
}
