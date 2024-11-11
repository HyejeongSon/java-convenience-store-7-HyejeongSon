package store.model;

public class Product {
    private String name;
    private int price;
    private int generalQuantity;
    private String promotion;
    private int promotionQuantity;

    public Product(String name, int price, int generalQuantity, String promotion, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.generalQuantity = generalQuantity;
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
    }

    public void setGeneralQuantity(int generalQuantity) {
        this.generalQuantity = generalQuantity;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void setPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getGeneralQuantity() {
        return generalQuantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public void decreaseGeneralQuantity(int amount) {
        generalQuantity -= amount;
    }

    public void decreasePromotionQuantity(int amount) {
        promotionQuantity -= amount;
    }

    public boolean canApplyPromotion(int quantity) {
        return promotionQuantity >= quantity;
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + price + ", " + generalQuantity + ", " + promotion + ", " + promotionQuantity + "}";
    }
}
