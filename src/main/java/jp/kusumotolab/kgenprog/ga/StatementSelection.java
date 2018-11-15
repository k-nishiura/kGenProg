package jp.kusumotolab.kgenprog.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import org.eclipse.jdt.core.dom.Statement;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import jp.kusumotolab.kgenprog.project.FullyQualifiedName;
import jp.kusumotolab.kgenprog.project.GeneratedAST;
import jp.kusumotolab.kgenprog.project.ProductSourcePath;

public class StatementSelection implements CandidateSelection {

  private final Random random;
  private Roulette<ReuseCandidate<Statement>> allRoulette;
  private final Multimap<String, ReuseCandidate<Statement>> packageNameStatementMultimap = HashMultimap.create();
  private final Multimap<FullyQualifiedName, ReuseCandidate<Statement>> fqnStatementMultiMap = HashMultimap.create();
  private final Map<String, Roulette<ReuseCandidate<Statement>>> packageNameRouletteMap = new HashMap<>();
  private final Map<FullyQualifiedName, Roulette<ReuseCandidate<Statement>>> fqnRouletteMap = new HashMap<>();

  public StatementSelection(final Random random) {
    this.random = random;
  }

  @Override
  public void setCandidates(final List<GeneratedAST<ProductSourcePath>> candidates) {
    final StatementVisitor visitor = new StatementVisitor(candidates);
    final List<ReuseCandidate<Statement>> reuseCandidates = visitor.getReuseCandidateList();

    putMaps(reuseCandidates);

    allRoulette = createRoulette(reuseCandidates);
  }

  protected double getStatementWeight(final ReuseCandidate<Statement> reuseCandidate) {
    return 1.0d;
  }

  @Override
  public Statement exec(final Scope scope) {
    final Roulette<ReuseCandidate<Statement>> roulette = getRoulette(scope);
    final ReuseCandidate<Statement> candidate = roulette.exec();
    return candidate.getValue();
  }

  private void putMaps(final List<ReuseCandidate<Statement>> reuseCandidates) {
    for (final ReuseCandidate<Statement> reuseCandidate : reuseCandidates) {
      packageNameStatementMultimap.put(reuseCandidate.getPackageName(), reuseCandidate);
      fqnStatementMultiMap.put(reuseCandidate.getFqn(), reuseCandidate);
    }
  }

  private Roulette<ReuseCandidate<Statement>> getRouletteInAllScope() {
    return allRoulette;
  }

  private Roulette<ReuseCandidate<Statement>> getRouletteInPackage(final String packageName) {
    return getRoulette(packageName, packageNameRouletteMap, packageNameStatementMultimap);
  }

  private Roulette<ReuseCandidate<Statement>> getRouletteInFile(final FullyQualifiedName fqn) {
    return getRoulette(fqn, fqnRouletteMap, fqnStatementMultiMap);
  }

  private <T> Roulette<ReuseCandidate<Statement>> getRoulette(final T key,
      final Map<T, Roulette<ReuseCandidate<Statement>>> rouletteMap,
      final Multimap<T, ReuseCandidate<Statement>> candidateMap) {
    Roulette<ReuseCandidate<Statement>> roulette = rouletteMap.get(key);
    if (roulette != null) {
      return roulette;
    }
    final Collection<ReuseCandidate<Statement>> candidates = candidateMap.get(key);
    roulette = createRoulette(new ArrayList<>(candidates));
    rouletteMap.put(key, roulette);
    return roulette;
  }

  private Roulette<ReuseCandidate<Statement>> getRoulette(final Scope scope) {
    final FullyQualifiedName fqn = scope.getFqn();
    switch (scope.getType()) {
      case ALL:
        return getRouletteInAllScope();
      case PACKAGE:
        return getRouletteInPackage(fqn.getPackageName());
      case FILE:
        return getRouletteInFile(fqn);
    }
    return null;
  }

  private Roulette<ReuseCandidate<Statement>> createRoulette(
      final List<ReuseCandidate<Statement>> candidates) {
    final Function<ReuseCandidate<Statement>, Double> weightFunction = this::getStatementWeight;
    return new Roulette<>(candidates, weightFunction, random);
  }
}
