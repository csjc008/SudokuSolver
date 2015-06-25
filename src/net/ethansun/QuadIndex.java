package net.ethansun;

public class QuadIndex {
    public int mi;
    public int mj;
    public int i;
    public int j;

    public QuadIndex() {
        mi = mj = i = j = 0;
    }

    public QuadIndex(int _mi, int _mj, int _i, int _j) {
        this.mi = _mi;
        this.mj = _mj;
        this.i = _i;
        this.j = _j;
    }
}
