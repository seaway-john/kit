package com.seaway.kit.blockChain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class Block {

    private String hash;
    private String previousHash;
    private String merkleRoot;
    private List<Transaction> transactions = new ArrayList<>();
    private long timestamp;
    // 随机数
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String plainText = previousHash + timestamp + nonce + merkleRoot;

        return AlgorithmUtil.applySha256(plainText);
    }

    // 挖矿
    public void mineBlock(int difficulty) {
        merkleRoot = AlgorithmUtil.getMerkleRoot(transactions);
        String target = AlgorithmUtil.getDificultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block mined, hash: " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        if (previousHash != "0") {
            if (!transaction.processTransaction()) {
                System.err.println("Failed to add transaction");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Success to add transaction");

        return true;
    }

}
