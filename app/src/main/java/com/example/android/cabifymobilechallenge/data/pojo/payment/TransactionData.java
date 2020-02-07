package com.example.android.cabifymobilechallenge.data.pojo.payment;

import java.util.Date;

/**
 * Public class for managing the online payment platform response. This class contains some sample
 * variables and should depend on the actual online payment platforms specifications.
 */
public class TransactionData {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String entity;
    private String transactionId;
    private String authorizationCode;
    private Double amount;
    private String userData;
    private Date transactionDate;
    private TransactionDataResult result;

    /* ****************** */
    /* Public constructor */
    /* ****************** */

    public TransactionData(String entity, String transactionId, String authorizationCode,
                           Double amount, String userData, Date transactionDate,
                           TransactionDataResult result) {
        this.entity = entity;
        this.authorizationCode = authorizationCode;
        this.transactionId = transactionId;
        this.amount = amount;
        this.userData = userData;
        this.transactionDate = transactionDate;
        this.result = result;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionDataResult getResult() {
        return result;
    }

    public void setResult(TransactionDataResult result) {
        this.result = result;
    }
}
