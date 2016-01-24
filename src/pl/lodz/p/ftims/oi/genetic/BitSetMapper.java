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

    public BitSetMapper(T min, T max, int parts){
        this.min = min;
        this.max = max;
        this.parts = parts;

        singlePartSize = max.subtract(min).divide(parts);

        mappedBitSet = new BitSet(parts);
        tClass = (Class<T>) min.getClass();
    }

    public T getBitSetMappedValue(){
        T tmp = min;
        long bitSetRawValue = convert(mappedBitSet);

        if(tClass.isAssignableFrom(Mulipliable.class)){
            Mulipliable<T> tmp1 = (Mulipliable) tmp;
            tmp = tmp1.multiply(bitSetRawValue);
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
