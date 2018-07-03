package com.seaway.kit.blockChain;

import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;

@Getter
@Setter
public class TransactionOutput {

    private String id;
    private String transactionId;
    private PublicKey receiver;
    private float value;

    public TransactionOutput(String transactionId, PublicKey receiver, float value) {
        this.transactionId = transactionId;
        this.receiver = receiver;
        this.value = value;

        String plainText = AlgorithmUtil.getFromKey(receiver) + value + transactionId;
        this.id = AlgorithmUtil.applySha256(plainText);
    }

    public boolean isOwner(PublicKey publicKey) {
        return publicKey == receiver;
    }

}
