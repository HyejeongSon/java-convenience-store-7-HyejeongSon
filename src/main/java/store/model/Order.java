package store.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> items;
    private boolean membershipDiscount;
    private Inventory inventory;

    public Order(Inventory inventory) {
        this.items = new ArrayList<>();
        this.inventory = inventory;
    }

    public void addItem(Product item) {
        items.add(item);
    }

    public List<Product> getItems() {
        return items;
    }

    public void setMembershipDiscount(boolean membershipDiscount) {
        this.membershipDiscount = membershipDiscount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Products:\n");
        sb.append(items.toString());
        return sb.toString();
    }

    public int calculateTotalPrice() {
        int total = 0;
        for (Product product : items) {
            total += product.getPrice() * (product.getGeneralQuantity() + product.getPromotionQuantity());
        }
        return total;
    }

    public int calculateMembershipDiscount() {
        if (!membershipDiscount) return 0;
        int discount = 0;
        for (Product product : items) {
            if (product.getPromotion() == null) { // 멤버십 할인이 프로모션 없는 제품에만 적용
                discount += Math.min(product.getPrice() * product.getGeneralQuantity() * 0.3, 8000);
            }
        }
        return discount;
    }

    public int calculateEventDiscount() {
        int discount = 0;
        for (Product product : items) {
            if (product.getPromotion() != null) {
                Promotion promotion = inventory.getPromotionByProductName(product.getName()); // Inventory를 통해 Promotion 조회
                int freeQuantity = calculateFreeQuantity(product.getPromotionQuantity(), promotion);
                discount += product.getPrice() * freeQuantity; // 무료로 제공된 수량에 대해 할인 계산
            }
        }
        return discount;
    }

    private int calculateFreeQuantity(int promotionQuantity, Promotion promotion) {
        if (promotion == null) return 0;
        int buy = promotion.getBuy();
        int get = promotion.getGet();
        int applicableGroups = promotionQuantity / (buy + get);
        return applicableGroups * get;
    }

    public List<Product> getGiftedProducts() {
        List<Product> giftedProducts = new ArrayList<>();
        for (Product product : items) {
            if (product.getPromotion() != null && product.getPromotionQuantity() > 0) {
                giftedProducts.add(new Product(product.getName(), product.getPrice(), 0, product.getPromotion(), product.getPromotionQuantity()));
            }
        }
        return giftedProducts;
    }



    public int calculateTotalQuantity() {
        int totalQuantity = 0;
        for (Product product : items) {
            totalQuantity += product.getGeneralQuantity() + product.getPromotionQuantity();
        }
        return totalQuantity;
    }
}
