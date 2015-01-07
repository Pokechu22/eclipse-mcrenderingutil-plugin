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
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class SizeFaceDialog extends TitleAreaDialog {
	/**
	 * The size of a face.  While 'x' and 'z' are used here, they actually
	 * represent two different faces and may be any set of variables.
	 * 
	 * TODO: Maybe this should be moved elsewhere?
	 * 
	 * @author Pokechu22
	 *
	 */
	public static class FaceSize {
		public FaceSize(double minX, double minZ, double maxX, double maxZ) {
			this.minX = minX;
			this.minZ = minZ;
			this.maxX = maxX;
			this.maxZ = maxZ;
		}
		
		public double minX;
		public double minZ;
		public double maxX;
		public double maxZ;
	}
	
	/**
	 * Runs this dialog and gets the result.
	 * 
	 * @return The FaceSize if ok'd or null if canceled.
	 */
	public static FaceSize calculate(Shell shell) {
		SizeFaceDialog dialog = new SizeFaceDialog(shell);
		int result = dialog.open();
		
		//If any result other than OK was given, return null.
		if (result != SizeFaceDialog.OK) {
			return null;
		} else {
			return dialog.getResult();
		}
	}
	
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SizeFaceDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}

	private Canvas canvas = null;
	
	private final Shell shell;
	
	protected double minX = 0;
	protected double minY = 0;
	protected double maxX = 1;
	protected double maxY = 1;
	
	/**
	 * Gets the FaceSize result from this dialog.
	 * 
	 * @return
	 */
	public FaceSize getResult() {
		return new FaceSize(minX, minY, maxX, maxY);
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Size Face");
		setMessage("Choose the size of your new face");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		new Label(container, SWT.NONE);
		
		final Scale scale_maxX = new Scale(container, SWT.BORDER);
		scale_maxX.setIncrement(10);
		scale_maxX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				maxX = (double)scale_maxX.getSelection() / (double)scale_maxX.getMaximum();
				canvas.redraw();
			}
		});
		GridData gd_scale_maxX = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale_maxX.widthHint = 150;
		scale_maxX.setLayoutData(gd_scale_maxX);
		scale_maxX.setPageIncrement(50);
		scale_maxX.setSelection(100);
		new Label(container, SWT.NONE);
		
		final Scale scale_minY = new Scale(container, SWT.BORDER | SWT.VERTICAL);
		scale_minY.setSelection(100);
		scale_minY.setIncrement(10);
		scale_minY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				minY = 1 - ((double)scale_minY.getSelection() / (double)scale_minY.getMaximum());
				canvas.redraw();
			}
		});
		GridData gd_scale_minY = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale_minY.heightHint = 150;
		scale_minY.setLayoutData(gd_scale_minY);
		scale_minY.setPageIncrement(50);
		
		canvas = new Canvas(container, SWT.BORDER | SWT.NO_FOCUS);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(new Color(shell.getDisplay(), 0, 0, 0));
				
				e.gc.fillRectangle((int)(canvas.getSize().x * minX),
						canvas.getSize().y - (int)(canvas.getSize().y * minY),
						(int)(canvas.getSize().x * (maxX - minX)),
						(int)(canvas.getSize().y * (minY - maxY)));
			}
		});
		GridData gd_canvas = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_canvas.heightHint = 150;
		gd_canvas.widthHint = 150;
		canvas.setLayoutData(gd_canvas);
		
		final Scale scale_maxY = new Scale(container, SWT.BORDER | SWT.VERTICAL);
		scale_maxY.setIncrement(10);
		scale_maxY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				maxY = 1 - ((double)scale_maxY.getSelection() / (double)scale_maxY.getMaximum());
				canvas.redraw();
			}
		});
		GridData gd_scale_maxY = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale_maxY.heightHint = 150;
		scale_maxY.setLayoutData(gd_scale_maxY);
		scale_maxY.setPageIncrement(50);
		new Label(container, SWT.NONE);
		
		final Scale scale_minX = new Scale(container, SWT.BORDER);
		scale_minX.setIncrement(10);
		scale_minX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				minX = (double)scale_minX.getSelection() / (double)scale_minX.getMaximum();
				canvas.redraw();
			}
		});
		GridData gd_scale_minX = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scale_minX.widthHint = 150;
		scale_minX.setLayoutData(gd_scale_minX);
		scale_minX.setPageIncrement(50);
		new Label(container, SWT.NONE);

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
		return new Point(271, 412);
	}
}
