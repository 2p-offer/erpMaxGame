package com.erp.gameserver.test.itemhitlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入A的初始层数、A可被敲打次数、B的数量、B的可敲打层数、B的初始层数
        System.out.print("请输入A的初始层数：");
        int aInitialLevel = scanner.nextInt();
        System.out.print("请输入A可被敲打次数：");
        int aMaxHits = scanner.nextInt();
        System.out.print("请输入B的数量：");
        int bCount = scanner.nextInt();
        System.out.print("请输入B的可敲打层数：");
        int bMaxHits = scanner.nextInt();
        System.out.print("请输入B的初始层数：");
        int bInitialLevel = scanner.nextInt();

        // 初始化A和B
        ItemA itemA = new ItemA(aInitialLevel, aMaxHits);
        List<ItemB> itemBs = new ArrayList<>();
        for (int i = 0; i < bCount; i++) {
            itemBs.add(new ItemB(bInitialLevel, bMaxHits));
        }

        // 计算所有可能的敲打方式
        List<Integer> results = new ArrayList<>();
        calculateMaximumALevel(itemA, itemBs, aMaxHits, 0, results);

        // 找到最大的A层数
        int maxALevel = results.stream().mapToInt(Integer::intValue).max().orElse(0);
        System.out.println("最终A的最大层数为：" + maxALevel);
    }

    private static void calculateMaximumALevel(ItemA itemA, List<ItemB> itemBs, int aMaxHits, int index, List<Integer> results) {
        if (index == aMaxHits) {
            results.add(itemA.getCurrentLevel());
            return;
        }

        // 尝试敲打A
        itemA.hit();
        int currentALevel = itemA.getCurrentLevel(); // 记录当前A的层数
        itemA.hitB(itemBs); // 更新所有未损坏的B道具的层数
        for (ItemB itemB : itemBs) {
            if (!itemB.isBroken()) {
                itemB.hit(itemA);
                calculateMaximumALevel(itemA, itemBs, aMaxHits, index + 1, results);
                itemB.undoHit(itemA);
            }
        }
        // 恢复A的层数
        while (itemA.getCurrentLevel() > currentALevel) {
            itemA.undoHit();
        }

        // 不敲打A，直接敲打B
        for (ItemB itemB : itemBs) {
            if (!itemB.isBroken()) {
                itemB.hit(itemA);
                calculateMaximumALevel(itemA, itemBs, aMaxHits, index + 1, results);
                itemB.undoHit(itemA);
            }
        }
    }
}

class ItemA {
    private int currentLevel;
    private final int maxHits;
    private int hits;

    public ItemA(int initialLevel, int maxHits) {
        this.currentLevel = initialLevel;
        this.maxHits = maxHits;
        this.hits = 0;
    }

    public void hit() {
        if (hits < maxHits) {
            hits++;
        }
    }

    public void undoHit() {
        if (hits > 0) {
            hits--;
        }
    }

    public void hitB(List<ItemB> itemBs) {
        for (ItemB itemB : itemBs) {
            if (!itemB.isBroken()) {
                itemB.increaseLevel(currentLevel);
            }
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}

class ItemB {
    private int currentLevel;
    private final int maxHits;
    private int hits;

    public ItemB(int initialLevel, int maxHits) {
        this.currentLevel = initialLevel;
        this.maxHits = maxHits;
        this.hits = 0;
    }

    public void hit(ItemA itemA) {
        if (hits < maxHits) {
            hits++;
            itemA.hit();
        }
    }

    public void undoHit(ItemA itemA) {
        if (hits > 0) {
            hits--;
            itemA.undoHit();
        }
    }

    public boolean isBroken() {
        return hits >= maxHits;
    }

    public void increaseLevel(int amount) {
        currentLevel += amount;
    }
}
