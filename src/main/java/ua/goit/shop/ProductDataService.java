package ua.goit.shop;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ProductDataService {

    private static Map<String, Product> ALL_PRODUCTS = new ConcurrentHashMap<>();

    static {
        Product a = new Product("A", 1.25D, 3D, 3l);
        Product b = new Product("B", 4.25D);
        Product c = new Product("C", 1.00D, 5D, 6l);
        Product d = new Product("D", 0.75D);

        ALL_PRODUCTS.put(a.getProductId(), a);
        ALL_PRODUCTS.put(b.getProductId(), b);
        ALL_PRODUCTS.put(c.getProductId(), c);
        ALL_PRODUCTS.put(d.getProductId(), d);
    }

    public static Map<String, Product> getAllProducts() {
        return new HashMap<>(ALL_PRODUCTS);
    }

    public static void setAllProducts(Map<String, Product> allProducts) {
        Objects.requireNonNull(allProducts);
        ALL_PRODUCTS = allProducts;
    }

    public static void addOrUpdateProduct(Product product) {
        Objects.requireNonNull(product);
        ALL_PRODUCTS.put(product.getProductId(), product);
    }

    public static void removeProductById(String productId) {
        Objects.requireNonNull(productId);
        ALL_PRODUCTS.remove(productId);
    }

    public static void removeProductByProduct(Product product) {
        Objects.requireNonNull(product);
        ALL_PRODUCTS.remove(product.getProductId(), product);
    }

    public static Product getProductById(String productId) {
        Objects.requireNonNull(productId);
        return ALL_PRODUCTS.getOrDefault(productId, null);
    }


    public static double calculateTotalCost(String productBucket) {
        if (validateProductBucket(productBucket)) {
            Map<String, Long> productsWithQuantity = productBucket.chars()
                    .filter(Character::isAlphabetic)
                    .mapToObj(c -> ALL_PRODUCTS.get(String.valueOf((char) c).toUpperCase(Locale.ROOT)))
                    .collect(Collectors.groupingBy(Product::getProductId, Collectors.mapping(Product::getProductId, Collectors.counting())));
            return productsWithQuantity.entrySet().stream()
                    .mapToDouble(entry -> ALL_PRODUCTS.get(entry.getKey()).getPriceByQuantity(entry.getValue()))
                    .sum();
        } else {
            return 0D;
        }

    }

    private static boolean validateProductBucket(String productBucket) {
        if (productBucket == null || productBucket.isEmpty()) return false;
        if (ALL_PRODUCTS == null || ALL_PRODUCTS.isEmpty())
            throw new RuntimeException("DataBaseOfPrices is not available");
        long countOfUnknownProductsId = productBucket.chars()
                .filter(Character::isAlphabetic)
                .filter(c -> !ALL_PRODUCTS.containsKey(String.valueOf((char) c).toUpperCase(Locale.ROOT)))
                .count();
        if (countOfUnknownProductsId != 0L) {
            throw new RuntimeException("Some product ids are not valid");
        }
        return true;
    }
}
