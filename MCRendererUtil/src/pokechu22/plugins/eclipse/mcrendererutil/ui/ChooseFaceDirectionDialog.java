package pokechu22.plugins.eclipse.mcrendererutil.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Label;

public class ChooseFaceDirectionDialog extends TitleAreaDialog {
	
	private final Shell shell;
	
	private Canvas frontCanvas;
	//Lighten values are the color, either 0 or 127.
	private int lightenFrontTop = 0;
	private int lightenFrontLeft = 0;
	private int lightenFrontRight = 0;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ChooseFaceDirectionDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Choose the direction for your new face - Names are based on looking from the +X axis");
		setTitle("Choose face direction");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		List list = new List(container, SWT.BORDER);
		list.setItems(new String[] {"Front", "Back", "Left", "Right", "Top", "Bottom"});
		list.setBounds(10, 10, 123, 132);
		
		frontCanvas = new Canvas(container, SWT.BORDER);
		frontCanvas.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				//If the cursor is no longer inside everything is deselected.
				lightenFrontTop = 0;
				lightenFrontLeft = 0;
				lightenFrontRight = 0;
				
				frontCanvas.redraw();
			}
		});
		frontCanvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				lightenFrontTop = ClickSection.TOP.isPointInside(e.x, e.y, frontCanvas.getSize().x, frontCanvas.getSize().y) ? 127 : 0;
				lightenFrontLeft = ClickSection.LEFT.isPointInside(e.x, e.y, frontCanvas.getSize().x, frontCanvas.getSize().y) ? 127 : 0;
				lightenFrontRight = ClickSection.RIGHT.isPointInside(e.x, e.y, frontCanvas.getSize().x, frontCanvas.getSize().y) ? 127 : 0;
				
				frontCanvas.redraw();
			}
		});
		frontCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(new Color(shell.getDisplay(), 255, lightenFrontTop, lightenFrontTop));
				e.gc.fillPolygon(ClickSection.TOP.getBounds(e.width, e.height));
				e.gc.setBackground(new Color(shell.getDisplay(), lightenFrontRight, 255, lightenFrontRight));
				e.gc.fillPolygon(ClickSection.RIGHT.getBounds(e.width, e.height));
				e.gc.setBackground(new Color(shell.getDisplay(), lightenFrontLeft, lightenFrontLeft, 255));
				e.gc.fillPolygon(ClickSection.LEFT.getBounds(e.width, e.height));
			}
		});
		frontCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
			}
		});
		frontCanvas.setBounds(139, 10, 123, 132);

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
	
	/**
	 * The section clicked on in the canvas for the block face.
	 * <code>null</code> should be returned if it resides in none.
	 * 
	 * @author Pokechu22
	 *
	 */
	protected static enum ClickSection {
		TOP {
			@Override
			public boolean isPointInside(int x, int y, int xMax, int yMax) {
				//Somewhat magic, but basically it's taking the distance from
				//the center and then dividing it by the scale, and then seeing
				//if the sum of both x and y is less than 1 (for a diamond 
				//shape).  It works perfectly.
				return (Math.abs(x - (xMax / 2.0)) / (xMax / 2.0)) +
						(Math.abs(y - (xMax / 3.0)) / (xMax / 3.0)) < 1.0; 
			}

			@Override
			public int[] getBounds(int xMax, int yMax) {
				return new int[] {
						0, yMax / 3,
						xMax / 2, 0,
						xMax, yMax / 3,
						xMax / 2, 2 * (yMax / 3),
				};
			}
		},
		RIGHT {
			@Override
			public boolean isPointInside(int x, int y, int xMax, int yMax) {
				//Horizontal bounds check
				if (x < 0 || x > xMax / 2) {
					return false;
				}
				
				//Vertical bounds check
				if (y < yMax / 3 || y > yMax) {
					return false;
				}
				
				//Diagonal check
				final double diag = (y / (yMax / 3.0)) - (x / (xMax / 2.0));
				if (diag < 1 || diag > 2) {
					return false;
				}
				
				return true;
			}

			@Override
			public int[] getBounds(int xMax, int yMax) {
				return new int[] {
						0, yMax / 3,
						xMax / 2, 2 * (yMax / 3),
						xMax / 2, yMax,
						0, 2 * (yMax / 3),
				};
			}
		},
		LEFT {
			@Override
			public boolean isPointInside(int x, int y, int xMax, int yMax) {
				//Horizontal bounds check
				if (x < xMax / 2 || x > xMax) {
					return false;
				}
				
				//Vertical bounds check
				if (y < yMax / 3 || y > yMax) {
					return false;
				}
				
				//Diagonal check (-x because of opposite direction)
				final double diag = (y / (yMax / 3.0)) - ((-x) / (xMax / 2.0));
				if (diag < 3 || diag > 4) {
					return false;
				}

				return true;
			}

			@Override
			public int[] getBounds(int xMax, int yMax) {
				return new int[] {
						xMax, 2 * (yMax / 3),
						xMax / 2, yMax,
						xMax / 2, 2 * (yMax / 3),
						xMax, yMax / 3,
				};
			}
		};
		
		public abstract boolean isPointInside(int x, int y, int xMax, int yMax);
		
		/**
		 * Gets the boundaries of this shape.
		 * @param xMax
		 * @param yMax
		 * 
		 * @return
		 */
		public abstract int[] getBounds(int xMax, int yMax);
		
		public static ClickSection getClickedArea(int x, int y, int xMax, int yMax) {
			for (ClickSection section : ClickSection.values()) {
				if (section.isPointInside(x, y, xMax, yMax)) {
					return section;
				}
			}
			return null;
		}
	}
}
