package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.dikshatech.beans.SalaryReportBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.ActiveChargeCodeDao;
import com.dikshatech.portal.dto.ActiveChargeCode;
import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.exceptions.ActiveChargeCodeDaoException;

public class ActiveChargeCodeDaoImpl implements ActiveChargeCodeDao {

	protected java.sql.Connection userConn;

	protected static final Logger logger = Logger
			.getLogger(ActiveChargeCodeDaoImpl.class);

	/**
	 * Index of column ID
	 */
	protected static final int EMP_ID = 1;

	protected static final int NAME = 2;

	protected static final int PO_NO = 3;

	protected static final int CHARGE_CODE = 4;

	protected static final int PROJECT = 5;

	protected static final int START_DATE = 6;

	protected static final int END_DATE = 7;

	protected static final int RATE = 8;

	protected static final int CURRENCY = 9;

	protected static final int PO_SATATUS = 10;
	
	protected static final int PROJECT_TYPE = 11;
	
	protected static final int PO_ID = 12;
	
	protected static final int PO_EMAP_ID = 13;

	
	public ActiveChargeCode[] findByAll() throws ActiveChargeCodeDaoException {
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			
			final String SQL = "select roll.emp_id,concat(pf.first_name,' ',pf.last_name),pomap.po_number, ch.ch_code_name,p.name as project_name,  pomap.PO_ST_DATE, pomap.PO_END_DATE,pomap.rate,pomap.currency, pomap.CURRENT as PO_STATUS,roll.project_type,pod.ID as poId,pomap.ID as PO_EMAP_ID from ROLL_ON roll, PROFILE_INFO pf,USERS u,PROJECT p,CHARGE_CODE ch,PO_DETAILS pod, PO_EMP_MAP pomap where pf.id=u.profile_id and u.emp_id=roll.emp_id and roll.roll_off_date is null and ch.id = roll.ch_code_id and pod.id=ch.po_id and p.id=pod.proj_id and pomap.po_id=ch.po_id and(TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW())<183 OR (TIMESTAMPDIFF(DAY,roll.ROLL_OFF_DATE,NOW()) IS null AND roll.CURRENT=1)) group by roll.emp_id;";

			// prepare statement
			stmt = conn.prepareStatement(SQL);
			rs = stmt.executeQuery();

			// fetch the results
			return fetchMultiResult(rs);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}

		}

		return null;
	}

	/**
	 * Fetches multiple rows from the result set
	 */
	protected ActiveChargeCode[] fetchMultiResult(ResultSet rs)
			throws SQLException {
		Collection<ActiveChargeCode> resultList = new ArrayList<ActiveChargeCode>();
		while (rs.next()) {
			ActiveChargeCode dto = new ActiveChargeCode();
			populateDto(dto, rs);
			resultList.add(dto);

		}

		ActiveChargeCode ret[] = new ActiveChargeCode[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}

	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(ActiveChargeCode dto, ResultSet rs)
			throws SQLException {
		dto.setEmpId(rs.getInt(EMP_ID));
		dto.setName(rs.getString(NAME));
		dto.setProject(rs.getString(PROJECT));
		dto.setChargeCode(rs.getString(CHARGE_CODE));
		dto.setStartDate(rs.getDate(START_DATE));
		dto.setEndDate(rs.getDate(END_DATE));
		dto.setPoNo(rs.getString(PO_NO));
		dto.setRate(rs.getInt(RATE));
		dto.setCurrency(rs.getString(CURRENCY));
		dto.setPoStatus(rs.getString(PO_SATATUS));
		dto.setProjectType(rs.getString(PROJECT_TYPE));
		dto.setPoId(rs.getInt(PO_ID));
		dto.setPoEmapId(rs.getInt(PO_EMAP_ID));
		

	}

	
	public ActiveChargeCodeDaoImpl() {
	}

	
	public ActiveChargeCodeDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}



}
