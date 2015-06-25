package net.ethansun.test;

import net.ethansun.BrickSet;

public class FuncTest3 {
    public static void main(String[] args) {
        BrickSet bs = new BrickSet();
        bs.initFromFile("src/net/ethansun/resources/problemVeryHard1.txt");
        bs.printAllVals2Screen();
        BrickSet result = bs.solve();
        System.out.println();
        if (result != null) {
            result.printAllVals2Screen();
        }
    }
}
