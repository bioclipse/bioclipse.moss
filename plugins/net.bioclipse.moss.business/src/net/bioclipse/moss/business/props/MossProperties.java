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

/**
 * Class to store MoSS properties. It allows properties to be serialized as
 * JSON, and an object instantiated from a JSON representation.
 *
 * @author egonw
 */
@SuppressWarnings("serial")
public class MossProperties {

    private double minimalSupport;
    private double maximalsupport;
    private double threshold;
    private boolean split, closed;
    private String exNode, exSeed, seed;
    private int minEmbed, maxEmbed;
    private double Limits;
    private int mbond, mrgbd;
    private int matom, mrgat;
    private int maxRing, minRing;
    private int mode;
    private int maxEmbMemory;
    private String path, pathId, namefile, namefileId;
	
	/**
	 * Creates a new properties object with default settings. These are
	 * expected to be modified in actual use.
	 */
	public MossProperties() {
        setMinimalSupport(0.1);
        setMaximalsupport(0.02);
        setThreshold(0.5);
        setSplit(false);
        setClosed(true);
        setExNode("H");
        setExSeed("");
        setSeed("");
        setMaxEmbed(0);
        setMinEmbed(1);
        setMaxRing(0);
        setMinRing(0);
        setMaxEmbMemory(0);
	}
	
	/**
	 * Uses the JSON representation is overwrite default settings. Settings that
	 * are, therefore, not represented in the JSON representation will be set
	 * by their default value.
	 *
	 * @param serialization JSON representation of the properties.
	 */
	public MossProperties(String serialization) {
		throw new IllegalAccessError(
			"To be implemented"
		);
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

	/**
	 * Returns a JSON representation of the properties in this model.
	 */
	public String toString() {
		return "";
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

    public void setMaximalsupport(double maximalsupport) {
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

    public double getLimits() {
        return Limits;
    }

    public void setLimits(double minsupp, double maxsupp) {
        minimalSupport = minsupp;
        maximalsupport = maxsupp;
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
}


