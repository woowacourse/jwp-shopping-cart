package study.materials;

import java.util.Objects;

public class FieldCompareProduct {

    private final String name;
    private final Long price;
    private final boolean comparePricesToo;

    public FieldCompareProduct(String name, Long price, boolean comparePricesToo) {
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

        FieldCompareProduct fieldCompareProduct = (FieldCompareProduct)o;

        if (comparePricesToo && !Objects.equals(price, fieldCompareProduct.price))
            return false;
        return Objects.equals(name, fieldCompareProduct.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        if (comparePricesToo) {
            result = 31 * result + (price != null ? price.hashCode() : 0);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", comparePricesToo=" + comparePricesToo +
                '}';
    }
}
