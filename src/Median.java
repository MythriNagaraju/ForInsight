import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
	
public class Median {
	/* Maintain two heaps - one max heap for storing the lower half
	 * of the number of unique words in the incoming tweets and a min
	 * heap for storing the bigger half of the numbers
	 */
	PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(); 
	PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	int numElements = 0;

	public int processTweet(String tweet){
		/*Gets number of unique words in each tweet */
		String [] arr = tweet.split(" ");
		int count = 0;
		HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
		for (String word : arr){
			if(wordCount.get(word) != null){
				count = wordCount.get(word);
				count += 1;
			}
			else{
				count = 0;
			}
			wordCount.put(word, count);
		}
		return wordCount.size();
	}
	
	public void insert(int num){
		/* The following 2 conditions are maintained:
		 * 
		 * 1. The number of elements in max heap is always
		 *    equal to or 1 greater than the number of 
		 *    elements in the min heap
		 * 2. The root of the min heap should always be greater
		 *    than the root of the max heap
		 *    
		 * The numbers are always inserted to the max heap 
		 * and then the numbers are adjusted to satisfy the 
		 * size requirement(1) 
		 */
		int toMin, toMax;
		if ((numElements%2) == 0){
			maxHeap.add(-1*num);
			numElements += 1;
			if (minHeap.size()!= 0 && -1*maxHeap.peek() > minHeap.peek()){
				toMin = -1*maxHeap.peek();
				toMax = minHeap.peek();
				maxHeap.remove(maxHeap.peek());
				minHeap.remove(minHeap.peek());
				maxHeap.add(-1*toMax);
				minHeap.add(toMin);
			}
		}
		else{
			maxHeap.add(-1*num);
			toMin = maxHeap.peek();
			maxHeap.remove(maxHeap.peek());
			minHeap.add(-1*toMin);
			numElements += 1;
		}
	}
	
	public  double calculateMedian(){
		/* If there are even number of elements on
		 * the heap , median is the mean of the roots
		 * of both the heaps, otherwise the root of 
		 * the max heap 
		 */
		if ((numElements%2)==0){
			return (-1*maxHeap.peek() + minHeap.peek())/(double)2.0;
		}
		else{
			return -1*maxHeap.peek();
		}
	}

	public static void main(String [] args) throws IOException, FileNotFoundException{
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//Date start = new Date();
		//Date end = new Date();
		//System.out.println(start);
		//System.out.println(dateFormat.format(start));
		if (args.length != 2){
			System.out.println("Please enter 2 filenames");
		}
		Median test = new Median();
		try{
			String filename = args[0];
			double median;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			PrintWriter out = new PrintWriter(args[1]);
				String input;
				int numUniqueWords;
				while ((input=br.readLine())!=null){
					numUniqueWords = test.processTweet(input);
					test.insert(numUniqueWords);
					median = test.calculateMedian();
					//System.out.println(median);
                    out.write(Double.toString(median)+'\n');
				}
			br.close();
			out.close();
			//System.out.println(end);
			//System.out.println(dateFormat.format(end));*/
		}catch(IOException io){
			io.printStackTrace();
		}
	}
}
