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
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class AdvancedAddFaceDialog extends TitleAreaDialog {
	
	/**
	 * A canvas for creation of faces.
	 */
	protected class FaceCreationCanvas extends Canvas {
		/**
		 * An individual point that can be clicked on.
		 * 
		 * @author Pokechu22
		 *
		 */
		protected class ClickPoint {
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
		}
	}
	
	private final Shell shell;
	
	private FaceCreationCanvas canvas;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AdvancedAddFaceDialog(Shell parentShell) {
		super(parentShell);
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
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		//Custom control inside which doesn't like rendering ;(
		Group grpChoosePoints = new Group(container, SWT.NONE);
		grpChoosePoints.setText("Choose Points");
		grpChoosePoints.setBounds(10, 10, 132, 132);
		grpChoosePoints.setLayout(null);
		
		canvas = new FaceCreationCanvas(grpChoosePoints, SWT.NONE);
		canvas.setBounds(13, 25, 106, 94);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
