import java.io.*;

//import java.nio.file.*;
import java.util.*;

public class keywordcounter
{
	
	public static void main(String args[])throws IOException
	{
		//Create HashMaps to store the keyword
		HashMap<String, Node> h = new HashMap<String, Node>();
		
		//Create object of the class fibonacciHeap
		FibonacciHeap f = new FibonacciHeap();
		
		Node[] nodeArray = new Node[20];
		//Stores the name of the input file passed as an argument
		String inputFile = args[0];
		//To read from the Input file
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		//To write to the output file
		BufferedWriter writer = new BufferedWriter(new FileWriter("outputTest.txt"));
		//Read the first line of the input file
		String line = br.readLine();
		
		String keyword, frequency;
		Node n;
		int i, v, q, t;
		//To keep track of the line number in the input file
		int l = 1;
		
		//Run till it finds 'stop'
		while(!line.equalsIgnoreCase("stop"))
		{
			try
			{
				//If the line starts with '$', then it must be inputed
				if(line.startsWith("$"))
				{
					//Separating keyword and the frequency
					t = line.lastIndexOf(" ");
					keyword = line.substring(1,t);
					frequency = line.substring(t+1);
					v = Integer.parseInt(frequency);
					
					//If keyword does not exist in the heap, it is inserted
					if(!h.containsKey(keyword))
					{
						n = new Node(keyword, v);
						f.insert(n);
						h.put(keyword, n);
					}
					//If keyword exists in the heap, the frequency is increased
					else
					{
						n = h.get(keyword);
						f.increaseKey(n, v);
						h.put(keyword, n);
					}
				}
				//If Line starts with a number 'q', the max element is removed 'q' times
				else if(Character.isDigit(line.charAt(0)))
				{
					q = Integer.parseInt(line);
					for(i = 0; i < q; i++)
					{
						n = f.removeMax();
						h.remove(n.keyword);
						//Print in output file
						writer.write(n.keyword);
						if(i < q-1)
							writer.write(",");
						nodeArray[i] = n;
					}
					//Insert the elements back in after all the removing max is done
					for(i = 0; i < q; i++)
					{
						f.insert(nodeArray[i]);
						h.put(nodeArray[i].keyword, nodeArray[i]);
					}
					//Read the next line in the input file
					writer.newLine();
				}
			}
			//If some invalid input is read from the file
			catch(Exception e)
			{
						System.out.println("Invalid Input at line "+l+" Exception: "+e);
			}
			line = br.readLine();
			l ++;
		}
		//Once the line reads stop in the input file
		br.close();
		writer.close();
	}
}
