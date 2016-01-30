package pl.lodz.p.ftims.oi.genetic;

import java.util.BitSet;

/**
 * Created by Kuba on 2016-01-30.
 */
public class Utils {
    public static BitSet shiftLeft(BitSet other, int length, int bitsToShift){
        BitSet r = new BitSet(length);
        if(bitsToShift==0){
            for (int i = 0; i < length; i++) {
                r.set(i, other.get(i));
            }
            return r;
        }

        r.set(0,bitsToShift-1,false);
        for (int i = bitsToShift; i < length; i++) {
            r.set(i,other.get(i-bitsToShift));
        }

        return r;
    }
}
