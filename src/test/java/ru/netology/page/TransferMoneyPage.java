package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {

    public TransferMoneyPage() {
        $(byText("Личный кабинет")).shouldBe(visible);
        $(byText("Пополнение карты")).shouldBe(visible);
    }

    private final SelenideElement amountField = $("[data-test-id=\"amount\"] .input__control");
    private final SelenideElement numberCardField = $("[data-test-id=\"from\"] .input__control");
    private final SelenideElement replenishButton = $("[data-test-id=\"action-transfer\"]");
    private final SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"]");


    public void clearingFormAndEntering(Integer transferSize, DataHelper.CardValue card) {
        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountField.setValue(Integer.toString(transferSize));
        numberCardField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        numberCardField.sendKeys(card.getCardNumber());
    }

    public DashboardPage replenish(Integer transferSize, DataHelper.CardValue card) {
        clearingFormAndEntering(transferSize, card);
        replenishButton.click();
        $(byText("Ваши карты")).shouldBe(visible);
        return new DashboardPage();
    }

    public DashboardPage cancel(Integer transferSize, DataHelper.CardValue card) {
        clearingFormAndEntering(transferSize, card);
        cancelButton.click();
        $(byText("Ваши карты")).shouldBe(visible);
        return new DashboardPage();
    }
}
