package com.thoughtworks.demos.jtong.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;


public class MyChain {

    private static ArrayList<Block> blockChain = new ArrayList<>();
    public static Map<String, TransactionOutput> UTXOs = new HashMap<>();
    public static float minimumTransaction = 0.1f;
    private static int difficulty = 5;
    private static Wallet walletA;
    private static Wallet walletB;


    public static void main(String[] args) {
        //Setup Bouncey castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();

        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.getPrivateKey()));
        System.out.println(StringUtil.getStringFromKey(walletA.getPublicKey()));

        Transaction transaction = new Transaction(walletA.getPublicKey(), walletB.getPublicKey(), 5, null);
        transaction.generateSignature(walletA.getPrivateKey());


        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());
    }

    private static void blockMiningWithDifficulty() {
        blockChain.add(new Block("Hello, World", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockChain.get(0).mineBlock(difficulty);

        blockChain.add(new Block("Yo, I am second one",blockChain.get(blockChain.size()-1).getHash()));
        System.out.println("Trying to Mine block 2... ");
        blockChain.get(1).mineBlock(difficulty);

        blockChain.add(new Block("Hey, I am the third one",blockChain.get(blockChain.size()-1).getHash()));
        System.out.println("Trying to Mine block 3... ");
        blockChain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }

    private static void blockChainWithoutMining() {
        blockChain.add(new Block("Hello, World", "0"));
        blockChain.add(new Block("Yo, I am second one", blockChain.get(blockChain.size() - 1).getHash()));
        blockChain.add(new Block("Hey, I am the third one", blockChain.get(blockChain.size() - 1).getHash()));

        String blockChainString = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);

        System.out.println("Block chain is: " + blockChainString);
    }

    public static Boolean isChainValid() {

        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i - 1);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current Hash not equal");
                return false;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviouseHash())) {
                System.out.println("Previous Hash not equal");
                return false;
            }
        }
        return true;
    }

    private static void tutorialStep1() {
        Block genesisBlock = new Block("Hello, World", "0");
        System.out.println("Hash for Genesis Block: " + genesisBlock.getHash());


        Block secondBlock = new Block("Yo, I am second one", genesisBlock.getHash());
        System.out.println("Hash for Block 2: " + secondBlock.getHash());

        Block thirdBlock = new Block("Hey, I am the third one", secondBlock.getHash());
        System.out.println("Hash for Block 3: " + thirdBlock.getHash());
    }
}
