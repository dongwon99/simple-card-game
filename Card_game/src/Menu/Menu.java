package Menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import Game.*;




public class Menu extends JFrame 
{
	private JButton menuButton[];
	private String menuName[] = {"   시작   ", "   규칙   ","   옵션   ", "   종료   "};
	
	private Container cp;
	private JPanel jp = new JPanel(); // 메인 메뉴 패널
	// 기타 메뉴(옵션, 규칙) 패널
	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	
	private int fontSize = 0; 

	public Menu(int fontSize)
	{
		setTitle("카드 게임");
		setSize(800,600);
		
		this.fontSize = fontSize;
		
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		setMenu();	//기본 메뉴 인터페이스
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);  //창 중간에 띄우기
		setResizable(false);  //창 크기 변경 불가 설정
	}	 
	public void setMenu()
	{
		 // 요소들을 세로 정렬하기위한 박스 레이아웃
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		Box box = Box.createVerticalBox();
		
		//게임 타이틀 생성
		JLabel lb = new JLabel("Indian Poker");
		lb.setAlignmentX(CENTER_ALIGNMENT);
		lb.setFont(new Font("HY견고딕",Font.PLAIN, 40));
		
		//메뉴 버튼: 순서대로 게임시작, 규칙, 옵션, 종료하기 버튼 생성
		Font btnFont = new Font("HY고딕 중간", Font.PLAIN, 25);  
		menuButton = new JButton[4];
		for(int i = 0; i < 4; i++)
		{
			menuButton[i] = new JButton(menuName[i]);
			menuButton[i].setFont(btnFont);
			menuButton[i].setAlignmentX(CENTER_ALIGNMENT);
		
		}
		//각 버튼에 ActionListener 부착
		menuButton[0].addActionListener(new startBtnAction());
		menuButton[1].addActionListener(new ruleBtnAction());
		menuButton[2].addActionListener(new optionBtnAction());
		menuButton[3].addActionListener(new exitBtnAction());
		
		
		
		
		//여백 생성 후 게임 타이틀, 버튼 등 부착하기
		box.add(Box.createVerticalStrut(90));
		box.add(lb);
		box.add(Box.createVerticalStrut(100));
		for(int i = 0; i < 4; i++)
		{
			box.add(menuButton[i]);
			box.add(Box.createVerticalStrut(10));
		}
		
		jp.add(box);
		jp.setVisible(true);
		cp.add(jp);	
	}
	public void setrule()
	{		
		//규칙 타이틀 / 규칙 본문 / 메뉴로 돌아가기 버튼을 부착하기 위한 JPanel
		northPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new FlowLayout());	
		
			
		//규칙 타이틀, 규칙 본문 JLabel 생성
		JLabel ruleTitle = new JLabel("게임 규칙");
		JLabel rule1_1 = new JLabel("1. 인원수를 입력합니다. 컴퓨터, 플레이어 합쳐서");
		JLabel rule1_2 = new JLabel("    최대 8명 까지 설정 가능합니다.");
		JLabel rule2_1 = new JLabel("2-1. 게임이 시작되고 자기 차례가 오면,");
		JLabel rule2_2 = new JLabel("       카드를 뒤집혀진 카드를 한장 뽑고, 턴을 넘깁니다.");
		JLabel rule3_1 = new JLabel("2-2. 턴당 제한시간은 20초로, 20초가 지날때까지 카드를 뽑지 않으면");
		JLabel rule3_2 = new JLabel("       무작위로 카드를 한장 선택하게 됩니다.");
		JLabel rule4 = new JLabel("3. 인당 4장의 카드를 뽑게되면, 게임이 종료되고 결과창이 출력됩니다.");	
		//폰트 설정
		Font ruleFont = new Font("", Font.PLAIN, 17+fontSize);	
		ruleTitle.setFont(new Font("HY견고딕", Font.BOLD, 30+fontSize));
		rule1_1.setFont(ruleFont);
		rule1_2.setFont(ruleFont);
		rule2_1.setFont(ruleFont);
		rule2_2.setFont(ruleFont);
		rule3_1.setFont(ruleFont);
		rule3_2.setFont(ruleFont);
		rule4.setFont(ruleFont);
		
		//메뉴로 돌아가기 버튼 생성
		JButton toMenu = new JButton("메뉴");
		toMenu.setFont(new Font("", Font.BOLD, 20+fontSize));
		toMenu.addActionListener(new menuBtnAction());
		
		//규칙 타이틀 JLabel 부착
		northPanel.add(ruleTitle);
		//규칙 본문 JLabel 부착
		centerPanel.add(Box.createVerticalStrut(90));
		centerPanel.add(rule1_1);
		centerPanel.add(rule1_2);
		centerPanel.add(Box.createVerticalStrut(25));
		centerPanel.add(rule2_1);
		centerPanel.add(rule2_2);
		centerPanel.add(Box.createVerticalStrut(25));
		centerPanel.add(rule3_1);
		centerPanel.add(rule3_2);
		centerPanel.add(Box.createVerticalStrut(25));
		centerPanel.add(rule4);
		//메뉴로 돌아가기 버튼 부착
		southPanel.add(toMenu);
				
		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
	}
	public void setOption()
	{		
		//옵션 타이틀 / 옵션 본문 / 메뉴로 돌아가기 버튼 부착을 위한 JPanel
		northPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new FlowLayout());	
			
		//옵션 타이틀 생성
		JLabel optionTitle = new JLabel("   옵션   ");
		optionTitle.setFont(new Font("HY견고딕", Font.BOLD, 30+fontSize));
		optionTitle.setAlignmentX(CENTER_ALIGNMENT);
	
		
		// 옵션 본문 생성 (옵션 항목표시를 위한 JLabel, 옵션 선택용 라디오버튼, 라디오버튼 그룹, 앞의 항목들을 담기위한 박스)
		Box b1 = Box.createHorizontalBox();
		JLabel inputTextSize = new JLabel("글자 크기");
		inputTextSize.setFont(new Font("", Font.PLAIN, 20+fontSize));
		b1.add(inputTextSize);
		
		JRadioButton radioText[] = new JRadioButton[3];
		ButtonGroup textGroup = new ButtonGroup();
		for(int i = 0; i < 3; i++)
		{
			radioText[i] = new JRadioButton(Integer.toString(i+1));
			radioText[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// 라디오버튼 액션리스너, 해당 버튼을 누를때 fontSize 변수에 값이 들어감.
					JRadioButton b = (JRadioButton) e.getSource();
					if(b == radioText[0])
					{
						fontSize = 0;
					}
					else if (b == radioText[1])
					{
						fontSize = 3;
					}
					else
					{
						fontSize = 8;
					}
				}	
			});
			textGroup.add(radioText[i]);
			b1.add(radioText[i]);
		}
		
		// 메뉴로 돌아가기 버튼 생성
		JButton toMenu = new JButton("메뉴");
		toMenu.setFont(new Font("", Font.BOLD, 20+fontSize));
		toMenu.addActionListener(new menuBtnAction());
		
		// 앞의 컴포넌트들 JPanel에 부착
		northPanel.add(optionTitle);
		centerPanel.add(Box.createVerticalStrut(90));
		centerPanel.add(b1);
		southPanel.add(toMenu);
		
		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
	}
	
	// 메뉴 버튼들을 위한 액션 리스너
	class startBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//게임시작 버튼 액션리스너
			//메뉴 프레임을 닫은 후 Game_system 클래스 실행
			dispose();
			new Game_system(fontSize);
		}
	}
	class ruleBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//규칙 버튼을 위한 액션리스너
			//기본 JPanel (메뉴) 를 지우고, 규칙window를 위한 JPanel 활성화
			jp.setVisible(false);
			jp.removeAll();
			
			northPanel.setVisible(true);
			centerPanel.setVisible(true);
			southPanel.setVisible(true);
			setrule();
		}
	}
	class optionBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//옵션 버튼을 위한 액션리스너
			//기본 JPanel (메뉴) 를 지우고, 옵션window를 위한 JPanel 활성화
			jp.setVisible(false);
			jp.removeAll();
			
			northPanel.setVisible(true);
			centerPanel.setVisible(true);
			southPanel.setVisible(true);
			setOption();
		}
	}
	class menuBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			jp.setVisible(false);
			northPanel.setVisible(false);
			centerPanel.setVisible(false);
			southPanel.setVisible(false);
			
			jp.removeAll();
			northPanel.removeAll();
			centerPanel.removeAll();
			southPanel.removeAll();
			
			setMenu();
		}
	}
	class exitBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//프로그램 종료
			System.exit(0);
		}
	}
}