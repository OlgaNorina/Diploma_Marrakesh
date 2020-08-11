package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        public String cardNumber;
        public String month;
        public String year;
        public String owner;
        public String cvc;
    }

    static Faker fakerRus = new Faker(new Locale("ru"));
    static Faker fakerEng = new Faker(new Locale("en"));
    static Calendar calendar = new GregorianCalendar();

    public static AuthInfo getInfoForPayApprovedCardNamedInRus() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerRus.name().fullName(),
                String.valueOf(fakerRus.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardNameInEng() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerEng.name().fullName(),
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayDeclinedCard() {
        return new AuthInfo("4444444444444442", String.valueOf("0" + calendar.get(Calendar.MONTH) + 1),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerEng.name().fullName(),
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayWithoutCardNumber() {
        return new AuthInfo("", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerEng.name().fullName(),
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardWithoutMonth() {
        return new AuthInfo("4444444444444441", "",
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerEng.name().fullName(),
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardWithoutYear() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                "", fakerEng.name().fullName(), String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardWithoutOwner() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), "",
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardWithoutCVC() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerEng.name().fullName(), "");
    }

    public static AuthInfo getInfoForPayApprovedOwnerLength() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2),
                "лывоаылоплдфаоплофрадлфоаырвплдфырполРАдфаырлыораждывтаимлоистлдЫоващрыфлдваодлЫОВАОРЫЛОПРЛыорвадлфВАЫВАЫП",
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedOwnerSimbol() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2),
                "_________________________________",
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardNotFull() {
        return new AuthInfo("444444444444", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerRus.name().fullName(),
                String.valueOf(fakerRus.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardYearExpired() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) - 1).substring(2), fakerRus.name().fullName(),
                String.valueOf(fakerRus.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayApprovedCardExpiredMonth() {
        return new AuthInfo("4444444444444441", "01", "20", fakerRus.name().fullName(),
                String.valueOf(fakerRus.number().numberBetween(100, 999)));
    }

    public static AuthInfo getInfoForPayNotApprovedCardNamedInRus() {
        return new AuthInfo("4444444444446547", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerRus.name().fullName(),
                String.valueOf(fakerRus.number().numberBetween(100, 999)));
    }

    public static AuthInfo getCreditInfoForPayApprovedCardNamedInRus() {
        return new AuthInfo("4444444444444441", String.valueOf("0" + calendar.get(Calendar.MONTH)),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerRus.name().fullName(),
                String.valueOf(fakerRus.number().numberBetween(100, 999)));
    }

    public static AuthInfo getCreditInfoForPayDeclinedCard() {
        return new AuthInfo("4444444444444442", String.valueOf("0" + calendar.get(Calendar.MONTH) + 1),
                String.valueOf(calendar.get(Calendar.YEAR) + 1).substring(2), fakerEng.name().fullName(),
                String.valueOf(fakerEng.number().numberBetween(100, 999)));
    }
}