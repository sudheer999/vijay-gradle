package com.dikshatech.portal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.log4j.Logger;
import com.dikshatech.beans.AppraisalBean;
import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.common.utils.JDBCUtiility;
import com.dikshatech.common.utils.LoggerUtil;
import com.dikshatech.portal.exceptions.AppraisalDaoException;

public class AppraisalDetailsDaoImpl {

	private static Logger					logger	= LoggerUtil.getLogger(AppraisalDetailsDaoImpl.class);
	private static AppraisalDetailsDaoImpl	add		= null;

	private AppraisalDetailsDaoImpl() {}

	public static AppraisalDetailsDaoImpl getInstance() {
		if (add == null) add = new AppraisalDetailsDaoImpl();
		return add;
	}

	// appraisal
	public int updateAppraisaldetails(AppraisalBean apprailsal) {
		// declare variables
		if (apprailsal == null || apprailsal.getId() == 0) return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int noOfRowsUpdated = 0;
		try{
			boolean isUpdate = JDBCUtiility.getInstance().getRowCount("FROM APPRAISAL_DETAILS WHERE ID=?", new Object[] { apprailsal.getId() }) > 0;
			conn = ResourceManager.getConnection();
			final String SQL_INSERT = "INSERT INTO APPRAISAL_DETAILS ( ID, PROJ_DETAILS, PROJ_ACTIVITY, PROJ_TOTALDAYS, PROF_RATINGS, PROF_PRACTICES, PROF_LIKEWISE, PROF_DIFFICULT, PROF_MOST, PROF_LEAST, PERS_RATINGS, INDI_GOALS, INDI_TIME, INDI_PLAN, INDI_STRENGTHS, INDI_OPPORTUNITIES, INDI_TIMEBOUND, INDI_GAPS, INDI_SKILS, INDI_PERFORMANCE, INDI_ADVANCEMENT, INDI_ROADMAP, FEED_RATINGS, FEED_COMMENTS, PROM_RATINGS, PROM_COMMENTS, PROM_PROMOTABLE, PROM_HOWSOON, PROM_PROMOTEDAS ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			final String SQL_UPDATE = "UPDATE APPRAISAL_DETAILS SET PROJ_DETAILS = ?, PROJ_ACTIVITY = ?, PROJ_TOTALDAYS = ?, PROF_RATINGS = ?, PROF_PRACTICES = ?, PROF_LIKEWISE = ?, PROF_DIFFICULT = ?, PROF_MOST = ?, PROF_LEAST = ?, PERS_RATINGS = ?, INDI_GOALS = ?, INDI_TIME = ?, INDI_PLAN = ?, INDI_STRENGTHS = ?, INDI_OPPORTUNITIES = ?, INDI_TIMEBOUND = ?, INDI_GAPS = ?, INDI_SKILS = ?, INDI_PERFORMANCE = ?, INDI_ADVANCEMENT = ?, INDI_ROADMAP = ?, FEED_RATINGS = ?, FEED_COMMENTS = ?, PROM_RATINGS = ?, PROM_COMMENTS = ?, PROM_PROMOTABLE = ?, PROM_HOWSOON = ?, PROM_PROMOTEDAS = ? WHERE ID = ?";
			stmt = conn.prepareStatement(!isUpdate ? SQL_INSERT : SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			if (!isUpdate) stmt.setInt(index++, apprailsal.getId());
			stmt.setString(index++, encrypt(getArrayAsString(apprailsal.getProj_details())));
			stmt.setString(index++, encrypt(apprailsal.getProj_activity()));
			stmt.setString(index++, encrypt(apprailsal.getProj_totalDays()));
			stmt.setString(index++, encrypt(getArrayAsString(apprailsal.getProf_ratings())));
			stmt.setString(index++, encrypt(apprailsal.getProf_practices()));
			stmt.setString(index++, encrypt(apprailsal.getProf_likewise()));
			stmt.setString(index++, encrypt(apprailsal.getProf_difficult()));
			stmt.setString(index++, encrypt(apprailsal.getProf_most()));
			stmt.setString(index++, encrypt(apprailsal.getProf_least()));
			stmt.setString(index++, encrypt(getArrayAsString(apprailsal.getPers_ratings())));
			stmt.setString(index++, encrypt(apprailsal.getIndi_goals()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_time()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_plan()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_strengths()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_opportunities()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_timeBound()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_gaps()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_skils()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_performance()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_advancement()));
			stmt.setString(index++, encrypt(apprailsal.getIndi_roadMap()));
			stmt.setString(index++, encrypt(getArrayAsString(apprailsal.getFeed_ratings())));
			stmt.setString(index++, encrypt(apprailsal.getFeed_comments()));
			stmt.setString(index++, encrypt(getArrayAsString(apprailsal.getProm_ratings())));
			stmt.setString(index++, encrypt(apprailsal.getProm_comments()));
			stmt.setString(index++, encrypt(apprailsal.getProm_promotable()));
			stmt.setString(index++, encrypt(apprailsal.getProm_howSoon()));
			stmt.setString(index++, encrypt(apprailsal.getProm_promotedAs()));
			if (isUpdate) stmt.setInt(index++, apprailsal.getId());
			noOfRowsUpdated = stmt.executeUpdate();
		} catch (Exception _e){
			logger.error("Exception: " + _e.getMessage(), _e);
			_e.printStackTrace();
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			ResourceManager.close(conn);
		}
		return noOfRowsUpdated;
	}

	public AppraisalBean getAppraisaldetails(int id) throws AppraisalDaoException {
		AppraisalBean[] res = selectAppraisaldetails("SELECT * FROM APPRAISAL_DETAILS WHERE ID=?", new Object[] { id }, false);
		if (res != null && res.length >= 1) return res[0];
		return new AppraisalBean();
	}
/*
	public void encryptOldData(String id) {
		try{
			synchronized (add){
				AppraisalBean[] res = selectAppraisaldetails("SELECT * FROM APPRAISAL_DETAILS WHERE ID=?", new Object[] { id }, true);
				for (AppraisalBean apraisldetails : res){
					logger.info("Encrypting data of ID : " + apraisldetails.getId());
					updateAppraisaldetails(apraisldetails);
					logger.info("Encrypting data of ID : " + apraisldetails.getId() + " is successful");
				}
			}
		} catch (Exception e){
			// TODO: handle exception
		}
	}*/

	public AppraisalBean[] selectAppraisaldetails(String sql, Object[] sqlParams, boolean decrypt) throws AppraisalDaoException {
		// declare variables
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			// get the user-specified connection or get a connection from the ResourceManager
			conn = ResourceManager.getConnection();
			// construct the SQL statement
			final String SQL = sql;
			// prepare statement
			stmt = conn.prepareStatement(SQL);
			// bind parameters
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++){
				stmt.setObject(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			Collection<AppraisalBean> resultList = new ArrayList<AppraisalBean>();
			while (rs.next()){
				AppraisalBean dto = new AppraisalBean();
				/*if (decrypt) populateDtowithourDecrypt(dto, rs);
				else*/ populateDto(dto, rs);
				resultList.add(dto);
			}
			AppraisalBean ret[] = new AppraisalBean[resultList.size()];
			resultList.toArray(ret);
			return ret;
		} catch (Exception _e){
			_e.printStackTrace();
			throw new AppraisalDaoException("Exception: " + _e.getMessage(), _e);
		} finally{
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			ResourceManager.close(conn);
		}
	}
/*
	private void populateDtowithourDecrypt(AppraisalBean dto, ResultSet rs) throws SQLException {
		int index = 1;
		dto.setId(rs.getInt(index++));
		dto.setProj_details(getStringAsArray(rs.getString(index++)));
		dto.setProj_activity(rs.getString(index++));
		dto.setProj_totalDays(rs.getString(index++));
		dto.setProf_ratings(getStringAsArray(rs.getString(index++)));
		dto.setProf_practices(rs.getString(index++));
		dto.setProf_likewise(rs.getString(index++));
		dto.setProf_difficult(rs.getString(index++));
		dto.setProf_most(rs.getString(index++));
		dto.setProf_least(rs.getString(index++));
		dto.setPers_ratings(getStringAsArray(rs.getString(index++)));
		dto.setIndi_goals(rs.getString(index++));
		dto.setIndi_time(rs.getString(index++));
		dto.setIndi_plan(rs.getString(index++));
		dto.setIndi_strengths(rs.getString(index++));
		dto.setIndi_opportunities(rs.getString(index++));
		dto.setIndi_timeBound(rs.getString(index++));
		dto.setIndi_gaps(rs.getString(index++));
		dto.setIndi_skils(rs.getString(index++));
		dto.setIndi_performance(rs.getString(index++));
		dto.setIndi_advancement(rs.getString(index++));
		dto.setIndi_roadMap(rs.getString(index++));
		dto.setFeed_ratings(getStringAsArray(rs.getString(index++)));
		dto.setFeed_comments(rs.getString(index++));
		dto.setProm_ratings(getStringAsArray(rs.getString(index++)));
		dto.setProm_comments(rs.getString(index++));
		dto.setProm_promotable(rs.getString(index++));
		dto.setProm_howSoon(rs.getString(index++));
		dto.setProm_promotedAs(rs.getString(index++));
	}
*/
	/**
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(AppraisalBean dto, ResultSet rs) throws SQLException {
		int index = 1;
		dto.setId(rs.getInt(index++));
		dto.setProj_details(getStringAsArray(decrypt(rs.getString(index++))));
		dto.setProj_activity(decrypt(rs.getString(index++)));
		dto.setProj_totalDays(decrypt(rs.getString(index++)));
		dto.setProf_ratings(getStringAsArray(decrypt(rs.getString(index++))));
		dto.setProf_practices(decrypt(rs.getString(index++)));
		dto.setProf_likewise(decrypt(rs.getString(index++)));
		dto.setProf_difficult(decrypt(rs.getString(index++)));
		dto.setProf_most(decrypt(rs.getString(index++)));
		dto.setProf_least(decrypt(rs.getString(index++)));
		dto.setPers_ratings(getStringAsArray(decrypt(rs.getString(index++))));
		dto.setIndi_goals(decrypt(rs.getString(index++)));
		dto.setIndi_time(decrypt(rs.getString(index++)));
		dto.setIndi_plan(decrypt(rs.getString(index++)));
		dto.setIndi_strengths(decrypt(rs.getString(index++)));
		dto.setIndi_opportunities(decrypt(rs.getString(index++)));
		dto.setIndi_timeBound(decrypt(rs.getString(index++)));
		dto.setIndi_gaps(decrypt(rs.getString(index++)));
		dto.setIndi_skils(decrypt(rs.getString(index++)));
		dto.setIndi_performance(decrypt(rs.getString(index++)));
		dto.setIndi_advancement(decrypt(rs.getString(index++)));
		dto.setIndi_roadMap(decrypt(rs.getString(index++)));
		dto.setFeed_ratings(getStringAsArray(decrypt(rs.getString(index++))));
		dto.setFeed_comments(decrypt(rs.getString(index++)));
		dto.setProm_ratings(getStringAsArray(decrypt(rs.getString(index++))));
		dto.setProm_comments(decrypt(rs.getString(index++)));
		dto.setProm_promotable(decrypt(rs.getString(index++)));
		dto.setProm_howSoon(decrypt(rs.getString(index++)));
		dto.setProm_promotedAs(decrypt(rs.getString(index++)));
	}

	private String getArrayAsString(String[] array) {
		if (array != null){
			StringBuffer res = new StringBuffer();
			boolean i = false;
			for (String line : array){
				if (i) res.append("~=&=~");
				i = true;
				res.append(line);
			}
			return res.toString();
		}
		return "";
	}

	private String[] getStringAsArray(String string) {
		if (string != null){
			return string.split("~=&=~");
		}
		return new String[] {};
	}

	public static String encrypt(String str) {// code for encrypting the existing data...
		/*try{
			DesEncrypterDecrypter desEncrypterDecrypter = new DesEncrypterDecrypter(SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[] { 0x001, 0x002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002 })));
			return desEncrypterDecrypter.encrypt(str);
		} catch (Exception e){
			e.printStackTrace();
		}*/
		return str;
	}

	public static String decrypt(String str) {// code for encrypting the existing data...
		/*try{
			DesEncrypterDecrypter desEncrypterDecrypter = new DesEncrypterDecrypter(SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[] { 0x001, 0x002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002 })));
			return desEncrypterDecrypter.decrypt(str);
		} catch (Exception e){}*/
		return str;
	}
}
