package com.thoughtworks.demos.jtong.blockchain;

import java.util.Date;

public class Block {
    public String getHash() {
        return hash;
    }

    private String hash;
    private String previouseHash;
    private String data;
    private long timeStamp;

    public Block(String data, String previouseHash) {
        this.previouseHash = previouseHash;
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    private String calculateHash() {
        String result = StringUtil.applySha256(previouseHash + Long.toString(timeStamp) + data);
        return result;
    }


}
