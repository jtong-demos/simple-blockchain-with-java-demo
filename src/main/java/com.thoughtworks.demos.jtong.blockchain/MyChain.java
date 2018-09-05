package com.thoughtworks.demos.jtong.blockchain;

public class MyChain {
    public static void main(String[] args) {
        Block genesisBlock = new Block("Hello, World", "0");
        System.out.println("Hash for Genesis Block: " + genesisBlock.getHash());


        Block secondBlock = new Block("Yo, I am second one", genesisBlock.getHash());
        System.out.println("Hash for Block 2: " + secondBlock.getHash());

        Block thirdBlock = new Block("Hey, I am the third one", secondBlock.getHash());
        System.out.println("Hash for Block 3: " + thirdBlock.getHash());

    }
}
