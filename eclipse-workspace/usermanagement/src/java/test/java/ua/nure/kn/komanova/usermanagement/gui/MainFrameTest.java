package ua.nure.kn.komanova.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.Finder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

import java.main.ua.nure.kn.komanova.usermanagement.User;
import java.main.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import java.main.ua.nure.kn.komanova.usermanagement.db.MockDaoFactory;
import java.main.ua.nure.kn.komanova.usermanagement.util.Messages;

public class MainFrameTest extends JFCTestCase {
	private static final String DELETE_COMMAND = "delete";
	private static final String CREATE_COMMAND = "create";
	private static final String UPDATE_COMMAND = "update";
	private static final String FIND_ALL_COMMAND = "findAll";
	private static final String EXPECTED_AGE = "0";
	private static final String OK_BUTTON_COMPONENT_NAME = "okButton";
	private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";
	private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
	private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
	private static final String ADD_PANEL_COMPONENT_NAME = "addPanel";
	private static final String USER_TABLE_COMPONENT_NAME = "userTable";
	private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton";
	private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
	private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
	private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
	private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	private static final Date DATE_OF_BIRTH = new Date();
	private static final String EDIT_PANEL_COMPONENT_NAME = "editPanel"; //$NON-NLS-1$	
	private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton"; //$NON-NLS-1$
	protected static final String DAO_FACTORY = "dao.Factory";
	private static final String DETAILS_PANEL_COMPONENT_NAME = "detailsPanel"; //$NON-NLS-1$
	private static final String CLOSE_BUTTON_COMPONENT_NAME = "closeButton"; //$NON-NLS-1$
	private static final String FULL_NAME_FIELD_COMPONENT_NAME = "fullNameField"; //$NON-NLS-1$
	private static final String AGE_FIELD_COMPONENT_NAME = "ageField"; //$NON-NLS-1$
		
	private MainFrame mainFrame;
	private Mock mockUserDao;
    private Collection<User> users;
    private User expectedUser = new User(new Long(1), FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);

	protected void setUp() throws Exception {
		super.setUp();
		try {
            Properties properties = new Properties();
            properties.setProperty(DAO_FACTORY, MockDaoFactory.class
                    .getName());
            DaoFactory.init(properties);
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance())
                    .getMockUserDao();
            users = Collections.singletonList(expectedUser);
            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
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
            TestHelper.cleanUp(this);
            super.tearDown();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void testBrowseControl() {
		find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
		JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
		assertEquals(3, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals(Messages.getString("UserTableModel.first_name"), table
            .getColumnName(1));
		assertEquals(Messages.getString("UserTableModel.last_name"), table
            .getColumnName(2));
    
		find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
		find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
		find(JButton.class, DELETE_BUTTON_COMPONENT_NAME);
		find(JButton.class, DETAIL_BUTTON_COMPONENT_NAME);
	}	
	
	private Component find(Class<?> componentClass, String name) {
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("could not find component " + name, component);
		return component;
	}
	
	public void testAddUser() {
		try {	        	        	
			User user = new User(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
	        mockUserDao.expectAndReturn(CREATE_COMMAND, user, expectedUser);
        	
	        ArrayList<User> users = new ArrayList<>(this.users);
	        users.add(expectedUser);
	        mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
	            
	        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
	        assertEquals(1, table.getRowCount());

	        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
	        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

	        find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
	        fillFields(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);

	        JButton okButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
	        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

	        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
	        JTable table2 = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
	        assertEquals(2, table2.getRowCount());	            
	        mockUserDao.verify();
	    } catch (Exception e) {
	    	fail(e.toString());
	    }
	}	
	
    public void testEditUser() {
        try {           
            mockUserDao.expect(UPDATE_COMMAND, expectedUser);

            List<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
            
            JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(1, table.getRowCount());
            
            String previous = (String) table.getModel().getValueAt(0, 1); 
            
            JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, EDIT_PANEL_COMPONENT_NAME);
            fillFields("1", LAST_NAME, DATE_OF_BIRTH);

            JButton okButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
            JTable table2 = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            
            assertEquals(previous, FIRST_NAME);
            assertEquals(table2.getModel().getValueAt(0, 1), FIRST_NAME + "1");
            mockUserDao.verify();            
        } catch (Exception e) {
            fail(e.toString());
        }
    }
	
	public void testDeleteUser() {
        try {
            List<User> users = new ArrayList<>();
            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
            mockUserDao.expectAndReturn(DELETE_COMMAND, expectedUser, users);
            
            JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, DELETE_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
            
            JDialog dialog;
            DialogFinder dFinder = new DialogFinder(Messages.getString("BrowsePanel.confirm"));
            dialog = (JDialog) dFinder.find();
            assertNotNull("Could not find dialog '" + Messages.getString("BrowsePanel.confirm") + "'", dialog);
            Finder finder = new ComponentFinder(JButton.class);            
            JButton jb = (JButton)finder.find(dialog, 0);
            
            getHelper().enterClickAndLeave(new MouseEventData(this,jb));
            
            find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(0, table.getRowCount());
            mockUserDao.verify();            
        } catch (Exception e) {
            fail(e.toString());
        }
    }
	
	public void testViewDetails() {
		try {	        	        	        	
	        ArrayList<User> users = new ArrayList<>(this.users);
	        users.add(expectedUser);
	        mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
	            
	        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
	        assertEquals(1, table.getRowCount());
	        
	        String firstName = (String) table.getModel().getValueAt(0, 1); 
	        String lastName = (String) table.getModel().getValueAt(0, 2);  

	        JButton detailButton = (JButton) find(JButton.class, DETAIL_BUTTON_COMPONENT_NAME);
	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	        getHelper().enterClickAndLeave(new MouseEventData(this, detailButton));

	        find(JPanel.class, DETAILS_PANEL_COMPONENT_NAME);
	        
	        JTextField fullNameField = (JTextField) find(JTextField.class, FULL_NAME_FIELD_COMPONENT_NAME);
	        assertEquals(fullNameField.getText(), lastName + ", " + firstName);	   
	        
	        JTextField ageField = (JTextField) find(JTextField.class, AGE_FIELD_COMPONENT_NAME);
	        assertEquals(ageField.getText(), EXPECTED_AGE);	  
	        
	        JButton closeButton = (JButton) find(JButton.class, CLOSE_BUTTON_COMPONENT_NAME);	        
	        getHelper().enterClickAndLeave(new MouseEventData(this, closeButton));

	        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);            
	        mockUserDao.verify();
	    } catch (Exception e) {
	    	fail(e.toString());
	    }
	}
	    
	public void testCancelEditUser() {
        try {   
            ArrayList<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
            
            JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(1, table.getRowCount());

            JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, EDIT_PANEL_COMPONENT_NAME);

            JButton cancelButton = (JButton) find(JButton.class, CANCEL_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(1, table.getRowCount());            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }
		
	public void testCancelAddUser() {
        try {   
            ArrayList<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
            
            JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, ADD_PANEL_COMPONENT_NAME);

            JButton cancelButton = (JButton) find(JButton.class, CANCEL_BUTTON_COMPONENT_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
            assertEquals(1, table.getRowCount());            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }	

    private void fillFields(String firstName, String lastName, Date dateOfBirth) {
        JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD_COMPONENT_NAME);
        JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD_COMPONENT_NAME);
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD_COMPONENT_NAME);

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        
        String date = DateFormat.getDateInstance().format(dateOfBirth);
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
    }
}
