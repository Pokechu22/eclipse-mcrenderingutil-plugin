package pokechu22.plugins.eclipse.mcrendererutil.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog;
import pokechu22.plugins.eclipse.mcrendererutil.ui.AdvancedAddFaceDialog.ClickPoint;

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
		AdvancedAddFaceDialog d = new AdvancedAddFaceDialog(shell);
		
		int resultValue = d.open();
		if (resultValue != AdvancedAddFaceDialog.OK) {
			//Canceled.
			return;
		}
		
		ClickPoint[] result = d.getResult();
		
		//Following is based off of http://stackoverflow.com/a/26421273/3991344 and http://help.eclipse.org/indigo/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Fguide%2Fjdt_api_manip.htm
		
		try {
			ICompilationUnit cu = currentMethod.getCompilationUnit(); 
			String source = cu.getSource();
			Document document= new Document(source);


			//Get the compilation unit for traversing AST
		    final ASTParser parser = ASTParser.newParser(AST.JLS8);
		    parser.setSource(currentMethod.getCompilationUnit());
		    parser.setResolveBindings(true);

		    final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

		    // record modification - to be later written with ASTRewrite
		    compilationUnit.recordModifications();

		    // Get AST node for IMethod
		    int methodIndex = currentMethod.getCompilationUnit().getSource().indexOf(currentMethod.getSource());

		    ASTNode methodASTNode = NodeFinder.perform(compilationUnit.getRoot(), methodIndex, currentMethod.getSource().length());

		    //Create the annotation
		    final NormalAnnotation newNormalAnnotation = methodASTNode.getAST().newNormalAnnotation();
		    newNormalAnnotation.setTypeName(methodASTNode.getAST().newName("AnnotationTest"));
		    
		    ASTRewrite rewrite = ASTRewrite.create(compilationUnit.getAST());
		    rewrite.replace(methodASTNode, newNormalAnnotation, null);
		    

		    // computation of the text edits
		    TextEdit edits = rewrite.rewriteAST(document, cu.getJavaProject().getOptions(true));
		    
		    // computation of the new source code
		    edits.apply(document);
		    String newSource = document.get();

		    // update of the compilation unit
		    cu.getBuffer().setContents(newSource);
		    
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
