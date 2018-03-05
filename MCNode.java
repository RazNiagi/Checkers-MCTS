import java.util.*;
public class MCNode 
{
	Board b0;
	Vector<MCNode> children = new Vector<MCNode>();
	MCNode parent;
	int depth;
	int visits;
	String alignment;
	int wins;
	Vector<Board> prevpos;
	boolean repeatedpos;
	
	public MCNode(Board b)
	{
		b0 = b;
		parent = this;
		alignment = b.turn;
		depth = 0;
		visits = 0;
		wins = 0;
		prevpos = new Vector<Board>(0);
	}
	
	public MCNode(MCNode p, Board b)
	{
		b0 = b;
		visits = 0;
		alignment = b.turn;
		parent = p;
		wins = 0;
		depth = p.getDepth() + 1;
		repeatedpos = this.parent.checkRepeat(b0);
	}
	
	public MCNode movetochild()
	{
		this.getNextMove().parent = this.getNextMove();
		this.prevpos.add(this.b0);
		this.getNextMove().prevpos = this.prevpos;
		return this.getNextMove();
	}
	
	public boolean samepotvals()
	{
		double pval = this.children.get(0).potential();
		boolean temp = true;
		for(int i = 0; i < children.size(); i++)
		{
			if(children.get(i).potential() != pval)
			{
				return false;
			}
		}
		return temp;
	}
	
	public MCNode movetochildplay(int i)
	{
		this.children.get(i).parent = this.children.get(i);
		return this.children.get(i);
	}
	
	public MCNode getNextMove()
	{
		double visitsmax = 0;
		int childpos = 0;
		for(int i = 0; i < children.size(); i++)
		{
			if(children.get(i).visits > visitsmax)
			{
				visitsmax = children.get(i).visits;
				childpos = i;
			}
		}
		return children.get(childpos);
		//return children.get(this.getHPC()).getBoard();
	}
	
	public void montecarlo()
	{
		this.pickChild();
	}
	
	public double potential()
	{
		if(this.repeatedpos == true)
		{
			return 0;
		}
		else
		{
			double winc = (double)this.wins;
			double visitc = (double)this.visits;
			double exploration = Math.sqrt(3);
			double pvisitc = (double)parent.visits;
			double zed = winc/visitc + exploration * Math.sqrt(Math.log(pvisitc/visitc));
			return zed;
		}
	}
	
	public double getRootEval(String color)
	{
		if(this.hasParent())
		{
			 return parent.getRootEval(color);
		}
		else
		{
			if(color.equals("red"))
			{
				return this.getBoard().evalPositionRed();
			}
			else
			{
				return this.getBoard().evalPositionBlack();
			}
		}
	}
	
	public int getRootDepth()
	{
		if(this.hasParent())
		{
			 return parent.getRootDepth();
		}
		else
		{
			return this.depth;
		}
	}
	
	public void pickChild()
	{
		if(this.hasUnvisitedChilren())
		{
			this.rollout();
		}
		else
		{
			visits++;
			int nextmove = this.getHPC();
			/*if(this.samepotvals())
			{
				this.rolloutEnd();
			}*/
			if(this.b0.turn.equals("red") && this.b0.availableMovesRed().size() != 0 || this.b0.turn.equals("black") && this.b0.availableMovesBlack().size() != 0)
			{
				children.get(nextmove).pickChild();
			}
			else
			{
				if(this.b0.evalPositionBlack() > this.b0.evalPositionRed())
				{
					this.backprop("black");
				}
				else if(this.b0.evalPositionBlack() < this.b0.evalPositionRed())
				{
					this.backprop("red");
				}
				else
				{
					this.backprop("draw");
				}
			}
		}
	}
	
	public boolean checkRepeat(Board b)
	{
		if(this.b0.equals(b))
		{
			return true;
		}
		else
		{
			if(this.hasParent())
			{
				return this.parent.checkRepeat(b);
			}
			else
			{
				for(Board b1 : prevpos)
				{
					if(b1.equals(b))
					{
						return true;
					}
				}
				return false;
			}
		}
	}
	
	public Integer getHPC()
	{
		double maxval = 0;
		int childpos = 0;
		for(int i = 0; i < children.size(); i++)
		{
			double temp = children.get(i).potential();
			if(temp > maxval)
			{
				maxval = temp;
				childpos = i;
			}
		}
		return childpos;
	}
	
	public boolean hasUnvisitedChilren()
	{
		Vector<Board> tempboard = new Vector<Board>(0);
		if(b0.turn.equals("red"))
		{
			tempboard = b0.availableMovesRed();
		}
		else
		{
			tempboard = b0.availableMovesBlack();
		}
		if(tempboard.size() == children.size())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void updateVisits()
	{
		this.visits++;
		if(this.hasParent())
		{
			parent.updateVisits();
		}
	}
	
	public void rolloutEnd()
	{
		this.visits++;
		if(this.b0.blackpiecesLeft() > 0 || this.b0.redpiecesLeft() > 0)
		{
			if(this.hasUnvisitedChilren())
			{
				Vector<Board> tempb = this.getUnvisitedChildren();
				if(tempb.size() > 0)
				{
					Random r = new Random();
					children.add(new MCNode(this, tempb.get(r.nextInt(tempb.size()))));
					children.get(children.size()-1).rolloutEnd();
				}
			}
			else
			{
				Random r = new Random();
				children.get(r.nextInt(children.size())).rolloutEnd();
			}
		}
		else
		{
			if(b0.blackpiecesLeft() == 0)
			{
				this.backprop("red");
			}
			else
			{
				this.backprop("black");
			}
		}
	}
	
	public void rollout()
	{
		this.visits++;
		if(this.depth < this.getRootDepth() + 50)
		{
			Vector<Board> tempb = this.getUnvisitedChildren();
			if(tempb.size() > 0)
			{
				Random r = new Random();
				children.add(new MCNode(this, tempb.get(r.nextInt(tempb.size()))));
				children.get(children.size()-1).rollout();
			}
		}
		else
		{
			if(this.improved("red") > 0 && this.improved("black") <= 0)
			{
				this.backprop("red");
			}
			else if(this.improved("red") <= 0 && this.improved("black") > 0)
			{
				this.backprop("black");
			}
			else
			{
				this.backprop("draw");
			}
			/*if(this.b0.evalPositionBlack() > this.b0.evalPositionRed()+.5)
			{
				this.backprop("black");
			}
			else if(this.b0.evalPositionBlack() + .5 < this.b0.evalPositionRed())
			{
				this.backprop("red");
			}
			else
			{
				if(this.improved("red") > 0 && this.improved("black") < 0)
				{
					this.backprop("red");
				}
				else if(this.improved("red") < 0 && this.improved("black") > 0)
				{
					this.backprop("black");
				}
				else
				{
					this.backprop("draw");
				}
			}*/
		}
	}
	
	public double improved(String color)
	{
		double improv;
		if(color.equals("red"))
		{
			improv = this.getBoard().evalPositionRed()-this.getRootEval(color);
		}
		else
		{
			improv = this.getBoard().evalPositionBlack()-this.getRootEval(color);
		}
		return improv;
	}
	
	public Vector<Board> getUnvisitedChildren()
	{
		Vector<Board> tboard;
		if(this.b0.turn.equals("red"))
		{
			tboard = b0.availableMovesRed();
		}
		else
		{
			tboard = b0.availableMovesBlack();
		}
		for(int i = 0; i < children.size(); i++)
		{
			for(int j = 0; j < tboard.size(); j++)
			{
				if(children.get(i).getBoard().equals(tboard.get(j)))
				{
					tboard.remove(j);
				}
			}
		}
		return tboard;
	}
	
	public void backprop(String color)
	{
		if(this.alignment.equals("red") && color.equals("black") || this.alignment.equals("black") && color.equals("red"))
		{
			wins++;
		}
		/*if(color.equals("draw"))
		{
			wins ++;
		}*/
		if(this.hasParent())
		{
			parent.backprop(color);
		}
	}
	
	public boolean hasParent()
	{
		if(!this.parent.equals(this))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Board getBoard()
	{
		return b0;
	}
	
	public void addChild(MCNode m)
	{
		children.add(m);
	}
	
	public boolean equals(MCNode m)
	{
		if(b0.equals(m.getBoard()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Integer getDepth()
	{
		return depth;
	}
}
