package net.ethansun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class BrickSet {

    public BrickSet() {
        this.bricks = new MiniBrickSet[3][];
        for (int i = 0; i < 3; i++) {
            this.bricks[i] = new MiniBrickSet[3];
            for (int j = 0; j < 3; j++) {
                this.bricks[i][j] = new MiniBrickSet();
            }
        }
    }

    public MiniBrickSet[][] getBricks() {
        return bricks;
    }

    private MiniBrickSet[][] bricks;

    public void confirmOne(int mi, int mj, int _i, int _j, byte _val) {
        if (mi < 0 || mi >= 3 || mj < 0 || mj >= 3) {
            return;
        }
        this.bricks[mi][mj].confirmOne(_i, _j, _val);
    }

    /**
     * read file format 1
     * 
     * @param fileName
     * @return
     */
    public boolean initFromFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String lineContent = null;
            while ((lineContent = reader.readLine()) != null) {
                lineContent = lineContent.replaceAll("\\t", " ");
                lineContent = lineContent.replaceAll("  ", " ");
                lineContent = lineContent.trim();
                if ("".equals(lineContent)) {
                    continue;
                }
                String[] eles = lineContent.split(" ");
                if (eles.length != 5) {
                    continue;
                }
                this.confirmOne(Integer.valueOf(eles[0]), Integer.valueOf(eles[1]), Integer.valueOf(eles[2]),
                        Integer.valueOf(eles[3]), Byte.valueOf(eles[4]));
            }
            reader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * read file format 2
     * 
     * @param fileName
     * @return
     */
    public boolean initFromFile2(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String lineContent = null;
            int _i = 0;
            while ((lineContent = reader.readLine()) != null) {
                lineContent = lineContent.replaceAll("\\t", " ");
                lineContent = lineContent.replaceAll("  ", " ");
                lineContent = lineContent.trim();
                if ("".equals(lineContent)) {
                    continue;
                }
                String[] eles = lineContent.split(" ");
                if (eles.length != 9) {
                    _i++;
                    continue;
                }
                for (int _j = 0; _j < 9; _j++) {
                    int mi = _i / 3;
                    int mj = _j / 3;
                    int i = _i - 3 * mi;
                    int j = _j - 3 * mj;
                    this.confirmOne(mi, mj, i, j, Byte.valueOf(eles[_j]));
                }
                _i++;
            }
            reader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * reduce the possibilities of all bricks <br/>
     * reduce Mini Brick, reduce Column, and reduce Row
     * 
     * @return
     */
    private int reducePoss() {
        for (int mi = 0; mi < 3; mi++) {
            for (int mj = 0; mj < 3; mj++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        byte val = this.bricks[mi][mj].getVal(i, j);
                        if (val > 0) {
                            this.bricks[mi][mj].reduceBrick(i, j, val);
                            for (int _i = 0; _i < 3; _i++) {
                                if (_i != mi) {
                                    this.bricks[_i][mj].reduceColumn(j, val);
                                }
                            }
                            for (int _j = 0; _j < 3; _j++) {
                                if (_j != mj) {
                                    this.bricks[mi][_j].reduceRow(i, val);
                                }
                            }
                        }
                    }
                }
            }
        }
        int changed = 0;
        for (int mi = 0; mi < 3; mi++) {
            for (int mj = 0; mj < 3; mj++) {
                int tmp = this.bricks[mi][mj].validateBrick();
                if (tmp == 1) {
                    changed = 1;
                }
                if (tmp == -1) {
                    return -1;
                }
            }
        }
        return changed;
    }

    /**
     * 
     * @return 1 means success,-1 means matrix is broken
     */
    public int reduceTillNoChange() {
        while (true) {
            int tmp = this.reducePoss();
            if (tmp == 0) {
                return 1;
            }
            if (tmp == -1) {
                return -1;
            }
        }
    }

    /**
     * use iteration
     * 
     * @return
     */
    public BrickSet solve() {
        this.reduceTillNoChange();
        int status = this.status();
        if (status == 1) {
            return this;
        } else if (status == -1) {
            return null;
        }
        // System.out.println();
        // this.printAllVals2Screen();
        // pick one undetermined brick
        QuadIndex qi = this.pickOneUndetermined();
        // for all the possibilities in the brick(_mi,_mj,_i,_j), solve
        if (qi != null) {
            for (int val = 0; val < 9; val++) {
                if (this.bricks[qi.mi][qi.mj].getBrick(qi.i, qi.j, val) == 1) {
                    BrickSet subBs = this.copy();
                    subBs.bricks[qi.mi][qi.mj].setVal(qi.i, qi.j, (byte) (val + 1));
                    BrickSet ret = subBs.solve();
                    if (ret != null) {
                        return ret;
                    }
                }
            }
        } else {
            System.out.println("wrong!!");
        }
        return null;
    }

    /**
     * use stack
     * 
     * @return
     */
    public BrickSet solve2() {
        Stack<BrickSet> bsStack = new Stack<BrickSet>();
        bsStack.push(this);
        while (!bsStack.isEmpty()) {
            BrickSet bs = bsStack.pop();
            bs.reduceTillNoChange();
            int bsStatus = bs.status();
            if (bsStatus == 1) {
                // done
                return bs;
            } else if (bsStatus == 0) {
                // undetermined
                QuadIndex qi = bs.pickOneUndetermined();
                if (qi != null) {
                    for (int val = 0; val < 9; val++) {
                        if (bs.bricks[qi.mi][qi.mj].getBrick(qi.i, qi.j, val) == 1) {
                            BrickSet subBs = bs.copy();
                            subBs.bricks[qi.mi][qi.mj].setVal(qi.i, qi.j, (byte) (val + 1));
                            bsStack.push(subBs);
                        }
                    }
                } else {
                    System.out.println("wrong!!");
                }
            } else if (bsStatus == -1) {
                // System.out.println("wrong path:");
                // bs.printAllVals2Screen();
            }
        }
        return null;
    }

    /**
     * sequential pick on undetermined brick
     * 
     * @return brick quad index
     */
    private QuadIndex pickOneUndetermined() {
        for (int _mi = 0; _mi < 3; _mi++) {
            for (int _mj = 0; _mj < 3; _mj++) {
                for (int _i = 0; _i < 3; _i++) {
                    for (int _j = 0; _j < 3; _j++) {
                        if (this.bricks[_mi][_mj].getVal(_i, _j) == 0) {
                            return new QuadIndex(_mi, _mj, _i, _j);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * copy values of the BrickSet and return a new allocated BrickSet
     * 
     * @return
     */
    public BrickSet copy() {
        BrickSet cp = new BrickSet();
        for (int mi = 0; mi < 3; mi++) {
            for (int mj = 0; mj < 3; mj++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        // for (int val = 0; val < 9; val++) {
                        // cp.bricks[mi][mj].setBrick(i, j, val,
                        // this.bricks[mi][mj].getBrick(i, j, val));
                        // }
                        System.arraycopy(this.bricks[mi][mj].getBrickPoss(i, j), 0,
                                cp.bricks[mi][mj].getBrickPoss(i, j), 0, 9);
                        // cp.bricks[mi][mj].setVal(i, j,
                        // this.bricks[mi][mj].getVal(i, j));
                    }
                    System.arraycopy(this.bricks[mi][mj].getValArray(i), 0, cp.bricks[mi][mj].getValArray(i), 0, 3);
                }
            }
        }
        return cp;
    }

    /**
     * status of the current BrickSet
     * 
     * @return 1 means success,-1 means matrix is broken, 0 means undetermined
     */
    public int status() {
        boolean undetermined = false;
        for (int mi = 0; mi < 3; mi++) {
            for (int mj = 0; mj < 3; mj++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        byte val = this.bricks[mi][mj].getVal(i, j);
                        if (val == 0) {
                            undetermined = true;
                        } else if (val < 0) {
                            return -1;
                        }
                    }
                }
            }
        }
        if (undetermined) {
            return 0;
        } else {
            return 1;
        }
    }

    public void printAllVals2Screen() {
        for (int _i = 0; _i < 9; _i++) {
            for (int _j = 0; _j < 9; _j++) {
                int mi = _i / 3;
                int mj = _j / 3;
                int i = _i - mi * 3;
                int j = _j - mj * 3;
                System.out.print("" + this.bricks[mi][mj].getVal(i, j) + " ");
            }
            System.out.println();
        }
    }

}
