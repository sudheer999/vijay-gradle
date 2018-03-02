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

import com.dikshatech.common.utils.DesEncrypterDecrypter;
import com.dikshatech.portal.dao.PoDetailDao;
//import com.dikshatech.portal.dao.Podetaildao;
import com.dikshatech.portal.dto.ActiveChargeCode;
import com.dikshatech.portal.dto.PoDetail;
import com.dikshatech.portal.dto.PoDetails;
import com.dikshatech.portal.dto.SalaryReconciliationReport;
import com.dikshatech.portal.exceptions.PoDetailDaoException;
import com.dikshatech.portal.exceptions.SalaryReconciliationReportDaoException;

public class PoDetailDaoImpl implements PoDetailDao{
	
	protected java.sql.Connection userConn;
	
	protected static final Logger logger = Logger
			.getLogger(PoDetailDaoImpl.class);
	
	/*public PoDetailDaoImpl(Connection conn) {
		// TODO Auto-generated constructor stub
	}*/
	
	

	@Override
	public PoDetail[] findByPoIdAndEmpId(int poId, int empId)throws PoDetailDaoException {
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
	
		List<PoDetail> list = new ArrayList<PoDetail>();
		try {

			// get the user-specified connection or get a connection from the
			// ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();

			// construct the SQL statement
			
			final String sql="SELECT PO_ID,PO_NUMBER,PO_DATE,PO_ST_DATE,PO_END_DATE,PO_DURATION,RATE FROM PO_EMP_MAP WHERE PO_ID=? AND ID=?";
			// prepare statement
			stmt = conn.prepareStatement(sql);
			stmt.setObject(1, poId);
			stmt.setObject(2, empId);
			res = stmt.executeQuery();

			return fetchMultiResults(res);
			

		}catch (Exception _e){
			_e.printStackTrace();
			   logger.error("Exception in findByPoIdAndEmpId: " + _e.getMessage(), _e);
			
		} finally{
			ResourceManager.close(res);
			ResourceManager.close(stmt);
			if (!isConnSupplied){
				ResourceManager.close(conn);
			}
		}
		return null;
	}
	
	protected PoDetail[] fetchMultiResults(ResultSet rs) throws SQLException {
		Collection<PoDetail> resultList = new ArrayList<PoDetail>();
		while (rs.next()){
			PoDetail dto = new PoDetail();
		
			populateDto(dto, rs);
			resultList.add(dto);
		}
		PoDetail ret[] = new PoDetail[resultList.size()];
		resultList.toArray(ret);
		return ret;
	}
	
	protected void populateDto(PoDetail dto, ResultSet res) throws SQLException {
		dto.setEmpPoId(res.getInt(1));
	   dto.setEmpPoNumber(res.getString(2));
		dto.setEmpPoDate(res.getDate(3));
	    dto.setEmpPoStDate(res.getDate(4));
	    dto.setEmpPoEndDate(res.getDate(5));
	    dto.setEmpPoDuration( res.getString(6) );
	    dto.setRate(res.getString(7));
		}
	
	
	
	
	public PoDetailDaoImpl(final java.sql.Connection userConn) {
		this.userConn = userConn;
	}
	
	public PoDetailDaoImpl() {
	}

	@Override
	public String updateAllPoDetailsById(int poId,PoDetails dto) throws PoDetailDaoException {
		// declare variables
				final boolean isConnSupplied = (userConn != null);
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				try{
					// get the user-specified connection or get a connection from the ResourceManager
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
				
					
					String sql="UPDATE PO_EMP_MAP SET RATE=?, PO_NUMBER =? , PO_DATE = ?, PO_ST_DATE = ?, PO_END_DATE = ?,PO_DURATION=? WHERE ID = ?";
					int i=3;
					stmt = conn.prepareStatement(sql);
					int index = 1;
			
				
					stmt.setString(index++, dto.getRate());
					stmt.setString( index++, dto.getPoNumber() );
					stmt.setDate(index++, dto.getPoDate()==null ? null : new java.sql.Date( dto.getPoDate().getTime() ) );
					stmt.setDate(index++, dto.getPoStDate()==null ? null : new java.sql.Date( dto.getPoStDate().getTime() ) );
					stmt.setDate(index++, dto.getPoEndDate()==null ? null : new java.sql.Date( dto.getPoEndDate().getTime() ) );
					stmt.setString(index++, dto.getPoDuration());
					stmt.setInt( index++, poId);
					
				
					
					
					int affectedrow=stmt.executeUpdate();
					
				} catch (Exception _e){
		          _e.printStackTrace();
		          logger.error("Exception in updateAllPoDetailsById: " + _e.getMessage(), _e);
				
				} finally{
					ResourceManager.close(rs);
					ResourceManager.close(stmt);
					if (!isConnSupplied){
						ResourceManager.close(conn);
					}
				}
				return "Successfully updated";	
				
	}

}
