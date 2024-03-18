
public class Word{
	 String word;
	
	 String [] txt = {"001","002","003","004","005","006","007","008","009","010","011","012","013",
             "014","015","016","017","018","019","020","021","022","023","024","025","026","027","028","029",
             "030","031","032","033","034","035","036","037","038","039","040","041","042","043","044","045",
             "046","047","048","049","050","051","052","053","054","055","056","057","058","059","060","061",
             "062","063","064","065","066","067","068","069","070","071","072","073","074","075","076","077",
             "078","079","080","081","082","083","084","085","086","087","088","089","090","091","092","093",
             "094","095","096","097","098","099","100"};
	 int [] count=new int[100];
	 int numberofentries=0;
	 String[] sortedtxt=new String[100];
	 int[] sortedcount=new int[100];
	 
	 public Word(String n) {
	    	
	    	word=n;
	    	
  	
	    }
    
	    public String getWord() {
			return word;
		}
	   
        public void addtxt(String txtname) {
        	boolean flag=true;
        	for(int i=0;i<100;i++) {
        		if(txt[i]!=null) {
        		String txt1=txt[i]+".txt";
        		if(txt1.equals(txtname)) {
        		   count[i]=count[i]+1;
        		  
        		   break;
        		} 
        		}
        	}
        	
        	
	    	
	    }
        public  void display_txt_arrays() {
	    	
        	for(int i=0;i<txt.length;i++) {
        		if(txt[i]!=null) {
        			System.out.println(txt[i]+".txt"+ " " +count[i]);
        		}
        		
        	}
        }
       
        public String[] txt_array() {
        	String[] temptxt=txt;
       	
       	return   temptxt;
       }
       
       public int[] txt_array_count() {
    	   int[] tempcount=count;
    	   
        	return  tempcount;
        }
        
       
}
        