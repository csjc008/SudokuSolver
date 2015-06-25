package net.ethansun.test;

import net.ethansun.BrickSet;

public class FuncTest2 {
    public static void main(String[] args) {
        BrickSet bs = new BrickSet();
        bs.initFromFile("src/net/ethansun/resources/problem1.txt");
        bs.printAllVals2Screen();
        BrickSet result = bs.solve();
        System.out.println();
        if (result != null) {
            result.printAllVals2Screen();
        }
    }
}
