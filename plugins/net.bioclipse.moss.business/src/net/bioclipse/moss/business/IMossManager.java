/*
 * ******************************************************************************
 * Copyright (c) 2010  Egon Willighagen <egonw@users.sf.net>
 *					   Annsofie Andersson <annzi.andersson@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.moss.business;

import java.io.IOException;

import javax.script.ScriptException;

import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IStringMatrix;
import net.bioclipse.managers.business.IBioclipseManager;
import net.bioclipse.moss.business.props.MossProperties;

@PublishedClass(
    value="Manager to provide MoSS substructure mining functionality.",
    doi="10.1145/1133905.1133908"
)
public interface IMossManager extends IBioclipseManager {
	
	@Recorded
	@PublishedMethod(
		methodSummary = "Creates a new MoSS run properties object.\n" +
				"\n" +
				"example:\n" +
				"var moss = moss.createMoSSParameter()\n" +
				"moss.setMinimalSupport(80)\n" +
				"\n" +
			"Following parameters are set the following way: \n \n"+
			" (aromatic: \"never\"/\"upgrade\"/\"downgrade\")  | \"String\"\n"+
			" (canonicequiv: ture/false)   | boolean\n"+
			" (canonic: true/false)   | boolean\n" +
			" (carbonChainLength: true/false)  | boolean\n"+
			" (closed: true/false)   | boolean \n"+
			" (exNode:, \"Atom\")    | \"String\"\n"+
			" (exSeed:, \"Atom\")    | \"String\"\n"+
			" (extPrune: \"none\"/\"full\"/\"partial\"/)    | \"String\"\n"+
			" (ignoreAtomTypes: \"never\"/\"always\"/\"in rings\")    | \"String\"\n"+
	        " (ignoreBond: \"never\"/\"always\"/\"in rings\")    | \"String\"\n"+
			" (kekule: true/false)   | boolean \n" +
			" (matchChargeOfAtoms: \"match\"/\"no match\")    | \"String\"\n"+
			" (matchAromaticityAtoms: \"match\"/\"no match\")    | \"String\"\n"+
			" (maxEmbMemory: value)   | integer \n"+
			" (maxEmbed:value)   | integer \n"+
			" (maxRing: value)   | integer \n"+
			" (maximalSupport:, value)   | double \n"+
			" (minEmbed: value)   | integer \n"+
			" (maxRing: value)   | integer \n" +
			" (minimalSupport: value)   | double"+
			" (ringExtension: \"none\"/\"full\"/\"merge\"/\"filter\")   | \"String\"\n"+
			" (seed: \"Atom\")   | \"String\"\n"+
			" (split: true/false)  | boolean \n"+
			" (threshold: value)   | double \n"+
			" (unembedSibling: true/false)   | boolean \n"
	)
	public MossProperties createMoSSProperties();

	@Recorded
	@PublishedMethod(
		params="String json",
		methodSummary = "Creates a new MoSS run properties object given the " +
			"parameters given in JSON. Throws an BioclipseException if the " +
			"passed JSON is invalid."
	)
	public MossProperties createMoSSProperties(String json)
	throws BioclipseException;
	
	@Recorded
	@PublishedMethod(
			params = "IFile fileName IFile fileOutName",
			methodSummary = "Divide a file into focus and complement. Creates a new file" )
	public void focusComplementSet(String file,String outFile) throws IOException;
	
	@Recorded
	@PublishedMethod(
			params = "String inputfile, String outfile, String outidfile",
			methodSummary = "Run MoSS" )
			public void run(String inputfile, String outfile, String outidfile,MossProperties mp) 
	throws BioclipseException, IOException, ScriptException;

	
	@Recorded
	@PublishedMethod(
			params = "String fam,String activity, int limit",
			methodSummary = "Create query" )
			public IStringMatrix query(String fam, String act, int limit) 
	throws BioclipseException, IOException, ScriptException;
	
//	@Recorded
//	@PublishedMethod(
//			params = "String fileName, List family",
//			methodSummary = "Saves moss file from SPARQL query" )
//			public void saveMoss(String fileName, List<ArrayList<String>> fam1)
//	throws BioclipseException, IOException ;
//
//	@Recorded
//	@PublishedMethod(
//			params = "String fileName, List fam1,  List fam2",
//			methodSummary = "Saves moss file from SPARQL query, list two families" )
//			public void saveMoss(String fileName, List<ArrayList<String>> fam1, 
//					List<ArrayList<String>> fam2)
//	throws BioclipseException, IOException ;
//
//	@Recorded
//	@PublishedMethod(
//			params = "String fileName, List fam1",
//			methodSummary = "Saves info from SPARQL query, in file" )
//			public void saveInformation(String filename, List<ArrayList<String>> fam)
//	throws BioclipseException, IOException ;

	
//	@Recorded
//	@PublishedMethod(
//			params = "String property ,Object value" +
//			"Existing properties:"+
//			" minimalSupport,maximalSupport,threshold, split, closed" + 
//			" exNode, exSeed, seed,minEmbed, maxEmbed,Limits" + 
//			" mbond, mrgbd, matom, mrgat,maxRing, minRing" +
//			" mode, maxEmbMemory, path, pathId, namefile, namefileId",
//			methodSummary =  "Sets paramters. Examples moss.createParamteters(\"aromatic\", \"always\") and" + "\n" +
//			"moss.createParamteters(\"minEmbed\", 6) \n" +
//			"Following are arguments to createParameters: \n \n"+
//			" (\"aromatic\", \"never\"/\"upgrade\"/\"downgrade\")  | \"String\"\n"+
//			" (\"canonicequiv\", false/true)   | boolean\n"+
//			" (\"canonic\", true/false)   | boolean\n" +
//			" (\"carbonChainLength\", true/false)  | boolean\n"+
//			" (\"closed\", true/false)   | boolean \n"+
//			" (\"exNode\", \"Atom\")    | \"String\"\n"+
//			" (\"exSeed\", \"Atom\")    | \"String\"\n"+
//			" (\"extPrune\", \"none\"/\"full\"/\"partial\"/)    | \"String\"\n"+
//			" (\"ignoreAtomTypes\", \"never\"/\"always\"/\"in rings\")    | \"String\"\n"+
//	        " (\"ignoreBond\", \"never\"/\"always\"/\"in rings\")    | \"String\"\n"+
//			" (\"kekule\", true/false)   | boolean \n" +
//			" (\"matchChargeOfAtoms\", \"never\"/\"always\"/\"in rings\")    | \"String\"\n"+
//			" (\"matchAromaticityAtoms\", \"match\"/\"no match\")    | \"String\"\n"+
//			" (\"maxEmbMemory\", value)   | integer \n"+
//			" (\"maxEmbed\",value)   | integer \n"+
//			" (\"maxRing\", value)   | integer \n"+
//			" (\"maximalSupport\", value)   | double \n"+
//			" (\"minEmbed\", value)   | integer \n"+
//			" (\"maxRing\", value)   | integer \n" +
//			" (\"minimalSupport\", value)   | double"+
//			" (\"ringExtension\", \"none\"/\"full\"/\"merge\"/\"filter\")   | \"String\"\n"+
//			" (\"seed\", \"Atom\")   | \"String\"\n"+
//			" (\"split\", true/false)  | boolean \n"+
//			" (\"threshold\", value)   | double \n"+
//			" (\"unembedSibling\", false/true)   | boolean \n")
//			public String createParameters(String property, Object value);
//	@Recorded
//	@PublishedMethod(
//			methodSummary = "Shows value of settings" )
//			public String parameterValues() throws Exception;



}