import java.util.*;
public class Board 
{
	String turn = "red";
	String[] pos = new String[64];
	int redmen;
	int blackmen;
	int redkings;
	int blackkings;
	
	public Board()
	{
		turn = "red";
		for(int i = 0; i < 64; i++)
		{
			pos[i] = "-";
			if(i < 8)
			{
				if(i % 2 != 0)
				{
					pos[i] = "r";
				}
				else
				{
					pos[i] = "-";
				}
			}
			else if(i < 16)
			{
				if(i % 2 == 0)
				{
					pos[i] = "r";
				}
				else
				{
					pos[i] = "-";
				}
			}
			else if(i < 24)
			{
				if(i % 2 != 0)
				{
					pos[i] = "r";
				}
				else
				{
					pos[i] = "-";
				}
			}
			else if(i < 40)
			{
				pos[i] = "-";
			}
			else if(i < 48)
			{
				if(i % 2 == 0)
				{
					pos[i] = "b";
				}
				else
				{
					pos[i] = "-";
				}
			}
			else if(i < 56)
			{
				if(i % 2 != 0)
				{
					pos[i] = "b";
				}
				else
				{
					pos[i] = "-";
				}
			}
			else if(i < 64)
			{
				if(i % 2== 0)
				{
					pos[i] = "b";
				}
				else
				{
					pos[i] = "-";
				}
			}
		}
	}
	
	public Board(String t, String[] position)
	{
		turn = t;
		pos = position;
	}
	
	public Board flipBoard()
	{
		if(turn.equals("red"))
		{
			turn = "black";
		}
		else
		{
			turn = "red";
		}
		for(int i = 0; i < pos.length; i++)
		{
			switch(pos[i])
			{
				case "r":
					pos[i] = "b";
					break;
				case "R":
					pos[i] = "B";
					break;
				case "b":
					pos[i] = "r";
					break;
				case "B":
					pos[i] = "R";
					break;
			}
		}
		return this;
	}
	
	public void printBoard()
	{
		for(int i = 63; i >= 0; i--)
		{
			System.out.print(pos[i] + " ");
			if(i % 8 == 0)
			{
				System.out.println();
			}
		}
	}
	
	public Double evalPositionBlack()
	{
		countPieces();
		double total = (blackkings-redkings)*1.5 + blackmen-redmen;
		total /= (blackkings + redkings + blackmen + redmen);
		return total;
	}
	
	public Double evalPositionRed()
	{
		countPieces();
		double total = (redkings-blackkings)*1.5 + redmen - blackmen;
		total /= (blackkings + redkings + blackmen + redmen);
		return total;
	}
	
	public String getTurn()
	{
		String temp = turn;
		return temp;
	}
	
	public String[] getPos()
	{
		String[] temp = new String[64];
		for(int i = 0; i < pos.length; i++)
		{
			temp[i] = pos[i];
		}
		return temp;
	}
	
	public Vector<Board> extraJump(Integer x1)
	{
		Vector<Board> temp = new Vector<Board>();
		int nw = x1+9;
		int sw = x1-7;
		int ne = x1+7;
		int se = x1-9;
		int morejump = x1;
		boolean jumpagain = false;
		if(turn.equals("black"))
		{
			if(x1%8 != 6 && x1%8 != 7 && x1 >15)
			{
				if(pos[sw].equalsIgnoreCase("r"))
				{
					if(pos[sw-7].equals("-"))
					{
						Board a = new Board("black", this.getPos());
						a.pos[sw] = "-";
						a.pos[sw-7] = pos[x1];
						a.pos[x1] = "-";
						morejump = sw-7;
						if(a.extraJump(morejump).size() != 0)
						{
							jumpagain = true;
							for(Board b2 : a.extraJump(morejump))
							{
								temp.add(b2);
							}
						}
					}
				}
			}
			if(x1%8 != 1 && x1%8 != 0 && x1 >15)
			{
				if(pos[se].equalsIgnoreCase("r"))
				{
					if(pos[se-9].equals("-"))
					{
						Board a = new Board("black", this.getPos());
						a.pos[se] = "-";
						a.pos[se-9] = pos[x1];
						a.pos[x1] = "-";
						morejump = se-9;
						if(a.extraJump(morejump).size() != 0)
						{
							jumpagain = true;
							for(Board b2 : a.extraJump(morejump))
							{
								temp.add(b2);
							}
						}
					}
				}
			}
			if(pos[x1].equals("B"))
			{
				if(x1%8 != 1 && x1%8 != 0 && x1 < 48)
				{
					if(pos[ne].equalsIgnoreCase("r"))
					{
						if(pos[ne+7].equals("-"))
						{
							Board a = new Board("black", this.getPos());
							a.pos[ne] = "-";
							a.pos[ne+7] = pos[x1];
							a.pos[x1] = "-";
							morejump = ne+7;
							if(a.extraJump(morejump).size() != 0)
							{
								jumpagain = true;
								for(Board b2 : a.extraJump(morejump))
								{
									temp.add(b2);
								}
							}
						}
					}
				}
				if(x1%8 != 6 && x1%8 != 7 && x1 < 48)
				{
					if(pos[nw].equalsIgnoreCase("r"))
					{
						if(pos[nw+9].equals("-"))
						{
							Board a = new Board("black", this.getPos());
							a.pos[nw] = "-";
							a.pos[nw+9] = pos[x1];
							a.pos[x1] = "-";
							morejump = nw+9;
							if(a.extraJump(morejump).size() != 0)
							{
								jumpagain = true;
								for(Board b2 : a.extraJump(morejump))
								{
									temp.add(b2);
								}
							}
						}
					}
				}
			}
		}
		else
		{
			if(x1%8 != 7 && x1%8 != 6 && x1 < 48)
			{
				if(pos[nw].equalsIgnoreCase("b"))
				{
					if(pos[nw+9].equals("-"))
					{
						Board a = new Board("red", this.getPos());
						a.pos[nw] = "-";
						a.pos[nw+9] = pos[x1];
						a.pos[x1] = "-";
						morejump = nw+9;
						if(a.extraJump(morejump).size() != 0)
						{
							jumpagain=true;
							for(Board b2 : a.extraJump(morejump))
							{
								temp.add(b2);
							}
						}
					}
				}
			}
			if(x1%8 != 1 && x1%8 != 0 && x1 < 48)
			{
				if(pos[ne].equalsIgnoreCase("b"))
				{
					if(pos[ne+7].equals("-"))
					{
						Board a = new Board("red", this.getPos());
						a.pos[ne] = "-";
						a.pos[ne+7] = pos[x1];
						a.pos[x1] = "-";
						morejump = ne+7;
						if(a.extraJump(morejump).size() != 0)
						{
							jumpagain = true;
							for(Board b2 : a.extraJump(morejump))
							{
								temp.add(b2);
							}
						}
					}
				}
			}
			if(pos[x1].equals("R"))
			{
				if(x1%8 != 1 && x1%8 != 0 && x1 >15)
				{
					if(pos[se].equalsIgnoreCase("b"))
					{
						if(pos[se-9].equals("-"))
						{
							Board a = new Board("red", this.getPos());
							a.pos[se] = "-";
							a.pos[se-9] = pos[x1];
							a.pos[x1] = "-";
							morejump = se-9;
							if(a.extraJump(morejump).size() != 0)
							{
								jumpagain = true;
								for(Board b2 : a.extraJump(morejump))
								{
									temp.add(b2);
								}
							}
						}
					}
				}
				if(x1%8 != 7 && x1%8 != 6 && x1 > 15)
				{
					if(pos[sw].equalsIgnoreCase("b"))
					{
						if(pos[sw-7].equals("-"))
						{
							Board a = new Board("red", this.getPos());
							a.pos[sw] = "-";
							a.pos[sw-7] = pos[x1];
							a.pos[x1] = "-";
							morejump = sw-7;
							if(a.extraJump(morejump).size() != 0)
							{
								jumpagain = true;
								for(Board b2 : a.extraJump(morejump))
								{
									temp.add(b2);
								}
							}
						}
					}
				}
			}
		}
		
		if(jumpagain==false)
		{
			temp.add(this);
		}
		
		if(temp.size()==0)
		{
			if(turn.equals("black"))
			{
				turn = "red";
			}
			else
			{
				turn = "black";
			}
		}
		for(int i = temp.size()-1; i > 0; i--)
		{
			for(int j = i-1; j >= 0; j--)
			{
				if(temp.get(i).equals(temp.get(j)))
				{
					temp.remove(i);
					break;
				}
			}
		}
		return temp;
	}
	
	public void promote()
	{
		for(int i = 0; i < 8; i++)
		{
			if(pos[i].equals("b"))
			{
				pos[i] = "B";
			}
		}
		for(int i = 56; i < 64; i++)
		{
			if(pos[i].equals("r"))
			{
				pos[i] = "R";
			}
		}
		return;
	}
	
	public Vector<Board> jumpAvailableRed()
	{
		Vector<Board> temp = new Vector<Board>();
		for(int i = 0; i < 64; i++)
		{
			if(pos[i].equalsIgnoreCase("r"))
			{
				if(i % 8 != 6 && i%8 != 7 && i < 48)
				{
					if(pos[i+9].equalsIgnoreCase("b") && pos[i+18].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
				if(i % 8 != 0 && i%8 != 1 && i < 48)
				{
					if(pos[i+7].equalsIgnoreCase("b") && pos[i+14].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
			}
			if(pos[i].equals("R"))
			{
				if(i % 8 != 6 && i%8 != 7 && i >15)
				{
					if(pos[i-7].equalsIgnoreCase("b") && pos[i-14].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
				if(i % 8 != 0 && i%8 != 1 && i > 15)
				{
					if(pos[i-9].equalsIgnoreCase("b") && pos[i-18].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
			}
		}
		
		for(Board b : temp)
		{
			b.promote();
			b.turn = "black";
		}
		return temp;
	}
	
	public Vector<Board> jumpAvailableBlack()
	{
		Vector<Board> temp = new Vector<Board>();
		for(int i = 0; i < 64; i++)
		{
			if(pos[i].equals("B"))
			{
				if(i % 8 != 7 && i%8 != 6 && i < 48)
				{
					if(pos[i+9].equalsIgnoreCase("r") && pos[i+18].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
				if(i % 8 != 0 && i%8 != 1 && i < 48)
				{
					if(pos[i+7].equalsIgnoreCase("r") && pos[i+14].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
			}
			if(pos[i].equalsIgnoreCase("b"))
			{
				if(i % 8 != 6 && i%8 != 7 && i >15)
				{
					if(pos[i-7].equalsIgnoreCase("r") && pos[i-14].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
				if(i % 8 != 1 && i%8 != 0 && i > 15)
				{
					if(pos[i-9].equalsIgnoreCase("r") && pos[i-18].equals("-"))
					{
						for(Board b : extraJump(i))
						{
							temp.add(b);
						}
					}
				}
			}
		}
		
		for(Board b : temp)
		{
			b.promote();
			b.turn = "red";
		}
		return temp;
	}
	
	public void clearboard()
	{
		for(int i = 0; i < 64; i++)
		{
			pos[i] = "-";
		}
	}
	
	public int piecesLeft()
	{
		return blackkings+blackmen+redkings+redmen;
	}
	
	public int redpiecesLeft()
	{
		return redkings+redmen;
	}
	
	public int blackpiecesLeft()
	{
		return blackkings+blackmen;
	}
	
	public void countPieces()
	{
		redmen = 0;
		blackmen = 0;
		redkings = 0;
		blackkings = 0;
		for(String i : pos)
		{
			switch(i)
			{
				case "r":
					redmen++;
					break;
				case "R":
					redkings++;
					break;
				case "b":
					blackmen++;
					break;
				case "B":
					blackkings++;
					break;
				default:
					break;
			}
		}
	}
	
	public Vector<Board> availableMovesRed()
	{
		Vector<Board> am = new Vector<Board>();
		for(Board b : jumpAvailableRed())
		{
			am.add(b);
		}
		if(am.size() != 0)
		{
			return am;
		}
		else
		{
			for(int i = 0; i < pos.length; i++)
			{
				if(pos[i].equals("r"))
				{
					if(i < 56)
					{
						if(i%8 != 7)
						{
							if(pos[i + 9].equals("-"))
							{
								Board temp = new Board("black", this.getPos());
								temp.pos[i] = "-";
								if(i+9 > 55)
								{
									temp.pos[i+9] = "R";
								}
								else
								{
									temp.pos[i+9] = "r";
								}
								am.add(temp);
							}
						}
						if(i%8 != 0)
						{
							if(pos[i + 7].equals("-"))
							{
								Board temp = new Board("black", this.getPos());
								temp.pos[i] = "-";
								if(i+7 > 55)
								{
									temp.pos[i+7] = "R";
								}
								else
								{
									temp.pos[i+7] = "r";
								}
								am.add(temp);
							}
						}
					}
				}
				else if(pos[i].equals("R"))
				{
					if(i < 56)
					{
						if(i%8 != 0)
						{
							if(pos[i + 7].equals("-"))
							{
								Board temp = new Board("black", this.getPos());
								temp.pos[i] = "-";
								temp.pos[i+7] = "R";
								am.add(temp);
							}
							if(i >= 9)
							{
								if(pos[i - 9].equals("-"))
								{
									Board temp = new Board("black", this.getPos());
									temp.pos[i] = "-";
									temp.pos[i-9] = "R";
									am.add(temp);
								}
							}
						}
						if(i%8 != 7)
						{
							if(pos[i + 9].equals("-"))
							{
								Board temp = new Board("black", this.getPos());
								temp.pos[i] = "-";
								temp.pos[i+9] = "R";
								am.add(temp);
							}
							if(i >= 7)
							{
								if(pos[i - 7].equals("-"))
								{
									Board temp = new Board("black", this.getPos());
									temp.pos[i] = "-";
									temp.pos[i-7] = "R";
									am.add(temp);
								}
							}
						}
					}
					else
					{
						if(i%8 != 7)
						{
							if(pos[i - 7].equals("-"))
							{
								Board temp = new Board("black", this.getPos());
								temp.pos[i] = "-";
								temp.pos[i-7] = "R";
								am.add(temp);
							}
						}
						if(i%8 != 0)
						{
							if(pos[i - 9].equals("-"))
							{
								Board temp = new Board("black", this.getPos());
								temp.pos[i] = "-";
								temp.pos[i-9] = "R";
								am.add(temp);
							}
						}
					}
				}
			}
			return am;
		}
	}
	
	public boolean equals(Board b)
	{
		boolean eq = true;
		String[] tempstr = b.getPos();
		for(int i = 0; i < 64; i++)
		{
			if(!pos[i].equals(tempstr[i]))
			{
				eq = false;
				return eq;
			}
		}
		return eq;
	}
	
	public Vector<Board> availableMovesBlack()
	{
		Vector<Board> am = new Vector<Board>();
		for(Board b : jumpAvailableBlack())
		{
			am.add(b);
		}
		if(am.size() != 0)
		{
			return am;
		}
		else
		{
			for(int i = 0; i < 64; i++)
			{
				if(pos[i].equalsIgnoreCase("b"))
				{
					if(i > 7)
					{
						if(i%8 != 7)
						{
							if(pos[i-7].equals("-"))
							{
								Board temp = new Board("red", this.getPos());
								temp.pos[i-7] = temp.pos[i];
								temp.pos[i] = "-";
								am.add(temp);
							}
						}
						if(i%8 != 0)
						{
							if(pos[i-9].equals("-"))
							{
								Board temp = new Board("red", this.getPos());
								temp.pos[i-9] = temp.pos[i];
								temp.pos[i] = "-";
								am.add(temp);
							}
						}
					}
				}
				if(pos[i].equals("B"))
				{
					if(i < 56)
					{
						if(i%8 != 0)
						{
							if(pos[i+7].equals("-"))
							{
								Board temp = new Board("red", this.getPos());
								temp.pos[i+7] = temp.pos[i];
								temp.pos[i] = "-";
								am.add(temp);
							}
						}
						if(i%8 != 7)
						{
							if(pos[i+9].equals("-"))
							{
								Board temp = new Board("red", this.getPos());
								temp.pos[i+9] = temp.pos[i];
								temp.pos[i] = "-";
								am.add(temp);
							}
						}
					}
				}
			}
			for(Board b : am)
			{
				b.promote();
			}
			return am;
		}
	}
	
	public void switchTurn()
	{
		if(turn.equals("black"))
		{
			turn = "red";	
		}
		else
		{
			turn = "black";
		}
	}
	
	public boolean makeMove(Integer x1, String move)
	{
		boolean flag = false;
		switch(move)
		{
			case "nw":
				if(x1%8 != 7 && x1 < 56)
				{
					if(pos[x1].equalsIgnoreCase("r") || pos[x1].equals("B"))
					{
						if(pos[x1+9].equals("-"))
						{
							pos[x1+9] = pos[x1];
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "jnw":
				if(x1%8 != 7 && x1 != 6 && x1 < 48)
				{
					if(pos[x1].equalsIgnoreCase("r"))
					{
						if(pos[x1+9].equalsIgnoreCase("b") && pos[x1+18].equals("-"))
						{
							pos[x1+18] = pos[x1];
							pos[x1+9] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else if(pos[x1].equals("B"))
					{
						if(pos[x1+9].equalsIgnoreCase("r") && pos[x1+18].equals("-"))
						{
							pos[x1+18] = pos[x1];
							pos[x1+9] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "sw":
				if(x1%8 != 7 && x1 > 7)
				{
					if(pos[x1].equalsIgnoreCase("b") || pos[x1].equals("R"))
					{
						if(pos[x1-7].equals("-"))
						{
							pos[x1-7] = pos[x1];
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "jsw":
				if(x1%8 != 7 && x1 != 6 && x1 > 15)
				{
					if(pos[x1].equalsIgnoreCase("b"))
					{
						if(pos[x1-7].equalsIgnoreCase("r") && pos[x1-14].equals("-"))
						{
							pos[x1-14] = pos[x1];
							pos[x1-7] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else if(pos[x1].equals("R"))
					{
						if(pos[x1-7].equalsIgnoreCase("b") && pos[x1-14].equals("-"))
						{
							pos[x1-14] = pos[x1];
							pos[x1-7] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "ne":
				if(x1%8 != 0 && x1 < 56)
				{
					if(pos[x1].equalsIgnoreCase("r") || pos[x1].equals("B"))
					{
						if(pos[x1+7].equals("-"))
						{
							pos[x1+7] = pos[x1];
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "jne":
				if(x1%8 != 0 && x1 != 1 && x1 < 48)
				{
					if(pos[x1].equalsIgnoreCase("r"))
					{
						if(pos[x1+7].equalsIgnoreCase("b") && pos[x1+14].equals("-"))
						{
							pos[x1+14] = pos[x1];
							pos[x1+7] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else if(pos[x1].equals("B"))
					{
						if(pos[x1+7].equalsIgnoreCase("r") && pos[x1+14].equals("-"))
						{
							pos[x1+14] = pos[x1];
							pos[x1+7] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "se":
				if(x1%8 != 0 && x1 > 7)
				{
					if(pos[x1].equalsIgnoreCase("b") || pos[x1].equals("R"))
					{
						if(pos[x1-9].equals("-"))
						{
							pos[x1-9] = pos[x1];
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
			case "jse":
				if(x1%8 != 0 && x1 != 1 && x1 > 15)
				{
					if(pos[x1].equalsIgnoreCase("b"))
					{
						if(pos[x1-9].equalsIgnoreCase("r") && pos[x1-18].equals("-"))
						{
							pos[x1-18] = pos[x1];
							pos[x1-9] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else if(pos[x1].equals("R"))
					{
						if(pos[x1-9].equalsIgnoreCase("b") && pos[x1-18].equals("-"))
						{
							pos[x1-18] = pos[x1];
							pos[x1-9] = "-";
							pos[x1] = "-";
							flag = true;
						}
						else
						{
							System.out.println("Illegal");
						}
					}
					else
					{
						System.out.println("Illegal");
					}
				}
				break;
		}
		
		return flag;
	}
}
