package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private final ElementsCollection cards = $$x("//ul[contains(@class,\"list\")]//div");
    private final String selectCardButton = "[data-test-id=\"action-deposit\"]";


    public DashboardPage() {
        $(byText("Личный кабинет")).shouldBe(visible);
        $(byText("Ваши карты")).shouldBe(visible);
    }

    public int getBalance(String data) {
        String cardValue = cards.get(Integer.parseInt(data)).text();
        return Integer.parseInt(cardValue.substring(cardValue.indexOf(':') + ":".length(),
                cardValue.indexOf(" р")).trim());
    }

    public TransferMoneyPage selectCard(int index) {
        cards.get(index).find(selectCardButton).click();
        return new TransferMoneyPage();
    }
}
