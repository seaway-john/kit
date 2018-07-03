package com.seaway.kit.blockChain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CoinChain {

    public static Map<String, TransactionOutput> UTXOs = new HashMap<>();
    public static float minTransaction = 0.1f;

    private List<Block> blockChain = new ArrayList<>();
    private int difficulty = 5;
    private Wallet walletA;
    private Wallet walletB;
    private Transaction transaction;

}
