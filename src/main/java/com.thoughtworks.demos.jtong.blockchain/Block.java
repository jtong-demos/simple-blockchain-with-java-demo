package com.thoughtworks.demos.jtong.blockchain;

import java.util.Date;

public class Block {
    public String getHash() {
        return hash;
    }

    public String getPreviouseHash() {
        return previouseHash;
    }

    private String hash;
    private String previouseHash;
    private String data;
    private long timeStamp;
    private int nonce;

    public Block(String data, String previouseHash) {
        this.previouseHash = previouseHash;
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String result = StringUtil.applySha256(previouseHash + Long.toString(timeStamp)+Integer.toString(nonce)
                + data);
        return result;
    }


    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }


//    public boolean addTransaction(Transaction transaction) {
//        if (transaction == null) {
//            return false;
//        }
//
//        if(!previouseHash.equals("0")){
//            if(transaction.())
//        }
//    }
}
