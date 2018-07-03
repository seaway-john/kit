package com.seaway.kit.blockChain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SeawayChain {

    private List<Block> blockChain = new ArrayList<>();

    private int chainSize = 10;

    private int difficulty = 5;

    public SeawayChain() {
        for (int i = 0; i < chainSize; i++) {
            String previousHash = "0";
            if (!blockChain.isEmpty()) {
                previousHash = blockChain.get(blockChain.size() - 1).getHash();
            }

            Block block = new Block(previousHash);
            block.mineBlock(difficulty);

            blockChain.add(block);
        }
    }

    public boolean isValid() {
        boolean valid = true;

        for (int i = 0; i < blockChain.size(); i++) {
            Block currentBlock = blockChain.get(i);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                valid = false;
                break;
            }

            if (i > 0) {
                Block previousBlock = blockChain.get(i - 1);
                if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                    valid = false;
                    break;
                }
            }
        }

        return valid;
    }

    public String getChain() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
    }

}
