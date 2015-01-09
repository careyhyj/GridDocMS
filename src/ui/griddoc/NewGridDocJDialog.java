package ui.griddoc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class NewGridDocJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldGridDocName;
	private JTextField textFieldGridDocPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewGridDocJDialog dialog = new NewGridDocJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewGridDocJDialog() {
		setTitle("新建格文档");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("格文档名称：");
		label.setBounds(22, 98, 72, 15);
		contentPanel.add(label);
		
		textFieldGridDocName = new JTextField();
		textFieldGridDocName.setBounds(95, 95, 137, 21);
		contentPanel.add(textFieldGridDocName);
		textFieldGridDocName.setColumns(10);
		
		JLabel label_1 = new JLabel("格文档路径：");
		label_1.setBounds(22, 151, 72, 15);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("创建一个新的格文档");
		label_2.setFont(new Font("宋体", Font.BOLD, 12));
		label_2.setBounds(10, 10, 184, 15);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("请在文本框内输入创建的格文档的名称，并指定其路径。");
		label_3.setBounds(22, 35, 381, 15);
		contentPanel.add(label_3);
		
		textFieldGridDocPath = new JTextField();
		textFieldGridDocPath.setBounds(95, 148, 318, 21);
		contentPanel.add(textFieldGridDocPath);
		textFieldGridDocPath.setColumns(10);
		
		JButton buttonBrowserDir = new JButton("浏览...");
		buttonBrowserDir.setBounds(320, 179, 93, 23);
		contentPanel.add(buttonBrowserDir);
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
