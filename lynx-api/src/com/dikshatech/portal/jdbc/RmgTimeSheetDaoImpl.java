package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.portal.dao.RmgTimeSheetDao;
import com.dikshatech.portal.dto.RmgTimeSheet;
import com.dikshatech.portal.dto.RmgTimeSheetPk;
import com.dikshatech.portal.exceptions.RmgTimeSheetDaoException;

public class RmgTimeSheetDaoImpl extends AbstractDAO implements RmgTimeSheetDao{
	
	protected java.sql.Connection	userConn;
	protected static final Logger logger = Logger.getLogger( EmpSerReqMapDaoImpl.class );
	
	//protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, RMG_TIMESHEET_ID, USER_ID,PRJ_ID, CHRG_CODE_ID,START_DATE,END_DATE, WORKING_DAYS,TIMESHEET_LEAVE,TIMESHEET_CATEGORY,STATUS,ACTION_BY,CREATE_DATE,ROLL_ON_ID,TR_FLAG) VALUES ( ?, ?, ?, ?,?, ?,?,?,?,?,?,?,?,?,?)";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, RMG_TIMESHEET_ID, USER_ID,PRJ_ID, CHRG_CODE_ID,START_DATE,END_DATE,CREATE_DATE,ROLL_ON_ID,TR_FLAG,WORKING_DAYS) VALUES ( ?, ?, ?, ?,?, ?,?,?,?,?,?)";

	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET  START_DATE = ?, END_DATE = ?, WORKING_DAYS = ?,TIMESHEET_LEAVE=?, TIMESHEET_CATEGORY=?,STATUS=?,ACTION_BY=?,TR_FLAG=? WHERE ID = ?";

/*	ID	int(11) AI PK
	RMG_TIMESHEET_ID	int(11)
	USER_ID	int(11)
	PRJ_ID	int(11)
	CHRG_CODE_ID	int(11)
	START_DATE	date
	END_DATE	date
	WORKING_DAYS	int(11)
	TIMESHEET_LEAVE	int(11)
	TIMESHEET_CATEGORY	int(11)
	STATUS	varchar(45)
	ACTION_BY	varchar(45)
	CREATE_DATE*/
	
	@Override
	public RmgTimeSheet insert(RmgTimeSheet dto)throws RmgTimeSheetDaoException {
		{
			long t1 = System.currentTimeMillis();
			// declare variables
			final boolean isConnSupplied = (userConn != null);
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				// get the user-specified connection or get a connection from the ResourceManager
				conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			
				stmt = conn.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );
				int index = 1;
				stmt.setInt( index++, dto.getId() );
				stmt.setInt( index++, dto.getRmg_timeSheet_Id() );
				stmt.setInt(index++,dto.getUser_Id() );
				stmt.setInt( index++, dto.getPrj_Id() );
				stmt.setInt(index++,dto.getChrg_Code_Id() );
				
				stmt.setDate(index++, dto.getStartDate()==null ? null : new java.sql.Date( dto.getStartDate().getTime() ) );
				stmt.setDate(index++, dto.getEndDate()==null ? null : new java.sql.Date( dto.getEndDate().getTime() ) );
				
			//	stmt.setInt(index++,dto.getWorking_Days());
				//stmt.setInt(index++,dto.getLeave());
				//stmt.setString(index++, dto.getTimeSheet_Cato());
				//stmt.setString(index++, dto.getStatus());
				//stmt.setString(index++, dto.getActionBy());
				stmt.setDate(index++,null );
				stmt.setInt(index++, dto.getRoll_on_id());
				stmt.setString(index++, "0");
				stmt.setInt(index++,dto.getWorking_Days());
				
				// WORKING_DAYS,TIMESHEET_LEAVE,TIMESHEET_CATEGORY,STATUS,ACTION_BY
				
				/*ID, RMG_TIMESHEET_ID, USER_ID,PRJ_ID, CHRG_CODE_ID,START_DATE,END_DATE, 
				WORKING_DAYS,TIMESHEET_LEAVE,TIMESHEET_CATEGORY,STATUS,ACTION_BY,CREATE_DATE*/
				
				/*ID	int(11) AI PK
				RMG_TIMESHEET_ID	int(11)
				USER_ID	int(11)
				PRJ_ID	int(11)
				CHRG_CODE_ID	int(11)
				START_DATE	date
				END_DATE	date
				WORKING_DAYS	int(11)
				TIMESHEET_LEAVE	int(11)
				TIMESHEET_CATEGORY	int(11)
				STATUS	varchar(45)
				ACTION_BY	varchar(45)
				CREATE_DATE	varchar(45)*/
				
				
				if (logger.isDebugEnabled()) {
					logger.debug( "Executing " + SQL_INSERT + " with DTO: " + dto);
				}
			
				int rows = stmt.executeUpdate();
				long t2 = System.currentTimeMillis();
				if (logger.isDebugEnabled()) {
					logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
				}
			
			
				// retrieve values from auto-increment columns
				rs = stmt.getGeneratedKeys();
				if (rs != null && rs.next()) {
					dto.setId( rs.getInt( 1 ) );
				}
			
				reset(dto);
				//return dto.getId();
			}
			catch (Exception _e) {
				logger.error( "Exception: " + _e.getMessage(), _e );
				throw new RmgTimeSheetDaoException( "Exception: " + _e.getMessage(), _e );
			}
			finally {
				ResourceManager.close(stmt);
				if (!isConnSupplied) {
					ResourceManager.close(conn);
				}
			
			}
			
		}
		return dto;
	}
	
	
	public void update(RmgTimeSheetPk pk, RmgTimeSheet dto) throws RmgTimeSheetDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			if (logger.isDebugEnabled()) {
				logger.debug( "Executing " + SQL_UPDATE + " with DTO: " + dto);
			}
		
			stmt = conn.prepareStatement(SQL_UPDATE);
					
			int index=1;
			
			
			
			stmt.setDate(index++, dto.getStartDate()==null ? null : new java.sql.Date( dto.getStartDate().getTime()));
			stmt.setDate(index++, dto.getEndDate()==null ? null : new java.sql.Date( dto.getEndDate().getTime()));
			stmt.setInt( index++, dto.getWorking_Days() );
		//	System.out.println("working days=="+dto.getWorking_Days() );
			stmt.setInt( index++, dto.getLeave());
			stmt.setString(index++,dto.getTimeSheet_Cato());
			stmt.setString(index++,dto.getStatus());
			stmt.setString(index++,dto.getActionBy());
       //      System.out.println("action by"+dto.getActionBy());
             stmt.setString(index++, dto.getTr_Flag());
			stmt.setInt( index++, dto.getId() );
	//		System.out.println("id days=="+dto.getId() );
			
			//stmt.setString( index++, dto.gett );
			//stmt.setInt( 10, pk.getId() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug( rows + " rows affected (" + (t2-t1) + " ms)");
			}
		
			
		
		}
		catch (Exception _e) {
			logger.error( "Exception: " + _e.getMessage(), _e );
			throw new RmgTimeSheetDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	
	
public RmgTimeSheetDaoImpl() {}
	
	public RmgTimeSheetDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}
	
	public String getTableName()
	{
		return " RMG_TIMESHEET_REPORT";
	}

	protected void reset(RmgTimeSheet dto)
	{
	}



	@Override
	public RmgTimeSheet insertDataBy(RmgTimeSheet dto) throws RmgTimeSheetDaoException {
		
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String sql="INSERT INTO RMG_TIMESHEET_DETAILS ( ID,TIMESHEET_ID,COMMENTS,LEAVE,TIMESHEET_CATEGORY,STATUS,ACTION_BY,CREATE_DATE) VALUES  (?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setInt(index++, 0);
			stmt.setInt(index++, dto.getId());
			stmt.setString(index++, dto.getComments());
			stmt.setInt(index++, dto.getLeave());
			stmt.setString(index++, dto.getTimeSheet_Cato());
			stmt.setString(index++, dto.getStatus());
			stmt.setString(index++, dto.getActionBy());
			//stmt.setDate(index++, dto.get==null ? null : new java.sql.Date( dto.getPoDate().getTime() ) );
			int affectedrow=stmt.executeUpdate();
		}catch(Exception e){
			logger.error("There is an SQLException occured while querying the users details from users table " + e.getMessage(), e);
		}
		return null;
	}

	


	@Override
	public RmgTimeSheet[] getTimeSheetReconcialition(int id)throws RmgTimeSheetDaoException {
		
		//final String SQL ="select distinct u.EMP_ID, concat(PF.first_name,' ',PF.last_name) ,cc.CH_CODE_NAME,PRJ.NAME AS PRJ_NAME,rmgt.START_DATE,rmgt.END_DATE, rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE, rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY from RMG_TIMESHEET_REPORT rmgt left join USERS u on u.ID=rmgt.USER_ID left join PROFILE_INFO PF on PF.ID=rmgt.USER_ID left join PROJECT PRJ on PRJ.ID=rmgt.PRJ_ID left join CHARGE_CODE cc on cc.ID=rmgt.CHRG_CODE_ID left join ROLL_ON rn on rn.ID=rmgt.ROLL_ON_ID where rmgt.RMG_TIMESHEET_ID=?;";
		final String SQL ="select distinct u.EMP_ID, concat(PF.first_name,' ',PF.last_name) ,cc.CH_CODE_NAME,PRJ.NAME AS PRJ_NAME,rmgt.START_DATE,rmgt.END_DATE, rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE, rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY from RMG_TIMESHEET_REPORT rmgt left join USERS u on u.ID=rmgt.USER_ID left join PROFILE_INFO PF on PF.ID=u.PROFILE_ID left join PROJECT PRJ on PRJ.ID=rmgt.PRJ_ID left join CHARGE_CODE cc on cc.ID=rmgt.CHRG_CODE_ID left join ROLL_ON rn on rn.ID=rmgt.ROLL_ON_ID where rmgt.RMG_TIMESHEET_ID=?;";

		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		RmgTimeSheet rmgTimeSheet = null;
		List<RmgTimeSheet> list = new ArrayList<RmgTimeSheet>();
		 //Collection resultList = new ArrayList();
		// RmgTimeSheet ret[] = new RmgTimeSheet[resultList.size()];
		try{
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		stmt = conn.prepareStatement(SQL);
				logger.debug("");
			
				stmt.setObject(1, id);
			 res = stmt.executeQuery();
			 
			
			 Collection resultList = new ArrayList();
				while (res.next()){
					RmgTimeSheet dto = new RmgTimeSheet();
					populateDto1(dto, res);
					list.add(dto);
				}
				
		} catch (SQLException e){
			logger.error("There is an SQLException occured while querying the users details from users table " + e.getMessage(), e);
		
		}finally{
			ResourceManager.close(res);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		return list.toArray(new RmgTimeSheet[list.size()]);
	
	
	}
	
	
	public RmgTimeSheet[] findByAl() throws RmgTimeSheetDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		RmgTimeSheet rmgTimeSheet=null;

		try {

			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			
		//	final String SQL = " select roll.emp_id, concat(pf.first_name,' ',pf.last_name),u.ID as user_id, ch.Id as charg_code_id,p.ID as project_ID, pomap.PO_ST_DATE, pomap.PO_END_DATE, rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,roll.ID as roll_id  from ROLL_ON roll, PROFILE_INFO pf,USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID,PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id";
			final String SQL = " select roll.emp_id, concat(pf.first_name,' ',pf.last_name),u.ID as user_id, ch.Id as charg_code_id,p.ID as project_ID,rmgt.TIMESHEET_LEAVE,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,roll.ID as roll_id  from ROLL_ON roll, PROFILE_INFO pf,USERS u left join RMG_TIMESHEET_REPORT rmgt on u.ID=rmgt.USER_ID,PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id";

			// prepare statement
			stmt = conn.prepareStatement(SQL);
			res = stmt.executeQuery();

			// fetch the results
			
			return fetchMultiResult(res);
			
			

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			ResourceManager.close(res);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

		return null;
	}
	
	protected RmgTimeSheet[] fetchMultiResult(ResultSet rs)
			throws SQLException {
		Collection<RmgTimeSheet> resultList = new ArrayList<RmgTimeSheet>();
		while (rs.next()) {
			RmgTimeSheet dto = new RmgTimeSheet();
			populateDto(dto, rs);
			resultList.add(dto);

		}

		RmgTimeSheet ret[] = new RmgTimeSheet[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	
	protected void populateDto(RmgTimeSheet dto, ResultSet res)
			throws SQLException {
		
		
		dto.setEmpId(res.getInt(1));
		dto.setName(res.getString(2));
		dto.setUser_Id(res.getInt(3));
		dto.setChrg_Code_Id(res.getInt(4));
		dto.setPrj_Id(res.getInt(5));
	//	dto.setStartDate(res.getDate(6));
	//	dto.setEndDate(res.getDate(7));
	//	dto.setWorking_Days(res.getInt(6));
		dto.setLeave(res.getInt(6));
		dto.setTimeSheet_Cato(res.getString(7));
		dto.setStatus(res.getString(8));
		dto.setRoll_on_id(res.getInt(9));
		
		

	}
	
	
	protected void populateDto1(RmgTimeSheet dto, ResultSet res)
			throws SQLException {
		
		
		dto.setEmpId(res.getInt(1));
		dto.setName(res.getString(2));
		dto.setChrg_Code_Name(res.getString(3));
		dto.setPrj_Name(res.getString(4));;
		dto.setStartDate(res.getDate(5));
		dto.setEndDate(res.getDate(6));
	//	System.out.println("end===="+dto.getEndDate());
		dto.setWorking_Days(res.getInt(7));
		dto.setLeave(res.getInt(8));
		dto.setTimeSheet_Cato(res.getString(9));
		dto.setStatus(res.getString(10));
		dto.setActionBy(res.getString(11));
		

	}


	@Override
	public RmgTimeSheet[] findByAll(int id,String flag) throws RmgTimeSheetDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		RmgTimeSheet rmgTimeSheet = null;
		List<RmgTimeSheet> list = new ArrayList<RmgTimeSheet>();

		try {

			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
		
			final String SQL_UPDATED_REPORT = "select distinct u.EMP_ID, concat(PF.first_name,' ',PF.last_name),PRJ.NAME AS PRJ_NAME,cc.CH_CODE_NAME,rmgt.START_DATE,rmgt.END_DATE, rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY,rmgt.ID AS RMG_TIMESHEET_REPORT_ID, rn.PROJECT_TYPE,u.ID as user_id  from RMG_TIMESHEET_REPORT rmgt left join USERS u on u.ID=rmgt.USER_ID left join PROFILE_INFO PF on PF.ID=u.PROFILE_ID left join PROJECT PRJ on PRJ.ID=rmgt.PRJ_ID left join CHARGE_CODE cc on cc.ID=rmgt.CHRG_CODE_ID left join ROLL_ON rn on rn.ID=rmgt.ROLL_ON_ID where rmgt.RMG_TIMESHEET_ID=? and TR_FLAG=1";
			
				final String SQL_ALL_REPORT = "select distinct u.EMP_ID, concat(PF.first_name,' ',PF.last_name),PRJ.NAME AS PRJ_NAME,cc.CH_CODE_NAME,rmgt.START_DATE,rmgt.END_DATE, rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY,rmgt.ID AS RMG_TIMESHEET_REPORT_ID, rn.PROJECT_TYPE,u.ID as user_id  from RMG_TIMESHEET_REPORT rmgt left join USERS u on u.ID=rmgt.USER_ID left join PROFILE_INFO PF on PF.ID=u.PROFILE_ID left join PROJECT PRJ on PRJ.ID=rmgt.PRJ_ID left join CHARGE_CODE cc on cc.ID=rmgt.CHRG_CODE_ID left join ROLL_ON rn on rn.ID=rmgt.ROLL_ON_ID where rmgt.RMG_TIMESHEET_ID=?";

		
			
			
			//final String SQL ="select u.EMP_ID, concat(PF.first_name,' ',PF.last_name),PRJ.NAME AS PRJ_NAME,cc.CH_CODE_NAME,rmgt.START_DATE,rmgt.END_DATE, rmgt.WORKING_DAYS,rmgt.TIMESHEET_LEAVE,rmgt.TIMESHEET_CATEGORY,rmgt.STATUS,rmgt.ACTION_BY,rmgt.ID AS RMG_TIMESHEET_REPORT_ID, rn.PROJECT_TYPE from RMG_TIMESHEET_REPORT rmgt,USERS u,PROFILE_INFO PF,PROJECT PRJ,CHARGE_CODE cc,ROLL_ON rn WHERE  PF.ID=rmgt.USER_ID AND PRJ.ID=rmgt.PRJ_ID AND u.ID=rmgt.USER_ID AND cc.ID=rmgt.CHRG_CODE_ID AND rn.ID=rmgt.ROLL_ON_ID and rmgt.RMG_TIMESHEET_ID=?";

			
			// prepare statement
			if("0".equals(flag)){
			stmt = conn.prepareStatement(SQL_ALL_REPORT);
			}else{
				stmt = conn.prepareStatement(SQL_UPDATED_REPORT);
			}
				
			stmt.setObject(1, id);
			res = stmt.executeQuery();

			while (res.next()){
				
				rmgTimeSheet = new RmgTimeSheet();
				rmgTimeSheet.setEmpId(res.getInt(1));
				rmgTimeSheet.setName(res.getString(2));
				rmgTimeSheet.setChrg_Code_Name(res.getString(3));
				rmgTimeSheet.setPrj_Name(res.getString(4));;
				rmgTimeSheet.setStartDate(res.getDate(5));
				rmgTimeSheet.setEndDate(res.getDate(6));
				rmgTimeSheet.setWorking_Days(res.getInt(7));
				rmgTimeSheet.setLeave(res.getInt(8));
				rmgTimeSheet.setTimeSheet_Cato(res.getString(9));
				rmgTimeSheet.setStatus(res.getString(10));
				rmgTimeSheet.setActionBy(res.getString(11));
				rmgTimeSheet.setId(res.getInt(12));
				rmgTimeSheet.setProjectType(res.getString(13));
				rmgTimeSheet.setUser_Id(res.getInt(14));
				list.add(rmgTimeSheet);
					
				}
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("There is an SQLException occured while querying the users details from users table " + e.getMessage(), e);

		} finally {
			ResourceManager.close(res);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

		return list.toArray(new RmgTimeSheet[list.size()]);
	}

}
