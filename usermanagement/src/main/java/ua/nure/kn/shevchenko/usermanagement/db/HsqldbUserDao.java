package ua.nure.kn.shevchenko.usermanagement.db;

import java.util.Collection;
import java.util.LinkedList;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.nure.kn.shevchenko.usermanagement.User;

class HsqldbUserDao implements UserDAO {
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?  WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";

	private ConnectionFactory connectionFactory;

	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory=connectionFactory;
	}
	
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}


	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public HsqldbUserDao() {
	}

	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseException("Number of the inserted rows: " + n);
			}
			CallableStatement callabelStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callabelStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callabelStatement.close();
			statement.close();
			connection.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return user;
	}

	public User update(User user) throws DatabaseException {
		PreparedStatement preparedStatement = null;
        try {
        	Connection connection = connectionFactory.createConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return user;
	}

	public void delete(User user) throws DatabaseException {
		PreparedStatement preparedStatement = null;
        try {
        	Connection connection = connectionFactory.createConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
	}

	public User find(Long id) throws DatabaseException {
		PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
        	Connection connection = connectionFactory.createConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                return user;
            }
            throw new DatabaseException("Can not find user by id: " + id);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
	}

	public Collection findAll() throws DatabaseException {
		Collection<User> result = new LinkedList<User>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));

                result.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return result;
	}

}
