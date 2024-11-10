package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public static String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public static boolean askForAdditionalPurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return validateResponse(Console.readLine()).equalsIgnoreCase("Y");
    }

    public static boolean askForMoreProduct(String productName, int quantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", productName, quantity);
        return validateResponse(Console.readLine()).equalsIgnoreCase("Y");
    }

    private static String validateResponse(String input) {
        if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
            return input;
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }

}
