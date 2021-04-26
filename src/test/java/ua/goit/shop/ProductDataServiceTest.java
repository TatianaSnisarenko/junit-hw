package ua.goit.shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.*;

class ProductDataServiceTest {

    private static Map<String, Product> allProductCopy;
    private static final double EPSILON = 0.000000001;
    private static Product h;

    @BeforeEach
    void setUp() {
        allProductCopy = ProductDataService.getAllProducts();
        h = new Product("H", 1.5D, 4D, 3l);
        ProductDataService.addOrUpdateProduct(h);
    }

    @AfterEach
    void tearDown() {
        ProductDataService.setAllProducts(allProductCopy);
    }

    @Test
    void getAllProducts() {
        assertThat(ProductDataService.getAllProducts().size()).isGreaterThan(0);
        assertThat(ProductDataService.getAllProducts()).containsValue(h);
    }

    @Test
    void setAllProducts() {
        Product a = new Product("A", 1.25D, 3D, 3l);
        Product b = new Product("B", 4.25D);

        Map<String, Product> testMap = Map.of(a.getProductId(), a, b.getProductId(), b);
        ProductDataService.setAllProducts(testMap);
        assertThat(testMap).isEqualTo(ProductDataService.getAllProducts());

        assertThatThrownBy(() -> {
            ProductDataService.setAllProducts(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void addOrUpdateProduct() {
        assertThat(ProductDataService.getAllProducts()).containsKey(h.getProductId());
        assertThat(ProductDataService.getAllProducts()).containsValue(h);
        h.setPromotionalPrice(4.25D);
        assertThat(ProductDataService.getProductById(h.getProductId()).getPromotionalPrice()).isEqualTo(4.25D, offset(EPSILON));
        assertThatThrownBy(() -> {
            ProductDataService.addOrUpdateProduct(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void removeProductById() {
        assertThat(ProductDataService.getAllProducts()).containsValue(h);
        ProductDataService.removeProductById("H");
        assertThat(ProductDataService.getAllProducts()).doesNotContainValue(h);
        assertThatThrownBy(() -> {
            ProductDataService.removeProductById(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void removeProductByProduct() {
        assertThat(ProductDataService.getAllProducts()).containsValue(h);
        ProductDataService.removeProductByProduct(h);
        assertThat(ProductDataService.getAllProducts()).doesNotContainValue(h);
        assertThatThrownBy(() -> {
            ProductDataService.removeProductByProduct(null);
        }).isInstanceOf(NullPointerException.class);
    }

    //if(productBucket == null || productBucket.isEmpty()) return false;
    @Test
    void calculateTotalCost() {
        String productbucketWithSpaces = " ABCDABA";
        String productbucketCorrectString = "ABCDABACCAACCC";
        String productbucketWithNotAlphabetic = " A2BC4DABA";
        String productbucketWithSmallLetters = " AbcDABa";
        String productbucketWithRussianLetters = " ABCПРDABA";
        String productbucketWithIdThatAreNotInAllProducts = " ABCDABAEF";

        assertThat(ProductDataService.calculateTotalCost(productbucketWithSpaces)).isEqualTo(13.25, offset(EPSILON));
        assertThat(ProductDataService.calculateTotalCost(productbucketCorrectString)).isEqualTo(19.75, offset(EPSILON));
        assertThat(ProductDataService.calculateTotalCost(productbucketWithNotAlphabetic)).isEqualTo(13.25, offset(EPSILON));
        assertThat(ProductDataService.calculateTotalCost(productbucketWithSmallLetters)).isEqualTo(13.25, offset(EPSILON));
        assertThat(ProductDataService.calculateTotalCost("")).isEqualTo(0D, offset(EPSILON));
        assertThat(ProductDataService.calculateTotalCost(null)).isEqualTo(0D, offset(EPSILON));

        assertThatThrownBy(() -> {
            ProductDataService.calculateTotalCost(productbucketWithRussianLetters);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Some product ids are not valid");
        assertThatThrownBy(() -> {
            ProductDataService.calculateTotalCost(productbucketWithIdThatAreNotInAllProducts);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Some product ids are not valid");


        ProductDataService.setAllProducts(new ConcurrentHashMap<>());
        assertThatThrownBy(() -> {
            ProductDataService.calculateTotalCost(productbucketWithIdThatAreNotInAllProducts);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("DataBaseOfPrices is not available");
    }

    @Test
    void getProductById() {
        assertThat(ProductDataService.getProductById(h.getProductId())).isEqualTo(h);
        assertThatThrownBy(() -> {
            ProductDataService.getProductById(null);
        }).isInstanceOf(NullPointerException.class);
    }
}