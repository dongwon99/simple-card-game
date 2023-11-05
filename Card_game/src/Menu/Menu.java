package Menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import Game.*;




public class Menu extends JFrame 
{
	private JButton menuButton[];
	private String menuName[] = {"   ����   ", "   ��Ģ   ","   �ɼ�   ", "   ����   "};
	
	private Container cp;
	private JPanel jp = new JPanel(); // ���� �޴� �г�
	// ��Ÿ �޴�(�ɼ�, ��Ģ) �г�
	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	
	private int fontSize = 0; 

	public Menu(int fontSize)
	{
		setTitle("ī�� ����");
		setSize(800,600);
		
		this.fontSize = fontSize;
		
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		setMenu();	//�⺻ �޴� �������̽�
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);  //â �߰��� ����
		setResizable(false);  //â ũ�� ���� �Ұ� ����
	}	 
	public void setMenu()
	{
		 // ��ҵ��� ���� �����ϱ����� �ڽ� ���̾ƿ�
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		Box box = Box.createVerticalBox();
		
		//���� Ÿ��Ʋ ����
		JLabel lb = new JLabel("Indian Poker");
		lb.setAlignmentX(CENTER_ALIGNMENT);
		lb.setFont(new Font("HY�߰��",Font.PLAIN, 40));
		
		//�޴� ��ư: ������� ���ӽ���, ��Ģ, �ɼ�, �����ϱ� ��ư ����
		Font btnFont = new Font("HY��� �߰�", Font.PLAIN, 25);  
		menuButton = new JButton[4];
		for(int i = 0; i < 4; i++)
		{
			menuButton[i] = new JButton(menuName[i]);
			menuButton[i].setFont(btnFont);
			menuButton[i].setAlignmentX(CENTER_ALIGNMENT);
		
		}
		//�� ��ư�� ActionListener ����
		menuButton[0].addActionListener(new startBtnAction());
		menuButton[1].addActionListener(new ruleBtnAction());
		menuButton[2].addActionListener(new optionBtnAction());
		menuButton[3].addActionListener(new exitBtnAction());
		
		
		
		
		//���� ���� �� ���� Ÿ��Ʋ, ��ư �� �����ϱ�
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
		//��Ģ Ÿ��Ʋ / ��Ģ ���� / �޴��� ���ư��� ��ư�� �����ϱ� ���� JPanel
		northPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new FlowLayout());	
		
			
		//��Ģ Ÿ��Ʋ, ��Ģ ���� JLabel ����
		JLabel ruleTitle = new JLabel("���� ��Ģ");
		JLabel rule1_1 = new JLabel("1. �ο����� �Է��մϴ�. ��ǻ��, �÷��̾� ���ļ�");
		JLabel rule1_2 = new JLabel("    �ִ� 8�� ���� ���� �����մϴ�.");
		JLabel rule2_1 = new JLabel("2-1. ������ ���۵ǰ� �ڱ� ���ʰ� ����,");
		JLabel rule2_2 = new JLabel("       ī�带 �������� ī�带 ���� �̰�, ���� �ѱ�ϴ�.");
		JLabel rule3_1 = new JLabel("2-2. �ϴ� ���ѽð��� 20�ʷ�, 20�ʰ� ���������� ī�带 ���� ������");
		JLabel rule3_2 = new JLabel("       �������� ī�带 ���� �����ϰ� �˴ϴ�.");
		JLabel rule4 = new JLabel("3. �δ� 4���� ī�带 �̰ԵǸ�, ������ ����ǰ� ���â�� ��µ˴ϴ�.");	
		//��Ʈ ����
		Font ruleFont = new Font("", Font.PLAIN, 17+fontSize);	
		ruleTitle.setFont(new Font("HY�߰��", Font.BOLD, 30+fontSize));
		rule1_1.setFont(ruleFont);
		rule1_2.setFont(ruleFont);
		rule2_1.setFont(ruleFont);
		rule2_2.setFont(ruleFont);
		rule3_1.setFont(ruleFont);
		rule3_2.setFont(ruleFont);
		rule4.setFont(ruleFont);
		
		//�޴��� ���ư��� ��ư ����
		JButton toMenu = new JButton("�޴�");
		toMenu.setFont(new Font("", Font.BOLD, 20+fontSize));
		toMenu.addActionListener(new menuBtnAction());
		
		//��Ģ Ÿ��Ʋ JLabel ����
		northPanel.add(ruleTitle);
		//��Ģ ���� JLabel ����
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
		//�޴��� ���ư��� ��ư ����
		southPanel.add(toMenu);
				
		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
	}
	public void setOption()
	{		
		//�ɼ� Ÿ��Ʋ / �ɼ� ���� / �޴��� ���ư��� ��ư ������ ���� JPanel
		northPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		southPanel.setLayout(new FlowLayout());	
			
		//�ɼ� Ÿ��Ʋ ����
		JLabel optionTitle = new JLabel("   �ɼ�   ");
		optionTitle.setFont(new Font("HY�߰��", Font.BOLD, 30+fontSize));
		optionTitle.setAlignmentX(CENTER_ALIGNMENT);
	
		
		// �ɼ� ���� ���� (�ɼ� �׸�ǥ�ø� ���� JLabel, �ɼ� ���ÿ� ������ư, ������ư �׷�, ���� �׸���� ������� �ڽ�)
		Box b1 = Box.createHorizontalBox();
		JLabel inputTextSize = new JLabel("���� ũ��");
		inputTextSize.setFont(new Font("", Font.PLAIN, 20+fontSize));
		b1.add(inputTextSize);
		
		JRadioButton radioText[] = new JRadioButton[3];
		ButtonGroup textGroup = new ButtonGroup();
		for(int i = 0; i < 3; i++)
		{
			radioText[i] = new JRadioButton(Integer.toString(i+1));
			radioText[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// ������ư �׼Ǹ�����, �ش� ��ư�� ������ fontSize ������ ���� ��.
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
		
		// �޴��� ���ư��� ��ư ����
		JButton toMenu = new JButton("�޴�");
		toMenu.setFont(new Font("", Font.BOLD, 20+fontSize));
		toMenu.addActionListener(new menuBtnAction());
		
		// ���� ������Ʈ�� JPanel�� ����
		northPanel.add(optionTitle);
		centerPanel.add(Box.createVerticalStrut(90));
		centerPanel.add(b1);
		southPanel.add(toMenu);
		
		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
	}
	
	// �޴� ��ư���� ���� �׼� ������
	class startBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//���ӽ��� ��ư �׼Ǹ�����
			//�޴� �������� ���� �� Game_system Ŭ���� ����
			dispose();
			new Game_system(fontSize);
		}
	}
	class ruleBtnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//��Ģ ��ư�� ���� �׼Ǹ�����
			//�⺻ JPanel (�޴�) �� �����, ��Ģwindow�� ���� JPanel Ȱ��ȭ
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
			//�ɼ� ��ư�� ���� �׼Ǹ�����
			//�⺻ JPanel (�޴�) �� �����, �ɼ�window�� ���� JPanel Ȱ��ȭ
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
			//���α׷� ����
			System.exit(0);
		}
	}
}