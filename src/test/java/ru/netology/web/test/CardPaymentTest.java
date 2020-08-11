package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.CardPayment;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardPaymentTest {
    private CardPayment cardPayment = new CardPayment();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:8080", CardPayment.class);
    }

    @Test
    void shouldPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNamedInRus();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(SQLHelper.getStatusPurchase(), "APPROVED");
    }

    @Test
    void shouldPayApprovedCardNamedInEng() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNameInEng();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkApprovedMessage();
    }

    @Test
    void shouldPayDeclinedCard() {
        val cardNumber = DataHelper.getInfoForPayDeclinedCard();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkDeclinedMessage();
    }

    @Test
    void shouldPayApprovedWithoutCardNumber() {
        val cardNumber = DataHelper.getInfoForPayWithoutCardNumber();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageCard();
    }

    @Test
    void shouldPayApprovedWithoutMonth() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutMonth();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageMonth();
    }

    @Test
    void shouldPayApprovedWithoutYear() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutYear();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageYear();
    }

    @Test
    void shouldPayApprovedWithoutOwner() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutOwner();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageOwner();
    }

    @Test
    void shouldPayApprovedWithoutCVC() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutCVC();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageCVC();
    }

    @Test
    void shouldPayApprovedOwnerLength50() {
        val cardNumber = DataHelper.getInfoForPayApprovedOwnerLength();
        cardPayment.pageForDebitCard(cardNumber);
        assertEquals(DataHelper.getInfoForPayApprovedOwnerLength().owner.substring(50), cardPayment.checkLengthOwner());
    }

    @Test
    void shouldPayApprovedOwnerSimbol() {
        val cardNumber = DataHelper.getInfoForPayApprovedOwnerSimbol();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageOwnerSimbol();
    }

    @Test
    void shouldPayApprovedCardNotFull() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNotFull();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageCard();
    }

    @Test
    void shouldPayApprovedCardYearExpired() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardYearExpired();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageYearExpired();
    }

    @Test
    void shouldPayApprovedCardExpiredMonth() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardExpiredMonth();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkErrorMessageExpiredMonth();
    }

    @Test
    void shouldPayNotApprovedCardAfterCloseDONTWORK() {
        val cardNumber = DataHelper.getInfoForPayNotApprovedCardNamedInRus();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.close();
        cardPayment.checkApprovedMessage();
    }

    @Test
    void shouldCreditPayApprovedCardNamedInRus() {
        val cardNumber = DataHelper.getCreditInfoForPayApprovedCardNamedInRus();
        cardPayment.pageForCreditCard(cardNumber);
        cardPayment.checkApprovedMessage();
    }

    @Test
    void shouldCreditInfoForPayDeclinedCard() {
        val cardNumber = DataHelper.getCreditInfoForPayDeclinedCard();
        cardPayment.pageForCreditCard(cardNumber);
        cardPayment.checkDeclinedMessage();
    }

    @Test
    void shouldCreditInfoForPayApprovedCardLongWaitDONTWORK() {
        val cardNumber = DataHelper.getInfoForPayNotApprovedCardNamedInRus();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkDeclinedMessageLongWait();
    }
}