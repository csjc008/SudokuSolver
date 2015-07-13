package net.ethansun.test;

import net.ethansun.BrickSet;

public class FuncTest8 {
    public static void main(String[] args) {
        BrickSet bs = new BrickSet();
        bs.initFromFile2("src/net/ethansun/resources/fmt2ProblemInsane2.txt");
        bs.printAllVals2Screen();
        long start = System.currentTimeMillis();
        BrickSet result = bs.solve();
        long end = System.currentTimeMillis();
        System.out.println("solve takes " + (end - start) + " ms");
        if (result != null) {
            result.printAllVals2Screen();
        }
    }
}
