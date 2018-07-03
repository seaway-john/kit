package com.seaway.kit.blockChain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
// 交易
public class Transaction {

    private String id;
    private PublicKey sender;
    private PublicKey receiver;
    private float value;
    private byte[] signature;
    private List<TransactionInput> inputs = new ArrayList<>();
    private List<TransactionOutput> outputs = new ArrayList<>();
    private static int sequence;

    public Transaction(PublicKey sender, PublicKey receiver, float value, List<TransactionInput> inputs) {
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
        this.inputs = inputs;
    }

    public void generateSignature(PrivateKey privateKey) {
        String plainText = AlgorithmUtil.getFromKey(sender) + AlgorithmUtil.getFromKey(receiver) + value;
        signature = AlgorithmUtil.applyECDSASignature(privateKey, plainText);
    }

    public boolean verifySignature() {
        String plainText = AlgorithmUtil.getFromKey(sender) + AlgorithmUtil.getFromKey(receiver) + value;
        return AlgorithmUtil.verifyECDSASignature(sender, plainText, signature);
    }

    public boolean processTransaction() {
        if (!verifySignature()) {
            System.err.println("Failed to verify signature");
            return false;
        }

        for (TransactionInput input : inputs) {
            input.setUTXO(CoinChain.UTXOs.get(input.getOutputId()));
        }

        float inputsValue = getInputsValue();
        if (inputsValue < CoinChain.minTransaction) {
            System.err.println("Can't less than min transaction");
            return false;
        }

        float leftOver = inputsValue - value;
        String transactionId = calculateHash();
        outputs.add(new TransactionOutput(transactionId, receiver, value));
        outputs.add(new TransactionOutput(transactionId, sender, leftOver));

        for (TransactionOutput output : outputs) {
            CoinChain.UTXOs.put(output.getId(), output);
        }

        for (TransactionInput input : inputs) {
            if (input.getUTXO() == null) {
                continue;
            }

            CoinChain.UTXOs.remove(input.getUTXO().getId());
        }

        return true;
    }

    public float getInputsValue() {
        float total = 0;
        for (TransactionInput input : inputs) {
            if (input.getUTXO() == null) {
                continue;
            }
            total += input.getUTXO().getValue();
        }
        return total;
    }

    public float getOutputsValue() {
        float total = 0;
        for (TransactionOutput output : outputs) {
            total += output.getValue();
        }
        return total;
    }

    private String calculateHash() {
        sequence++;

        String plainText = AlgorithmUtil.getFromKey(sender) + AlgorithmUtil.getFromKey(receiver) + value + sequence;

        return AlgorithmUtil.applySha256(plainText);
    }

}
