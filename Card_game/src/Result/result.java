package Result;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import Game.Game_system;
import Menu.Menu;

public class result extends JFrame 
{
	//���� �迭, �÷��̾� list �迭 
	private int pickCard[][];
	private int score[];
	private int n;
	int fontSize=0;
	private String list[];
	
	//���� �������� �ݱ����� ����
	private Game_system g;
	
	private Container cp;
	private JButton replay; 
	private JButton exit;
	
	public result(Game_system g, int pickCard[][], String list[], int fontSize)
	{
		setTitle("���� ����");
		setSize(400,600);
		
		this.g = g;
		this.fontSize = fontSize;
		
		this.pickCard = pickCard;
		this.list = list;
		n = list.length;
		
		cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		
		sortRank(); // ��� ����(���� ����)
		setResult(); // ���â �������̽� ����, ��� ���
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	public void setResult()
	{
		cp.add(Box.createVerticalStrut(25));
		
		//�������� Ÿ��Ʋ ���� �� ����
		JLabel gameEnd = new JLabel("���� ����!!");
		gameEnd.setFont(new Font("", Font.BOLD, 25+fontSize));
		gameEnd.setAlignmentX(CENTER_ALIGNMENT);
		cp.add(gameEnd);
		cp.add(Box.createVerticalStrut(30));
		
		
		//��� ���
		for(int i = 0; i < n; i++)
		{			
			Box box = Box.createHorizontalBox();
			JLabel rank = new JLabel(Integer.toString(i+1)+"��");
			JLabel name = new JLabel(list[i]);
			JLabel grade = new JLabel(Integer.toString(score[i])+"��");
			
			rank.setFont(new Font("", Font.PLAIN, 15+fontSize));
			name.setFont(new Font("", Font.PLAIN, 15+fontSize));
			grade.setFont(new Font("", Font.PLAIN, 15+fontSize));

			name.setHorizontalAlignment(JLabel.CENTER);
			grade.setHorizontalAlignment(JLabel.CENTER);
			
			//player�� computer�� ���� ũ�Ⱑ �޶� ����� ��ġ�� ���� ���� ����ȭ
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

		// ���� ����
		for(int i = 0; i < 8-n; i++)
		{
			cp.add(Box.createVerticalStrut(50));
		}
		
		//�ٽ��ϱ�, �����ϱ� ��ư ���� �� ����
		Box box = Box.createHorizontalBox();
		replay = new JButton("�ٽ��ϱ�");  replay.addActionListener(new resultBtnAction()); replay.setFont(new Font("",Font.PLAIN, 15+fontSize));
		exit = new JButton("������");     exit.addActionListener(new resultBtnAction()); exit.setFont(new Font("",Font.PLAIN, 15+fontSize));
		
		box.add(replay); 					   
		box.add(exit);
		box.setAlignmentX(CENTER_ALIGNMENT);
		
		cp.add(box);
	}
	public void sortRank()
	{
		//������ ���� ������ ����
		score = new int[n];
		for(int i = 0; i < n; i++)
		{
			score[i] = pickCard[i][4];
		}
		
		//���� ���� �ǽ�
		for(int i = 0; i < n; i++)
		{
			for(int j = i; j < n; j++)
			{
				if(score[i] < score[j])
				{
					//����(int)
					int temp = score[i];
					score[i] = score[j];
					score[j] = temp;
					
					//�÷��̾� ��ȣ(String)
					String name = list[i];
					list[i] = list[j];
					list[j] = name;
				}
			}
		}
	}

	//�ٽ��ϱ�, �����ϱ� ��ư �׼� ������
	class resultBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JButton b = (JButton)e.getSource();
			
			//���� � ��ư�� �������� �ϴ� ���â, ���� ���� ������ �ݾ��ֱ�
			dispose();
			g.gameDispose();
			if(b == replay) // �ٽ��ϱ�
			{
				new Game_system(fontSize); // �� Game_system ����
			}
			else // �����ϱ�
			{
				new Menu(fontSize); // �޴���, �� Menu ����
			}
		}
	}
}
