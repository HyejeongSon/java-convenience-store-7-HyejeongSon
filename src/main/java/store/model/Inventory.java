package store.model;

import store.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory {
    private Map<String, Product> products;
    private Map<String, Promotion> promotions;

    public Inventory() {
        this.products = new HashMap<>();
        this.promotions = new HashMap<>();
    }

    public boolean containsProduct(String product) {
        return products.containsKey(product);
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

    public void setPromotionQuantity(String name, String promotion, int quantity) {
        products.get(name).setPromotion(promotion);
        products.get(name).setPromotionQuantity(quantity);
    }

    public void setGeneralQuantity(String name, int quantity) {
        products.get(name).setGeneralQuantity(quantity);
    }

    public List<ProductDTO> getProductDTOList() {
        return products.values().stream()
                .map(product -> new ProductDTO(
                        product.getName(),
                        product.getPrice(),
                        product.getGeneralQuantity(),
                        product.getPromotion(),
                        product.getPromotionQuantity()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Products:\n");
        sb.append(products.toString());
        sb.append("\n\nPromotions:\n");
        sb.append(promotions.toString());
        return sb.toString();
    }
}
