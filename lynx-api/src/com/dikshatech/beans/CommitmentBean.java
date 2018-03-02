package com.dikshatech.beans;

import java.util.Date;

public class CommitmentBean
{

		/**
		 * This attribute maps to the column ID in the COMMITMENTS table.
		 */
		protected int		id;

		/**
		 * This attribute maps to the column COMMENTS in the COMMITMENTS table.
		 */
		protected String	comments;

		/**
		 * This attribute maps to the column DATE_PICKER in the COMMITMENTS table.
		 */
		protected Date		datePicker;

		/**
		 * This attribute maps to the column CANDIDATE_ID in the COMMITMENTS table.
		 */
		protected int		candidateId;

		/**
		 * This attribute represents whether the primitive attribute candidateId is null.
		 */
		protected boolean	candidateIdNull	= true;

		/**
		 * This attribute maps to the column USER_ID_EMP in the COMMITMENTS table.
		 */
		protected int		userIdEmp;

		/**
		 * This attribute represents whether the primitive attribute userIdEmp is null.
		 */
		protected boolean	userIdEmpNull		= true;


		/**
		 * Method 'Commitments'
		 */
		public CommitmentBean()
		{
		}

		/**
		 * Method 'getId'
		 * 
		 * @return int
		 */
		public int getId()
		{
			return id;
		}

		/**
		 * Method 'setId'
		 * 
		 * @param id
		 */
		public void setId(int id)
		{
			this.id = id;
		}

		/**
		 * Method 'getComments'
		 * 
		 * @return String
		 */
		public String getComments()
		{
			return comments;
		}

		/**
		 * Method 'setComments'
		 * 
		 * @param comments
		 */
		public void setComments(String comments)
		{
			this.comments = comments;
		}

		/**
		 * Method 'getDatePicker'
		 * 
		 * @return Date
		 */
		public Date getDatePicker()
		{
			return datePicker;
		}

		/**
		 * Method 'setDatePicker'
		 * 
		 * @param datePicker
		 */
		public void setDatePicker(Date datePicker)
		{
			this.datePicker = datePicker;
		}

		/**
		 * Method 'getCandidateId'
		 * 
		 * @return int
		 */
		public int getCandidateId()
		{
			return candidateId;
		}

		/**
		 * Method 'setCandidateId'
		 * 
		 * @param candidateId
		 */
		public void setCandidateId(int candidateId)
		{
			this.candidateId = candidateId;
			this.candidateIdNull = false;
		}

		/**
		 * Method 'setCandidateIdNull'
		 * 
		 * @param value
		 */
		public void setCandidateIdNull(boolean value)
		{
			this.candidateIdNull = value;
		}

		/**
		 * Method 'isCandidateIdNull'
		 * 
		 * @return boolean
		 */
		public boolean isCandidateIdNull()
		{
			return candidateIdNull;
		}

		/**
		 * Method 'getUserIdEmp'
		 * 
		 * @return int
		 */
		public int getUserIdEmp()
		{
			return userIdEmp;
		}

		/**
		 * Method 'setUserIdEmp'
		 * 
		 * @param userIdEmp
		 */
		public void setUserIdEmp(int userIdEmp)
		{
			this.userIdEmp = userIdEmp;
			this.userIdEmpNull = false;
		}

		/**
		 * Method 'setUserIdEmpNull'
		 * 
		 * @param value
		 */
		public void setUserIdEmpNull(boolean value)
		{
			this.userIdEmpNull = value;
		}

		/**
		 * Method 'isUserIdEmpNull'
		 * 
		 * @return boolean
		 */
		public boolean isUserIdEmpNull()
		{
			return userIdEmpNull;
		}

}