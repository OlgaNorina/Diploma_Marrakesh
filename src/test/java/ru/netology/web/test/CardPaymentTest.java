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
import static org.junit.jupiter.api.Assertions.assertNull;

class CardPaymentTest {
    private CardPayment cardPayment = new CardPayment();

    private final static String statusAPPROVED = "APPROVED";
    private final static String statusDECLINED = "DECLINED";
    private final static String cardAPPROVED = "4444444444444441";
    private final static String cardDECLINED = "4444444444444442";

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
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, SQLHelper.getStatusFromPaymentEntity());
        assertEquals(SQLHelper.getTransactionIdFromPaymentEntity(), SQLHelper.getPaymentIdFromOrderEntity());
    }

    @Test
    void shouldPayApprovedCardNamedInEng() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardDECLINED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(statusDECLINED, SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutCardNumber() throws SQLException {
        val cardNumber = DataHelper.getCardInfo("", DataHelper.getMoth(), DataHelper.getYear(1),
                DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCard();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutMonth() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, "",
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageMonth();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutYear() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                "", DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageYear();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutOwner() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), "", DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageOwner();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedWithoutCVC() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), "");
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedOwnerLength50() {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerLenght(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        assertEquals(DataHelper.getOwnerLenght().substring(50), cardPayment.checkLengthOwner());
    }

    @Test
    void shouldPayApprovedOwnerSimbol() {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), "_________________________________", DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageOwnerSimbol();
    }

    @Test
    void shouldPayApprovedCardNotFull() throws SQLException {
        val cardNumber = DataHelper.getCardInfo("444444444444", DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCard();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedCardYearExpired() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(-1), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageYearExpired();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayApprovedCardExpiredMonth() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, "01",
                DataHelper.getYear(0), DataHelper.getOwnerInEng(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageExpiredMonth();
        assertNull(SQLHelper.getStatusFromPaymentEntity());
    }

    @Test
    void shouldPayNotApprovedCardAfterClose() {
        val cardNumber = DataHelper.getCardInfo("4444444444446547", DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.debitPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.close();
        cardPayment.checkApprovedMessageNotVisible();
    }

    @Test
    void shouldCreditPayApprovedCardNamedInRus() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkApprovedMessage();
        assertEquals(statusAPPROVED, SQLHelper.getStatusFromCreditRequestEntity());
        assertEquals(SQLHelper.getBankIdFromCreditRequestEntity(), SQLHelper.getCreditIdFromOrderEntity());
    }

    @Test
    void shouldCreditInfoForPayDeclinedCard() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardDECLINED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInRus(), DataHelper.getValidCvc());
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkDeclinedMessage();
        assertEquals(statusDECLINED, SQLHelper.getStatusFromCreditRequestEntity());
    }

    @Test
    void shouldCreditInfoForPayInvalidCVC() throws SQLException {
        val cardNumber = DataHelper.getCardInfo(cardAPPROVED, DataHelper.getMoth(),
                DataHelper.getYear(1), DataHelper.getOwnerInEng(), "000");
        cardPayment.creditPurchase();
        cardPayment.pageFieldInfo(cardNumber);
        cardPayment.checkErrorMessageCVC();
        assertNull(SQLHelper.getStatusFromCreditRequestEntity());
    }
}