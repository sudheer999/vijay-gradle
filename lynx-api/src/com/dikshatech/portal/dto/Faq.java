/*
 * This source file was generated by FireStorm/DAO.
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * For more information please visit http://www.codefutures.com/products/firestorm
 */
package com.dikshatech.portal.dto;

import java.io.Serializable;
import com.dikshatech.portal.forms.PortalForm;

public class Faq extends PortalForm implements Serializable {

	/**
	 * This attribute maps to the column ID in the FAQ table.
	 */
	protected int		id;
	/**
	 * This attribute maps to the column QUESTION in the FAQ table.
	 */
	protected String	question;
	/**
	 * This attribute maps to the column ANSWER in the FAQ table.
	 */
	protected String	answer;
	private String		isEditable;
	private Object[]	list;

	/**
	 * Method 'Faq'
	 */
	public Faq() {}

	/**
	 * Method 'getId'
	 * 
	 * @return Integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method 'getQuestion'
	 * 
	 * @return String
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Method 'setQuestion'
	 * 
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Method 'getAnswer'
	 * 
	 * @return String
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Method 'setAnswer'
	 * 
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other) {
		if (_other == null){
			return false;
		}
		if (_other == this){
			return true;
		}
		if (!(_other instanceof Faq)){
			return false;
		}
		final Faq _cast = (Faq) _other;
		if (_cast.id != id){
			return false;
		}
		if (question == null ? _cast.question != question : !question.equals(_cast.question)){
			return false;
		}
		if (answer == null ? _cast.answer != answer : !answer.equals(_cast.answer)){
			return false;
		}
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode() {
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + id;
		if (question != null){
			_hashCode = 29 * _hashCode + question.hashCode();
		}
		if (answer != null){
			_hashCode = 29 * _hashCode + answer.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return FaqPk
	 */
	public FaqPk createPk() {
		return new FaqPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.Faq: ");
		ret.append("id=" + id);
		ret.append(", question=" + question);
		ret.append(", answer=" + answer);
		return ret.toString();
	}

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
}
