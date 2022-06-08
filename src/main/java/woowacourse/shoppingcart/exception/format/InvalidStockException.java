package woowacourse.shoppingcart.exception.format;

public class InvalidStockException extends FormatException{
    public InvalidStockException() {
        super("잘못된 수량 정보입니다.");
    }
}
