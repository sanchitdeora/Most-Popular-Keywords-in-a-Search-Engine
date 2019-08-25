import java.util.*;

public class FibonacciHeap 
{
	//Holds the Node with the maximum value
	static Node maxNode;	
	
	//Holds the value of the number of nodes in root list
	static int countNodes = 0;		 
	
	public void insert(Node a)
	{
		//Inserts Node a to the root list
		
		if(maxNode == null)
			maxNode = a;
		else
		{
			//Inserts Node a to the right of maxNode
			a.left = maxNode;
			a.right = maxNode.right;
			maxNode.right.left = a;
			maxNode.right = a;	
		}
		countNodes ++;
		//Checks and updates the maxNode value
		findMax(a);
	}
	
	private void findMax(Node a)
	{
		//Find maxNode and update in the root list	
		
		int i, max = 0;
		if(countNodes == 0)
			maxNode = null;
		else
		{
			for(i = 0; i < countNodes; i++)
			{
				if ( a.frequency > max)
				{
					max = a.frequency;
					maxNode = a;
				}
				a = a.right;		
			}
		}		
	}
	
	private void putChild(Node a, Node b)
	{
		//Parent is Node b and makes Node a the child of b
		
		a.parent = b;
		a.childCut = false;
		b.degree ++;
		
		//Delete Node a from the root list before adding to child list of Node b
		a.left.right = a.right;
		a.right.left = a.left;
		countNodes --;
		
		//To check if Node b has no other child and updating Child list of Node b
		if(b.child == null)
		{
			b.child = a;
			a.left = a;
			a.right = a;
		}
		else
		{
			a.left = b.child;
			a.right = b.child.right;
			b.child.right.left = a;
			b.child.right = a;
		}
	}
	
	private void cutNode(Node a, Node b)
	{
		//Cuts child Node a from parent Node b
		
		b.degree --;
		//If Node b has no children after cutting set to null
		if(b.degree == 0)
		{
			b.child = null;
		}
		a.parent = null;
		//Change child node of Node b if necessary
		if(b.child == a)
		{
			b.child = a.right;
		}
		//Update child list of node b
		a.left.right = a.right;
		a.right.left = a.left;
		a.childCut = false;
		insert(a);
	}
	
	private void cascadingCut(Node a)
	{
		//To perform cascading cuts
		
		Node b = a.parent;
		if(b != null)
		{
			//Change the childCut field to true if false
			if(a.childCut == false)
			{
				a.childCut = true;
			}
			//Cut the node and check again for the parent node till the root
			else
			{
				cutNode(a,b);
				cascadingCut(b);
			}
		}
	}
	
	public void increaseKey(Node a, int val)
	{
		//Increases the key value of the node by the specified amount val
		
		a.frequency += val;
		Node b = a.parent;
		if(b != null && a.frequency > b.frequency)
		{
			cutNode(a,b);
			cascadingCut(b);
		}
	}
	
	public Node removeMax()
	{
		//Remove the max element in the heap
		
		Node b = maxNode;
		Node a;
		if(b != null)
		{
			while(b.degree > 0)
			{
				a = b.child;
				cutNode(a,b);
			}
			b.left.right = b.right;
			b.right.left = b.left;
			countNodes --;
			//To check if heap is empty 
			if(b == b.right)
				maxNode = null;
			else
			{
				findMax(b.right);
				pairwiseCombine();
			}
			return b;
		}
		return null;
	}
	
	private void pairwiseCombine()
	{
		//After RemoveMax, the trees in the heap are combined
		
		int size = 100;
		//Hash table is used to store the Nodes according to their degrees
		HashMap<Integer, Node> treeTable = new HashMap<Integer, Node>(size);
		int i, deg, n;
		n = countNodes;
		//Initialize the tree table to null
		for(i = 0;i<100;i++)
			 treeTable.put(i,null);
		
		//Node a is used to traverse all the nodes in the root list before combining
		Node a = maxNode;
		//Node p is the current root node of the tree in the heap
		Node p = a;
		Node q, temp;
		if(a != null)
		{
			//Update tree table
			while( n!=0 )
			{
				p = a;
				deg = p.degree;
				a = a.right;
				//Node q is used to retrieve the node with the same degree as node p from the tree table, if any
				q = treeTable.get(deg);
				while(q != null)
				{
					//For Node q to be the child and Node p to be the parent root node
					if(p.frequency < q.frequency)
					{
						temp = p;
						p = q;
						q = temp;
					}
					putChild(q,p);
					treeTable.put(deg, null);
					deg=deg+1;
					q = treeTable.get(deg);
				}
				treeTable.put(deg,p);
				n --;
			}	
			findMax(p.right);
		}
	}
}
