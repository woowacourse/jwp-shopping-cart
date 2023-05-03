package cart.domain.product;

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
}
