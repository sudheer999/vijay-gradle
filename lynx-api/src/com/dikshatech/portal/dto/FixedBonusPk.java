package com.dikshatech.portal.dto;


public class FixedBonusPk {

		protected Integer id;

		/** 
		 * Sets the value of id
		 */
		public void setId(Integer id)
		{
			this.id = id;
		}

		/** 
		 * Gets the value of id
		 */
		public Integer getId()
		{
			return id;
		}

		/**
		 * Method 'FixedPerdiemPk'
		 * 
		 */
		public FixedBonusPk()
		{
		}

		/**
		 * Method 'FixedPerdiemPk'
		 * 
		 * @param id
		 */
		public FixedBonusPk(final Integer id)
		{
			this.id = id;
		}

		/**
		 * Method 'equals'
		 * 
		 * @param _other
		 * @return boolean
		 */
		public boolean equals(Object _other)
		{
			if (_other == null) {
				return false;
			}
			
			if (_other == this) {
				return true;
			}
			
			if (!(_other instanceof FixedBonusPk)) {
				return false;
			}
			
			final FixedBonusPk _cast = (FixedBonusPk) _other;
			if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
				return false;
			}
			
			return true;
		}

		/**
		 * Method 'hashCode'
		 * 
		 * @return int
		 */
		public int hashCode()
		{
			int _hashCode = 0;
			if (id != null) {
				_hashCode = 29 * _hashCode + id.hashCode();
			}
			
			return _hashCode;
		}

		/**
		 * Method 'toString'
		 * 
		 * @return String
		 */
		public String toString()
		{
			StringBuffer ret = new StringBuffer();
			ret.append( "com.dikshatech.portal.dto.FixedBonusPk: " );
			ret.append( "id=" + id );
			return ret.toString();
		}

	
}