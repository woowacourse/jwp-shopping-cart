package woowacourse.shoppingcart.dao;

public class PagingIndex {

    private final int startIndex;
    private final int limit;

    public PagingIndex(final int startIndex, final int limit) {
        this.startIndex = startIndex;
        this.limit = limit;
    }

    public static PagingIndex from(final int page, final int limit) {
        final int startIndex = limit * (page - 1);
        return new PagingIndex(startIndex, limit);
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "PagingIndex{" +
                "startIndex=" + startIndex +
                ", limit=" + limit +
                '}';
    }
}
