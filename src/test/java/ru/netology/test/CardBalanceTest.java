package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;


public class CardBalanceTest {

    private final int transfer = 10000;
    private final int transferAboveTheLimit = 98888;


    DashboardPage openAndEntrance() {
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        return open("http://localhost:9999", LoginPage.class).loginWithValidData(authInfo)
                .validVerify(DataHelper.getVerificationCode(authInfo));
    }

    void equalBalanceCard(DashboardPage balance) {
        if (balance.getBalance(String.valueOf(0)) < balance.getBalance(String.valueOf(1))) {
            int transfer = (balance.getBalance(String.valueOf(0)) + balance.getBalance(String.valueOf(1)))
                    / 2 - balance.getBalance(String.valueOf(0));
            balance.selectCard(0).replenish(transfer, DataHelper.getSecondCardNumber());
        }
        if (balance.getBalance(String.valueOf(0)) > balance.getBalance(String.valueOf(1))) {
            int transfer = (balance.getBalance(String.valueOf(0)) + balance.getBalance(String.valueOf(1)))
                    / 2 - balance.getBalance(String.valueOf(0));
            balance.selectCard(1).replenish(transfer, DataHelper.getFirstCardNumber());
        }
    }

    @Test
    void shouldTransferFirstCardToSecondCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(0));
        var secondCardBalance = dashboardPage.getBalance(String.valueOf(1));
        dashboardPage.selectCard(1).replenish(transfer, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance - transfer, dashboardPage.getBalance(String.valueOf(0)));
        assertEquals(secondCardBalance + transfer, dashboardPage.getBalance(String.valueOf(1)));
    }

    @Test
    void shouldTransferFirstCardToFirstCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(0));
        dashboardPage.selectCard(0).replenish(transfer, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalance(String.valueOf(0)));
    }

    @Test
    void shouldTransferSecondCardToFirstCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(0));
        var secondCardBalance = dashboardPage.getBalance(String.valueOf(1));
        dashboardPage.selectCard(0).replenish(transfer, DataHelper.getSecondCardNumber());
        assertEquals(firstCardBalance + transfer, dashboardPage.getBalance(String.valueOf(0)));
        assertEquals(secondCardBalance - transfer, dashboardPage.getBalance(String.valueOf(1)));
    }

    @Test
    void shouldTransferSecondCardToSecondCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(1));
        dashboardPage.selectCard(1).replenish(transfer, DataHelper.getSecondCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalance(String.valueOf(1)));
    }

    @Test
    void shouldGoingBeyondTheAmountOfAvailableFundsOnTheFirstCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(0));
        dashboardPage.selectCard(1).replenish(transferAboveTheLimit, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalance(String.valueOf(0)));
    }

    @Test
    void shouldGoingBeyondTheAmountOfAvailableFundsOnTheSecondCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var secondCardBalance = dashboardPage.getBalance(String.valueOf(1));
        dashboardPage.selectCard(0).replenish(transferAboveTheLimit, DataHelper.getSecondCardNumber());
        assertEquals(secondCardBalance, dashboardPage.getBalance(String.valueOf(1)));
    }

    @Test
    void shouldNotTransferFirstCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(0));
        var secondCardBalance = dashboardPage.getBalance(String.valueOf(1));
        dashboardPage.selectCard(1).cancel(transfer, DataHelper.getFirstCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalance(String.valueOf(0)));
        assertEquals(secondCardBalance, dashboardPage.getBalance(String.valueOf(1)));
    }

    @Test
    void shouldNotTransferSecondCard() {
        DashboardPage dashboardPage = openAndEntrance();
        equalBalanceCard(dashboardPage);
        var firstCardBalance = dashboardPage.getBalance(String.valueOf(0));
        var secondCardBalance = dashboardPage.getBalance(String.valueOf(1));
        dashboardPage.selectCard(0).cancel(transfer, DataHelper.getSecondCardNumber());
        assertEquals(firstCardBalance, dashboardPage.getBalance(String.valueOf(0)));
        assertEquals(secondCardBalance, dashboardPage.getBalance(String.valueOf(1)));
    }

}
