package net.bioclipse.moss.business.chemblMoss.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.bioclipse.chembl.Activator;
import net.bioclipse.chembl.business.IChEMBLManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.rdf.model.IStringMatrix;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
public class ChemblMossWizardPage1 extends WizardPage implements IRunnableContext{

	private IChEMBLManager chembl;
	private Label label, info, labelLow, labelHigh;
	private GridData gridData;
	private Combo cbox, cboxAct;
	private Table table;
	private TableColumn column1, column2, column3;
	private Spinner spinn, spinnLow, spinnHigh;
	private Button button, buttonb, check, buttonH, buttonUpdate;
	private Text text;
	XYSeries series;
	public static final String PAGE_NAME = "one";
	IStringMatrix matrixAct;
	private String index;
	public ChemblMossWizardPage1(String pagename){
		super(pagename);
		chembl = Activator.getDefault().getJavaChEMBLManager();

	}	
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp();		
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, false);
		layout.marginRight = 2;
		layout.marginLeft = 2;
		layout.marginBottom = 2;
		layout.marginTop = 10;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		layout.verticalSpacing = 5;
		layout.horizontalSpacing = 5;
		container.setLayout(layout);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, "net.bioclipse.moss.business.helpmessage");
		setControl(container);
		setMessage("This is an application for MoSS. Compounds are collected from chEMBL by simply \nchosing a Kinase" +
		" protein family. For further information go to help. ");
		setPageComplete(false);

		label = new Label(container, SWT.NONE);
		gridData = new GridData(GridData.FILL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		label.setLayoutData(gridData);
		label.setText("Choose Kinase Protein Familes");

		cbox = new Combo(container,SWT.READ_ONLY);
		cbox.setToolTipText("Kinase family");
		gridData = new GridData(GridData.BEGINNING);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		gridData.widthHint=100;
		cbox.setLayoutData(gridData);
		String[] items = { "TK","TKL","STE","CK1","CMGC","AGC","CAMK" };
		cbox.setItems(items);
		cbox.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				final String selected = cbox.getItem(cbox.getSelectionIndex());
				
				try {
					table.clearAll();
					table.removeAll();
					setErrorMessage(null);

//					getContainer().run(true, true, new IRunnableWithProgress() {
//					      public void run(IProgressMonitor monitor) {
//					    	  int sum = 10;
//					          monitor.beginTask("Computing sum: ", sum);
//					    	  list = chembl.MossAvailableActivities(selected);
//					         for (int i = 0; i < sum; i++) {
//					            monitor.subTask(Integer.toString(i));
//					            //sleep to simulate long running operation
//					            
//					            Thread.sleep(100);
//					            monitor.worked(1);
//					         }
//					         monitor.done();
//					      }
//					   });
					List<String> list = chembl.MossAvailableActivities(selected);
					if(list.size()>0){
						String[] item = new String[list.size()];
						for(int i=0;i<list.size(); i++){
							item[i]= list.get(i);
						}		

						
						if(cboxAct.isEnabled()){
							if(cboxAct.getSelection().x == cboxAct.getSelection().y){
								cboxAct.setItems(item);

							}else{

								/*EMERGENCY SOLUTION.. To solve the problem
									that involves changing the protein family...
								 */

								//Brings the current activities to an array
								String oldItems[] = cboxAct.getItems();
								// Takes that array and makes it a list
								for(int i = 0; i< list.size(); i++){
									cboxAct.add(item[i]);	
								}

								//Remove the old items in the combobox
								int oldlistsize = cboxAct.getItemCount() - list.size();
								String index = cboxAct.getText();//cboxAct.getItem(cboxAct.getSelectionIndex());
								cboxAct.remove(0, oldlistsize-1);
								//Adds new items to the comboboxlist
								List<String> oldItemsList = new ArrayList<String>();
								for(int i = 0; i< oldItems.length;i++){
									oldItemsList.add(oldItems[i]);
								}

								//New query with the given settings
								//if(oldItemsList.contains((index))==true){
								if(list.contains((index))==true){
									IStringMatrix matrix, matrix2;
									try {
										spinn.setSelection(50);
										matrix = chembl.MossProtFamilyCompounds(selected, index, spinn.getSelection());
										matrix2 = chembl.MossProtFamilyCompounds(selected,index);
										cboxAct.setText(index);
										info.setText("Total compund hit: "+ matrix2.getRowCount());
										addToTable(matrix);
									} catch (BioclipseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}else{
									setErrorMessage("The activity " + index +" does not exist for the protein family " + selected + ".");
									info.setText("Total compund hit:");
									setPageComplete(false);
									button.setVisible(false);
								}
							}
						}else{
							cboxAct.setItems(item);
							cboxAct.setEnabled(true);
						}	
					}
				}catch (BioclipseException e1) {
					e1.printStackTrace();
				}
			}
		});


		/*Returns the available compunds for the family*/
		label = new Label(container, SWT.NONE);
		gridData = new GridData(GridData.FILL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		label.setLayoutData(gridData);
		label.setText("Choose one available activity");

		cboxAct = new Combo(container,SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		gridData.widthHint=100;
		String[] item = { "No available activity" };
		cboxAct.setItems(item);
		cboxAct.setLayoutData(gridData);
		cboxAct.setEnabled(false);
		cboxAct.setToolTipText("These activities are only accurate for chosen protein");
		//Listener for available activities(IC50, Ki etc)
		cboxAct.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				String selected = cboxAct.getItem(cboxAct.getSelectionIndex());
				try{
					setErrorMessage(null);
					table.clearAll();
					table.removeAll();
					spinn.setSelection(50);
					IStringMatrix matrix = chembl.MossProtFamilyCompounds(cbox.getItem(cbox.getSelectionIndex()), selected,50);
					//IStringMatrix matrix = chembl.MossProtFamilyCompounds(cbox.getItem(cbox.getSelectionIndex()), selected, spinn.getSelection());
					addToTable(matrix);
					button.setEnabled(true);
					spinn.setEnabled(true);
					cboxAct.setEnabled(true);

					
					/*Count the amount of compounds there is for one hit,
					 * i.e. same query without limit.
					 * */
					 
					try {
						IStringMatrix matrix2 = chembl.MossProtFamilyCompounds(cbox.getItem(cbox.getSelectionIndex()),cboxAct.getItem(cboxAct.getSelectionIndex()));
						info.setText("Total compund hit: "+ matrix2.getRowCount());
					} catch (BioclipseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}catch(BioclipseException e1){
					e1.printStackTrace();
				}
				setPageComplete(true);
			} 
		});
		check = new Button(container, SWT.CHECK);
		check.setText("Cut-off");
		gridData = new GridData(GridData.BEGINNING);
		gridData.horizontalSpan = 1;
		check.setLayoutData(gridData);
		/*Limits the search
		 * The users are able to limit there search or to be saved data.*/

		button = new Button(container, SWT.PUSH);
		button.setText("Histogram");
		gridData = new GridData();
		gridData.horizontalSpan = 1; 
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
//			    
//				CategoryDataset data = new CategoryDataset(); 
//				data.
//				data.setValue("Category 1", 43.2); 
//				data.setValue("Category 2", 27.9); 
//				data.setValue("Category 3", 79.5); 
//				
//			ChartFrame c = ChartFactory.createBarChart("histogram", "","", data, true, true, true, true)
//				 XYBarChartDemo1 xybarchartdemo1 = new XYBarChartDemo1("State Executions - USA");
//			        xybarchartdemo1.pack();
//			        RefineryUtilities.centerFrameOnScreen(xybarchartdemo1);
//			        xybarchartdemo1.setVisible(true);
//			    
//				
//				
//				
//				JFreeChart chart = ChartFactory.createPieChart(
//				"Sample Pie Chart", data,true,true,false);
//				
//				ChartFrame frame = new ChartFrame("First", chart); 
//				frame.pack(); 
//				frame.setVisible(true);
				
				
//				Shell shell = new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
//				final GridLayout layout = new GridLayout(2, false);
//				shell.setLayout(layout);
//				text = new Text(shell, SWT.NONE);
//				text.setText("Annzi har en bra dag");
//				shell.pack();
//				shell.open();
			}
			});
		
		spinn = new Spinner(container,SWT.PUSH);
		spinn.setTextLimit(1000000);
		spinn.setSelection(0);
		gridData = new GridData(GridData.BEGINNING);
		gridData.horizontalSpan = 1;
		spinn.setLayoutData(gridData);
		
		spinn = new Spinner(container,SWT.PUSH);
		spinn.setTextLimit(1000000);
		spinn.setSelection(10000);
		gridData = new GridData(GridData.BEGINNING);
		gridData.horizontalSpan = 1;
		spinn.setLayoutData(gridData);
		
		label = new Label(container, SWT.NONE);
		gridData = new GridData(GridData.FILL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		label.setLayoutData(gridData);
		label.setText("Limit");

		spinn = new Spinner(container, SWT.BORDER);
		gridData = new GridData();
		spinn.setLayoutData(gridData);
		spinn.setSelection(50);
		spinn.setMaximum(10000000);
		spinn.setIncrement(50);
		spinn.setEnabled(false);
		gridData.widthHint=100;
		gridData.horizontalSpan = 1;
		spinn.setToolTipText("Limits the search, increases by 50");
		spinn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selected = spinn.getSelection();
				try{
					table.clearAll();
					table.removeAll();
					IStringMatrix matrix = chembl.MossProtFamilyCompounds(cbox.getItem(cbox.getSelectionIndex()),cboxAct.getItem(cboxAct.getSelectionIndex()), selected);
					table.setVisible(true);
					addToTable(matrix);
				}catch(BioclipseException e1){
					e1.printStackTrace();
				}
			}});
		info = new Label(container, SWT.NONE);
		gridData = new GridData();
		info.setLayoutData(gridData);
		gridData.horizontalSpan = 2;
		gridData.widthHint = 400;
		info.setText("Total compound hit:" );

		table = new Table(container, SWT.BORDER );
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		//		table.setVisible(false);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.widthHint=300;
		gridData.heightHint=300;
		gridData.horizontalSpan = 2;
		table.setLayoutData(gridData);
		column1 = new TableColumn(table, SWT.NONE);
		column1.setText("Compounds (SMILES)"); 

		//Button that adds all hits to the limit
		button = new Button(container, SWT.PUSH);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		button.setLayoutData(gridData);
		button.setText("Add all");
		button.setEnabled(false);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStringMatrix matrix;
				try {
					table.removeAll();
					matrix = chembl.MossProtFamilyCompounds(cbox.getItem(cbox.getSelectionIndex()),cboxAct.getItem(cboxAct.getSelectionIndex()));
					spinn.setSelection(matrix.getRowCount());
					addToTable(matrix);
				} catch (BioclipseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});


		label = new Label(container, SWT.NONE);
		label.setText("File directory: ");
		gridData = new GridData(GridData.FILL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		label.setLayoutData(gridData);

		text = new Text(container, SWT.BORDER|SWT.FILL);
		text.setText("MossFile");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 1;
		text.setLayoutData(gridData);

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (e.getSource() instanceof Text) {
					Text txt = (Text) e.getSource();
					((ChemblMossWizard) getWizard()).data.m  = txt.getText();
				}
			}
		});
	
		buttonb = new Button(container, SWT.NONE);
		buttonb.setText("Browse");
		gridData = new GridData();
		gridData.horizontalSpan = 1;
		buttonb.setLayoutData(gridData);
	}//end container

	// General method for adding items(i.e. compounds) to the table
	public void addToTable(IStringMatrix matrix){
		for(int r = 1; r < matrix.getRowCount()+1; r++){	
			TableItem item= new TableItem(table, SWT.NONE);
			item.setText(0,r+" "+ matrix.get(r, matrix.getColumnName(1)));
			column1.pack();
		}
		//If there are more than one column this should be used since it will add to more then one 
		// column in the table
		//		for(int r = 1; r < matrix.getRowCount()+1; r++){	
		//			TableItem item = new TableItem(table, SWT.NULL);
		//			for(int i = 0; i < matrix.getColumnCount(); i++){	
		//				item.setText(i, matrix.get(r, matrix.getColumnName(i+1)));
		//			}

		((ChemblMossWizard) getWizard()).data.matrix = matrix; 
	}
	@Override
	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable) throws InvocationTargetException,
			InterruptedException {
		// TODO Auto-generated method stub
		
	}

}

