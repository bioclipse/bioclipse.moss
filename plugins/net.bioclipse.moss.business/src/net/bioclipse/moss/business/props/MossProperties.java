/* Copyright (c) 2010  Egon Willighagen <egonw@users.sf.net>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 */
package net.bioclipse.moss.business.props;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to store MoSS properties. It allows properties to be serialized as
 * JSON, and an object instantiated from a JSON representation.
 *
 * @author egonw
 */
@SuppressWarnings("serial")
public class MossProperties {

	public enum Types {
		FOO
	}
	
	private Map<Types,Object> properties;

	// IMPORTANT: each Types enumeration value *must* have a default.
	private Map<Types,Object> defaults = new HashMap<Types, Object>() {{
		put(Types.FOO, "foo");
	}};

	/**
	 * Creates a new properties object with default settings. These are
	 * expected to be modified in actual use.
	 */
	public MossProperties() {
		// as long as the getProperty() methods properly returns the default
		// value for unset properties, I do not have to do anything here
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
	 * Returns the value of some property.
	 *
	 * @param  type A value of the {@link Types} enumeration.
	 * @return      the {@link Object} value for this property.
	 */
	public Object getProperty(Types type) {
		if (defaults.containsKey(type))
			throw new InvalidParameterException(
				"This property is not registered yet. Please file a bug report."
			);
		if (properties.containsKey(type))
			return properties.get(type);
		return defaults.get(type);
	}

	/**
	 * Sets a new property value for this object.
	 *
	 * @param type  A value of the {@link Types} enumeration.
	 * @param value the {@link Object} value for this property.
	 */
	public void setProperty(Types type, Object value) {
		if (defaults.containsKey(type))
			throw new InvalidParameterException(
				"This property is not registered yet. Please file a bug report."
			);
		if (defaults.get(type).equals(value)) {
			// removing the value, as it is identical to the default
			properties.remove(type);
		}
		properties.put(type, value);
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
}


