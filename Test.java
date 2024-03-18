import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Test {
	public static String txt_name="txt1";
	
	public static void display(HashedDictionary<Long, Word> dataBase) {
		Iterator<Long> keyIterator = dataBase.getKeyIterator();
		Iterator<Word> valueIterator = dataBase.getValueIterator();
		while (keyIterator.hasNext()) {
			System.out.println("Key: " + keyIterator.next() + " Value: " + valueIterator.next());
			
		}
		  
	}
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashedDictionary<Long,Word> Hashtable = new HashedDictionary<Long, Word>();
		String filename_part1="000";
		String filename_part2=".txt";
		
		
		//******************************************************************* read file and add hashtable operitions
		long startTime = System.currentTimeMillis();
		for(int i=1;i<=100;i++) {//for 100 times because we have 100 txt
			
			int filename_part1_int=Integer.parseInt(filename_part1)+1; //			
			filename_part1=txt_name_partone(filename_part1_int);			
			txt_name=filename_part1+filename_part2; //this part just name the file 000txt ,001txt ......
			
			String content = null;
			try {
				content = new Scanner(new File(txt_name)).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] words = content.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+"); //file operitions
						
			hashtable_add(Hashtable,words);
			
		}
		long endTime = System.currentTimeMillis();
		//System.out.println("That took " + (endTime - startTime) + " milliseconds");
		
		
		
			
		//*******************************************************************
		Scanner myObj = new Scanner(System.in);
		System.out.println("Please enter three word: ");
		String words = myObj.nextLine();  // Read user input
		String[] words_split=words.split(" ");
		String word1 = words_split[0];		
		String word2 = words_split[1];  		
		String word3 = words_split[2];  
		//*******************************************************************input three words and look relevant file		
		String most_relevant=most_relevant_file(word1,word2,word3,Hashtable);	
        System.out.println("Most relevant file: "+most_relevant+".txt");
      //*******************************************************************
		
	
        //                         Searching
      //************************wi*******************************************
	   long startTimeforSearch = System.currentTimeMillis();
	   try {
		search_words(Hashtable);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   long endTimeforSearch = System.currentTimeMillis();   	
	 //  System.out.println("That took " + (endTimeforSearch - startTimeforSearch) + " milliseconds");    
      //*******************************************************************

	  // System.out.println(Hashtable.collision());

	
		
	}
	
	
	
	public static void hashtable_add(HashedDictionary<Long,Word> Hashtable,String[] words) {
		
		     for(int j=0;j<words.length;j++) {	
		    	 //long key=Simple_Summation_Function (words[j]); // create key with simple or pol
			 long key=Polynomial_Accumulation_Function (words[j]);
			 if(!(Hashtable.containsword(key,words[j],txt_name))) {//this method check if the word already inside the hashtable and if word was found add the txt name 
				 Object entry=new Word(words[j]);						
				 Hashtable.add(key,(Word) entry);
				 ((Word) entry).addtxt(txt_name);
			 }						
		}
		
	}
	
	
	public static String most_relevant_file(String word1,String word2,String word3,HashedDictionary<Long, Word> hashTable) {
		String most_relevant=null;		
		int most_relevant_int=0;
		
		
	    String[] word1_Txt=word_txt_array(hashTable,word1); 
		int[] word1_Txt_count=word_txt_array_count(hashTable,word1);
	
		
		String[] word2_Txt=word_txt_array(hashTable,word2);
		int[] word2_Txt_count=word_txt_array_count(hashTable,word2);
	
		String[] word3_Txt=word_txt_array(hashTable,word3);
        int[] word3_Txt_count=word_txt_array_count(hashTable,word3);
          
        most_relevant_int=0;
        int index=0;
        for(int i=0;i<word1_Txt.length;i++) {
        	int sum=word1_Txt_count[i]+word2_Txt_count[i]+word3_Txt_count[i];        	
        	if(sum>most_relevant_int) {
        		most_relevant_int=sum;
        		index=i;       		  		
        	}          	
        }
        if(word1_Txt[index]!=null)        	
        	most_relevant=word1_Txt[index];		       
        else if(word2_Txt[index]!=null)
        	most_relevant=word2_Txt[index];	
        else
        	most_relevant=word3_Txt[index];
      				
		return most_relevant;
	}
	
	public static void search_words(HashedDictionary<Long, Word> hashTable) throws FileNotFoundException {
		
		@SuppressWarnings("resource")
		String search = new Scanner(new File("search.txt")).useDelimiter("\\Z").next();
		String[] searchwords = search.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+"); //file operitions
		
	    for(int i=0;i<searchwords.length;i++) {
	    	
	    	//Long key=(long) Simple_Summation_Function (searchwords[i]);

	    	Long key=(long) Polynomial_Accumulation_Function (searchwords[i]);
	    	
	    	hashTable.containsword_insearchtxt(key,searchwords[i]);
	 	
	    }
		
		
		
	}
	
	public static long Simple_Summation_Function (String word) {
		long key = (long) 0;
 		for(int i=0;i<word.length();i++) {
 			char character =word.charAt(i); 
 			int ascii = (int) character; 
 			key=key+ascii;
 		}	
 		return key ;
 	}
	
	public static long Polynomial_Accumulation_Function(String word) {		
		long key = 0;
 		for(int i=0;i<word.length();i++) {
 			char character =word.charAt(i); 
 			int ascii = (int) character; 
 			key=(long)((int) (key+(Math.pow(3,word.length()-1-i))*ascii));//n=7 
 		}	
 		return key ;
		
	}
	

	public static String txt_name_partone(int filename_part1_int) {
		String  filename_part1;
		    if(filename_part1_int<10) {
			  filename_part1=	"00"+Integer.toString(filename_part1_int);	  
			}
			else if(filename_part1_int>=10&&filename_part1_int<100) {
				
				filename_part1=	"0"+Integer.toString(filename_part1_int);
			}
			else
				 filename_part1=Integer.toString(filename_part1_int);
		return  filename_part1;
	}
	public static void word_txt_counts_display(HashedDictionary<Long, Word> hashTable,String word) {
		
		//Long key=Simple_Summation_Function (word);
		Long key=(long) Polynomial_Accumulation_Function (word);
		hashTable.getValueobjects(key, word).display_txt_arrays();
		
	}
    public static String[] word_txt_array(HashedDictionary<Long, Word> hashTable,String word) {
		
    	//Long key=Simple_Summation_Function (word);
    	Long key=(long) Polynomial_Accumulation_Function (word);
	
		return 	hashTable.getValueobjects(key, word).txt_array();
	}
    public static int[] word_txt_array_count(HashedDictionary<Long, Word> hashTable,String word) {
		
    	//Long key=Simple_Summation_Function (word);
    	Long key=(long) Polynomial_Accumulation_Function (word);
  	
  		return 	hashTable.getValueobjects(key, word).txt_array_count();
  	}
	

}
