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
		
		setTitle("ī�� ����");
		setSize(1200,900);
		
		cp = getContentPane();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLocationRelativeTo(null);
		setResizable(false);
		
		new inputNum();  // �ο� �� �Է��� ���� �� Frame ����
	}
	public void setGame()
	{
		Color Khaki = new Color(000,131,000); // ���� ����
		
		//��ü cp�� BorderLayout����
		cp.setLayout(new BorderLayout());
		cp.setBackground(Khaki);
		
		//BorderLayout.WEST�� �� �г�, �÷��̾� ������ ���
		JPanel L_jp = new JPanel();
		L_jp.setBackground(Khaki);
		L_jp.setLayout(new BoxLayout(L_jp, BoxLayout.Y_AXIS));
		
		//BorderLayout.EAST�� �� �г�, �÷��̾� ������ ���
		JPanel R_jp = new JPanel();
		R_jp.setBackground(Khaki);
		R_jp.setLayout(new BoxLayout(R_jp, BoxLayout.Y_AXIS));
		
		//BorderLayout.CENTER�� �� �г�, ī�� ����
		JPanel C_jp = new JPanel();
		C_jp.setBackground(Khaki);
		C_jp.setLayout(new FlowLayout());
		
		//BorderLayout.SOUTH�� �� �г�, ���� ���� ��ư ����
		S_jp = new JPanel();
		S_jp.setBackground(Khaki);
		S_jp.setLayout(new FlowLayout());

		
		card = new Card(num, C_jp); // ī�� ��ü ����, �����԰� ���ÿ� ī�� �迭�� C_jp�� ����
		
		setSize(C_jp); // ī�� ��ü�� ũ�� ����
		
		// �÷��̾� ����
		player = new Player(L_jp, R_jp, pla, com);
		player.player_setting();
				
		// ���ӽ��� JLabel
		lb = new JLabel("���� ����!");
		lb.setFont(new Font("",Font.BOLD, 25+fontSize));
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		
		// ���ӽ��� ��ư, Ŭ���� Ÿ�̸Ӱ� �����ǰ� 1�� �÷��̾���� �� ���� 
		JButton START = new JButton("�����ϱ�");
		START.addActionListener(new startBtn()); // ���ӽ��� ��ư�� ���ְ� playGame() �Լ� ����, ī�� Ȱ��ȭ		
		S_jp.add(START);
		
		//���� �г�, JLabel �����ϱ�
		cp.add(lb, BorderLayout.NORTH);	
		cp.add(L_jp, BorderLayout.WEST);
		cp.add(R_jp, BorderLayout.EAST);
		cp.add(C_jp, BorderLayout.CENTER);
		cp.add(S_jp, BorderLayout.SOUTH);
		
		setVisible(true);
		//���ӽ��� ��ư�� ������ ������ ī�� ��Ȱ��ȭ
		card.cardEnabled(false);
	}
	// ī�� ������ ����
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
	// ���� ����
	public void playGame()
	{
		//Game ������ ���� �� ����
		Game game = new Game();
		game.start();
	}
	public void endGame()
	{
		//���� ���� ǥ�� �� ī�� ��Ȱ��ȭ, ���â Frame ����
		lb.setText("���� ����!");
		card.cardEnabled(false);
		//������ ������ �����Ҷ�, �ش� ���� �������� �ݱ����� ���� ����, ����, �÷��̾� ����Ʈ, ����ũ�� ����
		new result(this, player.pickCard, player.list, fontSize);
	}
	// ���������� ���� ������ �ݱ�
	public void gameDispose()
	{
		dispose();
	}
	
	//ī��� �÷��̾� Ŭ����, ������ ������ �������� ���Ƽ� �̳�Ŭ������ ����
	class Game extends Thread
	{
		public void run() 
		{
			for (gameTurn = 0; gameTurn < 4; gameTurn++) // �� 4�� (gameTurn)
			{
				for (turn = 0; turn < num; turn++)     // �� �ϴ� �÷��̾� ����ŭ (turn)
				{
					if(player.list[turn].charAt(0) == 'p')     // ������� �����϶�
					{
						userCheck = true; // ����ڰ� ī�带 ������ �� �ֵ��� ���� ����/ false : ����ڰ� ī�带 �����ص� ��� ����x
						for(double i = 10.0; i > 0.0; i-= 0.1) // Ÿ�̸Ӹ� ���� for ��
						{
							if(selectCheck==true) // 0.1�ʸ��� ����ڰ� ī�� ���� ���θ� �˻�
							{
								break;
							}
							try 
							{
								lb.setText(player.list[turn] + "  " + String.format("%.1f", i)); // ȭ�鿡 Ÿ�̸� ���
								Thread.sleep(100);
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}
						}
						//����ڰ� ī�带 �Ȼ̰� 10�ʰ� �Ѿ��ٸ� ī�� ���� ���� ��� ����
						if(selectCheck == false)
						{
							card.randomCard();
						}
						//�� ���� ���� ����
						userCheck = false; 
						selectCheck = false;
					}
					else  // ��ǻ���� �����϶�
					{
						for(double i = 10.0; i > 9.0; i-= 0.1) // 1���� ������ ����
						{
							try 
							{
								lb.setText(String.format(player.list[turn] + "  " + "%.1f", i)); //����� ���Ǹ� ���� Ÿ�̸� ǥ��
								Thread.sleep(100);
							} 
							catch (InterruptedException e) 
							{
								e.printStackTrace();
							}
						}
						card.randomCard(); // ��ǻ�� ī�� ���� ����
					}
				}
			}
			//��� ���� �Ϸᰡ �Ǹ�, endGame �Լ� ���� �� returnȣ��� ������ ����
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
			cardCount = num*8+1; // �δ� 8�� + ��Ŀ 1��
			
			card_num = new int[cardCount]; // ī���� ���ڸ� �����ϱ� ���� int �迭
			card_front = new String[num*4+1]; //ī���� �ո�(����) ���� �ּ� ���� ���� �迭, ImageIcon ��ü�� ������.
			card = new JButton[cardCount]; // ī�� ��ư ��ü�迭
			
			c = new ImageIcon("C:\\zzal\\card.png"); // ī�� �޸� ImageIcon
			
			//��Ŀ ī�� ����, ��Ŀ�� ���ڴ� 0, ��Ŀ�� ���� �� ������� ���� ī������ *2
			card[0] = new JButton();
			card[0].setIcon(c);
			card[0].addActionListener(new cardActtionListener());
			card[0].setBorderPainted(false); card[0].setContentAreaFilled(false);
			jp.add(card[0]);
			card_front[0] = "C:\\zzal\\card\\joker.jpg";
			card_num[0] = 0;
			
			//���� ī�� ����/ ī�� �޸�ImageIcon ����, �� ī�帶�� 1���� ������� ��ȣ ����
			int a = 0;			
			for(int i = 1; i < cardCount; i++)
			{
				if (i % 2 == 1) // ī�� �ո� �����ּ����� ����
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
			//ī�� ����(ī�� ��ü�� ���ΰ� �� ��ü���� �ο��� ���ڸ� ����)
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
		public void randomCard() // ����ī��̱�
		{
			//�����߻�
			int n = (int)Math.floor(Math.random()*(num*8+1));
			//�̹� ���� ī�带 ���� �ʵ��� �˻�
			while(card[n].isEnabled() == false)
			{
				n = (int)Math.floor( Math.random()*(num*8+1)); 
			}
			
			//�÷��̾ ���� ī�� ���� �����ϱ�
			player.pickCard[turn][gameTurn] = card_num[n];
			player.setScore(turn, gameTurn);
			
			//ī�带 �̾����� �޸鿡�� �ո����� ������
			ImageIcon sizedFrontIcon = setCardSize(new ImageIcon(card_front[card_num[n]]),x,y); 
			card[n].setIcon(sizedFrontIcon);
			
			//ī�� ��Ȱ��ȭ
			card[n].setEnabled(false);
		}
		class cardActtionListener implements ActionListener // ����ڰ� ī�带 �̾������� �Լ�, ���� ����ī��̱�� ������ �����
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(userCheck == true)
				{
					JButton b = (JButton)e.getSource();
					int i;
					//� ī������ �˻�
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
		public void cardEnabled(boolean b) // ��ü ī�� Ȱ��ȭ �� ��Ȱ��ȭ
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
		private int pickCard[][]; //����ڰ� ���� ī�� ���� ���� �迭
		private String list[];  // ����� ����Ʈ(�÷��̾�, ��ǻ��)
		
		private JPanel L_jp, R_jp;
		private JLabel listLabel[];
		private Font f1 = new Font("", Font.BOLD, 15+fontSize); // �÷��̾� �� ��Ʈ
		
		
		public Player(JPanel L_jp, JPanel R_jp, int pla, int com)
		{
			this.pla = pla; this.com = com; num = pla+com;
			this.L_jp = L_jp; this.R_jp = R_jp;
			
			int ctemp = 1, ptemp = 1;
			
			//�÷��̾� ����Ʈ, ī�� ���� �迭 ����
			list = new String[num];
			listLabel = new JLabel[num]; 
			pickCard = new int[num][5];
			
			//��ǻ�Ϳ� �÷��̾ ��� �������̽��� �����ϱ� ���� list �迭 ����/ ���� �ϳ��� ������ �Ϸ�Ǹ� ������ �ϳ� ��� ����
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
			
			//�÷��̾� �̸�(��ȣ)�� ���� ǥ�� JLabel <- ������ ������ list�� JLabel text ����
			for(int j = 0; j < num; j++)
			{
				pickCard[j][4] = 0;
				listLabel[j] = new JLabel(list[j] + "  0");
				listLabel[j].setFont(f1);
				listLabel[j].setForeground(Color.white);
			}
		}	
		public void set_charactor(JPanel jp)// �÷��̾� ������ ���� �� �гο� ����
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
		public void setScore(int turn, int gameTurn) // ī�带 �̾��� ���� ���� ����
		{
			pickCard[turn][4] = 0;
			for(int i = 0; i < gameTurn; i++) // ���� ī�带 �����ϰ� �տ� ī�� �˻�
			{
				if(pickCard[turn][gameTurn] == pickCard[turn][i]) //���� ���� ī��� ���ڰ� ��ġ�� ī�带 ������ �ִٸ�
				{
					pickCard[turn][gameTurn] *= 2;     //�� ���� ī��� *2
				}
				pickCard[turn][4] += pickCard[turn][i];  // �� ���� �ջ�
			}
			if(pickCard[turn][gameTurn] == 0) // ���� ���� ī�尡 ��Ŀ���
			{
				pickCard[turn][gameTurn] = pickCard[turn][4]; // �̶����� ���� ī�� *2
			}
			
			pickCard[turn][4] += pickCard[turn][gameTurn];  // ������ ������ �ջ��Ҷ� ���� ī��� ���������Ƿ� �ջ�, ��Ŀ�� 0�̹Ƿ� ok
			
			// �÷��̾� ���Ǹ� ���� ���� ǥ��
			String s = Integer.toString(pickCard[turn][4]);
			String sa = list[turn] + " " + s;
			listLabel[turn].setText(sa);
		}
	}

	class inputNum extends JFrame
	{
		//Player(�����) �� �Է¿� ���� JLabel, ��ư
		private JLabel inputPlaLabel;
		private JButton minusPlaNum;
		private JLabel showPlaNum;
		private JButton plusPlaNum;
		
		//Computer(��ǻ��) �� �Է¿� ���� JLabel, ��ư
		private JLabel inputComLabel;
		private JButton minusComNum;
		private JLabel showComNum;
		private JButton plusComNum;
		
		private Container cp;
		
		public inputNum()
		{
			setTitle("ī�� ����");
			setSize(400,280);

			setInput(); // ��ǲ �������̽� ����
		
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setResizable(false);
		}
		public void setInput()
		{
			cp = getContentPane();
			cp.setLayout(null);

			//��ǲ Ÿ��Ʋ ���� �� ����
			JLabel inputTitleLabel = new JLabel("�ο� �� �Է�");
			inputTitleLabel.setFont(new Font("�޸յձ�������", Font.PLAIN, 25));
			inputTitleLabel.setBounds(110, 20, 200, 30);
			cp.add(inputTitleLabel);
			
			
			setPlaInput(); //�÷��̾� ��ǲ ��, ��ư ���� �� ����
			setComInput(); //��ǻ�� ��ǲ ��, ��ư ���� �� ����
				
			//�Է����(�������)�� ���� Cancel ��ư, �Է�Ȯ��(���ӽ���)�� ���� OK ��ư 
			JButton Cancel = new JButton("Cancel");		Cancel.setBounds(80,195,100,30);  Cancel.addActionListener(new CancelAction());
			JButton OK = new JButton("OK");		        OK.setBounds(200,195,100,30);	OK.addActionListener(new OKAction());
			cp.add(Cancel);
			cp.add(OK);
		}
		public void setPlaInput()
		{
			inputPlaLabel = new JLabel("�÷��̾� �� :");  inputPlaLabel.setFont(new Font("",Font.BOLD,15));
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
			inputComLabel = new JLabel("  ��ǻ�� �� :");  inputComLabel.setFont(new Font("",Font.BOLD,15));
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
				//OK��ư Ŭ���� �� pla, com ������ ���� JLabel�� ���ڸ� �Է�
				pla = Integer.parseInt(showPlaNum.getText());
				com = Integer.parseInt(showComNum.getText());
				num = pla+com;
				
				// �ο��� �Է� ����ó��
				if(num <= 0)
				{
					JOptionPane.showMessageDialog(null, "�ο����� �ʹ� �����ϴ�. �ٽ� �Է����ּ���.", "�Է� ����", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					dispose();
					setGame(); //Game_system �����ӿ� ���� �������̽� ����(�÷��̾�, ī�� ��)
				}
			}
		}
		class add_subBtn implements ActionListener
		{
			// +,- ��ư �׼� ������
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
			//Cancel ��ư Ŭ����, �Է�â �ݰ� �޴�â Ȱ��ȭ
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