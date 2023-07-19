package com.javalab.awt.menu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenu extends JFrame implements ActionListener {
	
	public DataBaseClass db; // 데이터베이스 클래스의 객체를 전달받음
	
	private JLabel titleLabel;		// GUI 구성요소
	private JButton bankButton;
	private JButton bankListButton;
	private JButton withdrawButton;
	private JButton depositButton;
	private JButton exitButton;

	public MainMenu(DataBaseClass db) {
		this.db = db;
		
		setTitle("계좌 관리 프로그램");					// JFrame 설정
		setLayout(new GridLayout(6,1));
		setSize(300, 400);

		titleLabel = new JLabel("원하시는 메뉴를 선택하세요.");    // 타이틀 레이블
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add(titleLabel);

		bankButton = new JButton("1. 계좌 등록");      // 계좌 등록 버튼
		bankButton.addActionListener(this);
		add(bankButton);

		bankListButton = new JButton("2. 계좌 조회");  // 계좌 리스트 버튼
		bankListButton.addActionListener(this);
		add(bankListButton);

		depositButton = new JButton("3. 예금");      // 예금 버튼
		depositButton.addActionListener(this);
		add(depositButton);
		
		withdrawButton = new JButton("4. 출금");     // 출금 버튼
		withdrawButton.addActionListener(this);
		add(withdrawButton);
		
		exitButton = new JButton("5. 종 료");        // 종료 버튼
		exitButton.addActionListener(this);
		add(exitButton);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// Handle button clicks

		if (e.getSource() == bankButton) {	// 계좌 생성 메뉴 버튼 
            System.out.println("계좌 생성");
            this.dispose();							// 현재 화면 닫기  
            BankForm bankForm = new BankForm(db); // Bankform 화면 객체 생성
            bankForm.setVisible(true);   // Bankform 화면 보이기
            
        } else if (e.getSource() == bankListButton) {	// 조회메뉴 버튼
            System.out.println("계좌 리스트");            
            this.dispose();							// 현재 화면 닫기             
            BankList bankList = new BankList(db); // BankList 화면 객체 생성         
            bankList.setVisible(true);   // BankList 화면 보이기 
            
        } else if (e.getSource() == withdrawButton) {	// 출금 메뉴 버튼
			System.out.println("출금");
			this.dispose();										// 현재 화면 닫기
			BankWithdraw bankWithdraw = new BankWithdraw(db); // Withdraw 화면 객체 생성
			bankWithdraw.setVisible(true);  // Withdraw 화면 보이기
			
		} else if (e.getSource() == depositButton) {	// 예금 메뉴 버튼
			System.out.println("예금");
			this.dispose();							// 현재 화면 닫기
			BankDeposit bankDeposit = new BankDeposit(db); // Deposit 화면 객체 생성
			bankDeposit.setVisible(true);	// Deposit 화면 보이기
			
		} else if (e.getSource() == exitButton) {	// 종료 버튼
			System.exit(0);							// 프로그램 종료
		}
	}

	public static void main(String[] args) {
		new MainMenu(new DataBaseClass());
	}
}