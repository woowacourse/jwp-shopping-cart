package woowacourse.shoppingcart.application.dto;

public class PageServiceRequest {

    private final int number;
    private final int size;

    public PageServiceRequest(final int number, final int size) {
        this.number = number;
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }
}
