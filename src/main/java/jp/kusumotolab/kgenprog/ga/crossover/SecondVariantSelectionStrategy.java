package jp.kusumotolab.kgenprog.ga.crossover;

import java.util.List;
import java.util.Random;
import jp.kusumotolab.kgenprog.ga.variant.Variant;

public interface SecondVariantSelectionStrategy {

  Variant exec(List<Variant> variants, Variant SecondVariant);

  public enum Technique {
    RandomSelection {
      @Override
      public SecondVariantSelectionStrategy initialize(final Random random) {
        return new SecondVariantRandomSelection(random);
      }
    },

    EliteSelection {
      @Override
      public SecondVariantSelectionStrategy initialize(final Random random) {
        return new SecondVariantEliteSelection();
      }
    },

    GeneSimilaritySelection {
      @Override
      public SecondVariantSelectionStrategy initialize(final Random random) {
        return new SecondVariantGeneSimilarityBasedSelection();
      }
    },

    TestSimilaritySelection {
      @Override
      public SecondVariantSelectionStrategy initialize(final Random random) {
        return new SecondVariantTestSimilarityBasedSelection();
      }
    };

    public abstract SecondVariantSelectionStrategy initialize(Random random);
  }

}
