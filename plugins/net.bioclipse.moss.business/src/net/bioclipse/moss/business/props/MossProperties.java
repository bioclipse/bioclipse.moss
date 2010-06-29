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
package net.bioclipse.moss.business.props;

import java.io.StringReader;
import java.io.StringWriter;

import moss.Atoms;
import moss.Bonds;
import moss.Miner;
import net.bioclipse.core.business.BioclipseException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Class to store MoSS properties. It allows properties to be serialized as
 * JSON, and an object instantiated from a JSON representation.
 *
 * @author egonw
 */
public class MossProperties {

    private double minimalSupport;
    private double maximalsupport;
    private double threshold;
    private boolean split, closed;
    private String exNode, exSeed, seed;
    private int minEmbed, maxEmbed;
    private int mbond, mrgbd;
    private int matom, mrgat;
    private int maxRing, minRing;
    private int mode;
    private int maxEmbMemory;
    private String path, pathId, namefile, namefileId;
	private String ringExtension, extPrune;
	private String aromatic, ignoreBond;
	public String ignoreTypeOfAtoms, matchChargeOfAtoms, matchAromaticityAtoms ;
	private boolean canonic, equiv, unembedSibling, kekuleRepresentation, carbonChainLength;
	

	/**
	 * Creates a new properties object with default settings. These are
	 * expected to be modified in actual use.
	 */
	public MossProperties() {
		setMinimalSupport(10);
        setMaximalSupport(2);
        setThreshold(0.5);
        setSplit(false);
        setClosed(true);
        setExNode("H");
        setExtPrune("none");
        setExSeed("");
        
        setSeed("");
        setMaxEmbed(0);
        setMinEmbed(1);
        setMaxRing(0);
        setMinRing(0);
        setMaxEmbMemory(0);
        setRingExtension("none");
        setKekule(true);
        setCarbonChainLength(false);
        
        setAromatic("never");
        setIgnoreBond("never");
        setCanonic(true);
        setEquiv(false);
        setUnembedSibling(false);
        setIgnoreTypeOfAtoms("never");
        setMatchChargeOfAtoms("no match");
        setMatchAromaticityAtoms("no match");
        
        setMbond(Bonds.BONDMASK);
        setMrgbd(Bonds.BONDMASK);
        setMatom(Atoms.ELEMMASK);
        setMrgat(Atoms.ELEMMASK);
        setMode(Miner.DEFAULT);
        
	}
	
	/**
	 * Uses the JSON representation is overwrite default settings. Settings that
	 * are, therefore, not represented in the JSON representation will be set
	 * by their default value.
	 *
	 * @param serialization JSON representation of the properties.
	 */
	public static MossProperties createMossProperties(String serialization)
	throws BioclipseException {
		ObjectMapper mapper = new ObjectMapper();
		StringReader reader = new StringReader(serialization);
		try {
			return mapper.readValue(reader, MossProperties.class);
		} catch (Exception exception) {
			throw new BioclipseException(
				"Error while reading JSON: " + exception.getMessage(),
				exception
			);
		}
	}

	/**
	 * Lists the differences between this and the given other properties. The
	 * returned String is not meant to be machine-readable.
	 *
	 * @param  otherProperties
	 * @return An empty String if there are no differences.
	 */
	public String diff(MossProperties otherProperties) {
		return "";
	}

	public String toString() {
		try {
			return toJSON();
		} catch (BioclipseException exception) {
			return exception.getMessage();
		}
	}
	
	/**
	 * Returns a JSON representation of the properties in this model.
	 */
	public String toJSON() throws BioclipseException {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, this);
		} catch (Exception exception) {
			throw new BioclipseException(
				"Error while creating JSON: " + exception.getMessage(),
				exception
			);
		}
		return writer.toString();
	}

    public double getMinimalSupport() {
        return minimalSupport;
    }

    public void setMinimalSupport(double minimalSupport) {
        this.minimalSupport = minimalSupport;
    }

    public double getMaximalSupport() {
        return maximalsupport;
    }

    public void setMaximalSupport(double maximalsupport) {
        this.maximalsupport = maximalsupport;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public boolean getSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public String getExNode() {
        return exNode;
    }

    public void setExNode(String exnode) {
        exNode = exnode;

    }

    public String getExSeed() {
        return exSeed;
    }

    public void setExSeed(String exseed) {
        exSeed = exseed;
    }

    public int getMaxEmbed() {
        return maxEmbed;
    }

    public void setMaxEmbed(int maximalEmbed) {
        maxEmbed = maximalEmbed;
    }

    public int getMinEmbed() {
        return minEmbed;
    }

    public void setMinEmbed(int minimalEmbed) {
        this.minEmbed = minimalEmbed;
    }

    public int getMbond() {
        return mbond;
    }

    public void setMbond(int mbond) {
        this.mbond = mbond;
    }

    public int getMrgbd() {
        return mrgbd;
    }

    public void setMrgbd(int mrgbd) {
        this.mrgbd = mrgbd;
    }

    public int getMaxRing() {
        return maxRing;
    }

    public void setMaxRing(int maxRing) {
        this.maxRing = maxRing;
    }

    public int getMinRing() {
        return minRing;
    }

    public int getMatom() {
        return matom;
    }

    public void setMatom(int matom) {
        this.matom = matom;
    }

    public void setMinRing(int minRing) {
        this.minRing = minRing;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getMrgat() {
        return mrgat;
    }

    public void setMrgat(int mrgat) {
        this.mrgat = mrgat;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMaxEmbMemory() {
        return maxEmbMemory;
    }

    public void setMaxEmbMemory(int maxEmbMemory) {
        this.maxEmbMemory = maxEmbMemory;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getNamefile() {
        return namefile;
    }

    public void setNamefile(String namefile) {
        this.namefile = namefile;
    }

    public String getNamefileId() {
        return namefileId;
    }

    public void setNamefileId(String namefileId) {
        this.namefileId = namefileId;
    }
    
    public String getRingExtension() {
		return ringExtension;
	}

	public void setRingExtension(String ringExtension) {
		this.ringExtension = ringExtension;
	}

	public boolean getKekule() {
		return kekuleRepresentation;
	}

	public void setKekule(boolean kekule) {
		kekuleRepresentation = kekule;
	}

	public boolean getCarbonChainLength() {
		return carbonChainLength;
	}

	public void setCarbonChainLength(boolean carbonChainLength) {
		this.carbonChainLength = carbonChainLength;
	}

	public String getExtPrune() {
		return extPrune;
	}

	public void setExtPrune(String extPrune) {
		this.extPrune = extPrune;
	}
	public String getAromatic() {
		return aromatic;
	}

	public void setAromatic(String aromatic) {
		this.aromatic = aromatic;
	}

	public String getIgnoreBond() {
		return ignoreBond;
	}

	public void setIgnoreBond(String ignoreBond) {
		this.ignoreBond = ignoreBond;
	}
	public boolean getCanonic() {
		return canonic;
	}

	public void setCanonic(boolean canonic) {
		this.canonic = canonic;
	}

	public boolean getEquiv() {
		return equiv;
	}

	public void setEquiv(boolean equiv) {
		this.equiv = equiv;
	}

	public boolean getUnembedSibling() {
		return unembedSibling;
	}

	public void setUnembedSibling(boolean unembedSibling) {
		this.unembedSibling = unembedSibling;
	}
	public String getIgnoreTypeOfAtoms() {
		return ignoreTypeOfAtoms;
	}

	public void setIgnoreTypeOfAtoms(String ignoreTypeOfAtoms) {
		this.ignoreTypeOfAtoms = ignoreTypeOfAtoms;
	}

	public String getMatchChargeOfAtoms() {
		return matchChargeOfAtoms;
	}

	public void setMatchChargeOfAtoms(String matchChargeOfAtoms) {
		this.matchChargeOfAtoms = matchChargeOfAtoms;
	}

	public String getMatchAromaticityAtoms() {
		return matchAromaticityAtoms;
	}

	public void setMatchAromaticityAtoms(String matchAromaticityAtoms) {
		this.matchAromaticityAtoms = matchAromaticityAtoms;
	}

}


