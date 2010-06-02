/* Copyright (c) 2010  Annsofie Andersson <annzi.andersson@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://bioclipse.net/
 *******************************************************************************/
package net.bioclipse.moss.business.backbone;

import java.util.ArrayList;

import net.bioclipse.moss.business.props.MossProperties;

/**
 * This is a model class that contains all information about a MOSS run
 * 
 * @author 
 * 
 */
public class MossModel extends MossProperties {

    private ArrayList<InputMolecule> inputMolecules;

    // Initializing parameters
    public MossModel() {
        super();
    }

  
    // Method for adding molecules to array list InputMolecule
    public void addMolecule(InputMolecule mol) {
        if (inputMolecules == null)
            inputMolecules = new ArrayList<InputMolecule>();
        inputMolecules.add(mol);
    }
   public void emptyList(){
	   if (inputMolecules != null)
		   inputMolecules.clear();
          
   }
    /* Generating getters and setters for all parameters*/

    public ArrayList<InputMolecule> getInputMolecules() {
        return inputMolecules;
    }

    public void setInputMolecules(ArrayList<InputMolecule> inputMolecules) {
        this.inputMolecules = inputMolecules;
    }

}
