package cart.domain;

public class Price {

    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 10_000_000;

    private final int price;

    public Price(final int price) {
        validateRange(price);
        this.price = price;
    }

    private void validateRange(final int price){
        if(price < MIN_PRICE || MAX_PRICE < price){
            throw new IllegalArgumentException("상품 등록을 등록하기 위해서는 1원 이상 천만원 이하의 가격을 설정해야 합니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
