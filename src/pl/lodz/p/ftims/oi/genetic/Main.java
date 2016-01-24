package pl.lodz.p.ftims.oi.genetic;

import pl.lodz.p.ftims.oi.genetic.model.Individual;
import pl.lodz.p.ftims.oi.genetic.model.NumberIndividual;
import pl.lodz.p.ftims.oi.genetic.model.Population;
import pl.lodz.p.ftims.oi.genetic.types.DoubleWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.function.Function;

public class Main {

    public static final double MIN_RANGE = 0.5;
    public static final double MAX_RANGE = 2.5;
    public static final int RANGES = 16384;
    public static int POP_SIZE = 64;

    public static void main(String[] args) {
        Random random = new Random();

        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < POP_SIZE; i++) {
            double v = random.nextDouble() * MAX_RANGE - MIN_RANGE + MIN_RANGE;
            NumberIndividual numberIndividual = new NumberIndividual();
            BitSetMapper<DoubleWrapper> mapper = new BitSetMapper<>(
                    new DoubleWrapper(MIN_RANGE),
                    new DoubleWrapper(MAX_RANGE),
                    RANGES
            );
            numberIndividual.setBitSetMapper(mapper);
            mapper.setLongValue((long)i);
            numberIndividual.setGenes(mapper.getMappedBitSet());
            individuals.add(numberIndividual);
        }

        Function<Individual, Double> fitness = $ -> {
            Double x = ((NumberIndividual) $).getDoubleValue();
            return (Math.exp(x)*Math.sin(10*Math.PI*x)+1)/x;
        };


        Population<Individual> population = new Population<Individual>(individuals, fitness);
        SortedMap<Individual, Double> individualDoubleSortedMap = population.byFitness();
        population.toString();

    }
}
