package pv.selenium.hybrid.util;

public class Constants {

	public static final String USER_DIR = "user.dir";
	public static final String SQL_QUERY_XLS = System.getProperty(USER_DIR) + "\\Data\\newSQLQuery.xlsx";
	public static final String MANAGE_QUERIES_XLS = System.getProperty(USER_DIR) + "\\Data\\manageQueries.xlsx";
	public static final String MANAGE_CASE_SERIES_XLS = System.getProperty(USER_DIR) + "\\Data\\manageCaseSeries.xlsx";
	public static final String MANAGE_REPORTS_XLS = System.getProperty(USER_DIR) + "\\Data\\manageReports.xlsx";
	public static final String BUSINESS_CONFIG_XLS = System.getProperty(USER_DIR)+ "\\Data\\businessConfiguration.xlsx";
	public static final String FLEXIBLE_QBE_GLOBAL_XLS = System.getProperty(USER_DIR)+"\\Data\\flexibleQBEQueryGlobal.xlsx";
	public static final String ETL_ADMIN_XLS = System.getProperty(USER_DIR) + "\\Data\\etlAdministration.xlsx";
	public static final String PROPERTIES_FILE_PATH = System.getProperty(USER_DIR)+ "//src//pv//resources//project.properties";
	public static final String TESTNG_REPORT_FILE_PATH_EMAIL = System.getProperty(USER_DIR) +"\\target\\surefire-reports";
    public static final String TESTNG_REPORT_FILE_PATH = System.getProperty(USER_DIR) +"\\target\\surefire-reports\\old\\index.html";
	public static final String MOP_XLS = System.getProperty(USER_DIR) + "\\Data\\MOP.xlsx";
	public static final String HCP_XLS = System.getProperty(USER_DIR) + "\\Data\\HCP.xlsx";
	public static final String SR_XLS = System.getProperty(USER_DIR) + "\\Data\\SR.xlsx";
	public static final String POP_XLS = System.getProperty(USER_DIR) + "\\Data\\POP.xlsx";
	public static final String MAX_LENGTH_XLS = System.getProperty(USER_DIR) + "\\Data\\MaxLength_MOP.xlsx";
	public static final String POP_VALIDATION_XLS = System.getProperty(USER_DIR) + "\\Data\\POPValidation.xlsx";
	public static final String POP_VALIDATION_VALID_XLS = System.getProperty(USER_DIR)+ "\\Data\\POPValidationValid.xlsx";
	public static final String USER_MANAGEMENT_XLS = System.getProperty(USER_DIR) + "\\Data\\UserManagement.xlsx";
	public static final String REGISTER_GUEST_USER_XLS = System.getProperty(USER_DIR)+ "\\Data\\RegisterGuestUser.xlsx";
	public static final String START_END_DATE_XLS = System.getProperty(USER_DIR) + "\\Data\\StartEndDate.xlsx";
	public static final String UPDATE_PROFILE_XLS = System.getProperty(USER_DIR) + "\\Data\\UpdateProfile.xlsx";
	public static final String CASE_SEARCH_XLS = System.getProperty(USER_DIR) + "\\Data\\caseSearch.xlsx";
	public static final String CASE_DASHBOARD_XLS = System.getProperty(USER_DIR) + "\\Data\\caseDashboard.xlsx";
	public static final String CHANGE_PASSWORD_XLS = System.getProperty(USER_DIR) + "\\Data\\changePassword.xlsx";
	public static final String LOGIN_SALESFORCE = System.getProperty(USER_DIR) + "\\Data\\LoginSF.xlsx";
	public static final String MEDDRA_BROWSER = System.getProperty(USER_DIR) + "\\Data\\medDRABrowser.xlsx";
	public static final String ELEM_2208 = System.getProperty(USER_DIR) + "\\Data\\ELEM2208.xlsx";
	public static final String ELEM_2258 = System.getProperty(USER_DIR) + "\\Data\\ELEM2258.xlsx";
	public static final String ELEM_2114 = System.getProperty(USER_DIR) + "\\Data\\ELEM2114.xlsx";
	public static final String ELEM_3163 = System.getProperty(USER_DIR) + "\\Data\\ELEM3163.xlsx";
	public static final String ELEM_2976 = System.getProperty(USER_DIR) + "\\Data\\ELEM2976.xlsx";
	public static final String KEYWORDS_SHEET = "Keywords";

	public static final String TCID_COL = "TCID";
	public static final String KEYWORD_COL = "Keyword";
	public static final String OBJECT_COL = "Object";
	public static final String DATA_COL = "Data";
	public static final String TEST_CASES_SHEET = "TestCases";
	public static final String RUNMODE_COL = "Runmode";
	public static final String SCREENSHOT_PATH = System.getProperty(USER_DIR) + "\\Screenshots\\";
	public static final String FILE_DOWNLOAD_PATH = System.getProperty(USER_DIR) + "\\test-output\\TestCaseOutput\\";
	public static final String REPORT_ARCHIVE_FOLDER = System.getProperty(USER_DIR) + "\\Archive Reports\\";
	public static final String REPORT_PATH = System.getProperty(USER_DIR) + "\\Reports\\";
	public static final String ZIP_REPORT_FOLDER_NAME ="ZipReports_";
	public static final String REPORT_FOLDER_NAME ="Execution Reports";												  					  
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String FAILURE_MSG = "FAIL -- Failure in Element Extraction - ";
	public static final String DEFAULT_DROPDOWN = "Please";

	public static final String adminConsole_XLS = System.getProperty("user.dir")+"\\Data\\adminConsole.xlsx";
	public static final String caseDashboard_XLS = System.getProperty("user.dir")+"\\Data\\caseDashboard.xlsx";
	public static final String caseSearch_XLS = System.getProperty("user.dir")+"\\Data\\caseSearch.xlsx";
	public static final String MaxLength_XLS = System.getProperty("user.dir")+"\\Data\\MaxLength_MOP.xlsx";
	public static final String POPValidation_XLS = System.getProperty("user.dir")+"\\Data\\POPValidation.xlsx";
	public static final String POPValidationValid_XLS = System.getProperty("user.dir")+"\\Data\\POPValidationValid.xlsx";
	public static final String UpdateProfile_XLS = System.getProperty("user.dir")+"\\Data\\UpdateProfile.xlsx";
	public static final String UserManagement_XLS = System.getProperty("user.dir")+"\\Data\\UserManagement.xlsx";
	public static final String changePassword_XLS = System.getProperty("user.dir")+"\\Data\\changePassword.xlsx";
	public static final String RegisterGuestUser_XLS = System.getProperty("user.dir")+"\\Data\\RegisterGuestUser.xlsx";
	public static final String StartEndDate_XLS = System.getProperty("user.dir")+"\\Data\\StartEndDate.xlsx";
	public static final String MOPEmail_XLS = System.getProperty("user.dir")+"\\Data\\verifyEmailMOP.xlsx";
	public static final String POPEmail_XLS = System.getProperty("user.dir")+"\\Data\\verifyEmailPOP.xlsx";
	public static final String SREmail_XLS = System.getProperty("user.dir")+"\\Data\\verifyEmailSR.xlsx";
	public static final String HCPEmail_XLS = System.getProperty("user.dir")+"\\Data\\verifyEmailHCP.xlsx";
	
	public static final String passwordPolicy_XLS = System.getProperty("user.dir")+"\\Data\\passwordPolicy.xlsx";
	public static final String UserRegValidation_XLS = System.getProperty("user.dir")+"\\Data\\UserRegValidation.xlsx";
	
	 public static final String changeDetailMOP_XLS = System.getProperty("user.dir")+"\\Data\\changeDetailMOP.xlsx";
     public static final String changeDetailHCP_XLS = System.getProperty("user.dir")+"\\Data\\changeDetailHCP.xlsx";
     public static final String changeDetailSR_XLS = System.getProperty("user.dir")+"\\Data\\changeDetailSR.xlsx";
     public static final String RemoveFromReportMOP_XLS = System.getProperty("user.dir")+"\\Data\\RemoveFromReportMOP.xlsx";
     public static final String RemoveFromReportHCP_XLS = System.getProperty("user.dir")+"\\Data\\RemoveFromReportHCP.xlsx";
     public static final String RemoveFromReportPOP_XLS = System.getProperty("user.dir")+"\\Data\\RemoveFromReportPOP.xlsx";
     public static final String RemoveFromReportSR_XLS = System.getProperty("user.dir")+"\\Data\\RemoveFromReportSR.xlsx";
     public static final String SR_Attachments_XLS = System.getProperty("user.dir")+"\\Data\\SR_Attachments.xlsx";
     public static final String MOP_Attachments_XLS = System.getProperty("user.dir")+"\\Data\\MOP_Attachments.xlsx";
     public static final String HCP_Attachments_XLS = System.getProperty("user.dir")+"\\Data\\HCP_Attachments.xlsx";
     public static final String POP_Attachments_XLS = System.getProperty("user.dir")+"\\Data\\POP_Attachments.xlsx";
	/*public static final String ADMIN_CONSOLE_XLS = System.getProperty(USER_DIR) + "\\Data\\adminConsole.xlsx";
	public static final String MOP_EMAIL_XLS = System.getProperty(USER_DIR) + "\\Data\\verifyEmailMOP.xlsx";
	public static final String POP_EMAIL_XLS = System.getProperty(USER_DIR) + "\\Data\\verifyEmailPOP.xlsx";
	public static final String SRE_MAIL_XLS = System.getProperty(USER_DIR) + "\\Data\\verifyEmailSR.xlsx";
	public static final String HCP_EMAIL_XLS = System.getProperty(USER_DIR) + "\\Data\\verifyEmailHCP.xlsx";

	public static final String PASSWORD_POLICY_XLS = System.getProperty(USER_DIR) + "\\Data\\passwordPolicy.xlsx";
	public static final String USER_REG_VALIDATION_XLS = System.getProperty(USER_DIR)
			+ "\\Data\\UserRegValidation.xlsx";

	public static final String CHANGE_DETAIL_MOP_XLS = System.getProperty(USER_DIR) + "\\Data\\changeDetailMOP.xlsx";
	public static final String CHANGE_DETAIL_HCP_XLS = System.getProperty(USER_DIR) + "\\Data\\changeDetailHCP.xlsx";
	public static final String CHANGE_DETAIL_SR_XLS = System.getProperty(USER_DIR) + "\\Data\\changeDetailSR.xlsx";
	public static final String REMOVE_FROM_REPORT_MOP_XLS = System.getProperty(USER_DIR)
			+ "\\Data\\RemoveFromReportMOP.xlsx";
	public static final String REMOVE_FROM_REPORT_HCP_XLS = System.getProperty(USER_DIR)
			+ "\\Data\\RemoveFromReportHCP.xlsx";
	public static final String REMOVE_FROM_REPORT_POP_XLS = System.getProperty(USER_DIR)
			+ "\\Data\\RemoveFromReportPOP.xlsx";
	public static final String REMOVE_FROM_REPORT_SR_XLS = System.getProperty(USER_DIR)
			+ "\\Data\\RemoveFromReportSR.xlsx";
	public static final String SR_ATTACHMENTS_XLS = System.getProperty(USER_DIR) + "\\Data\\SR_Attachments.xlsx";
	public static final String MOP_ATTACHMENTS_XLS = System.getProperty(USER_DIR) + "\\Data\\MOP_Attachments.xlsx";
	public static final String HCP_ATTACHMENTS_XLS = System.getProperty(USER_DIR) + "\\Data\\HCP_Attachments.xlsx";
	public static final String POP_ATTACHMENTS_XLS = System.getProperty(USER_DIR) + "\\Data\\POP_Attachments.xlsx";*/

}
