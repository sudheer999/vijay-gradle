package com.dikshatech.portal.dto;

import java.io.Serializable;

public class ExitQuestionsMap implements Serializable {

	/**
	 * This attribute maps to the column EXIT_QUESTION_ID in the EXIT_QUESTIONS_MAP table.
	 */
	protected int		exitQuestionId;
	/**
	 * This attribute maps to the column EXIT_ID in the EXIT_QUESTIONS_MAP table.
	 */
	protected int		exitId;
	/**
	 * This attribute maps to the column ANSWER in the EXIT_QUESTIONS_MAP table.
	 */
	protected String	answer;
	private Object[]	answers;

	/**
	 * Method 'ExitQuestionsMap'
	 */
	public ExitQuestionsMap() {}

	/**
	 * Method 'getExitQuestionId'
	 * 
	 * @return int
	 */
	public int getExitQuestionId() {
		return exitQuestionId;
	}

	/**
	 * Method 'setExitQuestionId'
	 * 
	 * @param exitQuestionId
	 */
	public void setExitQuestionId(int exitQuestionId) {
		this.exitQuestionId = exitQuestionId;
	}

	/**
	 * Method 'getExitId'
	 * 
	 * @return int
	 */
	public int getExitId() {
		return exitId;
	}

	/**
	 * Method 'setExitId'
	 * 
	 * @param exitId
	 */
	public void setExitId(int exitId) {
		this.exitId = exitId;
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
		if (!(_other instanceof ExitQuestionsMap)){
			return false;
		}
		final ExitQuestionsMap _cast = (ExitQuestionsMap) _other;
		if (exitQuestionId != _cast.exitQuestionId){
			return false;
		}
		if (exitId != _cast.exitId){
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
		_hashCode = 29 * _hashCode + exitQuestionId;
		_hashCode = 29 * _hashCode + exitId;
		if (answer != null){
			_hashCode = 29 * _hashCode + answer.hashCode();
		}
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("com.dikshatech.portal.dto.ExitQuestionsMap: ");
		ret.append("exitQuestionId=" + exitQuestionId);
		ret.append(", exitId=" + exitId);
		ret.append(", answer=" + answer);
		return ret.toString();
	}

	public void setAnswers(Object[] answers) {
		this.answers = answers;
	}

	public Object[] getAnswers() {
		return answers;
	}

	public ExitQuestionsMap(int exitQuestionId, int exitId, String answer) {
		this.exitQuestionId = exitQuestionId;
		this.exitId = exitId;
		this.answer = answer;
	}
}
