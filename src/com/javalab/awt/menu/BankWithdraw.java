package com.javalab.awt.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankWithdraw extends JFrame implements ActionListener {

	public DataBaseClass db;

	private JTextField accField;		// 계좌번호 텍스트 필드
	private JTextField nameField;		// 예금주 텍스트 필드
	private JTextField withdrawField;	// 출금액 텍스트 필드

	public BankWithdraw() {
	}

	public BankWithdraw(DataBaseClass db) {
		this.db = db;  // 데이터베이스 클래스의 객체를 전달받아서 변수에 저장

		accField = new JTextField(10);			// 텍스트필드(입력칸)와 레이블 설정
		withdrawField = new JTextField(10);

		JLabel accLabel = new JLabel("계좌번호:");
		JLabel balanceLabel = new JLabel("출금 금액:");

		JButton withdrawButton = new JButton("출금하기"); // 계좌 생성 버튼 생성
		withdrawButton.addActionListener(this);  // 버튼 클릭시 이벤트 발생

		JButton closeButton = new JButton("이전 메뉴"); // 닫기 버튼 생성
		closeButton.addActionListener(new ActionListener() { // 닫기 버튼 클릭시 익명 구현 객체 
			@Override
			public void actionPerformed(ActionEvent arg) {
				dispose();
				MainMenu mainMenu = new MainMenu(db);  // 이동해갈 메인 메뉴 생성
				mainMenu.setVisible(true);  // 메인 메뉴 보이기
			}
		});

		// 버튼, 레이블, 텍스트 필드를 배치할 input 패널을 배치할 메인 패널 생성
		// 즉, 메인 패널 위에 inputPanel이 놓이게 됨.
		JPanel mainPanel = new JPanel();

		// 메인 패널을 BoarderLayout으로 만듦.
		mainPanel.setLayout(new BorderLayout());

		// 버튼, 레이블, 텍스트 필드를 배치할 input 패널
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(3, 2));
		inputPanel.add(accLabel);
		inputPanel.add(accField);
		inputPanel.add(balanceLabel);
		inputPanel.add(withdrawField);

		// 메인 패널에 input패널 부착
		mainPanel.add(inputPanel, BorderLayout.CENTER);

		// 메인 패널에 "추가"버튼 부착
		mainPanel.add(withdrawButton, BorderLayout.SOUTH);
		mainPanel.add(closeButton, BorderLayout.NORTH);

		// 프레임(윈도우 창) 설정
		setTitle("계좌 정보 입력");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 메인 패널을 프레임의 루트 컨텐트에 저장
		setContentPane(mainPanel);
		setSize(250,200);
		setLocationRelativeTo(null);
		setVisible(true);

		/**
		 * 컴포넌트(버튼, 텍스트필드, 레이블)가 프레임에 쌓이는 순서 - 프레임 > 루트 컨텐트 > 메인 패널 > input패널 >
		 * 버튼,텍스트필드,레이블
		 */
	}

	/**
	 * 액션 리스너 - 계좌 생성 버튼 클릭시 실행되는 메소드로 데이터베이스 클래스에 계좌를 추가시킨다. 즉, 계좌 ArrayList에 계좌 하나
	 * 추가함.
	 */
	public void actionPerformed(ActionEvent e) {
		// 화면에서 입력된 값을 임시 변수에 저장
		String acc = accField.getText();	// 입력받은 계좌번호 값 저장
		int amount = 0;						// 출금액을 저장할 객체 초기화

		try {	// 입력한 출금액이 정수가 아니면 경고창 생성
			amount = Integer.parseInt(withdrawField.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "출금액은 정수로 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
			accField.setText("");
			withdrawField.setText("");
			return;
		}

		Bank bank = db.findBank(acc);				// 입력한 계좌번호를 갖는 리스트 추출하여 저장

		if (bank == null) {	// 입력한 계좌번호를 갖고 있는 리스트가 없다면 경고창 생성
			JOptionPane.showMessageDialog(this, "입력하신 계좌번호는 존재하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			accField.setText("");
			withdrawField.setText("");
			return;
		} else if (bank.getBalance() < amount) {	// 소지한 금액이 입력한 출금액보다 적으면 경고창 생성
			JOptionPane.showMessageDialog(this, "잔액이 부족합니다.", "Error", JOptionPane.ERROR_MESSAGE);
			accField.setText("");
			withdrawField.setText("");
			return;
		} else if (amount < 0) {					// 입력한 출금액이 0보다 적으면 경고창 생성
			JOptionPane.showMessageDialog(this, "잘못된 정보를 입력했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			accField.setText("");
			withdrawField.setText("");
			return;
		} else {									// 정상적인 값 입력 시 해당 계좌에서 출금 후 안내창 생성
			bank.withdraw(amount);
			JOptionPane.showMessageDialog(this, "출금 완료", "Success", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		
		this.dispose();	// 계좌 생성 끝나고 현재의 화면 닫기
		MainMenu mainMenu = new MainMenu(db);	// 이동해갈 메인 메뉴 생성
		mainMenu.setVisible(true);  // 메인 메뉴 보이기
	}
}
