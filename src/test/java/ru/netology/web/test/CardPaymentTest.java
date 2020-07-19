package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.CardPayment;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardPaymentTest {
    private CardPayment cardPayment = new CardPayment();

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:8080", CardPayment.class);
    }

    @Test
    void shouldPayApprovedCardNamedInRus() {
        val cardNumber = DataHelper.getInfoForPayApprovedCardNamedInRus();
        cardPayment.pageForDebitCard(cardNumber);
        cardPayment.checkApprovedMessage();
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
}