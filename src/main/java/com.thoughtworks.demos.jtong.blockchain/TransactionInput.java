package com.thoughtworks.demos.jtong.blockchain;

public class TransactionInput {
    public String getTransactionOutputId() {
        return transactionOutputId;
    }

    private String transactionOutputId;
    private TransactionOutput  UTXO; //Contains the Unspent transaction output

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public void setUTXO(TransactionOutput UTXO) {
        this.UTXO = UTXO;
    }

    public TransactionOutput getUTXO() {
        return UTXO;
    }
}
