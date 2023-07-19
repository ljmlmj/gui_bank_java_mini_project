package com.javalab.awt.menu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BankDeposit extends JFrame implements ActionListener {

	private DataBaseClass db;
	private JTextField accField;
	private JTextField depositField;

	public BankDeposit(DataBaseClass db) {
		this.db = db;	// 데이터베이스 클래스의 객체를 전달받아서 변수에 저장

		accField = new JTextField(10);		// 계좌번호 텍스트필드 
		depositField = new JTextField(10);	// 예금액 텍스트필드
		
		JLabel accLabel = new JLabel("계좌번호:");
		JLabel balanceLabel = new JLabel("입금 금액:");
		
		JButton depositButton = new JButton("입금하기"); // 입금 버튼 생성
		depositButton.addActionListener(this);	// "입금" 버튼 클릭 시 이벤트 발생

		JButton closeButton = new JButton("이전 메뉴");	// 닫기 버튼 생성
		closeButton.addActionListener(new ActionListener() { // 버튼 클릭시 익명 구현 객체 actionPerformed()가 호출됨.
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				MainMenu mainMenu = new MainMenu(db);	// 이동해갈 메인 메뉴 생성
				mainMenu.setVisible(true);	// 메인 메뉴 보이기
			}
		});

		JPanel mainPanel = new JPanel();			// 메인 패널 생성
		mainPanel.setLayout(new BorderLayout());	// 메인 패널 BorderLayout으로 세팅

		JPanel inputPanel = new JPanel();			// 입력 패널 생성 (메인 패널 위에 덮어짐)
		inputPanel.setLayout(new GridLayout(2, 2));	// 입력 패널 2x2 GridLayout으로 세팅
		inputPanel.add(accLabel);
		inputPanel.add(accField);
		inputPanel.add(balanceLabel);
		inputPanel.add(depositField);

		mainPanel.add(inputPanel, BorderLayout.CENTER);
		mainPanel.add(depositButton, BorderLayout.SOUTH);
		mainPanel.add(closeButton, BorderLayout.NORTH);

		setTitle("예금 메뉴");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(mainPanel);
		setSize(250,200);
		setLocationRelativeTo(null);	// 창을 모니터 중앙에 띄우기
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String acc = accField.getText();	// 입력받은 계좌번호 값 저장
		int amount = 0;						// 예금액을 저장할 객체 초기화

		try {	// 입력한 예금액이 정수가 아니면 경고창 생성
			amount = Integer.parseInt(depositField.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "입금액은 정수로 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Bank bank = db.findBank(acc);	// 입력한 계좌번호를 갖는 리스트 추출하여 저장

		if (bank == null) {			// 입력한 계좌번호를 갖고 있는 리스트가 없다면 경고창 생성
			JOptionPane.showMessageDialog(this, "입력하신 계좌번호는 존재하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			accField.setText("");
			depositField.setText("");
			return;
		} else if (amount < 0) {		// 입력한 예금액이  0보다 적으면 경고창 생성
			JOptionPane.showMessageDialog(this, "잘못된 정보를 입력했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			accField.setText("");
			depositField.setText("");
			return;
		} else {						// 정상적인 값 입력 시 해당 계좌에 입금 후 안내창 생성
			bank.deposit(amount);
			JOptionPane.showMessageDialog(this, amount + "원이 예금되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}

		this.dispose();	// 계좌 생성 끝나고 현재의 화면 닫기
		MainMenu mainMenu = new MainMenu(db);	// 메인 메뉴 생성
		mainMenu.setVisible(true);	// 메인 메뉴로 돌아가기
	}
}
