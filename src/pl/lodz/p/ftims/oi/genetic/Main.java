package pl.lodz.p.ftims.oi.genetic;

import pl.lodz.p.ftims.oi.genetic.model.Individual;
import pl.lodz.p.ftims.oi.genetic.model.NumberIndividual;
import pl.lodz.p.ftims.oi.genetic.model.Population;
import pl.lodz.p.ftims.oi.genetic.types.DoubleWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {

    public static final double MIN_RANGE = 0.5;
    public static final double MAX_RANGE = 2.5;

    public static final int RANGES = 1<<20;

    private static final double BEST_FITTING_PROB = 0.35;
    private static final double CROSS_PROB = 0.1;
    private static final double SINGLE_GENE_MUTATION_PROB = 0.5;
    public static final int GENERATIONS = 100;
    public static int POP_SIZE = 8;

    public static void main(String[] args) {
        Random random = new Random();

        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < POP_SIZE; i++) {
            double v = random.nextDouble() * MAX_RANGE - MIN_RANGE + MIN_RANGE;
            NumberIndividual numberIndividual = new NumberIndividual(MIN_RANGE, MAX_RANGE, RANGES);
            BitSetMapper<DoubleWrapper> mapper = numberIndividual.getBitSetMapper();
            mapper.setLongValue((long)i*RANGES/POP_SIZE);
            numberIndividual.setGenes(mapper.getMappedBitSet());
            individuals.add(numberIndividual);
        }

        Function<Individual, Double> fitness = $ -> {
            Double x = ((NumberIndividual) $).getDoubleValue();
            return maximisedFunc(x);
        };


        Population<Individual> population = new Population<Individual>(individuals, fitness);

        List<Individual> nextPopulationIndividuals = individuals;
        Population<Individual> newPop = population;

        for(int i = 0; i< GENERATIONS; i++) {
            nextPopulationIndividuals = getIndividuals(random, nextPopulationIndividuals, newPop);
            newPop = new Population<>(nextPopulationIndividuals, fitness);

            newPop.byFitness();

            List<Double> collect = nextPopulationIndividuals.stream().map($ -> ((DoubleWrapper) $.getBitSetMapper().getBitSetMappedValue()).getObject()).sorted().collect(Collectors.toList());
            List<String> collect1 = collect.stream().map($ -> $ + " => " + maximisedFunc($)).collect(Collectors.toList());

            System.out.println("GEN "+i);
            for (String s : collect1) {
                System.out.println(s);
            }
            System.out.println("");
        }

    }

    private static double maximisedFunc(Double x) {
        return (Math.exp(x)*Math.sin(10*Math.PI*x)+1)/x;
    }

    private static List<Individual> getIndividuals(Random random, List<Individual> individuals, Population<Individual> population) {
        List<Individual> individualsByFitness = population.byFitness();

        //TODO move from sortedmap to a list sorted by ad-hoc calc'd fitness

        List<Individual> nextPopulationIndividuals = new ArrayList<>();
        for(int i=0; i<POP_SIZE; i++){
            double choice = random.nextDouble();
            if(choice<BEST_FITTING_PROB){
                System.out.print("F");
                Individual bestFit = individualsByFitness.get(0);
                nextPopulationIndividuals.add(bestFit);
                individualsByFitness.remove(bestFit);
            } else if(choice>BEST_FITTING_PROB && choice<BEST_FITTING_PROB+CROSS_PROB && individualsByFitness.size()>=2){
                System.out.print("X");
                Individual bestFit1 = individualsByFitness.get(0);
                Individual bestFit2 = individualsByFitness.get(1);
                individualsByFitness.remove(bestFit1);
                individualsByFitness.remove(bestFit2);

                List<Individual> cross = bestFit1.cross(bestFit2);
                nextPopulationIndividuals.addAll(cross);

                i++;
            } else {
                System.out.print("M");
                Individual randomIndividual = individuals.get(random.nextInt(individuals.size()));
                randomIndividual.mutate(SINGLE_GENE_MUTATION_PROB);
                individualsByFitness.remove(randomIndividual);
                nextPopulationIndividuals.add(randomIndividual);
            }
        }
        System.out.println("");
        return nextPopulationIndividuals;
    }
}
