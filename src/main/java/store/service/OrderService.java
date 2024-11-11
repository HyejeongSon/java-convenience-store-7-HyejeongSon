package store.service;

import store.model.Inventory;
import store.model.Order;
import store.model.Product;
import store.model.Promotion;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;

public class OrderService {
    private Inventory inventory;
    private Order order;
    private boolean continueShopping;

    public OrderService(Inventory inventory) {
        this.inventory = inventory;
        this.continueShopping = true;
    }

    public void startOrderService() {
        while (continueShopping) {
            OutputView.printProducts(inventory.getProductDTOList());
            order = new Order(inventory);
            processOrder();
            order.setMembershipDiscount(InputView.askMembershipDiscount());
            printReceipt();
            continueShopping = InputView.askForAdditionalPurchase();
        }
    }

    public void printReceipt() {
        int totalPrice = order.calculateTotalPrice();
        int eventDiscount = order.calculateEventDiscount();
        int membershipDiscount = order.calculateMembershipDiscount();
        int finalPrice = totalPrice - eventDiscount - membershipDiscount;

        OutputView.printReceipt(order, totalPrice, eventDiscount, membershipDiscount, finalPrice, inventory);
    }

    private void processOrder() {
        while (true) {
            try {
                processProducts(InputView.readItem());
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processProducts(String items) {
        for (String item : items.split(",")) {
            try {
                String[] itemInfo = item.replaceAll("\\[", "").replaceAll("\\]", "").split("-");
                processProduct(itemInfo[0], Integer.parseInt(itemInfo[1]));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
        }
    }

    private void processProduct(String productName, int quantity) {
        validateProduct(productName, quantity);
        Product product = inventory.getProduct(productName);
        Promotion promotion = inventory.getPromotionByProductName(productName);
        if (isPromotionApplicable(promotion)) {
            applyPromotion(product, promotion, quantity);
            return;
        }
        processItem(product.getName(), product.getPrice(), quantity, null, 0);
    }

    private boolean isPromotionApplicable(Promotion promotion) {
        return promotion != null && promotion.isDateInRange(LocalDate.now());
    }

    private void applyPromotion(Product product, Promotion promotion, int quantity) {
        int maxPromoTotal = calculateMaxPromoTotal(product, promotion);
        if (isEligibleForFreeProduct(quantity, maxPromoTotal, promotion)) {
            handleFreeProductCase(product, quantity);
            return;
        }
        if (requiresFullPriceOffer(quantity, maxPromoTotal)) {
            handleFullPriceCase(product, quantity, maxPromoTotal);
            return;
        }
        processItem(product.getName(), product.getPrice(), 0, product.getPromotion(), quantity);
    }

    private int calculateMaxPromoTotal(Product product, Promotion promotion) {
        int maxPromoApplies = product.getPromotionQuantity() / (promotion.getBuy() + 1);
        int maxPromoItems = maxPromoApplies * promotion.getBuy();
        return maxPromoApplies + maxPromoItems;
    }

    private boolean isEligibleForFreeProduct(int quantity, int maxPromoTotal, Promotion promotion) {
        return quantity < maxPromoTotal && (quantity + 1) % (promotion.getBuy() + 1) == 0;
    }

    private void handleFreeProductCase(Product product, int quantity) {
        if (InputView.askForMoreProduct(product.getName(), 1)) {
            processItem(product.getName(), product.getPrice(), 0, product.getPromotion(), quantity + 1);
            return;
        }
        processItem(product.getName(), product.getPrice(), 0, product.getPromotion(), quantity);
    }

    private boolean requiresFullPriceOffer(int quantity, int maxPromoTotal) {
        return quantity > maxPromoTotal;
    }

    private void handleFullPriceCase(Product product, int quantity, int maxPromoTotal) {
        if (InputView.askForFullPrice(product.getName(), quantity - maxPromoTotal)) {
            int promotionQuantity = Math.min(quantity, product.getPromotionQuantity());
            int generalQuantity = quantity - promotionQuantity;
            processItem(product.getName(), product.getPrice(), generalQuantity, product.getPromotion(), promotionQuantity);
            return;
        }
        processItem(product.getName(), product.getPrice(), 0, product.getPromotion(), maxPromoTotal);
    }

    private void processItem(String name, int price, int generalQuantity, String promotion, int promotionQuantity) {
        order.addItem(new Product(name, price, generalQuantity, promotion, promotionQuantity));
        if(generalQuantity > 0) {
            inventory.getProduct(name).decreaseGeneralQuantity(generalQuantity);
        }
        if(promotionQuantity > 0) {
            inventory.getProduct(name).decreasePromotionQuantity(promotionQuantity);
        }
    }

    // 예외처리
    private void validateProduct(String productName, int quantity) {
        validateProductExists(productName);
        validateProductQuantity(productName, quantity);
    }

    private void validateProductExists(String productName) {
        if (!inventory.containsProduct(productName)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void validateProductQuantity(String productName, int quantity) {
        int totalQuantity = 0;
        if(inventory.hasPromotionByProductName(productName) && inventory.getPromotionByProductName(productName).isDateInRange(LocalDate.now())) {
            totalQuantity += inventory.getPromotionQuantityByProductName(productName);
        }
        totalQuantity += inventory.getGeneralQuantityByProductName(productName);
        if (totalQuantity < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
