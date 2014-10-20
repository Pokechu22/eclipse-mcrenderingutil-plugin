package pokechu22.plugins.eclipse.mcrendererutil.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.jdt.core.IMethod;

import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog;

public class AddFace2 implements IObjectActionDelegate {

	private Shell shell;
	
	private IMethod currentMethod;
	
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
		new AdvancedAddFaceDialog(shell).open();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			action.setEnabled(false);
			return;
		}
		IStructuredSelection sel = (IStructuredSelection) selection;
		
		if (!(sel.getFirstElement() instanceof IMethod)) {
			//Only handles IMethods.
			action.setEnabled(false);
			return;
		}
		
		action.setEnabled(true);
		this.currentMethod = (IMethod) sel.getFirstElement();
	}

}
