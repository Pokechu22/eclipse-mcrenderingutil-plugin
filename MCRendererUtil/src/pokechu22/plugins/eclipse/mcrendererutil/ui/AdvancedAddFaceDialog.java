package pokechu22.plugins.eclipse.mcrendererutil.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;

public class AdvancedAddFaceDialog extends TitleAreaDialog {
	
	/**
	 * A canvas for creation of faces.
	 */
	protected class FaceCreationCanvas extends Canvas {
		private int clickedX = -100;
		private int clickedY = -100;
		
		/**
		 * Gets the locations that can be clicked on.
		 * @return
		 */
		protected int[] getClickPoints() {
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
			
			return new int[] {
					//TopLeft corner.
					xs[0], ys[2],
					//Between TopLeft and TopTop
					xs[1], ys[1],
					//TopTop
					xs[2], ys[0],
					//TopTop and TopRight
					xs[3], ys[1],
					//TopRight
					xs[4], ys[2],
					//TopRight and TopBot
					xs[3], ys[3],
					//TopBot
					xs[2], ys[4],
					//TopBot and TopLeft
					xs[1], ys[3],
					//TODO: Between tops and bottoms.
					//BotLeft.
					xs[0], ys[8],
					//BotLeft and BotTop
					xs[1], ys[9],
					//BotTop
					xs[2], ys[10],
					//BotTop and BotRight
					xs[3], ys[9],
					//BotRight
					xs[4], ys[8],
					//BotRight and BotBot
					xs[3], ys[7],
					//BotBot
					xs[2], ys[6],
					//BotBot and BotLeft
					xs[1], ys[7],
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
					e.gc.fillOval(clickedX - 6, clickedY - 6, 12, 12);
				}
			});
			
			this.addMouseMoveListener(new MouseMoveListener() {
				public void mouseMove(MouseEvent e) {
					int[] clickPoints = getClickPoints();
					for (int i = 0; i + 1 < clickPoints.length; i += 2) {
						final int x = clickPoints[i];
						final int y = clickPoints[i + 1];
						
						//Delta/distance values.
						final int dx = x - e.x;
						final int dy = y - e.y;
						
						//Circle distances - x^2 + y^2 < distance^2
						if ((dx * dx) +
								(dy * dy) <
								(10 * 10)) {
							clickedX = x;
							clickedY = y;
							
							break;
						}
					}
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
		
		canvas = new FaceCreationCanvas(container, SWT.BORDER);
		canvas.setBounds(10, 10, 132, 132);

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
