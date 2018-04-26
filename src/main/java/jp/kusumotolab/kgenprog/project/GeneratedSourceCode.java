package jp.kusumotolab.kgenprog.project;

import java.util.List;

/**
 * APR によって生成されたソースコード
 * 複数ソースファイルの AST の集合を持つ
 */
public class GeneratedSourceCode {
	private List<GeneratedAST> files;

	public GeneratedSourceCode(List<GeneratedAST> files){
		this.files = files;
	}

}