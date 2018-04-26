package jp.kusumotolab.kgenprog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jp.kusumotolab.kgenprog.fl.FaultLocalization;
import jp.kusumotolab.kgenprog.fl.Ochiai;
import jp.kusumotolab.kgenprog.ga.Crossover;
import jp.kusumotolab.kgenprog.ga.DefaultCodeValidation;
import jp.kusumotolab.kgenprog.ga.DefaultSourceCodeGeneration;
import jp.kusumotolab.kgenprog.ga.DefaultVariantSelection;
import jp.kusumotolab.kgenprog.ga.Mutation;
import jp.kusumotolab.kgenprog.ga.RandomMutation;
import jp.kusumotolab.kgenprog.ga.SiglePointCrossover;
import jp.kusumotolab.kgenprog.ga.SourceCodeGeneration;
import jp.kusumotolab.kgenprog.ga.SourceCodeValidation;
import jp.kusumotolab.kgenprog.ga.VariantSelection;
import jp.kusumotolab.kgenprog.project.ClassPath;
import jp.kusumotolab.kgenprog.project.SourceFile;
import jp.kusumotolab.kgenprog.project.TargetProject;

public class CUILauncher {

	public List<String> sourceFiles = Arrays.asList(new String[] {"src/main/java"});
	public List<String> testFiles = Arrays.asList(new String[] {});
	public List<String> classPaths = Arrays.asList(new String[] {});
	
	
	public static void main(String[] args) {
		CUILauncher launcher = new CUILauncher();
		/*
		CmdLineParser parser = new CmdLineParser(launcher);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			parser.printUsage(System.err);
			System.exit(1);
		}
		*/
		launcher.launch();
	}

	public void launch(){
		List<SourceFile> sourceFiles = this.sourceFiles.stream()
			.map(SourceFile::new)
			.collect(Collectors.toList());
		

		List<SourceFile> testFiles = this.testFiles.stream()
			.map(SourceFile::new)
			.collect(Collectors.toList());
		

		List<ClassPath> classPaths = this.classPaths.stream()
			.map(ClassPath::new)
			.collect(Collectors.toList());
		
		TargetProject targetProject = new TargetProject(sourceFiles, testFiles, classPaths);
		FaultLocalization faultLocalization = new Ochiai();
		Mutation mutation = new RandomMutation();
		Crossover crossover = new SiglePointCrossover();
		SourceCodeGeneration sourceCodeGeneration = new DefaultSourceCodeGeneration();
		SourceCodeValidation sourceCodeValidation = new DefaultCodeValidation();
		VariantSelection variantSelection = new DefaultVariantSelection();
		
		KGenProgMain kGenProgMain = new KGenProgMain(targetProject, faultLocalization, mutation, crossover, sourceCodeGeneration, sourceCodeValidation, variantSelection);
		kGenProgMain.run();
	}
}