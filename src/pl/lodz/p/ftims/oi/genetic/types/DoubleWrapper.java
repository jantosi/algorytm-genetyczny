package pl.lodz.p.ftims.oi.genetic.types;

/**
 * Created by Kuba on 2016-01-24.
 */
public class DoubleWrapper implements Summable<DoubleWrapper>, Subtractable<DoubleWrapper>, Mulipliable<DoubleWrapper>, Dividable<DoubleWrapper>{
    private Double object;

    public DoubleWrapper(Double object) {
        this.object = object;
    }

    public Double getObject() {
        return object;
    }

    public void setObject(Double object) {
        this.object = object;
    }

    @Override
    public DoubleWrapper divide(int divisor) {
        return new DoubleWrapper(object / divisor);
    }

    @Override
    public DoubleWrapper multiply(long a) {
        return new DoubleWrapper(object * a);
    }

    @Override
    public DoubleWrapper subtract(DoubleWrapper b) {
        return new DoubleWrapper(object - b.getObject());
    }

    @Override
    public DoubleWrapper sum(DoubleWrapper b) {
        return new DoubleWrapper(object + b.getObject());
    }
}
