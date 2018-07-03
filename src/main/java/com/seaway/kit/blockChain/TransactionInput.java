package com.seaway.kit.blockChain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionInput {

    private String outputId;
    private TransactionOutput UTXO;

    public TransactionInput(String outputId) {
        this.outputId = outputId;
    }

}
