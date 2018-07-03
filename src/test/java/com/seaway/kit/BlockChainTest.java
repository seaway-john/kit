package com.seaway.kit;

import com.seaway.kit.blockChain.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.Security;

public class BlockChainTest {

    @Test
    public void test() {
        seawayTest();
//        coinChain2();
    }

    private void seawayTest() {
        SeawayChain chain = new SeawayChain();
        System.out.println(chain.isValid());

        String strChain = chain.getChain();
        System.out.println(strChain);
    }

    private void coinChain1() {
        Security.addProvider(new BouncyCastleProvider());

        Wallet walletA = new Wallet();
        System.out.println("Wallet A Public Key: " + AlgorithmUtil.getFromKey(walletA.getPublicKey()));
        System.out.println("Wallet A Private Key: " + AlgorithmUtil.getFromKey(walletA.getPrivateKey()));

        Wallet walletB = new Wallet();
        System.out.println("Wallet B Public Key: " + AlgorithmUtil.getFromKey(walletB.getPublicKey()));
        System.out.println("Wallet B Private Key: " + AlgorithmUtil.getFromKey(walletB.getPrivateKey()));

        float money = 10;
        Transaction transaction = new Transaction(walletA.getPublicKey(), walletB.getPublicKey(), money, null);
        transaction.generateSignature(walletA.getPrivateKey());

        System.out.println("Transaction verify: " + transaction.verifySignature());

    }

    private void coinChain2() {
        Security.addProvider(new BouncyCastleProvider());

        Wallet wallet = new Wallet();
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();

        // wallet send 100 coin to walletA
        Transaction transaction = new Transaction(wallet.getPublicKey(), walletA.getPublicKey(), 100, null);
        transaction.generateSignature(wallet.getPrivateKey());
        transaction.setId("0");

        TransactionOutput output = new TransactionOutput(transaction.getId(), transaction.getReceiver(), transaction.getValue());
        transaction.getOutputs().add(output);

        CoinChain.UTXOs.put(output.getId(), output);

        System.out.println("Creating and Mining Block");
        Block block = new Block("0");
        block.addTransaction(transaction);

    }

}
