package Game;


import java.awt.*;
import java.awt.event.*;
import java.lang.Thread.State;
import javax.swing.*;
import javax.swing.event.*;
import java.math.*;
import Menu.Menu;
import Result.result;


public class Game_system extends JFrame
{
	private int num, pla = 1, com = 1;
	private int gameTurn, turn;
	private int x, y;
	private int fontSize = 0;
	private boolean selectCheck = false;
	private boolean userCheck = false;
	
	private Player player;
	private Card card;

	private Container cp;
	private JPanel S_jp;
	private JLabel lb;
	private ImageIcon sizedBackIcon;
	
	
	public Game_system(int fontSize)
	{	
		this.fontSize = fontSize;
		
		setTitle("카드 게임");
		setSize(1200,900);
		
		cp = getContentPane();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLocationRelativeTo(null);
		setResizable(false);
		
		new inputNum();  // 인원 수 입력을 위한 새 Frame 생성
	}
	public void setGame()
	{
		Color Khaki = new Color(000,131,000); // 배경색 설정
		
		//전체 cp는 BorderLayout으로
		cp.setLayout(new BorderLayout());
		cp.setBackground(Khaki);
		
		//BorderLayout.WEST에 들어갈 패널, 플레이어 아이콘 등록
		JPanel L_jp = new JPanel();
		L_jp.setBackground(Khaki);
		L_jp.setLayout(new BoxLayout(L_jp, BoxLayout.Y_AXIS));
		
		//BorderLayout.EAST에 들어갈 패널, 플레이어 아이콘 등록
		JPanel R_jp = new JPanel();
		R_jp.setBackground(Khaki);
		R_jp.setLayout(new BoxLayout(R_jp, BoxLayout.Y_AXIS));
		
		//BorderLayout.CENTER에 들어갈 패널, 카드 나열
		JPanel C_jp = new JPanel();
		C_jp.setBackground(Khaki);
		C_jp.setLayout(new FlowLayout());
		
		//BorderLayout.SOUTH에 들어갈 패널, 게임 시작 버튼 삽입
		S_jp = new JPanel();
		S_jp.setBackground(Khaki);
		S_jp.setLayout(new FlowLayout());

		
		card = new Card(num, C_jp); // 카드 객체 생성, 생성함과 동시에 카드 배열을 C_jp에 부착
		
		setSize(C_jp); // 카드 객체의 크기 설정
		
		// 플레이어 셋팅
		player = new Player(L_jp, R_jp, pla, com);
		player.player_setting();
				
		// 게임시작 JLabel
		lb = new JLabel("게임 시작!");
		lb.setFont(new Font("",Font.BOLD, 25+fontSize));
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		
		// 게임시작 버튼, 클릭시 타이머가 설정되고 1번 플레이어부터 턴 진행 
		JButton START = new JButton("시작하기");
		START.addActionListener(new startBtn()); // 게임시작 버튼을 없애고 playGame() 함수 실행, 카드 활성화		
		S_jp.add(START);
		
		//각종 패널, JLabel 부착하기
		cp.add(lb, BorderLayout.NORTH);	
		cp.add(L_jp, BorderLayout.WEST);
		cp.add(R_jp, BorderLayout.EAST);
		cp.add(C_jp, BorderLayout.CENTER);
		cp.add(S_jp, BorderLayout.SOUTH);
		
		setVisible(true);
		//게임시작 버튼을 누르기 전까지 카드 비활성화
		card.cardEnabled(false);
	}
	// 카드 사이즈 설정
	public void setSize(JPanel C_jp)
	{
		switch (num)
		{
		case 2:
			x = 140; y = 163;
			break;
		case 3:
			x = 110; y = 148;
			break;
		case 4:
			x = 110; y = 120;
			break;
		case 5:
			x = 80; y = 100;
			break;
		case 6:
			x = 80; y = 80;
			break;
		case 7:
			x = 70; y = 80;
			break;
		case 8:
			x = 70; y = 70;
			break;
		}
		sizedBackIcon = setCardSize(card.c, x,y);
		for(int i = 0; i < card.cardCount; i++)
		{
			card.card[i].setIcon(sizedBackIcon);
		}
	}
	public ImageIcon setCardSize(ImageIcon original, int x, int y)
	{
		Image ximg = original.getImage();
		Image yimg = ximg.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg);
		
		return xyimg;
	}
	// 게임 시작
	public void playGame()
	{
		//Game 스레드 생성 및 실행
		Game game = new Game();
		game.start();
	}
	public void endGame()
	{
		//게임 종료 표시 및 카드 비활성화, 결과창 Frame 생성
		lb.setText("게임 종료!");
		card.cardEnabled(false);
		//게임을 완전히 종료할때, 해당 게임 프레임을 닫기위한 변수 전달, 점수, 플레이어 리스트, 글자크기 전달
		new result(this, player.pickCard, player.list, fontSize);
	}
	// 최종적으로 게임 프레임 닫기
	public void gameDispose()
	{
		dispose();
	}
	
	//카드와 플레이어 클래스, 데이터 전달이 생각보다 많아서 이너클래스로 개발
	class Game extends Thread
	{
		public void run() 
		{
			for (gameTurn = 0; gameTurn < 4; gameTurn++) // 총 4턴 (gameTurn)
			{
				for (turn = 0; turn < num; turn++)     // 한 턴당 플레이어 수만큼 (turn)
				{
					if(player.list[turn].charAt(0) == 'p')     // 사용자의 차례일때
					{
						userCheck = true; // 사용자가 카드를 선택할 수 있도록 변수 설정/ false : 사용자가 카드를 선택해도 어떠한 반응x
						for(double i = 10.0; i > 0.0; i-= 0.1) // 타이머를 위한 for 문
						{
							if(selectCheck==true) // 0.1초마다 사용자가 카드 선택 여부를 검사
							{
								break;
							}
							try 
							{
								lb.setText(player.list[turn] + "  " + String.format("%.1f", i)); // 화면에 타이머 출력
								Thread.sleep(100);
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}
						}
						//사용자가 카드를 안뽑고 10초가 넘었다면 카드 랜덤 선택 기능 실행
						if(selectCheck == false)
						{
							card.randomCard();
						}
						//턴 종료 변수 설정
						userCheck = false; 
						selectCheck = false;
					}
					else  // 컴퓨터의 차례일때
					{
						for(double i = 10.0; i > 9.0; i-= 0.1) // 1초의 딜레이 설정
						{
							try 
							{
								lb.setText(String.format(player.list[turn] + "  " + "%.1f", i)); //사용자 편의를 위한 타이머 표시
								Thread.sleep(100);
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}
						}
						card.randomCard(); // 컴퓨터 카드 랜덤 선택
					}
				}
			}
			//모든 턴이 완료가 되면, endGame 함수 실행 및 return호출로 스레드 종료
			endGame();
			return;
		}
	}
	class Card
	{
		private int card_num[];    
		private int num;        
		private int cardCount;    
		private String card_front[];
		private JButton card[];    
		private JPanel jp;        
		private ImageIcon c;
		
		
		public Card(int num, JPanel jp)
		{
			this.num = num;
			this.jp = jp;
			cardCount = num*8+1; // 인당 8장 + 조커 1장
			
			card_num = new int[cardCount]; // 카드의 숫자를 저장하기 위한 int 배열
			card_front = new String[num*4+1]; //카드의 앞면(숫자) 사진 주소 정보 저장 배열, ImageIcon 객체의 인자임.
			card = new JButton[cardCount]; // 카드 버튼 객체배열
			
			c = new ImageIcon("C:\\zzal\\card.png"); // 카드 뒷면 ImageIcon
			
			//조커 카드 생성, 조커의 숫자는 0, 조커를 뽑을 시 현재까지 뽑은 카드점수 *2
			card[0] = new JButton();
			card[0].setIcon(c);
			card[0].addActionListener(new cardActtionListener());
			card[0].setBorderPainted(false); card[0].setContentAreaFilled(false);
			jp.add(card[0]);
			card_front[0] = "C:\\zzal\\card\\joker.jpg";
			card_num[0] = 0;
			
			//숫자 카드 생성/ 카드 뒷면ImageIcon 부착, 각 카드마다 1부터 순서대로 번호 지정
			int a = 0;			
			for(int i = 1; i < cardCount; i++)
			{
				if (i % 2 == 1) // 카드 앞면 사진주소정보 저장
				{
					a++;
					card_front[a] = new String();
					card_front[a] = "C:\\zzal\\card\\" + Integer.toString(a)+".png";
				}
				
				card[i] = new JButton();
				card[i].setIcon(c);
				card[i].addActionListener(new cardActtionListener());
				card[i].setBorderPainted(false); card[i].setContentAreaFilled(false);
				jp.add(card[i]);
				
				card_num[i] = a;	
			}
			//카드 섞기(카드 객체는 놔두고 그 객체마다 부여된 숫자만 섞음)
			Shuffle();
		}
		public void Shuffle()
		{
			for(int i = num*8; i >= 0; i--)
			{
				int n = (int)Math.floor(Math.random()*(i+1));
				
				int temp = card_num[i];
				card_num[i] = card_num[n];
				card_num[n] = temp;
			}
		}	
		public void randomCard() // 랜덤카드뽑기
		{
			//난수발생
			int n = (int)Math.floor(Math.random()*(num*8+1));
			//이미 뽑은 카드를 뽑지 않도록 검사
			while(card[n].isEnabled() == false)
			{
				n = (int)Math.floor( Math.random()*(num*8+1)); 
			}
			
			//플레이어가 뽑은 카드 점수 적용하기
			player.pickCard[turn][gameTurn] = card_num[n];
			player.setScore(turn, gameTurn);
			
			//카드를 뽑았으니 뒷면에서 앞면으로 뒤집기
			ImageIcon sizedFrontIcon = setCardSize(new ImageIcon(card_front[card_num[n]]),x,y); 
			card[n].setIcon(sizedFrontIcon);
			
			//카드 비활성화
			card[n].setEnabled(false);
		}
		class cardActtionListener implements ActionListener // 사용자가 카드를 뽑았을때의 함수, 위의 랜덤카드뽑기와 절차가 비슷함
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(userCheck == true)
				{
					JButton b = (JButton)e.getSource();
					int i;
					//어떤 카드인지 검색
					for(i = 0; i < num*8+1; i++)
					{
						if(card[i] == b)
						{
							break;
						}
					}
					player.pickCard[turn][gameTurn] = card_num[i];
					player.setScore(turn, gameTurn);
				
				
					ImageIcon sizedFrontIcon = setCardSize(new ImageIcon(card_front[card_num[i]]),x,y); 
					
					b.setIcon(sizedFrontIcon);
					b.setEnabled(false);
				
				
					selectCheck = true;
				}
			}
		}
		public void cardEnabled(boolean b) // 전체 카드 활성화 및 비활성화
		{
			if(b == true)
			{
				for(int i = 0; i < num*8+1; i++)
				{
					card[i].setEnabled(true);
				}
			}
			else
			{
				for(int i = 0; i < num*8+1; i++)
				{
					card[i].setEnabled(false);
				}
			}
		}

	}
	class Player 
	{
		private int num, com, pla;
		private int Lindex = 0;
		private int pickCard[][]; //사용자가 뽑은 카드 점수 저장 배열
		private String list[];  // 사용자 리스트(플레이어, 컴퓨터)
		
		private JPanel L_jp, R_jp;
		private JLabel listLabel[];
		private Font f1 = new Font("", Font.BOLD, 15+fontSize); // 플레이어 라벨 폰트
		
		
		public Player(JPanel L_jp, JPanel R_jp, int pla, int com)
		{
			this.pla = pla; this.com = com; num = pla+com;
			this.L_jp = L_jp; this.R_jp = R_jp;
			
			int ctemp = 1, ptemp = 1;
			
			//플레이어 리스트, 카드 점수 배열 생성
			list = new String[num];
			listLabel = new JLabel[num]; 
			pickCard = new int[num][5];
			
			//컴퓨터와 플레이어를 섞어서 인터페이스에 부착하기 위한 list 배열 설정/ 둘중 하나라도 설정이 완료되면 나머지 하나 모두 설정
			int i = 0;
			while((ctemp <= com) && (ptemp <= pla))
			{
				list[i] = "computer" + Integer.toString(ctemp);
				i++; ctemp++;
				list[i] = "player" + Integer.toString(ptemp);
				i++; ptemp++;
			}
			while(ctemp <= com)
			{
				list[i] = "computer" + Integer.toString(ctemp); 
				i++; ctemp++;
			}
			while(ptemp <= pla)
			{
				list[i] = "player" + Integer.toString(ptemp); 
				i++; ptemp++;
			}
			
			//플레이어 이름(번호)과 점수 표시 JLabel <- 위에서 설정한 list로 JLabel text 설정
			for(int j = 0; j < num; j++)
			{
				pickCard[j][4] = 0;
				listLabel[j] = new JLabel(list[j] + "  0");
				listLabel[j].setFont(f1);
				listLabel[j].setForeground(Color.white);
			}
		}	
		public void set_charactor(JPanel jp)// 플레이어 아이콘 설정 및 패널에 부착
		{
			ImageIcon siba = new ImageIcon("C:\\zzal\\siba.jpg");
			
			Image img = siba.getImage();
			img = img.getScaledInstance(120, 100, Image.SCALE_SMOOTH);
			
			siba.setImage(img);
			
			JLabel charactor = new JLabel(siba);
	        jp.add(charactor);
	        jp.add(listLabel[Lindex]);
	        Lindex++;
		}	
		public void player_setting() //
		{		
			for(int i = 0; i < com+pla && i < 4; i++)
			{
				set_charactor(L_jp);
			}
			for(int i = 0; i < com+pla-4; i++)
			{
				set_charactor(R_jp);
			}
		}
		public void setScore(int turn, int gameTurn) // 카드를 뽑았을 때의 점수 적용
		{
			pickCard[turn][4] = 0;
			for(int i = 0; i < gameTurn; i++) // 뽑은 카드를 제외하고 앞에 카드 검사
			{
				if(pickCard[turn][gameTurn] == pickCard[turn][i]) //만약 뽑은 카드와 숫자가 겹치는 카드를 가지고 있다면
				{
					pickCard[turn][gameTurn] *= 2;     //그 숫자 카드는 *2
				}
				pickCard[turn][4] += pickCard[turn][i];  // 총 점수 합산
			}
			if(pickCard[turn][gameTurn] == 0) // 만약 뽑은 카드가 조커라면
			{
				pickCard[turn][gameTurn] = pickCard[turn][4]; // 이때까지 뽑은 카드 *2
			}
			
			pickCard[turn][4] += pickCard[turn][gameTurn];  // 위에서 점수를 합산할때 뽑은 카드는 제외했으므로 합산, 조커는 0이므로 ok
			
			// 플레이어 편의를 위한 점수 표시
			String s = Integer.toString(pickCard[turn][4]);
			String sa = list[turn] + " " + s;
			listLabel[turn].setText(sa);
		}
	}

	class inputNum extends JFrame
	{
		//Player(사용자) 수 입력에 대한 JLabel, 버튼
		private JLabel inputPlaLabel;
		private JButton minusPlaNum;
		private JLabel showPlaNum;
		private JButton plusPlaNum;
		
		//Computer(컴퓨터) 수 입력에 대한 JLabel, 버튼
		private JLabel inputComLabel;
		private JButton minusComNum;
		private JLabel showComNum;
		private JButton plusComNum;
		
		private Container cp;
		
		public inputNum()
		{
			setTitle("카드 게임");
			setSize(400,280);

			setInput(); // 인풋 인터페이스 설정
		
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setResizable(false);
		}
		public void setInput()
		{
			cp = getContentPane();
			cp.setLayout(null);

			//인풋 타이틀 생성 및 부착
			JLabel inputTitleLabel = new JLabel("인원 수 입력");
			inputTitleLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 25));
			inputTitleLabel.setBounds(110, 20, 200, 30);
			cp.add(inputTitleLabel);
			
			
			setPlaInput(); //플레이어 인풋 라벨, 버튼 설정 및 부착
			setComInput(); //컴퓨터 인풋 라벨, 버튼 설정 및 부착
				
			//입력취소(게임취소)를 위한 Cancel 버튼, 입력확인(게임시작)을 위한 OK 버튼 
			JButton Cancel = new JButton("Cancel");		Cancel.setBounds(80,195,100,30);  Cancel.addActionListener(new CancelAction());
			JButton OK = new JButton("OK");		        OK.setBounds(200,195,100,30);	OK.addActionListener(new OKAction());
			cp.add(Cancel);
			cp.add(OK);
		}
		public void setPlaInput()
		{
			inputPlaLabel = new JLabel("플레이어 수 :");  inputPlaLabel.setFont(new Font("",Font.BOLD,15));
			minusPlaNum = new JButton("<");
			showPlaNum = new JLabel(Integer.toString(pla) , JLabel.CENTER);  
			plusPlaNum = new JButton(">");
			
			inputPlaLabel.setBounds(10,100,100,20);
			minusPlaNum.setBounds(130,90,45,35);
			showPlaNum.setBounds(200,92,45,35);
			plusPlaNum.setBounds(280,90,45,35);
			
			minusPlaNum.addActionListener(new add_subBtn());
			plusPlaNum.addActionListener(new add_subBtn());
			
			cp.add(inputPlaLabel);
			cp.add(minusPlaNum);
			cp.add(showPlaNum);
			cp.add(plusPlaNum);
		}
		public void setComInput()
		{
			inputComLabel = new JLabel("  컴퓨터 수 :");  inputComLabel.setFont(new Font("",Font.BOLD,15));
			minusComNum = new JButton("<");
			showComNum = new JLabel(Integer.toString(com), JLabel.CENTER);  
			plusComNum = new JButton(">");
			
			inputComLabel.setBounds(16,140,100,20);
			minusComNum.setBounds(130,130,45,35);
			showComNum.setBounds(200,132,45,35);
			plusComNum.setBounds(280,130,45,35);
			
			minusComNum.addActionListener(new add_subBtn());
			plusComNum.addActionListener(new add_subBtn());
			
			cp.add(inputComLabel);
			cp.add(minusComNum);
			cp.add(showComNum);
			cp.add(plusComNum);
		}	
		
		class OKAction implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//OK버튼 클릭시 각 pla, com 변수에 현재 JLabel에 숫자를 입력
				pla = Integer.parseInt(showPlaNum.getText());
				com = Integer.parseInt(showComNum.getText());
				num = pla+com;
				
				// 인원수 입력 오류처리
				if(num <= 0)
				{
					JOptionPane.showMessageDialog(null, "인원수가 너무 적습니다. 다시 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					dispose();
					setGame(); //Game_system 프레임에 게임 인터페이스 설정(플레이어, 카드 등)
				}
			}
		}
		class add_subBtn implements ActionListener
		{
			// +,- 버튼 액션 리스너
			public void actionPerformed(ActionEvent e) 
			{
				JButton b = (JButton)e.getSource();
				if(b == minusPlaNum)
				{
					pla--;
					showPlaNum.setText(Integer.toString(pla));
				}
				else if(b == plusPlaNum)
				{
					pla++;
					showPlaNum.setText(Integer.toString(pla));
				}
				else if(b == minusComNum)
				{
					com--;
					showComNum.setText(Integer.toString(com));
				}
				else if(b == plusComNum)
				{
					com++;
					showComNum.setText(Integer.toString(com));
				}			
			}
		}
		class CancelAction implements ActionListener
		{
			//Cancel 버튼 클릭시, 입력창 닫고 메뉴창 활성화
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				gameDispose();
				new Menu(fontSize);
			}
		}
	}
	class startBtn implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			cp.remove(S_jp);
			card.cardEnabled(true);
			playGame();
		}
	}
}