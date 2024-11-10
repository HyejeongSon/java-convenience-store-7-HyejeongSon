package store.service;

import store.model.Inventory;
import store.model.Product;
import store.model.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {
    private Inventory inventory;

    public InventoryService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void loadInventoryFromFile() {
        loadProducts();
        loadPromotions();
    }

    private void loadProducts() {
        List<String> products = readFile("products.md");
        for (String product : products) {
            String[] parts = product.split(",");
            addProductByPromotionStatus(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3]);
        }
    }

    private void addProductByPromotionStatus(String name, int price, int quantity, String promotion) {
        if (promotion.equals("null")) {
            addProductWithoutPromotion(name, price, quantity);
            return;
        }
        addProductWithPromotion(name, price, quantity, promotion);
    }

    private void addProductWithoutPromotion(String name, int price, int quantity) {
        if (inventory.containsProduct(name)) {
            inventory.setGeneralQuantity(name, quantity);
            return;
        }
        inventory.addProduct(new Product(name, price, quantity, null, 0));
    }

    private void addProductWithPromotion(String name, int price, int quantity, String promotion) {
        if (inventory.containsProduct(name)) {
            inventory.setPromotionQuantity(name, promotion, quantity);
            return;
        }
        inventory.addProduct(new Product(name, price, 0, promotion, quantity));
    }

    private void loadPromotions() {
        List<String> promotions = readFile("promotions.md");
        for (String promotion : promotions) {
            String[] parts = promotion.split(",");
            inventory.addPromotion(new Promotion(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), LocalDate.parse(parts[3]), LocalDate.parse(parts[4])));
        }
    }

    private List<String> readFile(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            br.readLine();
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
