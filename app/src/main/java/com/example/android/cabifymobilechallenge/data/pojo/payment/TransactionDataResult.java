package com.example.android.cabifymobilechallenge.data.pojo.payment;

/**
 * Public class for managing the online payment platform RESULTS. This class contains some sample
 * variables and results and should depend on the actual online payment platforms specifications.
 */
public class TransactionDataResult {

    // Place here all the numeric result codes.
    public static final int CODE_1 = 1;
    public static final int CODE_2 = 2;
    public static final int CODE_3 = 3;
    // ...

    // Place here all the result descriptions.
    public static final String DESC_CODE_1 = "OPERACIÓN ACEPTADA";
    public static final String DESC_CODE_2 = "ERROR: NO HAY FONDOS SUFICIENTES EN LA TARJETA";
    public static final String DESC_CODE_3 = "ERROR: TARJETA EN LISTA NEGRA";
    // ...

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private int code;
    private String description;

    /* ****************** */
    /* Public constructor */
    /* ****************** */

    /**
     * @param code        is the numeric transaction result code. Allowed values are
     *                    {@link TransactionDataResult#CODE_1}, {@link TransactionDataResult#CODE_2},
     *                    {@link TransactionDataResult#CODE_3}...
     * @param description is the description of the transaction result. Allowed values are
     *                    {@link TransactionDataResult#DESC_CODE_1},
     *                    {@link TransactionDataResult#DESC_CODE_2},
     *                    {@link TransactionDataResult#DESC_CODE_3}...
     */
    public TransactionDataResult(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /* ******************* */
    /* Getters and setters */
    /* ******************* */

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
