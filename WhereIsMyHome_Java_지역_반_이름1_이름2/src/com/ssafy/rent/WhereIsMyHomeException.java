package com.ssafy.rent;

public class WhereIsMyHomeException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public WhereIsMyHomeException() {
		super("데이타를 처리 중 오류 발생");
	}
	public WhereIsMyHomeException(String msg) {
		super(msg);
	}
	
}
