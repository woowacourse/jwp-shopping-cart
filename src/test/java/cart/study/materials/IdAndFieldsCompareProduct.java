package cart.study.materials;

import java.util.Objects;

public class IdAndFieldsCompareProduct {

    private final Integer id;
    private final String name;
    private final Long price;
    private final boolean comparePricesToo;

    public IdAndFieldsCompareProduct(Integer id, String name, Long price,
            boolean comparePricesToo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.comparePricesToo = comparePricesToo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        IdAndFieldsCompareProduct that = (IdAndFieldsCompareProduct)o;

        if (!Objects.equals(id, that.id))
            return false;
        if (!Objects.equals(name, that.name))
            return false;
        return comparePricesToo && !Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        if (comparePricesToo) {
            result = 31 * result + (price != null ? price.hashCode() : 0);
        }
        return result;
    }
}