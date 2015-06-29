package net.ethansun.test;

import net.ethansun.BrickSet;

public class FuncTest5 {
    public static void main(String[] args) {
        BrickSet bs = new BrickSet();
        bs.initFromFile("src/net/ethansun/resources/problemVeryHard1.txt");
        bs.printAllVals2Screen();
        long start = System.currentTimeMillis();
        BrickSet result = bs.solve2();
        long end = System.currentTimeMillis();
        System.out.println("solve takes " + (end - start) + " ms");
        if (result != null) {
            result.printAllVals2Screen();
        }
    }
}
