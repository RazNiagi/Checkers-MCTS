import java.util.*;

import javax.swing.JOptionPane;

import java.awt.*;

class MyCanvas extends Canvas
{
	public MyCanvas()
	{
		setBackground(new Color(210,180,140));
		setSize(800,800);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2;
		g2 = (Graphics2D)g;
		g2.setColor(new Color(125,125,0));
		for(int i = 0; i < 400; i+=50)
		{
			if((i/50)%2 == 1)
			{
				g2.fillRect(0, i, 50, 50);
				g2.fillRect(100, i, 50, 50);
				g2.fillRect(200, i, 50, 50);
				g2.fillRect(300, i, 50, 50);
			}
			else
			{
				g2.fillRect(50, i, 50, 50);
				g2.fillRect(150, i, 50, 50);
				g2.fillRect(250, i, 50, 50);
				g2.fillRect(350, i, 50, 50);
			}
		}
	}
	
	public void paintBoard(Board b)
	{
		Graphics2D g2 = (Graphics2D)this.getGraphics();
		for(int i = 63; i >= 0; i--)
		{
			if(b.pos[i].equals("r"))
			{
				
			}
			else if(b.pos[i].equals("b"))
			{
				
			}
			else if(b.pos[i].equals("R"))
			{
				
			}
			else if(b.pos[i].equals("B"))
			{
				
			}
		}
	}
}

public class Checkers 
{
	private static Frame mainframe;
	public static void main(String[] args) 
	{
		int tests = Integer.parseInt(JOptionPane.showInputDialog("How many Iterations would you like to run?"));
		mainframe = new Frame("Checkers Monte Carlo Tree Search");
		mainframe.setSize(400, 400);
		MyCanvas canvas = new MyCanvas();
		canvas.setSize(400, 400);
		mainframe.add(canvas);
		mainframe.setVisible(true);
		int rw = 0;
		int bw = 0;
		int draws = 0;
		double rv = 0;
		double bv = 0;
		for(int x = 0; x < tests; x++)
		{
		String cont = "y";
		//Scanner kb = new Scanner(System.in);
		Board test = new Board();
		test.turn = "black";
		test.pos[26] = "r";
		test.pos[17] = "-";
		test.pos[42] = "-";
		test.pos[35] = "b";
		test.pos[33] = "r";
		test.pos[26] = "-";
		test.printBoard();
		System.out.println();
		canvas.getGraphics().setColor(Color.RED);
		canvas.getGraphics().fillOval(5, 5, 40, 40);
		canvas.update(canvas.getGraphics());
		for(int i = 63; i >= 0; i++)
		{
			if(i/8%2 == 0)
			{
				
			}
		}
		//Vector<Board> game = new Vector<Board>();
		
		//if the player is black
		/*Vector<Board> possiblemovs;
		if(test.turn.equals("red"))
		{
			possiblemovs = test.availableMovesRed();
		}
		else
		{
			possiblemovs = test.availableMovesBlack();
		}
		for(int i = 0; i < possiblemovs.size(); i++)
		{
			System.out.println("Move " + (i+1) + ": ");
			possiblemovs.get(i).printBoard();
		}
		System.out.println("Pick a move by number: ");
		int movenput = Integer.parseInt(kb.next());
		test = possiblemovs.get(movenput-1);*/
		//int t = 0;
		MCNode main = new MCNode(test);
		
		//game.add(main.getBoard());
		while(cont.equalsIgnoreCase("y"))
		{
		long end = System.currentTimeMillis();
		int gameevals = 0;
		while(System.currentTimeMillis() - end < 100)
		{
			if(main.getBoard().turn.equals("red") && main.getBoard().availableMovesRed().size() == 1
					|| main.getBoard().turn.equals("black") && main.getBoard().availableMovesBlack().size() == 1)
			{
				main.montecarlo();
				gameevals++;
				break;
			}
			else
			{
				main.montecarlo();
			}
			gameevals++;
		}
		/*if(main.depth == 200)
		{
			break;
		}*/
		for(MCNode m : main.children)
		{
			System.out.println(m.wins + "/" + m.visits + " " + m.potential());
		}
		
		main = main.movetochild();
		System.out.println(main.depth);
		main.getBoard().printBoard();
		//game.add(main.getBoard());
		
		if(main.getBoard().turn.equals("red") && main.getBoard().availableMovesRed().size() == 0
				|| main.getBoard().turn.equals("black") && main.getBoard().availableMovesBlack().size() == 0)
		{
			break;
		}
		
		
		System.out.println(gameevals + " games evaluated.");
		
		
		/*if(t%3 == 0)
		{
		Vector<MCNode> possiblemoves;
		possiblemoves = main.children;
		Random r = new Random();
		main = main.movetochildplay(r.nextInt(possiblemoves.size()));
		main.getBoard().printBoard();
		
		if(main.getBoard().turn.equals("red") && main.getBoard().availableMovesRed().size() == 0
				|| main.getBoard().turn.equals("black") && main.getBoard().availableMovesBlack().size() == 0)
		{
			break;
		}
		}
		t++;*/
		/*for(int i = 0; i < possiblemoves.size(); i++)
		{
			System.out.println("Move " + (i+1) + ": ");
			possiblemoves.get(i).getBoard().printBoard();
		}
		System.out.println("Pick a move by number: ");
		int moveinput = Integer.parseInt(kb.next());
		main = main.movetochildplay(moveinput-1);
		game.add(main.getBoard());
		
		if(main.getBoard().turn.equals("red") && main.getBoard().availableMovesRed().size() == 0
				|| main.getBoard().turn.equals("black") && main.getBoard().availableMovesBlack().size() == 0)
		{
			break;
		}*/
		//cont = kb.next();
		}
		if(main.getBoard().redpiecesLeft() > main.getBoard().blackpiecesLeft())
		{
			rw++;
			rv+= main.getBoard().redpiecesLeft() - main.getBoard().blackpiecesLeft();
			
		}
		else if(main.getBoard().redpiecesLeft() < main.getBoard().blackpiecesLeft())
		{
			bw++;
			bv+= main.getBoard().blackpiecesLeft() - main.getBoard().redpiecesLeft();
		}
		else
		{
			draws++;
		}
		
		System.out.println("Game " + (x+1) + " complete.");
		}
		
		rv /= rw;
		bv /= bw;
		System.out.println("Average number of pieces left: Red: " + rv + "\tBlack: " + bv);
		System.out.println("Red wins: " + rw + "\nBlack Wins: " + bw + "\nDraws: " + draws);
		mainframe.dispose();
		
		/*System.out.println("Waiting for ready: ");
		if(!kb.next().equals("exit"))
		{
			for(Board b2 : game)
			{
				b2.printBoard();
				System.out.println();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		kb.close();*/
		/*Board test = new Board();
		test.printBoard();
		System.out.println("0");
		int count = 1;
		Random r = new Random();
		String cor = "red";
		while(gameover(test) == false)
		{
			if(test.getTurn().equals("red"))
			{
				Vector<Board> temp = new Vector<Board>();
				if(test.jumpAvailableRed().size() > 0)
					temp = test.jumpAvailableRed();
				else
					temp = test.availableMovesRed();
						
				test = temp.get(r.nextInt(temp.size()));
			}
			else
			{
				Vector<Board> temp = new Vector<Board>();
				if(test.jumpAvailableBlack().size() > 0)
				{
					temp = test.jumpAvailableBlack();
				}
				else
				{
					temp = test.availableMovesBlack();
					
				}
						
				test = temp.get(r.nextInt(temp.size()));
			}
			test.printBoard();*/
		/*	if(test.getTurn().equals(cor))
			{
				test.switchTurn();
			}*/
			/*cor = test.getTurn();
			System.out.println(cor);
			
			System.out.println(count);
			count++;
		}
		
		System.out.println(winner(test));
		if(test.turn.equals("red"))
		{
			System.out.println(test.evalPositionRed());
		}
		else
		{
			System.out.println(test.evalPositionBlack());
		}*/
		/*Board a = new Board();
		a.printBoard();
		String[] b = a.getPos();
		b[28] = "R";
		b[21] = "-";
		b[62] = "-";
		b[60] = "-";
		b[53] = "r";
		b[14] = "-";
		b[23] = "R";
		Board b2 = new Board("red", b);
		b2.printBoard();
		System.out.println(b2.evalPositionRed() +" " + b2.evalPositionBlack());
		System.out.println(a.evalPositionRed());
		String[] tempo = new String[64];
		for(int i = 0; i < 64; i++)
		{
			tempo[i] = "-";
		}
		Board testboard = new Board("red", tempo);
		
		testboard.pos[3] = "r";
		testboard.pos[7] = "r";
		testboard.pos[8] = "r";
		testboard.pos[19] = "R";
		testboard.pos[62] = "r";
		testboard.pos[60] = "b";
		testboard.pos[56] = "b";
		testboard.pos[55] = "b";
		testboard.pos[44] = "B";
		testboard.pos[5] = "b";
		
		testboard.printBoard();
		testboard.promote();
		testboard.printBoard();
		
		System.out.println();
		
		int outer = 0;
		for(Board b4 : testboard.availableMovesRed())
		{
			int inner = 0;
			for(Board b5 : b4.availableMovesBlack())
			{
				b5.printBoard();
				System.out.println(outer + ", " + inner);
				System.out.println(b5.evalPositionRed());
				inner++;
			}
			outer++;
		}
		
		testboard.printBoard();
		*/
		/*Board testboard2 = new Board();
		
		for(int i = 0; i < testboard2.pos.length; i++)
		{
			testboard2.pos[i] = "-";
		}
		
		testboard2.pos[1] = "R";
		testboard2.pos[10] = "b";
		testboard2.pos[28] = "B";
		testboard2.pos[26] = "b";
		testboard2.pos[44] = "b";
		testboard2.pos[46] = "b";
		
		Vector<Board> t2 = testboard2.extraJump(1);
		//testboard2.printBoard();
		
		for(Board b4 : t2)
		{
			System.out.println(b4.evalPositionRed());
			
			b4.printBoard();
		}
		
		Board testboard3 = testboard2.flipBoard();
		
		Vector<Board> t3 = testboard3.extraJump(1);
		//testboard2.printBoard();
		
		for(Board b4 : t3)
		{
			System.out.println(b4.evalPositionRed());
			
			b4.printBoard();
		}
		
		MCNode root = new MCNode(testboard3);
		System.out.println(root.getValue());
		for(Board b5 : t3)
		{
			MCNode temp = new MCNode(root, b5);
			System.out.println(temp.getValue());
		}
		
		root.pickChild().getBoard().printBoard();
		*/
		
		
/*		Vector<Board> t2 = testboard.availableMovesRed();
		Queue<Board> q = new LinkedList<Board>();
		
		for(Board b0 : t2)
		{
			q.add(b0);
		}
		int ol = 1;
		while(q.isEmpty() == false)
		{
			int il = 1;
			Vector<Board> t4 = q.poll().availableMovesRed();
			for(Board b0 : t4)
			{
				b0.printBoard();
				System.out.println(ol + " " + il);
				il++;
			}
			ol++;
		}*/
		
	/*	
		for(Board b0 : t2)
		{
			b0.printBoard();
			System.out.println();
		}
		*/
	/*	Vector<Board> test = b2.availableMovesRed();
		for(Board bo : test)
		{
			bo.printBoard();
			System.out.println(bo.turn);
			System.out.println(bo.evalPositionRed());
		}
		System.out.print(test.size());*/
	}
	
	static MCNode monteCarlo(MCNode m)
	{
		long time = System.currentTimeMillis();
		while(System.currentTimeMillis()-time < 2000)
		{
			if(m.getBoard().getTurn().equals("red"))
			{
				
			}
			else
			{
				
			}
		}
		return m;
	}
	
	static boolean gameover(Board b)
	{
		Vector<Board> temp;
		b.countPieces();
		if(b.blackkings+b.blackmen+b.redkings+b.redmen <= 5)
		{
			System.out.println("Too few pieces.");
			return true;
		}
		
		if(b.evalPositionRed() >= 2)
		{
			if(b.getTurn().equals("black"))
			{
				temp = b.availableMovesBlack();
				boolean flag = true;
				for(Board b2 : temp)
				{
					if(b2.evalPositionBlack() >= -1)
					{
						flag = false;
					}
				}
				if(flag == true)
				{
					System.out.println("Overwhelming Majority.");
				}
				return flag;
			}
			else
			{
				return true;
			}
		}
		if(b.evalPositionBlack() >= 2)
		{
			if(b.getTurn().equals("red"))
			{
				temp = b.availableMovesRed();
				boolean flag = true;
				for(Board b2 : temp)
				{
					if(b2.evalPositionRed() >= -1)
					{
						flag = false;
					}
				}
				if(flag == true)
				{
					System.out.println("Overwhelming Majority.");
				}
				return flag;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	static String winner(Board b)
	{
		if(b.getTurn().equals("red"))
		{
			if(b.availableMovesRed().size()==0)
			{
				if(b.blackkings+b.blackmen<b.redkings+b.redmen)
				{
					return "red";
				}
				else
				{
					return "black";
				}
			}
		}
		else
		{
			if(b.availableMovesBlack().size()==0)
			{
				if(b.blackkings+b.blackmen<b.redkings+b.redmen)
				{
					return "red";
				}
				else
				{
					return "black";
				}
			}
		}
		if(b.blackkings+b.blackmen+b.redkings+b.redmen <= 5)
		{
			if(b.blackkings+b.blackmen<b.redkings+b.redmen)
			{
				return "red";
			}
			else
			{
				return "black";
			}
		}
		else
		{
			if(b.evalPositionRed() >= 2)
			{
				return "red";
			}
			else
			{
				return "black";
			}
		}
	}
}
