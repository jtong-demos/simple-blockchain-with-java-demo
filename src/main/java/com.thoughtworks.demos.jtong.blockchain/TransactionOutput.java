package com.thoughtworks.demos.jtong.blockchain;

import java.security.PublicKey;

public class TransactionOutput {
    private String id;
    private PublicKey recipient;
    private float value;
    private String parentTransactionId;

    public TransactionOutput(PublicKey recipient, float value, String parentTransactionId) {
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(recipient) + Float.toString(value) + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey == recipient;//这个check有点奇怪
    }

    public float getValue() {
        return value;
    }
}