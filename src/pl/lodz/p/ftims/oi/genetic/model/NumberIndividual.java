package pl.lodz.p.ftims.oi.genetic.model;

import pl.lodz.p.ftims.oi.genetic.BitSetMapper;
import pl.lodz.p.ftims.oi.genetic.types.DoubleWrapper;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Kuba on 2016-01-18.
 */
public class NumberIndividual implements Individual<NumberIndividual> {

    private BitSetMapper<DoubleWrapper> bitSetMapper;

    public BitSet getGenes() {
        return bitSetMapper.getMappedBitSet();
    }

    public void setGenes(BitSet genes) {
        bitSetMapper.setMappedBitSet(genes);
    }

    @Override
    public void mutate(double mutationProbability) {
        for (int i = 0; i < bitSetMapper.getMappedBitSet().size(); i++) {
            Random random = new Random();
            if (random.nextDouble() <= mutationProbability) {
                bitSetMapper.getMappedBitSet().flip(i);
            }
        }
    }

    @Override
    public List<Individual> cross(NumberIndividual other) {

        int crossingPoint = new Random().nextInt(Double.SIZE - 1);

        BitSet selfA = bitSetMapper.getMappedBitSet().get(0, crossingPoint);
        BitSet selfB = bitSetMapper.getMappedBitSet().get(crossingPoint, bitSetMapper.getMappedBitSet().size());

        BitSet otherA = other.bitSetMapper.getMappedBitSet().get(0, crossingPoint);
        BitSet otherB = other.bitSetMapper.getMappedBitSet().get(crossingPoint, other.bitSetMapper.getMappedBitSet().size());

        BitSet resultA = new BitSet(Double.SIZE);
        BitSet resultB = new BitSet(Double.SIZE);

        for (int i = 0; i < crossingPoint; i++) {
            resultA.set(i, selfA.get(i));
            resultB.set(i, otherA.get(i));
        }
        for (int i = crossingPoint; i < Double.SIZE; i++) {
            resultA.set(i, otherB.get(i));
            resultB.set(i, selfB.get(i));
        }

        NumberIndividual a = new NumberIndividual();
        a.setGenes(resultA);
        NumberIndividual b = new NumberIndividual();
        b.setGenes(resultB);

        ArrayList<Individual> individuals = new ArrayList<>();
        individuals.add(a);
        individuals.add(b);
        return individuals;
    }

    public Double getDoubleValue() {
        return bitSetMapper.getBitSetMappedValue().getObject();
    }


    public void setBitSetMapper(BitSetMapper<DoubleWrapper> bitSetMapper) {
        this.bitSetMapper = bitSetMapper;
    }

    public BitSetMapper<DoubleWrapper> getBitSetMapper() {
        return bitSetMapper;
    }
}
