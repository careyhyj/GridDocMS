package ui.model;

import javax.swing.tree.DefaultMutableTreeNode;

public class GDMSTreeNode extends DefaultMutableTreeNode {
	private String nodeName;
	public GDMSTreeNodeType nodeType;
	
	public GDMSTreeNode(String nodeName, GDMSTreeNodeType nodeType) {
		super(nodeName);
		this.nodeName = nodeName;
		this.nodeType = nodeType;
	}
	
	public String getNodeName() {
		this.nodeName = super.toString();
		return this.nodeName;
	}
}

enum GDMSTreeNodeType {
	ROOT, GRIDDOC, SECTION, PARAGRAPH
}