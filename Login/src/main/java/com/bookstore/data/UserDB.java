package com.bookstore.data;

import com.bookstore.models.User;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDB {
    public static User findUser(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Get connection from pool
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/bookstore");
            connection = ds.getConnection();
            
            // Prepare SQL query
            String query = "SELECT * FROM user WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            
            // Execute query
            resultSet = statement.executeQuery();
            
            // Process results
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) {}
            try { if (statement != null) statement.close(); } catch (SQLException e) {}
            try { if (connection != null) connection.close(); } catch (SQLException e) {}
        }
        
        return user;
    }
}