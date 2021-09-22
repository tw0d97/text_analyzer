//////////////////////
//   Tristan Wood   //
//    09/22/2021    //
//     Module 2     //
//   Text Analyzer  //
//////////////////////
package text_analyzer;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TestClass {

	public static void main(String[] args) {
		
		//Passes url to wordMap method
		Map<String, Integer> wordMap = wordMap("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm"); 
		// Passes wordMap to sort method
		List<Entry<String, Integer>> list = sort(wordMap); 
		
		System.out.println("Word       Frequency");
		System.out.println("————————————————————");

		// Loop through each entry in sorted list
        for (Map.Entry<String, Integer> entry : list) {
        	// Format output
        	String spaces = " ";
        	String answer = entry.getKey() + ":" + spaces;
        	
        	while(answer.length() != 18) { 
        		answer += spaces;
        		}
        	
        	answer += entry.getValue();
        	// Output to console
            System.out.println(answer);
        }
		
	}
	
	public static Map<String, Integer> wordMap(String url) {
		
		Map<String, Integer> wordMap = new HashMap<>();
		String line;
		Document doc = null;
		Pattern pattern = Pattern.compile("\\s+");
		//Jsoup library to connect to given URL
		try {
			doc = Jsoup.connect(url).timeout(6000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements body = doc.select("body");
		
		// Loops through each element in body and selects text in each paragraph html tag named poem
		for(Element e : body.select("p.poem")) {
			line = e.select("p.poem").text();
			// Changes all text to upper case to account for case sensitivity
			line = line.toUpperCase();
			// I know this is bad code but it gave me errors whenever trying to replace multiple RegEx in one line
			// Please let me know how I could improve these lines
			line = line.replace("—", " ");
			line = line.replace("!", "");
			line = line.replace("”", "");
			line = line.replace("“", "");
			line = line.replace(".", "");
			line = line.replace(",", "");
			line = line.replace(";", "");
			line = line.replace("’", "");
			// Splits all words separated by spaces
			String[] words = pattern.split(line);
			// Loops through each word, adds 1 to frequency if exists, else adds word to map
			for (String word : words) {
				if(wordMap.containsKey(word)) {
					wordMap.put(word, (wordMap.get(word) + 1));
				} else {
					wordMap.put(word, 1);
				}
			}
		}
		return wordMap;
	}
	
	public static List<Entry<String, Integer>> sort(Map<String, Integer> wordMap) {
		// Puts wordMap into entries set
		Set<Entry<String, Integer>> entries = wordMap.entrySet();
		// Puts entries set into an ArrayList
		List<Entry<String, Integer>> list = new ArrayList<>(entries);
		// Sorts list in descending order
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> object1, 
                               Map.Entry<String, Integer> object2) {
                return (object2.getValue()).compareTo(object1.getValue());
            }
        });
		return list;
	}
}
