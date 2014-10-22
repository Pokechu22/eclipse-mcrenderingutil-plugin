package pokechu22.plugins.eclipse.mcrendererutil.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.jface.fieldassist.ControlDecoration;

public class AdvancedAddFaceDialog extends TitleAreaDialog {
	
	/**
	 * An individual point that can be clicked on.
	 * 
	 * @author Pokechu22
	 *
	 */
	public static class ClickPoint {
		public ClickPoint(int clickedX, int clickedY,
				double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.clickedX = (int)clickedX;
			this.clickedY = (int)clickedY;
		}
		
		@Override
		public String toString() {
			return "ClickPoint [x=" + x + ", y=" + y + ", z=" + z
					+ ", clickedX=" + clickedX + ", clickedY=" + clickedY
					+ "]";
		}

		public double x;
		public double y;
		public double z;
		public int clickedX;
		public int clickedY;
	}
	
	/**
	 * Contains names of variables used in faces.
	 * 
	 * @author Pokechu22
	 *
	 */
	public static class VariableNames {
		public VariableNames(String tesselator, String x, String y, String z,
				String minU, String maxU, String minV, String maxV) {
			this.tesselator = tesselator;
			this.x = x;
			this.y = y;
			this.z = z;
			this.minU = minU;
			this.maxU = maxU;
			this.minV = minV;
			this.maxV = maxV;
		}
		
		public String tesselator;
		public String x;
		public String y;
		public String z;
		
		public String minU;
		public String maxU;
		public String minV;
		public String maxV;
	}
	
	/**
	 * A canvas for creation of faces.
	 */
	protected class FaceCreationCanvas extends Canvas {
		
		public ClickPoint[] points = new ClickPoint[4];
		protected int pointsClicked = 0;
				
		/**
		 * The currently chosen point.  If null, don't draw.
		 */
		private ClickPoint clicked = null;
		
		/**
		 * Gets the locations that can be clicked on.
		 * @return
		 */
		protected ClickPoint[] getClickPoints() {
			//X and Y constants, which are scaled.
			final double x = (getSize().x - 40) / 4.0;
			final double y = (getSize().y - 35) / 10.0;
			
			//And the x and y point positions.
			final int[] xs = {
					(int)(0 * x) + 10,
					(int)(1 * x) + 15,
					(int)(2 * x) + 20,
					(int)(3 * x) + 25,
					(int)(4 * x) + 30,
			};
			
			final int[] ys = {
					(int)((0 * y) + 5),
					(int)((1 * y) + 7.5),
					(int)((2 * y) + 10),
					(int)((3 * y) + 12.5),
					(int)((4 * y) + 15),
					(int)((5 * y) + 17.5),
					(int)((6 * y) + 20),
					(int)((7 * y) + 22.5),
					(int)((8 * y) + 25),
					(int)((9 * y) + 27.5),
					(int)((10 * y) + 30)
			};
			
			return new ClickPoint[] {
					//TopLeft corner.
					new ClickPoint(xs[0], ys[2], 0, 1, 0),
					//Between TopLeft and TopTop
					new ClickPoint(xs[1], ys[1], 0, 1, .5),
					//TopTop
					new ClickPoint(xs[2], ys[0], 0, 1, 1),
					//TopTop and TopRight
					new ClickPoint(xs[3], ys[1], .5, 1, 1),
					//TopRight
					new ClickPoint(xs[4], ys[2], 1, 1, 1),
					//TopRight and TopBot
					new ClickPoint(xs[3], ys[3], 1, 1, .5),
					//TopBot
					new ClickPoint(xs[2], ys[4], 1, 1, 0),
					//TopBot and TopLeft
					new ClickPoint(xs[1], ys[3], .5, 1, 0),
					//TopLeft and BotLeft
					new ClickPoint(xs[0], ys[5], 0, .5, 0),
					//TopTop and BotTop
					new ClickPoint(xs[2], ys[3], 0, .5, 1),
					//TopRight and BotRight
					new ClickPoint(xs[4], ys[5], 1, .5, 1),
					//TopBot and BotBot
					new ClickPoint(xs[2], ys[7], 1, .5, 0),
					//BotLeft.
					new ClickPoint(xs[0], ys[8], 0, 1, 0),
					//BotLeft and BotTop
					new ClickPoint(xs[1], ys[9], 0, 1, .5),
					//BotTop                   
					new ClickPoint(xs[2], ys[10], 0, 1, 1),
					//BotTop and BotRight      
					new ClickPoint(xs[3], ys[9], .5, 1, 1),
					//BotRight                 
					new ClickPoint(xs[4], ys[8], 1, 1, 1),
					//BotRight and BotBot      
					new ClickPoint(xs[3], ys[7], 1, 1, .5),
					//BotBot                   
					new ClickPoint(xs[2], ys[6], 1, 1, 0),
					//BotBot and BotLeft       
					new ClickPoint(xs[1], ys[7], .5, 1, 0),
			};
		};
		
		public FaceCreationCanvas(Composite parent, int style) {
			super(parent, style);
			
			this.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					//X and Y constants, which are scaled.
					final double x = (getSize().x - 40) / 2.0;
					final double y = (getSize().y - 35) / 5.0;
					
					//And the x and y point positions.
					final int[] xs = {
							(int)(0 * x) + 10,
							(int)(1 * x) + 20,
							(int)(2 * x) + 30
					};
					
					final int[] ys = {
							(int)(0 * y) + 5,
							(int)(1 * y) + 10,
							(int)(2 * y) + 15,
							(int)(3 * y) + 20,
							(int)(4 * y) + 25,
							(int)(5 * y) + 30
					};
					
					e.gc.setBackground(new Color(shell.getDisplay(),
							0, 0, 0));
					e.gc.setForeground(new Color(shell.getDisplay(),
							0, 0, 0));
					
					//Top square
					e.gc.drawPolygon(new int[] {
							xs[0], ys[1],
							xs[1], ys[0],
							xs[2], ys[1],
							xs[1], ys[2]
					});
					
					//Bottom square
					e.gc.drawPolygon(new int[] {
							xs[0], ys[4],
							xs[1], ys[3],
							xs[2], ys[4],
							xs[1], ys[5]
					});
					
					//Connecting lines.
					e.gc.drawLine(xs[0], ys[1],
							xs[0], ys[4]);
					e.gc.drawLine(xs[1], ys[0],
							xs[1], ys[3]);
					e.gc.drawLine(xs[2], ys[1],
							xs[2], ys[4]);
					e.gc.drawLine(xs[1], ys[2],
							xs[1], ys[5]);
					
					//Circle for current point.
					if (clicked != null) {
						e.gc.fillOval(clicked.clickedX - 6, clicked.clickedY - 6, 12, 12);
					}
					
					//Clicked points.
					e.gc.setForeground(new Color(shell.getDisplay(),
							63, 63, 63));
					for (int i = 0; i < pointsClicked; i++) {
						e.gc.fillOval(points[i].clickedX - 6, points[i].clickedY - 6, 12, 12);
						e.gc.drawLine(points[i].clickedX, points[i].clickedY, 
								points[(i + 1) % pointsClicked].clickedX, points[(i + 1) % pointsClicked].clickedY);
					}
				}
			});
			
			this.addMouseMoveListener(new MouseMoveListener() {
				public void mouseMove(MouseEvent e) {
					ClickPoint[] clickPoints = getClickPoints();
					for (ClickPoint point : clickPoints) {
						//Delta or distance values.
						final int dx = point.clickedX - e.x;
						final int dy = point.clickedY - e.y;
						
						//Circle distances - x^2 + y^2 < distance^2
						if ((dx * dx) +
								(dy * dy) <
								(15 * 15)) {
							clicked = point;
							redraw();
							return;
						}
					}
					clicked = null;
					redraw();
				}
			});
			
			this.addMouseTrackListener(new MouseTrackAdapter() {
				public void mouseExit(MouseEvent e) {
					clicked = null;
					redraw();
				}
			});
			
			this.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					if (clicked != null) {
						if (pointsClicked < 4) {
							points[pointsClicked] = clicked;
							pointsClicked++;
							redraw();
							
							onPointsChange();
						}
					}
				}
			});
		}
		
		/**
		 * Called when the points are changed.
		 * @param point
		 */
		protected void onPointsChange() {}
	}
	
	private final Shell shell;
	
	private VariableNames names;
	
	private FaceCreationCanvas canvas;
	private Table table;
	
	private Text textTesselatorName;
	private Text textX;
	private Text textY;
	private Text textZ;
	private Text textMinU;
	private Text textMaxU;
	private Text textMinV;
	private Text textMaxV;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AdvancedAddFaceDialog(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		this.shell = parentShell;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Draw 4 points to create your new face.");
		setTitle("Add face");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		//Custom control inside which doesn't like rendering ;(
		Group grpChoosePoints = new Group(container, SWT.NONE);
		grpChoosePoints.setToolTipText("Click on the different points you want to use for your new face.");
		FormData fd_grpChoosePoints = new FormData();
		fd_grpChoosePoints.bottom = new FormAttachment(0, 142);
		fd_grpChoosePoints.right = new FormAttachment(0, 142);
		fd_grpChoosePoints.top = new FormAttachment(0, 10);
		fd_grpChoosePoints.left = new FormAttachment(0, 10);
		grpChoosePoints.setLayoutData(fd_grpChoosePoints);
		grpChoosePoints.setText("Choose Points");
		grpChoosePoints.setLayout(null);
		
		canvas = new FaceCreationCanvas(grpChoosePoints, SWT.NONE) {
			@Override
			protected void onPointsChange() {
				for (int i = 0; i < this.pointsClicked; i++) {
					table.getItem(i).setText(1, Double.toString(this.points[i].x));
					table.getItem(i).setText(2, Double.toString(this.points[i].y));
					table.getItem(i).setText(3, Double.toString(this.points[i].z));
				}
				
				//Enable/disable OK button.
				getButton(IDialogConstants.OK_ID).setEnabled(pointsClicked == 4);
			}
		};
		canvas.setToolTipText("Click on the different points you want to use for your new face.");
		
		Group grpResult = new Group(container, SWT.NONE);
		grpResult.setToolTipText("The resulting coordinates that are used.  \r\n\r\nThis table cannot be modified directly but instead is configured by clicking on points on the cube to the side.  \r\n\r\nSeriously, the entire point of this plugin is so that you don't need to manually type in these numbers.  Don't try.");
		FormData fd_grpResult = new FormData();
		fd_grpResult.bottom = new FormAttachment(0, 142);
		fd_grpResult.right = new FormAttachment(0, 434);
		fd_grpResult.top = new FormAttachment(0, 10);
		fd_grpResult.left = new FormAttachment(0, 148);
		grpResult.setLayoutData(fd_grpResult);
		grpResult.setText("Result");
		grpResult.setLayout(null);
		
		table = new Table(grpResult, SWT.BORDER | SWT.VIRTUAL);
		table.setToolTipText("The resulting coordinates that are used.  \r\n\r\nThis table cannot be modified directly but instead is configured by clicking on points on the cube to the side.  \r\n\r\nSeriously, the entire point of this plugin is so that you don't need to manually type in these numbers.  Don't try.");
		table.setBounds(10, 19, 265, 104);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNum = new TableColumn(table, SWT.NONE);
		tblclmnNum.setToolTipText("The ID of the individual points.  The order shown here is the order the actual function will use.");
		tblclmnNum.setResizable(false);
		tblclmnNum.setWidth(21);
		tblclmnNum.setText("#");
		
		TableItem tableItem_0 = new TableItem(table, SWT.NONE);
		tableItem_0.setText("1");
		
		TableColumn tblclmnX = new TableColumn(table, SWT.NONE);
		tblclmnX.setToolTipText("The x-coordinate for the individual point.");
		tblclmnX.setResizable(false);
		tblclmnX.setWidth(80);
		tblclmnX.setText("X");
		
		TableColumn tblclmnY = new TableColumn(table, SWT.NONE);
		tblclmnY.setToolTipText("The y-coordinate for the individual point.");
		tblclmnY.setWidth(80);
		tblclmnY.setText("Y");
		
		TableColumn tblclmnZ = new TableColumn(table, SWT.NONE);
		tblclmnZ.setToolTipText("The z-coordinate for the individual point.");
		tblclmnZ.setWidth(80);
		tblclmnZ.setText("Z");
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText("2");
		
		TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText("3");
		
		TableItem tableItem_3 = new TableItem(table, SWT.NONE);
		tableItem_3.setText("4");
		
		Group grpVariableNames = new Group(container, SWT.NONE);
		grpVariableNames.setToolTipText("Configure the names of different variables used in the output.");
		grpVariableNames.setLayout(null);
		FormData fd_grpVariableNames = new FormData();
		fd_grpVariableNames.bottom = new FormAttachment(0, 266);
		fd_grpVariableNames.right = new FormAttachment(0, 434);
		fd_grpVariableNames.top = new FormAttachment(0, 148);
		fd_grpVariableNames.left = new FormAttachment(0, 10);
		grpVariableNames.setLayoutData(fd_grpVariableNames);
		grpVariableNames.setText("Variable Names");
		
		Group grpTesselator = new Group(grpVariableNames, SWT.NONE);
		grpTesselator.setToolTipText("Enter the name that the Tesselator uses.");
		grpTesselator.setBounds(13, 14, 96, 44);
		grpTesselator.setText("Tesselator");
		grpTesselator.setLayout(null);
		
		textTesselatorName = new Text(grpTesselator, SWT.BORDER);
		textTesselatorName.setToolTipText("Enter the name that the Tesselator uses.");
		textTesselatorName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textTesselatorName.setText("t");
		textTesselatorName.setBounds(10, 14, 76, 19);
		
		Group grpX = new Group(grpVariableNames, SWT.NONE);
		grpX.setToolTipText("Enter the name of the x-coordinate variable (part of the renderer function parameters)");
		grpX.setLayout(null);
		grpX.setText("X");
		grpX.setBounds(115, 14, 96, 44);
		
		textX = new Text(grpX, SWT.BORDER);
		textX.setToolTipText("Enter the name of the x-coordinate variable (part of the renderer function parameters)");
		textX.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textX.setText("x");
		textX.setBounds(10, 14, 76, 19);
		
		Group grpY = new Group(grpVariableNames, SWT.NONE);
		grpY.setToolTipText("Enter the name of the y-coordinate variable (part of the renderer function parameters)");
		grpY.setLayout(null);
		grpY.setText("Y");
		grpY.setBounds(215, 14, 96, 44);
		
		textY = new Text(grpY, SWT.BORDER);
		textY.setToolTipText("Enter the name of the y-coordinate variable (part of the renderer function parameters)");
		textY.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textY.setText("y");
		textY.setBounds(10, 14, 76, 19);
		
		Group grpZ = new Group(grpVariableNames, SWT.NONE);
		grpZ.setToolTipText("Enter the name of the z-coordinate variable (part of the renderer function parameters)");
		grpZ.setLayout(null);
		grpZ.setText("Z");
		grpZ.setBounds(317, 14, 97, 44);
		
		textZ = new Text(grpZ, SWT.BORDER);
		textZ.setToolTipText("Enter the name of the z-coordinate variable (part of the renderer function parameters)");
		textZ.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textZ.setText("z");
		textZ.setBounds(10, 14, 77, 19);
		
		Group grpMinimumU = new Group(grpVariableNames, SWT.NONE);
		grpMinimumU.setToolTipText("Enter the minimum U coordinate (U and V are texture coordinates)");
		grpMinimumU.setLayout(null);
		grpMinimumU.setText("Minimum U");
		grpMinimumU.setBounds(13, 64, 96, 44);
		
		textMinU = new Text(grpMinimumU, SWT.BORDER);
		textMinU.setToolTipText("Enter the minimum U coordinate (U and V are texture coordinates)");
		textMinU.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textMinU.setText("minU");
		textMinU.setBounds(10, 14, 76, 19);
		
		Group grpMaximumU = new Group(grpVariableNames, SWT.NONE);
		grpMaximumU.setToolTipText("Enter the maximum U coordinate (U and V are texture coordinates)");
		grpMaximumU.setLayout(null);
		grpMaximumU.setText("Maximum U");
		grpMaximumU.setBounds(115, 64, 96, 44);
		
		textMaxU = new Text(grpMaximumU, SWT.BORDER);
		textMaxU.setToolTipText("Enter the maximum U coordinate (U and V are texture coordinates)");
		textMaxU.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textMaxU.setText("maxU");
		textMaxU.setBounds(10, 14, 76, 19);
		
		Group grpMinimumV = new Group(grpVariableNames, SWT.NONE);
		grpMinimumV.setToolTipText("Enter the minimum V coordinate (U and V are texture coordinates)");
		grpMinimumV.setLayout(null);
		grpMinimumV.setText("Minimum V");
		grpMinimumV.setBounds(215, 64, 96, 44);
		
		textMinV = new Text(grpMinimumV, SWT.BORDER);
		textMinV.setToolTipText("Enter the minimum V coordinate (U and V are texture coordinates)");
		textMinV.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textMinV.setText("minV");
		textMinV.setBounds(10, 14, 76, 19);
		
		Group grpMaximumV = new Group(grpVariableNames, SWT.NONE);
		grpMaximumV.setToolTipText("Enter the maximum V coordinate (U and V are texture coordinates)");
		grpMaximumV.setLayout(null);
		grpMaximumV.setText("Maximum V");
		grpMaximumV.setBounds(317, 64, 97, 44);
		
		textMaxV = new Text(grpMaximumV, SWT.BORDER);
		textMaxV.setToolTipText("Enter the maximum V coordinate (U and V are texture coordinates)");
		textMaxV.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateVariableNames();
			}
		});
		textMaxV.setText("maxV");
		textMaxV.setBounds(10, 14, 77, 19);
		canvas.setBounds(13, 25, 106, 94);
		
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.setEnabled(false);
		
		ControlDecoration controlDecoration = new ControlDecoration(button, SWT.LEFT | SWT.TOP);
		controlDecoration.setDescriptionText("All 4 points must be entered before you can continue");
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 414);
	}
	
	/**
	 * Gets the resulting 4 ClickPoints.
	 * @return
	 */
	public ClickPoint[] getResult() {
		if (canvas.pointsClicked != 4) {
			return null;
		}
		return canvas.points;
	}
	
	/**
	 * Gets the variable names used.
	 * @return
	 */
	public VariableNames getVariableNames() {
		return names;
	}
	
	/**
	 * Updates the cached variable names.  Called when text is changed.
	 */
	private void updateVariableNames() {
		//Due to initialization stuff, null tests are needed.  Sigh.
		this.names = new VariableNames(
				textTesselatorName != null ? textTesselatorName.getText() : "",
				textX != null ? textX.getText() : "",
				textY != null ? textY.getText() : "",
				textZ != null ? textZ.getText() : "",
				textMinU != null ? textMinU.getText() : "",
				textMaxU != null ? textMaxU.getText() : "",
				textMinV != null ? textMinV.getText() : "",
				textMaxV != null ? textMaxV.getText() : "");
	}
}
