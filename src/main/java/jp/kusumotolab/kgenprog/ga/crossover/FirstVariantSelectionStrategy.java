package jp.kusumotolab.kgenprog.ga.crossover;

import java.util.List;
import java.util.Random;
import jp.kusumotolab.kgenprog.ga.variant.Variant;

public interface FirstVariantSelectionStrategy {

  Variant exec(List<Variant> variants);


  public enum Technique {
    RandomSelection {
      @Override
      public FirstVariantSelectionStrategy initialize(final Random random) {
        return new FirstVariantRandomSelection(random);
      }
    },

    EliteSelection {
      @Override
      public FirstVariantSelectionStrategy initialize(final Random random) {
        return new FirstVariantEliteSelection(random);
      }
    };

    public abstract FirstVariantSelectionStrategy initialize(Random random);
  }
}
