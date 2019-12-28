package ua.nure.kn.shevchenko.usermanagement.agent;

import ua.nure.kn.shevchenko.usermanagement.db.DatabaseException;

public class SearchException extends Exception {
	private static final long serialVersionUID = 71450439268462163L;

	public SearchException(DatabaseException e) {
		e.printStackTrace();
	}
}