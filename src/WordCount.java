import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;

public class WordCount {
	/* Gets count of all unique words in all tweets in the 
	 * input file
	 */
	
    HashMap<String, Integer> allWords = new HashMap<String,Integer>();
    
	public void addWords(String line){
		/* Maintains a HashMap to store unique words
		 * and number of times each word occurs
		 */
		int count=0;
		for (String word: line.split(" ")){
			if (allWords.get(word) != null){
				count = allWords.get(word);
				count += 1;
			}
			allWords.put(word, count);
		}
	}
	
	public void writeToOutput(String filename) throws IOException{
		/* Writes each word and corresponding count 
		 * to output file
		 */
		PrintWriter out = new PrintWriter(filename);
		int count;
		String formatStr = "%-100s %-100s%n";
		for(String word: allWords.keySet()){
			count = allWords.get(word);
            out.write(String.format(formatStr,word,Integer.toString(count)));
            //System.out.print(String.format(formatStr,word,Integer.toString(count)));
		}
		out.close();
	}
	
	public static void main(String [] args) throws IOException, FileNotFoundException{
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date start = new Date();
		Date end = new Date();
		System.out.println(start);
		System.out.println(dateFormat.format(start));*/
		if (args.length != 2){
			System.out.println("Please enter two filenames");
		}
		WordCount wc = new WordCount();
		try{
			String filename = args[0];
			BufferedReader br = new BufferedReader(new FileReader(filename));
				String input;
				while ((input=br.readLine())!=null){
					wc.addWords(input);
				}
			br.close();
			wc.writeToOutput(args[1]);
			/*System.out.println(end);
			System.out.println(dateFormat.format(end));*/
		}catch(IOException io){
			io.printStackTrace();
		}
	}
}
