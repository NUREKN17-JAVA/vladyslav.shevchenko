package ua.nure.kn.shevchenko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.shevchenko.usermanagement.User;

public class DetailsPanel extends JPanel implements ActionListener {
	private User user;
	
	private MainFrame parent;
	private JPanel fieldPanel;
    private JButton closeButton;
    private JTextField fullNameField;
    private JTextField ageField;

    public DetailsPanel(MainFrame parent) {
        this.parent = parent;
        this.user = parent.getSelectedUser();
        initialize();
    }

    private void initialize() {
        setName("detailsPanel");
        setLayout(new BorderLayout());
        add(getFieldPanel(), BorderLayout.NORTH);
        add(getCloseButton(), BorderLayout.SOUTH);
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton();
            closeButton.setText("Close");
            closeButton.setName("close");
            closeButton.addActionListener(this);
        }
        return closeButton;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledImmutableField(fieldPanel, "fullName", getFullNameField());
            addLabeledImmutableField(fieldPanel, "age", getAgeField());
        }
        return fieldPanel;
    }

    private void addLabeledImmutableField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JTextField getFullNameField() {
        if (fullNameField == null) {
            fullNameField = new JTextField();
            fullNameField.setEditable(false);
            fullNameField.setName("fullNameField");
            fullNameField.setText(this.user.getFullName());
        }
        return fullNameField;
    }

    private JTextField getAgeField() {
        if (ageField == null) {
            ageField = new JTextField();
            ageField.setEditable(false);
            ageField.setName("ageField");
            ageField.setText(String.valueOf(this.user.getAge()));
        }
        return ageField;
    }

	public void actionPerformed(ActionEvent e) {
		setVisible(false);
        parent.showBrowsePanel();
	}
}