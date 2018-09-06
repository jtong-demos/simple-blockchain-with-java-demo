package com.thoughtworks.demos.jtong.blockchain;

import org.omg.PortableInterceptor.SUCCESSFUL;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private PublicKey sender;
    private PublicKey recipient;
    private float value;
    private byte[] signature;

    private List<TransactionInput> inputs = new ArrayList<>();
    private List<TransactionOutput> outputs = new ArrayList<>();

    private static int sequence = 0;

    public Transaction(PublicKey sender, PublicKey recipient, float value, List<TransactionInput> inputs) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash(){
        sequence++;
        return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient)
                + Float.toString(this.value) + sequence);
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }


    public boolean processTransaction(){
        if (verifySignature() == true) {
            return false;
        }

        //gather transaction inputs (Make sure they are unspent):
        for (TransactionInput i : inputs) {
            i.setUTXO(MyChain.UTXOs.get(i.getTransactionOutputId()));
        }

        if(getInputsValue()< MyChain.minimumTransaction){
            System.out.println("#Transaction Inputs too small: " + getInputsValue());
            return false;
        }

        //TODO
        
        return true;
    }

    public float getInputsValue(){
        float total = 0;
        for (TransactionInput i : inputs) {
            if(i.getUTXO() == null) continue;
            total+=i.getUTXO().getValue();
        }
        return total;
    }
}
