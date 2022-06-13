package woowacourse.shoppingcart.exception.format;

public class InvalidStockException extends FormatException{
    public InvalidStockException() {
        super("잘못된 재고 정보입니다.");
    }
}
