package com.dikshatech.common.utils;

public class Loan
{
	public static final String PERSONAL = "Personal Loan";
	public static final String LAPTOP = "Laptop Loan";
	
	public static String getLoanType(int id){
		String loan = "";
		switch(id){
		case 1 : loan = PERSONAL;
		break;
		case 2 : loan = LAPTOP;
		break;
		}
		return loan;
	}
	public static int getLoanId(String loan)
	{
		int loanId = 0;

		if (loan.equals(PERSONAL))
		{
			loanId = 1;
		}
		else if(loan.equals(LAPTOP))
		{
			loanId = 2;
		}
		
		return loanId;
	}
}
