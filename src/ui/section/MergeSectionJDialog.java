package ui.section;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.Color;
import javax.swing.UIManager;

public class MergeSectionJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	final JTextArea textAreaSelectedPara = new JTextArea();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MergeSectionJDialog dialog = new MergeSectionJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MergeSectionJDialog() {
		setTitle("合并节");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 144, 209);
		contentPanel.add(scrollPane);
		{
			final JTree tree = new JTree();
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					String paragraphPath = "/";
					Object[] selectionPathArray = tree.getSelectionPath().getPath();
					for(int i = 1; i < selectionPathArray.length; i++) {
						paragraphPath += selectionPathArray[i].toString() + "/";
					}
					textAreaSelectedPara.setText(paragraphPath);
				}
			});
			tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("Global View") {
					{
						DefaultMutableTreeNode node_1;
						DefaultMutableTreeNode node_2;
						DefaultMutableTreeNode node_3;
						node_1 = new DefaultMutableTreeNode("GridDoc1");
							node_2 = new DefaultMutableTreeNode("section1");
								node_2.add(new DefaultMutableTreeNode("paragraph2"));
							node_1.add(node_2);
							node_2 = new DefaultMutableTreeNode("section2");
								node_3 = new DefaultMutableTreeNode("section1");
									node_3.add(new DefaultMutableTreeNode("paragraph2"));
								node_2.add(node_3);
								node_2.add(new DefaultMutableTreeNode("paragraph3"));
							node_1.add(node_2);
							node_1.add(new DefaultMutableTreeNode("section3"));
							node_1.add(new DefaultMutableTreeNode("paragraph1"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode("GridDoc2");
							node_2 = new DefaultMutableTreeNode("section1");
								node_2.add(new DefaultMutableTreeNode("paragraph2"));
							node_1.add(node_2);
							node_1.add(new DefaultMutableTreeNode("section3"));
							node_1.add(new DefaultMutableTreeNode("paragraph2"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode("GridDoc3");
							node_1.add(new DefaultMutableTreeNode("section3"));
							node_1.add(new DefaultMutableTreeNode("paragraph3"));
						add(node_1);
					}
				}
			));
			scrollPane.setViewportView(tree);
		}
		
		JLabel lblNewLabel = new JLabel("<html>请在左侧选取一个节，<br><br>被选取的节将合并到本节中来。</html>");
		lblNewLabel.setBounds(183, 10, 216, 60);
		contentPanel.add(lblNewLabel);
		{
			JLabel label = new JLabel("您当前选择的段是：");
			label.setBounds(183, 101, 144, 15);
			contentPanel.add(label);
		}
		{
			textAreaSelectedPara.setBackground(UIManager.getColor("Button.background"));
			textAreaSelectedPara.setLineWrap(true);
			textAreaSelectedPara.setEditable(false);
			textAreaSelectedPara.setBounds(183, 128, 241, 60);
			contentPanel.add(textAreaSelectedPara);
		}
		
		JCheckBox checkBox = new JCheckBox("合并时删除源数据");
		checkBox.setBounds(183, 196, 241, 23);
		contentPanel.add(checkBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
