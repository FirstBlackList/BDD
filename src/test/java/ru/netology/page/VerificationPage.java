package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeVerify = $("[data-test-id=\"code\"] input");
    private final SelenideElement button = $("[data-test-id=\"action-verify\"]");

    public VerificationPage() {
        $(byText("Необходимо подтверждение")).shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeVerify.shouldBe(visible).setValue(verificationCode.getCode());
        button.click();
        return new DashboardPage();
    }
}
