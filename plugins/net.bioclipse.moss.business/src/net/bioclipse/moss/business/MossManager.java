/* Copyright (c) 2010  Egon Willighagen <egonw@users.sf.net>
 *               2010  Annsofie Andersson <annzi.andersson@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 */
package net.bioclipse.moss.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import moss.Atoms;
import moss.Bonds;
import moss.Miner;
import net.bioclipse.core.api.BioclipseException;
import net.bioclipse.core.api.ResourcePathTransformer;
import net.bioclipse.core.api.managers.IBioclipseManager;
import net.bioclipse.moss.business.backbone.MossBean;
import net.bioclipse.moss.business.backbone.MossModel;
import net.bioclipse.moss.business.backbone.MossRunner;
import net.bioclipse.moss.business.props.MossProperties;
import net.bioclipse.rdf.Activator;
import net.bioclipse.rdf.business.IJavaRDFManager;
import net.bioclipse.rdf.model.IStringMatrix;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class MossManager implements IBioclipseManager {

	private IJavaRDFManager rdf = Activator.getDefault().getJavaManager();
	private static final Logger logger = Logger.getLogger(MossManager.class);

//	/**A bean that sets the parameters
//	 * Parameters: propertyName, Object values
//	 * */
//	MossBean mossbean = new MossBean();
//	public String createParameters(String propertyName, Object value) throws Exception{
//		//	if(propertyName.equals("matom")||propertyName.equals("mbond")||propertyName.equals("mrgat")||propertyName.equals("mrgbd")){
//		//	return "Not allowed to set value to " + propertyName+ ", internal proerty.";
//		//}
//		//else{
//		if(value.getClass().equals(Double.class)){
//			value= ((Double) value).intValue();
//			int values = (Integer) value;
//			MossBean.setParameters(mossbean, propertyName, values);
//		}else{
//			MossBean.setParameters(mossbean, propertyName, value);	
//		}
//		return value  +" is set to " +propertyName;
//	}
	//}	
	/**
	 * Gives a short one word name of the manager used as variable name when
	 * scripting.
	 */

	public String getManagerName() {
		return "moss";}

	//Collects compounds from a protein family
	public IStringMatrix query(String fam, String actType, int limit) throws BioclipseException{

		String sparql =
			"PREFIX onto: <http://rdf.farmbio.uu.se/chembl/onto/#> " +
			"PREFIX bo: <http://www.blueobelisk.org/chemistryblogs/>"+
			"SELECT ?smiles where{ " +
			"	?target a onto:Target." +
			"   ?target onto:classL5 ?fam. " + //+ " \"" + fam + "\"." +
			"	?assay onto:hasTarget ?target . ?activity onto:onAssay ?assay ." +
			" ?activity onto:standardValue ?st ." +
			"	?activity onto:type ?actType . " + //" \"" + actType + "\"."+ 
			"	?activity onto:forMolecule ?mol ."+
			"	?mol bo:smiles ?smiles.  " +
			"FILTER regex(?fam, " + "\"" + fam + "\"" + ", \"i\")."+
			"FILTER regex(?actType, " + "\"" + actType + "\"" + ", \"i\")."+
			"}LIMIT "+ limit; 

		IStringMatrix matrix = rdf.sparqlRemote("http://rdf.farmbio.uu.se/chembl/sparql",sparql);
		return matrix;
	}

	
	public void run(String inputfile, String outfile,String outidfile, MossProperties mp) 
	throws BioclipseException, IOException,ScriptException{
		IFile in = ResourcePathTransformer.getInstance().transform(inputfile);
		IFile out= saveMossOutputHelper(outfile);
		IFile outid =saveMossOutputHelper(outidfile);
		MossModel mossmodel = new MossModel();

		mossmodel = collectMoSSProperties(mossmodel,mp);
		MossRunner.runMoss(mossmodel,in.getLocation().toOSString(), out.getLocation().toOSString(), outid.getLocation().toOSString());
	}

	public IFile saveMossOutputHelper(String file) throws BioclipseException, IOException{
		IFile ifile =ResourcePathTransformer.getInstance().transform(file);
		return saveMossOutput(ifile, null);
	}
	public IFile saveMossOutput(IFile filename, IProgressMonitor monitor)
	throws BioclipseException, IOException {

		if (filename.exists()) {
			throw new BioclipseException("File already exists!");
		}
		if (monitor == null)
			monitor = new NullProgressMonitor();
		monitor.beginTask("Writing file", 100);
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			output.close();
			filename.create(
					new ByteArrayInputStream(output.toByteArray()),
					false,
					monitor
			);}
		catch (Exception e) {
			monitor.worked(100);
			monitor.done();
			throw new BioclipseException("Error while writing moss outfile.", e);
		} 
		monitor.worked(100);
		monitor.done();
		return filename;
	};

	public MossProperties createMoSSProperties() {
		return new MossProperties();
	}

	public MossProperties createMoSSProperties(String json)
	throws BioclipseException {
		return MossProperties.createMossProperties(json);
	}

	private MossModel collectMoSSProperties(MossModel mossmodel,MossProperties mp){
		mossmodel.setMinimalSupport(mp.getMinimalSupport()*0.01);
		mossmodel.setMaximalSupport(mp.getMaximalSupport()*0.01);
		mossmodel.setThreshold(mp.getThreshold());
		mossmodel.setExNode(mp.getExNode());
		mossmodel.setExSeed(mp.getExSeed());
		mossmodel.setSeed(mp.getSeed());
		mossmodel.setMinRing(mp.getMinRing());
		mossmodel.setMaxRing(mp.getMaxRing());
		mossmodel.setMaxEmbMemory(mp.getMaxEmbMemory());
		mossmodel.setMbond(getMbond(mp));
		mossmodel.setMrgbd(getMrgbd(mp));
		mossmodel.setMatom(getMatom(mp));
		mossmodel.setMrgat(getMrgat(mp));
		mossmodel.setMode(getMode(mp));
		
		return mossmodel;
	}

	public int getMatom(MossProperties mp){
		int matom= Atoms.ELEMMASK;//nollställa if =never, don't match atom/aromaticity

		// Ignore atoms
		if(mp.getIgnoreBond().equals("always")){
			matom &= ~Atoms.ELEMMASK;}

		//Match charge of atoms
		if(mp.getMatchChargeOfAtoms().equals("match")){
			matom |= Atoms.CHARGEMASK;
		}

		//Match aromaticity
		if(mp.matchAromaticityAtoms.equals("match")){
			matom &= Atoms.AROMATIC;}

		return matom;
	}
	public int getMbond(MossProperties mp){
		int mbond = Bonds.BONDMASK;//nollställa = never,

		//Aromatic bonds
		if(mp.getAromatic().equals("upgrade")){
			mbond &= Bonds.UPGRADE;
		}else if(mp.getAromatic().equals("downgrade")){
			mbond &= Bonds.DOWNGRADE;}

		//Ignore bonds
		if(mp.getIgnoreBond().equals("always")){
			mbond &= Bonds.SAMETYPE;
		}
		return mbond;
	}

	public int getMrgat(MossProperties mp){
		int mrgat = Atoms.ELEMMASK ; //nollställa =never, 

		if(mp.getIgnoreTypeOfAtoms().equals("always")||mp.getIgnoreTypeOfAtoms().equals("in rings")){
			mrgat &= ~Atoms.ELEMMASK;}
		return mrgat;
	}

	public int getMrgbd(MossProperties mp){
		int mrgbd = Bonds.BONDMASK;//nollställa = never Aromatic bonds
		if(mp.getAromatic().equals("upgrade")){
			mrgbd &= Bonds.UPGRADE;}
		else if(mp.getAromatic().equals("downgrade")){
			mrgbd &= Bonds.DOWNGRADE;}
		//Ignore bonds
		if(mp.getIgnoreBond().equals("always") || mp.getIgnoreBond().equals("in rings")){
			mrgbd &= Bonds.SAMETYPE;
		}
		return mrgbd;
	}
	public int getMode(MossProperties mp){
		int mode =Miner.DEFAULT;// default   | Miner.RINGEXT;Ring extension;
		
		//none is default
		if(mp.getRingExtension().equals("full")){
			mode |= Miner.RINGEXT;
		}else if(mp.getRingExtension().equals("merge")){
			mode |= Miner.MERGERINGS |Miner.RINGEXT |Miner.CLOSERINGS | Miner.PR_UNCLOSE;
		}else if(mp.getRingExtension().equals("filter")){
			mode |= Miner.CLOSERINGS | Miner.PR_UNCLOSE;
		}
		//kekule if no representation
		if(mp.getKekule() == false){
			mode |= Miner.AROMATIZE;
		}
		//Carbon chain length. default false
		if(mp.getCarbonChainLength()){
			mode |= Miner.CHAINEXT;
		}
		//extPrunr
		//none is default
		if(mp.getExtPrune().equals("full")){
			mode |=  Miner.PR_PERFECT;
		    mode &= ~Miner.PR_PARTIAL;
		}else if(mp.getExtPrune().equals("partial")){
			mode |=  Miner.PR_PARTIAL;
			mode &= ~Miner.PR_PERFECT;
		}
		//canonic default true
		if(!mp.getCanonic()){
			mode &= ~Miner.PR_CANONIC;
		}
		//equiv default false
		if(mp.getEquiv()){
			mode |= Miner.PR_EQUIV;		
		}
		//unembed sibling default false
		if(mp.getUnembedSibling()){
			mode |= Miner.UNEMBED;
		}
		//defualt true
		if(!mp.getClosed()){
			mode &= ~Miner.CLOSED;
		}
		
		return mode;
	}

	/*String[] desc ={
	"Examplea moss.createParamteters(\"aromatic\", \"always\")," + "\n" +
	"moss.createParamteters(\"minEmbed\", 6)" + "\n",
	" (\"aromatic\", \"never\"/\"upgrade\"/\"downgrade\")  |\"String\"",
	" (\"canonicequiv\", false/true)   |boolean",
	" (\"canonic\", true/false)   |boolean" ,
	" (\"carbonChainLength\", true/false)  |boolean",
	" not for use",
	" (\"closed\", true/false)   |boolean",
	" (\"exNode\", \"Atom\")    |\"String\"",
	" (\"exSeed\", \"Atom\")    |\"String\"",
	" (\"extPrune\", \"none\"/\"full\"/\"partial\"/)    |\"String\"",
	" (\"ignoreAtomTypes\", \"never\"/\"always\"/\"in rings\")    |\"String\"",
    " (\"ignoreBond\", \"never\"/\"always\"/\"in rings\")    |\"String\"", 
	" (\"kekule\", true/false)   |boolean" ,
	" not for use",
	" (\"matchChargeOfAtoms\", \"never\"/\"always\"/\"in rings\")    |\"String\"",
	" (\"matchAromaticityAtoms\", \"match\"/\"no match\")    |\"String\"",
	" not for use",
	" (\"maxEmbMemory\", value)   |integer",
	" (\"maxEmbed\",value)   |integer",
	" (\"maxRing\", value)   |integer",
	" (\"maximalSupport\", value)   |double",
	" not for use",
	" (\"minEmbed\", value)   |integer", 
	" (\"maxRing\", value)   |integer", 
	" (\"minimalSupport\", value)   |double",
	" not for use"," not for use"," not for use",
	" (\"ringExtension\", \"none\"/\"full\"/\"merge\"/\"filter\")   |\"String\"",
	" (\"seed\", \"Atom\")   |\"String\"",
	" (\"split\", true/false)  |boolean",
	" (\"threshold\", value)   |double",
	" (\"unembedSibling\", false/true)   |boolean",""

	};

	 */


	public void focusComplementSet(IFile infiles,IFile outfile,IProgressMonitor monitor) 
	throws BioclipseException, IOException{
		File infile = new File(infiles.getLocationURI());
		BufferedReader br = new BufferedReader(new FileReader(infile));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String line = null;
		String[]split;
		String string=null;
		if (outfile.exists()) {
			throw new BioclipseException("File already exists!");
		}

		if (monitor == null)
			monitor = new NullProgressMonitor();
		monitor.beginTask("Writing file", 100);

		try {
			while((line=br.readLine())!=null){
				split= line.split(",");
				try {
					if(split[0].contains("A")){string = split[0]+",0,"+split[2]+"\n";}
					else if(split[0].contains("B")){string = split[0]+",1,"+split[2]+"\n";}

					byte but[]= string.getBytes();
					out.write(but); 

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			out.close();
			outfile.create(
					new ByteArrayInputStream(out.toByteArray()),
					false,
					monitor
			);
		}
		catch (Exception e) {
			monitor.worked(100);
			monitor.done();
			throw new BioclipseException("Error while writing moss file.", e);
		} 

		monitor.worked(100);
		monitor.done();
	
	}
}


