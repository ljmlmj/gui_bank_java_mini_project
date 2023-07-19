package com.javalab.awt.menu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BankList extends JFrame {
	
	public DataBaseClass db;
    private JTable table;	// 계좌 목록이 보여질 테이블
    private DefaultTableModel tableModel;	// 다양한 기능이 사용 가능한 테이블

    public BankList() {    	
    }
    
    public BankList(DataBaseClass db) {
    	this.db = db; // 데이터베이스 클래스의 객체를 전달받음
    	
        // 테이블 세팅
        String[] columnNames = {"계좌 번호", "예금주", "금액"};
        List<Bank> bankList = db.getBankList(); // ArrayList<Bank>의 갯수 만큼 행을 생성
        Object[][] data = new Object[bankList.size()][3];
        
        for (int i = 0; i < bankList.size(); i++) {	// 저장된 리스트를 data 2차원 배열에 저장
            Bank bank = bankList.get(i);
            data[i][0] = bank.getAcc();		// i행 1열
            data[i][1] = bank.getName();	// i행 2열
            data[i][2] = bank.getBalance();	// i행 3열
        }

        tableModel = new DefaultTableModel(data, columnNames);	// 데이터를 ix3 배열로 구성되어 있는 테이블에 저장
        table = new JTable(tableModel);							
        
        JButton closeButton = new JButton("이전 메뉴");	  // 닫기 버튼
        closeButton.addActionListener(new ActionListener() {	// 익명 구현 자식 객체 생성 
			@Override											// "이전 메뉴" 버튼 클릭 시 메인 메뉴로 돌아감
			public void actionPerformed(ActionEvent e) {
				dispose();
	            MainMenu mainMenu = new MainMenu(db);
	            mainMenu.setVisible(true);
			}
        });

        JPanel mainPanel = new JPanel();			// 메인 패널 생성
        mainPanel.setLayout(new BorderLayout());	// 메인 패널을 BorderLayout으로 생성
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);	// 리스트가 길어질 경우를 대비해 스크롤이 가능한 JScrollPane로 table을 보여줌 
        mainPanel.add(closeButton, BorderLayout.NORTH);	// 메인 패널에 닫기 버튼 부착

        // 프레임(윈도우 창) 설정
        setTitle("계좌 정보 입력");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}