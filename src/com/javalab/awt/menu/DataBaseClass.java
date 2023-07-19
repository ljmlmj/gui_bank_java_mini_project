package com.javalab.awt.menu;

import java.util.ArrayList;
import java.util.List;

public class DataBaseClass {
	public List<Bank> bankList; 	// 계좌 리스트 객체 생성

	public DataBaseClass() {
		bankList = new ArrayList<Bank>(); // 계좌 ArrayList 객체 저장
	}

	public List<Bank> getBankList() {	// 계좌 리스트 받는 메소드
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {	// 계좌 리스트 수정하는 메소드
		this.bankList = bankList;
	}

	public void addBank(Bank bank) {	// 계좌 추가 메소드
		this.bankList.add(bank);
	}

	public int getBankCount() {	// 현재 등록된 계좌 수 메소드
		return bankList.size();
	}

	public Bank findBank(String acc) {	// 해당 계좌번호를 갖는 계좌 리스트 저장하는 메소드
		for(Bank bank : bankList) {
			if(bank.getAcc().equals(acc)) {
				return bank;
			}
		}
		return null; // 해당 계좌번호를 가진 계좌가 없을 경우 null 반환
	}
}
