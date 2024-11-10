package store.model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;
    private Map<String, Promotion> promotions;

    public Inventory() {
        this.products = new HashMap<>();
        this.promotions = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public void addPromotion(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }

    public int productSize() {
        return products.size();
    }

    public int promotionSize() {
        return promotions.size();
    }
}
