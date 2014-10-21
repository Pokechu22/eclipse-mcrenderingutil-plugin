package pokechu22.plugins.eclipse.mcrendererutil.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog;
import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog.ClickPoint;

public class AddFace2 implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public AddFace2() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		AdvancedAddFaceDialog d = new AdvancedAddFaceDialog(shell);
		
		int resultValue = d.open();
		if (resultValue != AdvancedAddFaceDialog.OK) {
			//Canceled.
			return;
		}
		
		ClickPoint[] result = d.getResult();
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
