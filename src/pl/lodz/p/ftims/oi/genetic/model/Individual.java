package pl.lodz.p.ftims.oi.genetic.model;

import java.util.List;

/**
 * Created by Kuba on 2016-01-18.
 */
public interface Individual<T> {
    void mutate(double mutationProbability);
    List<Individual> cross(T other);


}
