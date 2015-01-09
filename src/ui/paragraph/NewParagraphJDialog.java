package ui.paragraph;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class NewParagraphJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldParagraphName;
	private JTextField textFieldParagraphPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewParagraphJDialog dialog = new NewParagraphJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewParagraphJDialog() {
		setTitle("新建段");
		setBounds(100, 100, 450, 312);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("创建一个新的段");
			label.setFont(new Font("宋体", Font.BOLD, 12));
			label.setBounds(10, 10, 184, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("请在文本框内输入创建的段的名称，并指定其路径。");
			label.setBounds(22, 35, 381, 15);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("段名称：");
			label.setBounds(22, 72, 48, 15);
			contentPanel.add(label);
		}
		{
			textFieldParagraphName = new JTextField();
			textFieldParagraphName.setColumns(10);
			textFieldParagraphName.setBounds(76, 69, 137, 21);
			contentPanel.add(textFieldParagraphName);
		}
		{
			JLabel label = new JLabel("段路径：");
			label.setBounds(22, 100, 48, 15);
			contentPanel.add(label);
		}
		{
			textFieldParagraphPath = new JTextField();
			textFieldParagraphPath.setColumns(10);
			textFieldParagraphPath.setBounds(76, 97, 282, 21);
			contentPanel.add(textFieldParagraphPath);
		}
		{
			JButton buttonBrowserDir = new JButton("...");
			buttonBrowserDir.setBounds(368, 96, 38, 23);
			contentPanel.add(buttonBrowserDir);
		}
		{
			JLabel label = new JLabel("段位置：");
			label.setBounds(22, 128, 54, 15);
			contentPanel.add(label);
		}
		
		JComboBox comboBoxParagraphIndex = new JComboBox();
		comboBoxParagraphIndex.setBounds(76, 125, 66, 21);
		contentPanel.add(comboBoxParagraphIndex);
		
		JCheckBox chckbxIsSynCreateSection = new JCheckBox("是否同步创建");
		chckbxIsSynCreateSection.setBounds(224, 68, 103, 23);
		contentPanel.add(chckbxIsSynCreateSection);
		
		JLabel lblNewLabel = new JLabel("段描述：");
		lblNewLabel.setBounds(22, 176, 54, 15);
		contentPanel.add(lblNewLabel);
		
		JTextArea textAreaParagraphDesc = new JTextArea();
		textAreaParagraphDesc.setLineWrap(true);
		textAreaParagraphDesc.setBounds(76, 156, 329, 63);
		contentPanel.add(textAreaParagraphDesc);
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
