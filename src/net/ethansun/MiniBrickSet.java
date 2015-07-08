package net.ethansun;

public class MiniBrickSet {

    public MiniBrickSet() {
        // row i, column j, possibilities for 1~9 k, value=1 for possible 0 for
        // not possible
        this.bricks = new byte[3][3][9];
        // row i, column j, positive value for confirmed value 0 for
        // unconfirmed, -1 for not resolvable (not possiblities)
        this.val = new byte[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 9; k++) {
                    this.bricks[i][j][k] = 1;
                }
                this.val[i][j] = 0;
            }
        }
    }

    private byte[][][] bricks;
    private byte[][]   val;

    public byte getBrick(int i, int j, int val) {
        return bricks[i][j][val];
    }

    public byte getVal(int i, int j) {
        return val[i][j];
    }

    public void setBrick(int i, int j, int val, byte poss) {
        this.bricks[i][j][val] = poss;
    }

    public void setVal(int i, int j, byte val) {
        this.val[i][j] = val;
    }

    public byte[] getValArray(int i) {
        return this.val[i];
    }

    public byte[] getBrickPoss(int i, int j) {
        return this.bricks[i][j];
    }

    /**
     * confirm on brick's value
     * 
     * @param i
     * @param j
     * @param _val
     */
    public void confirmOne(int i, int j, byte _val) {
        if (_val < 1 || _val > 9 || i < 0 || i >= 3 || j < 0 || j >= 3) {
            return;
        }
        this.val[i][j] = _val;
    }

    /**
     * given the index, reduce the possibility of the value excluding the
     * current position
     * 
     * @param _i
     * @param _j
     * @param _val
     */
    public void reduceBrick(int _i, int _j, byte _val) {
        if (_val < 1 || _val > 9 || _i < 0 || _i >= 3 || _j < 0 || _j >= 3) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == _i && j == _j) {
                    continue;
                }
                bricks[i][j][_val - 1] = 0;
            }
        }
    }

    /**
     * given the row number, reduce the possibility of the value
     * 
     * @param _i
     * @param _val
     */
    public void reduceRow(int _i, byte _val) {
        if (_val < 1 || _val > 9 || _i < 0 || _i >= 3) {
            return;
        }
        for (int j = 0; j < 3; j++) {
            bricks[_i][j][_val - 1] = 0;
        }
    }

    /**
     * given the column number, reduce the possibility of the value
     * 
     * @param _j
     * @param _val
     */
    public void reduceColumn(int _j, byte _val) {
        if (_val < 1 || _val > 9 || _j < 0 || _j >= 3) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            bricks[i][_j][_val - 1] = 0;
        }
    }

    /**
     * validate the mini brick, check determined, broken case
     * 
     * @return 1 for added determined case number senario, -1 for bad case
     *         occurrence
     */
    public int validateBrick() {
        int changed = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int numPoss = 0;
                byte curPoss = 0;
                for (int val = 0; val < 9; val++) {
                    if (bricks[i][j][val] == 1) {
                        numPoss++;
                        curPoss = (byte) (val + 1);
                    }
                }
                if (numPoss == 1) {
                    // previously undetermined
                    if (this.val[i][j] == 0) {
                        changed = 1;
                    }
                    this.val[i][j] = curPoss;
                }
                if (numPoss == 0) {
                    // bad case!
                    this.val[i][j] = -1;
                    changed = -1;
                }
            }
        }
        return changed;
    }
}
