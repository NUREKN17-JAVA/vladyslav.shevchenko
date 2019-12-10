package ua.nure.kn.shevchenko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.kn.shevchenko.usermanagement.User;
import ua.nure.kn.shevchenko.usermanagement.db.DatabaseException;
import ua.nure.kn.shevchenko.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {
	
    private final MainFrame parent;
	private JPanel  buttonPanel;
	private JScrollPane  tablePanel;
	private JButton addButton;
	private JButton detailsButton;
	private JButton deleteButton;
	private JButton editButton;
	private JTable userTable;


	public BrowsePanel(MainFrame frame) {
		 parent = frame;
	     initialize();
	}
	
	 private void initialize() {
	    this.setName("browsePanel"); //$NON-NLS-1$
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
	 }

	 private JScrollPane getTablePanel() {
        if(tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
	 }

	private JTable getUserTable() {
        if(userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable"); //$NON-NLS-1$
        }
        return userTable;
    }

	public void initTable() {
		UserTableModel model = null;
        try {
            model = new UserTableModel(parent.getDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
            		JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
	}

	private JPanel getButtonPanel() {
        if(buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton(), null);
            buttonPanel.add(getEditButton(), null);
            buttonPanel.add(getDeleteButton(), null);
            buttonPanel.add(getDetailsButton(), null);
        }
        return buttonPanel;
    }

	private JButton getDetailsButton() {
        if(detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
            detailsButton.setName("detailsButton"); //$NON-NLS-1$
            detailsButton.setActionCommand("details"); //$NON-NLS-1$
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

	private JButton getDeleteButton() {
        if(deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
            deleteButton.setName("deleteButton"); //$NON-NLS-1$
            deleteButton.setActionCommand("delete"); //$NON-NLS-1$
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

	private JButton getEditButton() {
        if(editButton == null) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
            editButton.setName("editButton"); //$NON-NLS-1$
            editButton.setActionCommand("edit"); //$NON-NLS-1$
            editButton.addActionListener(this);
        }
        return editButton;
    }

	private JButton getAddButton() {
        if(addButton == null) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
            addButton.setName("addButton"); //$NON-NLS-1$
            addButton.setActionCommand("add"); //$NON-NLS-1$
            addButton.addActionListener(this);
        }
        return addButton;
    }

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
            this.setVisible(false);
            parent.showAddPanel();
        }
        if ("edit".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showEditPanel();
        }
        if ("delete".equalsIgnoreCase(actionCommand)) {
            User selectedUser = getSelectedUser();
            if (selectedUser != null) {
                int result = JOptionPane.showConfirmDialog(this, "Delete user?",
                        "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        parent.getDao().delete(selectedUser);
                        getUserTable().setModel(new UserTableModel(parent.getDao().findAll()));
                    } catch (DatabaseException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        if ("details".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showDetailsPanel();
        }
	}

	public User getSelectedUser() {
        int selectedRow = getUserTable().getSelectedRow();
        int IdColumn = 0;
        User user = null;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select user", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Long userId = (Long) userTable.getValueAt(userTable.getSelectedRow(), IdColumn);
            try {
                user = parent.getDao().find(userId);
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return user;
    }
	

}
