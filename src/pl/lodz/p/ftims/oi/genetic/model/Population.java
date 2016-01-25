package pl.lodz.p.ftims.oi.genetic.model;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public SortedMap<T, Double> byFitness(){
        TreeMap<T, Double> result = new TreeMap<T, Double>(
                (a, b) -> fitnessFunction.apply(a).compareTo(fitnessFunction.apply(b))
        );

        for (T individual : individuals) {
            result.put(individual, fitnessFunction.apply(individual));
        }

//        Map<T, Double> collect = individuals.stream().collect(Collectors.toMap($ -> $, $ -> fitnessFunction.apply($)));
//
//        result.putAll(collect);
        return result;
    }
}
