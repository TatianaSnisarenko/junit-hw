package ua.goit.shop;

import java.util.Locale;
import java.util.Objects;

public class Product {
    private String productId;
    private double price;
    private double promotionalPrice;
    private long promotionalQuantity;

    public Product(String productId, double price, double promotionalPrice, long promotionalQuantity) {
        if (isProductIdValid(productId) && isPriceValid(price) && isPriceValid(promotionalPrice) && isQuantityValid(promotionalQuantity)) {
            this.productId = productId;
            this.price = price;
            this.promotionalPrice = promotionalPrice;
            this.promotionalQuantity = promotionalQuantity;
        }
    }

    public Product(String productId, double price) {
        this(productId, price, price, Long.MAX_VALUE);
    }

    public String getProductId() {
        return productId;
    }

    public void setPrice(double price) {
        if (isPriceValid(price)) {
            this.price = price;
        }
    }

    public void setPromotionalPrice(double promotionalPrice) {
        if (isPriceValid(promotionalPrice)) {
            this.promotionalPrice = promotionalPrice;
        }
    }

    public void setPromotionalQuantity(long promotionalQuantity) {
        if (isQuantityValid(promotionalQuantity)) {
            this.promotionalQuantity = promotionalQuantity;
        }
    }

    public double getPrice() {
        return price;
    }

    public double getPromotionalPrice() {
        return promotionalPrice;
    }

    public long getPromotionalQuantity() {
        return promotionalQuantity;
    }

    private boolean isPriceValid(double price) {
        if (price > 0D) {
            return true;
        } else {
            throw new RuntimeException("Price is not valid");
        }
    }

    private boolean isQuantityValid(long quantity) {
        if (quantity > 0L) {
            return true;
        } else {
            throw new RuntimeException("Quantity is not valid");
        }
    }

    private boolean isProductIdValid(String productId) {
        String englishAlphabet = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";
        if (productId != null && productId.length() == 1 && englishAlphabet.contains(productId.toUpperCase(Locale.ROOT))) {
            return true;
        } else {
            throw new RuntimeException("Product id is not valid");
        }
    }

    public double getPriceByQuantity(long quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity is less than zero");
        }
        if (quantity < promotionalQuantity) {
            return price * quantity;
        } else {
            return (quantity / promotionalQuantity) * promotionalPrice + (quantity % promotionalQuantity) * price;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 && Double.compare(product.getPromotionalPrice(), getPromotionalPrice()) == 0 && getPromotionalQuantity() == product.getPromotionalQuantity() && Objects.equals(getProductId(), product.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getPrice(), getPromotionalPrice(), getPromotionalQuantity());
    }
}
