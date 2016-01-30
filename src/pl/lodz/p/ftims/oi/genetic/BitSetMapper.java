package pl.lodz.p.ftims.oi.genetic;

import pl.lodz.p.ftims.oi.genetic.types.Dividable;
import pl.lodz.p.ftims.oi.genetic.types.Mulipliable;
import pl.lodz.p.ftims.oi.genetic.types.Subtractable;
import pl.lodz.p.ftims.oi.genetic.types.Summable;

import java.util.BitSet;

/**
 * Created by Kuba on 2016-01-24.
 */
public class BitSetMapper<T extends Subtractable<T> & Dividable<T> & Summable<T>> {
    private final Class<T> tClass;

    private final T min;
    private final T max;

    private final T singlePartSize;
    private final int parts;
    private BitSet mappedBitSet;
    private long longValue;

    private int bits;

    public BitSetMapper(T min, T max, int parts){
        this.min = min;
        this.max = max;
        this.parts = parts;

        singlePartSize = max.subtract(min).divide(parts);

        int bits=1;
        int t=1;
        while(t<parts){
            t=t<<1;
            bits++;
        }
        this.bits = bits;
        mappedBitSet = new BitSet(bits);
        tClass = (Class<T>) min.getClass();
    }

    public T getBitSetMappedValue(){
        T tmp = min;
        long bitSetRawValue = convert(mappedBitSet);

        if(Mulipliable.class.isAssignableFrom(tClass)){
            Mulipliable<T> sps = (Mulipliable) singlePartSize;
            T multiplied = sps.multiply(bitSetRawValue);
            tmp = tmp.sum(multiplied);
        }
        else{
            for (long i = 0; i < bitSetRawValue; i++) {
                tmp = tmp.sum(singlePartSize);
            }
        }

        return tmp;
    }

    public BitSet getMappedBitSet() {
        return mappedBitSet;
    }

    public int getParts() {
        return parts;
    }

    public T getSinglePartSize() {
        return singlePartSize;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public int getBits() {
        return bits;
    }

    private static long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    public void setMappedBitSet(BitSet mappedBitSet) {
        this.mappedBitSet = mappedBitSet;
    }

    public void setLongValue(long value) {

        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                mappedBitSet.set(index);
            }
            ++index;
            value = value >>> 1;
        }

    }
}
