package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    @Value
    public static class AuthInfoForPaymentEntity {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id;
    }

    @Value
    public static class AuthInfoForCreditRequestEntity {
        private String id;
        private String bank_id;
        private String credit;
        private String status;
    }

     @Value
    public static class AuthInfoForOrderEntity {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }

    private static final String url = System.getProperty("db.url");
    private static final String user = "app";
    private static final String password = "pass";

    public static String getStatusPurchase() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password);
        ) {
            val selectStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val status = runner.query(conn, selectStatus, new BeanHandler<>(AuthInfoForPaymentEntity.class));
            return status.getStatus();
        }
    }

    public static String getTransactionIdFromPaymentEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password);
        ) {
            val selectSql = "SELECT transaction_id FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val status = runner.query(conn, selectSql, new BeanHandler<>(AuthInfoForPaymentEntity.class));
            return status.getTransaction_id();
        }
    }

    public static AuthInfoForOrderEntity getDataFromOrderEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password);
        ) {
            val selectOrderEntity = "SELECT payment_id, credit_id FROM order_entity  LIMIT 1;";
            return runner.query(conn, selectOrderEntity, new BeanHandler<AuthInfoForOrderEntity>(AuthInfoForOrderEntity.class));
        }
    }

    public static String getStatusPurchaseByCredit() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password);
        ) {
            val selectStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val status = runner.query(conn, selectStatus, new BeanHandler<>(AuthInfoForCreditRequestEntity.class));
            return status.getStatus();
        }
    }

    public static String getBankIdFromPaymentEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password);
        ) {
            val selectStatus = "SELECT bank_id FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val status = runner.query(conn, selectStatus, new BeanHandler<>(AuthInfoForCreditRequestEntity.class));
            return status.getBank_id();
        }
    }

    public static void clearDBTables() throws SQLException {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password);
        ) {
            runner.update(conn, "DELETE  FROM credit_request_entity;");
            runner.update(conn, "DELETE  FROM payment_entity;");
            runner.update(conn, "DELETE  FROM order_entity;");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}

