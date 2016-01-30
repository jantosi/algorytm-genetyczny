package pl.lodz.p.ftims.oi.genetic.model;

import pl.lodz.p.ftims.oi.genetic.types.DoubleWrapper;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Kuba on 2016-01-18.
 */
public class Population<T extends Individual> {

    private List<T> individuals;

    private Function<T, Double> fitnessFunction;

    public Population(List<T> individuals, Function<T, Double> fitnessFunction) {
        this.individuals = individuals;
        this.fitnessFunction = fitnessFunction;
    }

    public List<T> byFitness(){
        individuals.sort((a, b) -> fitnessFunction.apply(a).compareTo(fitnessFunction.apply(b)));
        return individuals;
    }
}
