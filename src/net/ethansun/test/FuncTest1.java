package net.ethansun.test;

import net.ethansun.BrickSet;

public class FuncTest1 {
    public static void main(String[] args) {
        BrickSet bs = new BrickSet();
        bs.initFromFile("src/net/ethansun/resources/problem1.txt");
        bs.printAllVals2Screen();
        bs.reduceTillNoChange();
        System.out.println();
        bs.printAllVals2Screen();
    }
}
