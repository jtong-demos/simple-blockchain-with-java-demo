package com.thoughtworks.demos.jtong.blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
    public String getHash() {
        return hash;
    }

    public String getPreviouseHash() {
        return previouseHash;
    }

    private String hash;
    private String previouseHash;
    private String merkleRoot;
    private List<Transaction> transactions = new ArrayList<>();
    private long timeStamp;
    private int nonce;

    public Block(String previouseHash) {
        this.previouseHash = previouseHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String result = StringUtil.
                applySha256(previouseHash
                        + Long.toString(timeStamp)
                        +Integer.toString(nonce)
                        + merkleRoot);
        return result;
    }


    public void mineBlock(int difficulty) {
        this.merkleRoot = StringUtil.getMarkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("OldBlock Mined!!! : " + hash);
    }


    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        if(!"0".equals(previouseHash)){
            if (transaction.processTransaction() != true) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to OldBlock");
        return true;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
