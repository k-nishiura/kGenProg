package jp.kusumotolab.kgenprog.ga.crossover;

import java.util.List;
import java.util.Random;
import jp.kusumotolab.kgenprog.ga.variant.Variant;
import jp.kusumotolab.kgenprog.ga.variant.VariantStore;

public interface Crossover {

  public List<Variant> exec(VariantStore variantStore);

  public FirstVariantSelectionStrategy getFirstVariantSelectionStrategy();

  public SecondVariantSelectionStrategy getSecondVariantSelectionStrategy();


  public enum Technique {
    Uniform {
      @Override
      public Crossover initialize(
          final Random random,
          final FirstVariantSelectionStrategy firstVariantSelectionStrategy,
          final SecondVariantSelectionStrategy secondVariantSelectionStrategy,
          final int generatingCount) {
        return new UniformCrossover(random, firstVariantSelectionStrategy,
            secondVariantSelectionStrategy, generatingCount);
      }
    },

    SinglePoint {
      @Override
      public Crossover initialize(
          final Random random,
          final FirstVariantSelectionStrategy firstVariantSelectionStrategy,
          final SecondVariantSelectionStrategy secondVariantSelectionStrategy,
          final int generatingCount) {
        return new SinglePointCrossover(random, firstVariantSelectionStrategy,
            secondVariantSelectionStrategy, generatingCount);
      }
    },

    Random {
      @Override
      public Crossover initialize(
          final Random random,
          final FirstVariantSelectionStrategy firstVariantSelectionStrategy,
          final SecondVariantSelectionStrategy secondVariantSelectionStrategy,
          final int generatingCount) {
        return new RandomCrossover(random, firstVariantSelectionStrategy,
            secondVariantSelectionStrategy, generatingCount);
      }
    };

    public abstract Crossover

    initialize(Random random, FirstVariantSelectionStrategy firstVariantSelectionStrategy,
        SecondVariantSelectionStrategy secondVariantSelectionStrategy, int generatingCount);
  }
}
