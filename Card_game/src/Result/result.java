package Result;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import Game.Game_system;
import Menu.Menu;

public class result extends JFrame 
{
	//점수 배열, 플레이어 list 배열 
	private int pickCard[][];
	private int score[];
	private int n;
	int fontSize=0;
	private String list[];
	
	//게임 프레임을 닫기위한 변수
	private Game_system g;
	
	private Container cp;
	private JButton replay; 
	private JButton exit;
	
	public result(Game_system g, int pickCard[][], String list[], int fontSize)
	{
		setTitle("게임 종료");
		setSize(400,600);
		
		this.g = g;
		this.fontSize = fontSize;
		
		this.pickCard = pickCard;
		this.list = list;
		n = list.length;
		
		cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		
		sortRank(); // 등수 정렬(버블 정렬)
		setResult(); // 결과창 인터페이스 셋팅, 등수 출력
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	public void setResult()
	{
		cp.add(Box.createVerticalStrut(25));
		
		//게임종료 타이틀 생성 및 부착
		JLabel gameEnd = new JLabel("게임 종료!!");
		gameEnd.setFont(new Font("", Font.BOLD, 25+fontSize));
		gameEnd.setAlignmentX(CENTER_ALIGNMENT);
		cp.add(gameEnd);
		cp.add(Box.createVerticalStrut(30));
		
		
		//등수 출력
		for(int i = 0; i < n; i++)
		{			
			Box box = Box.createHorizontalBox();
			JLabel rank = new JLabel(Integer.toString(i+1)+"등");
			JLabel name = new JLabel(list[i]);
			JLabel grade = new JLabel(Integer.toString(score[i])+"점");
			
			rank.setFont(new Font("", Font.PLAIN, 15+fontSize));
			name.setFont(new Font("", Font.PLAIN, 15+fontSize));
			grade.setFont(new Font("", Font.PLAIN, 15+fontSize));

			name.setHorizontalAlignment(JLabel.CENTER);
			grade.setHorizontalAlignment(JLabel.CENTER);
			
			//player와 computer가 글자 크기가 달라서 깔끔한 배치를 위한 여백 차별화
			if(list[i].charAt(0) == 'p')
			{
				box.add(rank);
				box.add(Box.createHorizontalStrut(51));
				box.add(name);
				box.add(Box.createHorizontalStrut(51));
				box.add(grade);
			}
			else
			{
				box.add(rank);
				box.add(Box.createHorizontalStrut(40));
				box.add(name);
				box.add(Box.createHorizontalStrut(40));
				box.add(grade);
			}
			cp.add(box);
		}

		// 여백 생성
		for(int i = 0; i < 8-n; i++)
		{
			cp.add(Box.createVerticalStrut(50));
		}
		
		//다시하기, 종료하기 버튼 생성 및 부착
		Box box = Box.createHorizontalBox();
		replay = new JButton("다시하기");  replay.addActionListener(new resultBtnAction()); replay.setFont(new Font("",Font.PLAIN, 15+fontSize));
		exit = new JButton("끝내기");     exit.addActionListener(new resultBtnAction()); exit.setFont(new Font("",Font.PLAIN, 15+fontSize));
		
		box.add(replay); 					   
		box.add(exit);
		box.setAlignmentX(CENTER_ALIGNMENT);
		
		cp.add(box);
	}
	public void sortRank()
	{
		//마지막 최종 점수만 저장
		score = new int[n];
		for(int i = 0; i < n; i++)
		{
			score[i] = pickCard[i][4];
		}
		
		//버블 정렬 실시
		for(int i = 0; i < n; i++)
		{
			for(int j = i; j < n; j++)
			{
				if(score[i] < score[j])
				{
					//점수(int)
					int temp = score[i];
					score[i] = score[j];
					score[j] = temp;
					
					//플레이어 번호(String)
					String name = list[i];
					list[i] = list[j];
					list[j] = name;
				}
			}
		}
	}

	//다시하기, 종료하기 버튼 액션 리스너
	class resultBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JButton b = (JButton)e.getSource();
			
			//둘중 어떤 버튼을 누르더라도 일단 결과창, 이전 게임 프레임 닫아주기
			dispose();
			g.gameDispose();
			if(b == replay) // 다시하기
			{
				new Game_system(fontSize); // 새 Game_system 생성
			}
			else // 종료하기
			{
				new Menu(fontSize); // 메뉴로, 새 Menu 생성
			}
		}
	}
}
