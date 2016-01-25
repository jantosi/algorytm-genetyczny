package pl.lodz.p.ftims.oi.genetic.model;

import pl.lodz.p.ftims.oi.genetic.BitSetMapper;
import pl.lodz.p.ftims.oi.genetic.types.DoubleWrapper;

import java.util.BitSet;
import java.util.List;

/**
 * Created by Kuba on 2016-01-18.
 */
public interface Individual<T> {
    BitSet getGenes();

    void setGenes(BitSet genes);

    void mutate(double mutationProbability);
    List<Individual> cross(T other);


    BitSetMapper<DoubleWrapper> getBitSetMapper();
}
