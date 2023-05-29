package com.example.BookStore.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
public static Connection connection;
	
	public DAO() {
		if(connection == null) {
			String dbUrl = "jdbc:sqlserver://localhost:1433; database = bookstore; user = sa; password = Kiki1401moon0208; encrypt = false";
			String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

			try {
				Class.forName(dbClass);
				connection = DriverManager.getConnection (dbUrl);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
