package ui.paragraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class ParagraphPropertyJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ParagraphPropertyJDialog dialog = new ParagraphPropertyJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ParagraphPropertyJDialog() {
		setTitle("段属性");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("段名称");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 10, 54, 15);
			contentPanel.add(label);
		}
		{
			JLabel lblParaName = new JLabel("p1");
			lblParaName.setBounds(34, 35, 54, 15);
			contentPanel.add(lblParaName);
		}
		{
			JLabel label = new JLabel("段路径");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 60, 54, 15);
			contentPanel.add(label);
		}
		{
			JLabel lblGriddocsp = new JLabel("griddoc1/s1/p1");
			lblGriddocsp.setBounds(34, 85, 265, 15);
			contentPanel.add(lblGriddocsp);
		}
		{
			JLabel label = new JLabel("段位置");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 110, 54, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("3");
			label.setBounds(34, 135, 54, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("备注");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 160, 54, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("同步创建");
			label.setBounds(34, 182, 265, 15);
			contentPanel.add(label);
		}
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
