
public class Node 
{
	Node parent, child, left, right;
	int degree = 0;
	String keyword;
	int frequency;
	boolean childCut = false;
	public Node(String keyword ,int freq)
	{
		this.parent = null;
		this.child = null;
		this.left = this;
		this.right = this;
		this.degree = 0;
		this.keyword = keyword;
		this.frequency = freq;
	}
}
