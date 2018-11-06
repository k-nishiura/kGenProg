package jp.kusumotolab.kgenprog.ga;

import java.util.Collections;
import java.util.List;

public class MutationHistoricalElement implements HistoricalElement {

  private final Variant parent;
  private final Base appendedBase;

  public MutationHistoricalElement(final Variant parent, final Base appendedBase) {
    this.parent = parent;
    this.appendedBase = appendedBase;
  }

  @Override
  public List<Variant> getParents() {
    return Collections.singletonList(parent);
  }

  public Base getAppendedBase() {
    return appendedBase;
  }
}