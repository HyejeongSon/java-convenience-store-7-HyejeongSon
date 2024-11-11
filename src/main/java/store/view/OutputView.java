package store.view;

import store.dto.ProductDTO;
import store.model.Inventory;
import store.model.Order;
import store.model.Product;
import store.model.Promotion;

import java.util.List;

public class OutputView {

    public static void printProducts(List<ProductDTO> products) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        System.out.print(productsList(products));
    }

    private static String productsList(List<ProductDTO> products) {
        StringBuilder sb = new StringBuilder();
        for (ProductDTO product : products) {
            if (product.getPromotion() != null) {
                sb.append(productDetails(product.getName(), product.getPrice(), product.getPromotionQuantity(), product.getPromotion()));
            }
            sb.append(productDetails(product.getName(), product.getPrice(), product.getGeneralQuantity(), ""));
        }
        return sb.toString();
    }

    private static String productDetails(String name, int price, int quantity, String promotion) {
        String displayQuantity = quantity + "개";
        if (quantity == 0) displayQuantity = "재고 없음";

        return String.format("- %s %,d원 %s %s%n", name, price, displayQuantity, promotion);
    }

    public static void printReceipt(Order order, int totalPrice, int eventDiscount, int membershipDiscount, int finalPrice, Inventory inventory) {
        printReceiptHeader();
        printPurchasedItems(order);
        printGiftedItems(order, inventory); // Inventory 추가 전달
        printReceiptFooter(order, totalPrice, eventDiscount, membershipDiscount, finalPrice);
    }

    private static void printReceiptHeader() {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
    }

    private static void printPurchasedItems(Order order) {
        for (Product product : order.getItems()) {
            int totalQuantity = product.getGeneralQuantity() + product.getPromotionQuantity();
            System.out.printf("%s\t\t%d \t%,d\n", product.getName(), totalQuantity, product.getPrice() * totalQuantity);
        }
    }

    private static void printGiftedItems(Order order, Inventory inventory) {
        System.out.println("=============증\t정===============");
        for (Product gift : order.getGiftedProducts()) {
            Promotion promotion = inventory.getPromotionByProductName(gift.getName());
            int freeQuantity = calculateFreeQuantity(gift.getPromotionQuantity(), promotion);
            System.out.printf("%s\t\t%d\n", gift.getName(), freeQuantity);
        }
    }

    private static int calculateFreeQuantity(int promotionQuantity, Promotion promotion) {
        if (promotion == null) return 0;
        int buy = promotion.getBuy();
        int get = promotion.getGet();
        int applicableGroups = promotionQuantity / (buy + get);
        return applicableGroups * get;
    }

    private static void printReceiptFooter(Order order, int totalPrice, int eventDiscount, int membershipDiscount, int finalPrice) {
        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\t%,d\n", order.calculateTotalQuantity(), totalPrice);
        System.out.printf("행사할인\t\t-%,d\n", eventDiscount);
        System.out.printf("멤버십할인\t\t\t-%,d\n", membershipDiscount);
        System.out.printf("내실돈\t\t\t %,d\n", finalPrice);
    }
}
