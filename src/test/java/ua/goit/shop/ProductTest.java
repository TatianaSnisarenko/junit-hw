package ua.goit.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {
    private static final double EPSILON = 0.000000001;
    private static Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product("H", 5D, 14D, 3l);
    }

    @Test
    void setPrice() {
        testProduct.setPrice(6D);
        assertThat(testProduct.getPrice()).isEqualTo(6D, offset(EPSILON));

        assertThatThrownBy(() -> {
            testProduct.setPrice(-1);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");

        assertThatThrownBy(() -> {
            testProduct.setPrice(0);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");
    }

    @Test
    void setPromotionalPrice() {
        testProduct.setPromotionalPrice(6D);
        assertThat(testProduct.getPromotionalPrice()).isEqualTo(6D, offset(EPSILON));

        assertThatThrownBy(() -> {
            testProduct.setPromotionalPrice(-1);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");

        assertThatThrownBy(() -> {
            testProduct.setPromotionalPrice(0);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Price is not valid");
    }

    @Test
    void setPromotionalQuantity() {
        testProduct.setPromotionalQuantity(3);
        assertThat(testProduct.getPromotionalQuantity()).isEqualTo(3);

        assertThatThrownBy(() -> {
            testProduct.setPromotionalQuantity(-1);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Quantity is not valid");

        assertThatThrownBy(() -> {
            testProduct.setPromotionalQuantity(0);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Quantity is not valid");
    }

    @Test
    void getPriceByQuantity() {
        assertThat(testProduct.getPriceByQuantity(1L)).isEqualTo(5D, offset(EPSILON));
        assertThat(testProduct.getPriceByQuantity(2L)).isEqualTo(10D, offset(EPSILON));
        assertThat(testProduct.getPriceByQuantity(3L)).isEqualTo(14D, offset(EPSILON));
        assertThat(testProduct.getPriceByQuantity(4L)).isEqualTo(19D, offset(EPSILON));
        assertThat(testProduct.getPriceByQuantity(5L)).isEqualTo(24D, offset(EPSILON));
        assertThat(testProduct.getPriceByQuantity(6L)).isEqualTo(28D, offset(EPSILON));
        assertThat(testProduct.getPriceByQuantity(0)).isEqualTo(0, offset(EPSILON));

        assertThatThrownBy(() -> {
            testProduct.getPriceByQuantity(-1);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Quantity is less than zero");
    }

    @Test
    void testEquals() {
        Product productIdenticalTestProduct = testProduct;
        Product copyOfTestProduct = new Product("H", 5D, 14D, 3l);
        Product anotherProductId = new Product("A", 5D, 14D, 3l);
        Product anotherProductPrice = new Product("H", 4D, 14D, 3l);
        Product anotherProductPromotionalPrice = new Product("H", 5D, 13D, 3l);
        Product anotherProductPromotionalQuantity = new Product("H", 5D, 14D, 4l);
        assertThat(testProduct).isEqualTo(testProduct);
        assertThat(testProduct).isEqualTo(productIdenticalTestProduct);
        assertThat(testProduct).isEqualTo(copyOfTestProduct);
        assertThat(testProduct).isNotEqualTo(anotherProductId);
        assertThat(testProduct).isNotEqualTo(anotherProductPrice);
        assertThat(testProduct).isNotEqualTo(anotherProductPromotionalPrice);
        assertThat(testProduct).isNotEqualTo(anotherProductPromotionalQuantity);
    }

    @Test
    void testHashCode() {
        Product productIdenticalTestProduct = testProduct;
        Product copyOfTestProduct = new Product("H", 5D, 14D, 3l);

        assertThat(testProduct.hashCode()).isEqualTo(testProduct.hashCode());
        assertThat(testProduct.hashCode()).isEqualTo(productIdenticalTestProduct.hashCode());
        assertThat(testProduct.hashCode()).isEqualTo(copyOfTestProduct.hashCode());
    }
}