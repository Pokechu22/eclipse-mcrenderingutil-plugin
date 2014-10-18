package pokechu22.plugins.eclipse.mcrendererutil.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import pokechu22.plugins.eclipse.mcrendererutil.ui.ChooseFaceDirectionDialog;
import pokechu22.plugins.eclipse.mcrendererutil.ui.SizeFaceDialog;

public class AddFace implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public AddFace() {
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
		new SizeFaceDialog(shell).open();
		new ChooseFaceDirectionDialog(shell).open();
//		MessageDialog.openInformation(
//			shell,
//			"MCRendererUtil",
//			"Add face was executed.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
