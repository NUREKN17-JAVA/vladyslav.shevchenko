package ua.nure.kn.shevchenko.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import ua.nure.kn.shevchenko.usermanagement.User;
import ua.nure.kn.shevchenko.usermanagement.db.DaoFactory;
import ua.nure.kn.shevchenko.usermanagement.db.DaoFactoryImpl;
import ua.nure.kn.shevchenko.usermanagement.db.MockDaoFactory;
import ua.nure.kn.shevchenko.usermanagement.db.MockUserDao;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;


public class MainFrameTest extends JFCTestCase {

	private MainFrame mainFrame;
	private Mock mockUserDao;
	protected void setUp() throws Exception {
		super.setUp();
		
		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance())
					.getMockUserDao();
			
			mockUserDao.expectAndReturn("findAll", new ArrayList());
			
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}
	
	protected void tearDown() throws Exception {
        try {
            mockUserDao.verify();
            mainFrame.setVisible(false);
            getHelper();
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private Component find(Class componentClass, String name){
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}
	
	public void testBrowseControls(){
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("First Name", table.getColumnName(1));
		assertEquals("Last Name", table.getColumnName(2));
		find(JButton.class, "addButton");	
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}
	
	public void testAddUser(){
		try{
			String firstName = "John";
			String lastName = "Doe";
			Date now = new Date();
			
			User user = new User(firstName, lastName, now);
			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndReturn("create", user, expectedUser);
			
			ArrayList users = new ArrayList();
			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			
			JButton addButton = (JButton) find(JButton.class, "addButton");
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
			
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			find(JPanel.class, "addPanel");
			JTextField firstNameField = (JTextField)find(JTextField.class, "firstNameField");
			JTextField lastNameField =(JTextField)find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField)find(JTextField.class, "dateOfBirthField");
			
			JButton okButton = (JButton)find(JButton.class, "okButton");
			JButton cancelButton = (JButton)find(JButton.class, "cancelButton");
			
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formater = DateFormat.getDateInstance();
			String date = formater.format(new Date());
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
			
			find(JPanel.class, "browsePanel");
		    table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
		} catch (Exception e){
			fail(e.toString());
		}

	}
	
	public void testEditUser() {
		String firstName = "John";
		String lastName = "Doe";	
		Date now = new Date();
		
        User user = new User(firstName, lastName, now);
        User expectedUser = new User(1L, firstName, lastName, now);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        ArrayList users = new ArrayList();
        users.add(expectedUser);

        mockUserDao.expectAndReturn("findAll", users);
        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addButton");
        
        setFields(firstName, lastName, now);

        find(JPanel.class, "browsePanel");
        JTable table = (JTable) find(JTable.class, "userTable");
        int expectedRows = 1;
        assertEquals(expectedRows, table.getRowCount());

        JButton editButton = (JButton) find(JButton.class, "editButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0,1,1));
        getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

        find(JPanel.class, "editPanel");
        setFields("EDITED", "EDITED", now);

        JButton okButton = (JButton) find(JButton.class, "okButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTable.class, "userTable");
        assertEquals(expectedRows, table.getRowCount());
    }

	public void setFields(String firstName, String lastName, Date now) {
		JTextField firstNameField = (JTextField)find(JTextField.class, "firstNameField");
		JTextField lastNameField =(JTextField)find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField)find(JTextField.class, "dateOfBirthField");
        JButton okCreateButton = (JButton) find(JButton.class, "okButton");
        
        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        String date = DateFormat.getDateInstance().format(now);
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
        getHelper().enterClickAndLeave(new MouseEventData(this, okCreateButton));
	}

    public void testDetailsPanel() {
    	String firstName = "John";
		String lastName = "Doe";	
		Date now = new Date();
        User user = new User(firstName, lastName, now);
        User expectedUser = new User(1L, firstName, lastName, now);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        ArrayList users = new ArrayList();
        users.add(expectedUser);

        mockUserDao.expectAndReturn("findAll", users);
        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        setFields(firstName, lastName, now);

        JButton okCreateButton = (JButton) find(JButton.class, "okButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okCreateButton));

        find(JPanel.class, "browsePanel");

        JTable table = (JTable) find(JTable.class, "userTable");
        int expectedRows = 1;
        assertEquals(expectedRows, table.getRowCount());

        JButton detailButton = (JButton) find(JButton.class, "detailsButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0,1,1));
        getHelper().enterClickAndLeave(new MouseEventData(this, detailButton));
    }
	
}
