package store.view;

import store.dto.ProductDTO;

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
}
