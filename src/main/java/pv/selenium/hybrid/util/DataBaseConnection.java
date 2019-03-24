package pv.selenium.hybrid.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.relevantcodes.extentreports.ExtentTest;

import pv.selenium.hybrid.keywords.GenericKeywords;

public class DataBaseConnection extends GenericKeywords {
	public static Properties prop;
	public static Connection dbCon;
	public static CallableStatement callableStatement;
	public static ResultSet rs;
	public static String caseCount;
	public static Statement stmt;
	public static String queryCount;
	public static String getDBUSERByUserIdSql;
	public static String dbAddress;
	public static String port;
	public static String instance;
	public static String user;
	public static String password;
	ExtentTest test;

	public DataBaseConnection(ExtentTest test) {
		super(test);
	}

	public static String executeSQL(String sql) throws DatabaseException{

		
		// System.out.println("Entered in-executeSQL method---");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			callOracleStoredProcOUTParameter();
			// test.log(LogStatus.INFO,"Retrieving the case count form");
			stmt = dbCon.createStatement();

			// test.log(LogStatus.INFO,"Safety DB Connected and executing the SQL to count
			// the cases");
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// int count =rs.getInt(0);
				queryCount = rs.getString("count(*)");
				// System.out.println(rs.getString("COUNT(CASE_MASTER.CASE_ID)"));
				 System.out.println("Count Value----" + queryCount);
			}
			rs.close();
			dbCon.close();
		} catch (Exception e) {
			throw new DatabaseException(Constants.FAIL + " ----- Unable to connect to DataBase");
		}
		return queryCount;
	}

	// Setting Context in Safety Database for particular tenant

	public static void callOracleStoredProcOUTParameter() throws SQLException, IOException {
		prop = new Properties();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "//src//pvi//resources//project.properties");
		prop.load(fs);
		// getDBUSERByUserIdSql = "{call PKG_RLS.SET_CONTEXT(?,?,?,?)}";
		dbAddress = prop.getProperty("dataBaseHost"); // 10.10.40.53
		port = prop.getProperty("portNumber");
		instance = prop.getProperty("dbInstanceName");
		user = prop.getProperty("schemaUserName");
		password = prop.getProperty("schemaPassword");

		try {
			// dbCon=DriverManager.getConnection("jdbc:oracle:thin:@10.10.40.53:1523:DMT9999","PVQ_1_3_T","manager");
			// System.out.println("jdbc:oracle:thin:@"+dbAddress+":"+port+":"+instance+","+user+","+password);
			dbCon = DriverManager.getConnection("jdbc:oracle:thin:@" + dbAddress + ":" + port + ":" + instance, user,
					password);
			// dbCon=DriverManager.getConnection("jdbc:oracle:thin:@10.10.49.50:1521:FDRPVQ23","argus_app","argus_app");
			 System.out.println("Connection established");
			/*
			 * callableStatement = dbCon.prepareCall(getDBUSERByUserIdSql);
			 * callableStatement.setString(1, "system"); callableStatement.setInt(2, -1);
			 * callableStatement.setString(3, "ARGUS_SAFETY");
			 * callableStatement.setString(4, "#$!AgSeRvIcE@SaFeTy");
			 * callableStatement.execute();
			 */
		} catch (SQLException e) {
			e.getMessage();
		}

	}

}
