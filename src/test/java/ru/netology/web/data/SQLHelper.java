package ru.netology.web.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final String url = System.getProperty("db.url");
    private static final String user = "app";
    private static final String password = "pass";

    public static String getStatusFromPaymentEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val selectStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val status = runner.query(conn, selectStatus, new ScalarHandler<String>());
            return status;
        }
    }

     public static String getTransactionIdFromPaymentEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val selectStatus = "SELECT transaction_id FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val transaction_id = runner.query(conn, selectStatus, new ScalarHandler<String>());
            return transaction_id;
        }
    }

     public static String getBankIdFromCreditRequestEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val selectStatus = "SELECT bank_id FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val bank_id = runner.query(conn, selectStatus, new ScalarHandler<String>());
            return bank_id;
        }
    }


    public static String getPaymentIdFromOrderEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val selectOrderEntity = "SELECT payment_id FROM order_entity  LIMIT 1;";
            val payment_id = runner.query(conn, selectOrderEntity, new ScalarHandler<String>());
            return payment_id;
        }
    }

    public static String getCreditIdFromOrderEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val selectOrderEntity = "SELECT credit_id FROM order_entity  LIMIT 1;";
            val credit_id = runner.query(conn, selectOrderEntity, new ScalarHandler<String>());
            return credit_id;
        }
    }

    public static String getStatusFromCreditRequestEntity() throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val selectStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val status = runner.query(conn, selectStatus, new ScalarHandler<String>());
            return status;
        }
    }

    public static void clearDBTables() {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            runner.update(conn, "DELETE  FROM credit_request_entity;");
            runner.update(conn, "DELETE  FROM payment_entity;");
            runner.update(conn, "DELETE  FROM order_entity;");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}

