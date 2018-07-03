package com.seaway.kit.blockChain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class Wallet {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private Map<String, TransactionOutput> UTXOs = new HashMap<>();

    public Wallet() {
        generatePair();
    }

    private void generatePair() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyGen.initialize(ecSpec, random);

            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            log.error("Exception in generatePair, reason {}", e.getMessage());
        }
    }

    public Transaction sendFunds(PublicKey receiver, float value) {
        if (getBalance() < value) {
            System.out.println("Not Enough funds to send transaction.");
            return null;
        }

        List<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (TransactionOutput output : UTXOs.values()) {
            if (total > value) {
                break;
            }
            total += output.getValue();
            inputs.add(new TransactionInput(output.getId()));
        }

        Transaction transaction = new Transaction(publicKey, receiver, value, inputs);
        transaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            UTXOs.remove(input.getOutputId());
        }

        return transaction;
    }

    private float getBalance() {
        float total = 0;

        for (TransactionOutput output : UTXOs.values()) {
            if (output.isOwner(publicKey)) {
                UTXOs.put(output.getId(), output);
                total += output.getValue();
            }
        }

        return total;
    }

}
