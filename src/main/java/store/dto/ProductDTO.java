package store.dto;

public class ProductDTO {
    private String name;
    private int price;
    private int generalQuantity;
    private String promotion;
    private int promotionQuantity;

    public ProductDTO(String name, int price, int generalQuantity, String promotion, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.generalQuantity = generalQuantity;
        this.promotion = promotion;
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
}
