package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardPayment {
    public SelenideElement buyTour = $$(".button").find(exactText("Купить"));
    public SelenideElement inputCardNumber = $("input[type=\"text\"][placeholder=\"0000 0000 0000 0000\"]");
    public SelenideElement inputMonth = $("input[type=\"text\"][placeholder=\"08\"]");
    public SelenideElement inputYear = $("input[type=\"text\"][placeholder=\"22\"]");
    public SelenideElement inputOwner = $$(".input").find(exactText("Владелец")).$(".input__control");
    public SelenideElement inputCVC = $("input[type=\"text\"][placeholder=\"999\"]");
    public SelenideElement buyContinue = $$(".button").find(exactText("Продолжить"));

    public SelenideElement checkApprovedMessage = $$(".notification__title").find(exactText("Успешно"));
    public SelenideElement checkDeclinedMessage = $$(".notification__title").find(exactText("Ошибка"));
    private SelenideElement checkErrorMessageCard = $$(".input__top").find(exactText("Номер карты")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageMonth = $$(".input__top").find(exactText("Месяц")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageYear = $$(".input__top").find(exactText("Год")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement checkErrorMessageOwner = $$(".input__top").find(exactText("Владелец")).parent().
            $$(".input__sub").find(exactText("Поле обязательно для заполнения"));
    private SelenideElement checkErrorMessageCVC = $$(".input__top").find(exactText("CVC/CVV")).parent().
            $$(".input__sub").find(exactText("Неверный формат"));

    public CardPayment pageForDebitCard(DataHelper.AuthInfo info) {
        buyTour.click();
        inputCardNumber.setValue(info.getCardNumber());
        inputMonth.setValue(info.getMonth());
        inputYear.setValue(info.getYear());
        inputOwner.setValue(info.getOwner());
        inputCVC.setValue(info.getCvc());
        buyContinue.click();
        return new CardPayment();
    }

    public void checkApprovedMessage() {
        checkApprovedMessage.waitUntil(visible, 15000);
    }

    public void checkDeclinedMessage() {
        checkDeclinedMessage.waitUntil(visible, 15000);
    }

    public void checkErrorMessageCard() {
        checkErrorMessageCard.shouldBe(visible);
    }

    public void checkErrorMessageMonth() {
        checkErrorMessageMonth.shouldBe(visible);
    }

    public void checkErrorMessageYear() {
        checkErrorMessageYear.shouldBe(visible);
    }

    public void checkErrorMessageOwner() {
        checkErrorMessageOwner.shouldBe(visible);
    }

    public void checkErrorMessageCVC() {
        checkErrorMessageCVC.shouldBe(visible);
    }

    public String checkLengthOwner(){
       return String.valueOf(inputOwner.getValue());
    }

    public void checkDeclinedMessageLongWait() {
        checkDeclinedMessage.waitUntil(visible, 15000000);
    }
}
