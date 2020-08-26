package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
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

    @AfterEach
    void clearAll() throws SQLException {
        SQLHelper.clearDBTables();
    }

    @Test
    void shouldPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNamedInRus();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), "APPROVED");
        assertEquals(SQLHelper.getTransactionIdFromPaymentEntity(), SQLHelper.getPaymentIdFromOrderEntity());
    }

    @Test
    void shouldPayApprovedCardNamedInEng() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNameInEng();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), "APPROVED");
    }

    @Test
    void shouldPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayDeclinedCard();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), "DECLINED");
    }

    @Test
    void shouldPayApprovedWithoutCardNumber() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayWithoutCardNumber();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCard();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedWithoutMonth() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutMonth();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageMonth();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedWithoutYear() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutYear();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageYear();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedWithoutOwner() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutOwner();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageOwner();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedWithoutCVC() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardWithoutCVC();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedOwnerLength50() {
        val cardNumber = DataHelper.getInfoForPayApprovedOwnerLength();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        assertEquals(DataHelper.getInfoForPayApprovedOwnerLength().owner.substring(50), cardPayment.checkLengthOwner());
    }

    @Test
    void shouldPayApprovedOwnerSimbol() {
        val cardNumber = DataHelper.getInfoForPayApprovedOwnerSimbol();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageOwnerSimbol();
    }

    @Test
    void shouldPayApprovedCardNotFull() throws SQLException{
        val cardNumber = DataHelper.getInfoForPayApprovedCardNotFull();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCard();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedCardYearExpired() throws SQLException{
        val cardNumber = DataHelper.getInfoForPayApprovedCardYearExpired();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageYearExpired();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayApprovedCardExpiredMonth() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardExpiredMonth();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageExpiredMonth();
        assertEquals(SQLHelper.getStatusFromPaymentEntity(), null);
    }

    @Test
    void shouldPayNotApprovedCardAfterClose() {
        val cardNumber = DataHelper.getInfoForPayNotApprovedCardNamedInRus();
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.close();
        cardPayment.checkApprovedMessageNotVisible();
    }

    @Test
    void shouldCreditPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNamedInRus();
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(SQLHelper.getStatusFromCreditRequestEntity(), "APPROVED");
        assertEquals(SQLHelper.getBankIdFromCreditRequestEntity(), SQLHelper.getCreditIdFromOrderEntity());
    }

    @Test
    void shouldCreditInfoForPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNameInEng();
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(SQLHelper.getStatusFromCreditRequestEntity(), "DECLINED");
    }

    @Test
    void shouldCreditInfoForPayInvalidCVC() throws SQLException {
        val cardNumber = DataHelper.getInfoForPayApprovedCardInvalidCVC();
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertEquals(SQLHelper.getStatusFromCreditRequestEntity(), null);
    }
}