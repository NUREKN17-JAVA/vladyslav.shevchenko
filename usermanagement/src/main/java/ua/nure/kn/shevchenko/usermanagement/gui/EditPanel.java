package ua.nure.kn.shevchenko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.shevchenko.usermanagement.User;
import ua.nure.kn.shevchenko.usermanagement.db.DatabaseException;
import ua.nure.kn.shevchenko.usermanagement.util.Messages;

public class EditPanel extends JPanel implements ActionListener {
	private User user;
	
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField dateOfBirthField;
	private JTextField firstNameField;
	private JTextField lastNameField;

	public EditPanel(MainFrame parent) {
        this.parent = parent;
        this.user = parent.getSelectedUser();
        initialize();
    }
	
	private void initialize() {
		this.setName("editPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

	private JPanel getButtonPanel() {
        if(buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

	private JButton getCancelButton() {
        if(cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText("Cancel");
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

	private JButton getOkButton()  {
        if(okButton == null) {
            okButton = new JButton();
            okButton.setText("OK");
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }


	private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, "firstName", getFirstNameField());
            addLabeledField(fieldPanel, "lastName", getLastNameField());
            addLabeledField(fieldPanel, "dateOfBirth", getDateOfBirthField());
        }
        return fieldPanel;
    }

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

	private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
            dateOfBirthField.setText(user.getDateOfBirth().toString());
        }
        return dateOfBirthField;
    }

	private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
            lastNameField.setText(user.getLastName());
        }
        return lastNameField;
    }

	private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
            firstNameField.setText(user.getFirstName());
        }
        return firstNameField;
    }	
	
	public void actionPerformed(ActionEvent e){
        if ("ok".equalsIgnoreCase(e.getActionCommand())) {
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                Date date = format.parse(getDateOfBirthField().getText());
                user.setDateOfBirth(date);
            } catch (ParseException ex) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields();
	    this.setVisible(false);
	    parent.showBrowsePanel();
    }
	
	private void clearFields() {
		Color bgColor = Color.WHITE;

        getFirstNameField().setText("");
        getFirstNameField().setBackground(bgColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(bgColor);
	}

	
}
