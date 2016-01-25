package pl.lodz.p.ftims.oi.genetic;

import com.sun.org.apache.bcel.internal.generic.POP;
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
    private static final double BEST_FITTING_PROB = 0.35;
    private static final double CROSS_PROB = 0.45;
    private static final double SINGLE_GENE_MUTATION_PROB = 0.2;
    public static int POP_SIZE = 64;

    public static void main(String[] args) {
        Random random = new Random();

        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < POP_SIZE; i++) {
            double v = random.nextDouble() * MAX_RANGE - MIN_RANGE + MIN_RANGE;
            NumberIndividual numberIndividual = new NumberIndividual(MIN_RANGE, MAX_RANGE, RANGES);
            BitSetMapper<DoubleWrapper> mapper = numberIndividual.getBitSetMapper();
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

        //TODO move from sortedmap to a list sorted by ad-hoc calc'd fitness

        population.toString();

        List<Individual> nextPopulationIndividuals = new ArrayList<>();
        for(int i=0; i<POP_SIZE; i++){
            System.out.println("Filling new population, "+i+"/"+ POP_SIZE);
            double choice = random.nextDouble();
            if(choice<BEST_FITTING_PROB){
                Individual bestFit = individualDoubleSortedMap.lastKey();
                nextPopulationIndividuals.add(bestFit);
            } else if(choice>BEST_FITTING_PROB && choice<BEST_FITTING_PROB+CROSS_PROB){
                Individual bestFit1 = individualDoubleSortedMap.lastKey();
                Double bestFit1Val = individualDoubleSortedMap.get(bestFit1);
                individualDoubleSortedMap.remove(bestFit1);
                Individual bestFit2 = individualDoubleSortedMap.lastKey();
                individualDoubleSortedMap.put(bestFit1, bestFit1Val);

                List<Individual> cross = bestFit1.cross(bestFit2);
                nextPopulationIndividuals.addAll(cross);

                i++;
            } else {
                Individual randomIndividual = individuals.get(random.nextInt(individuals.size()));
                randomIndividual.mutate(SINGLE_GENE_MUTATION_PROB);
            }
        }

        Population<Individual> newPop = new Population<>(nextPopulationIndividuals, fitness);
        newPop.byFitness();
        newPop.toString();
    }
}
