package pokechu22.plugins.eclipse.mcrendererutil.popup.actions;

import java.text.MessageFormat;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog;
import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog.ClickPoint;
import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog.VariableNames;

public class AddFace implements IObjectActionDelegate {

	private Shell shell;

	private IMethod currentMethod;

	/**
	 * Use maxU instead of minU at the specified tick.
	 */
	private static final boolean[] useMaxUAtTick = {
		false,
		true,
		true,
		false
	};
	
	/**
	 * Use maxV instead of minV at the specified tick.
	 */
	private static final boolean[] useMaxVAtTick = {
		false,
		false,
		true,
		true
	};
	
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
	 * Creates the text needed for a single line of tesselator code.
	 * @param names
	 * @param x
	 * @param y
	 * @param z
	 * @param tick The current tick, used to determine min/max U and V.
	 * @return
	 */
	private String getTesselatorText(VariableNames names, double x, 
			double y, double z, int tick) {
		return MessageFormat.format(
				"{0}.addVertexWithUV({1} + {4}, {2} + {5}, {3} + {6}, {7}, {8});",
				names.tesselator, names.x, names.y, names.z, x, y, z, 
				useMaxUAtTick[tick] ? names.maxU : names.minU, 
				useMaxVAtTick[tick] ? names.maxV : names.minV);	
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
		VariableNames names = d.getVariableNames();
		
		try {
			ICompilationUnit cu = currentMethod.getCompilationUnit();
			
			final String source = currentMethod.getSource();
			int newLineIndex = source.lastIndexOf(System.lineSeparator());
			if (newLineIndex <= -1) {
				//TODO handle this.
				throw new RuntimeException("Couldn't find newline in method!");
			}
			final String before = source.substring(0, newLineIndex);
			final String after = source.substring(newLineIndex);
			
			final String newLineIndent = System.lineSeparator() + "\t";
			
			StringBuilder text = new StringBuilder(before); 

			int tick = 0;
			
			for (ClickPoint point : result) {
				text.append(newLineIndent).append(
						getTesselatorText(names, point.x, point.y, point.z, tick));
				//Cycles the tick.
				tick++;
				tick %= 4;
			}
			
			text.append(after);
			
			ReplaceEdit edit = new ReplaceEdit(currentMethod.getSourceRange().getOffset(), currentMethod.getSourceRange().getLength(), text.toString());
			
			cu.applyTextEdit(edit, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

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
