
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V> {
	private TableEntry<K, V>[] hashTable; 
	private int numberOfEntries;
	private int locationsUsed; 
	private static final int DEFAULT_SIZE = 2477; 
	private static final double MAX_LOAD_FACTOR = 0.5;
    private int collision_count;

	public HashedDictionary() {
		this(DEFAULT_SIZE); 
	} 

	@SuppressWarnings("unchecked")
	public HashedDictionary(int tableSize) {
		int primeSize = getNextPrime(tableSize);
		hashTable = new TableEntry[primeSize];
		numberOfEntries = 0;
		locationsUsed = 0;
	}

	public boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i <= num / 2; i++) {
			if ((num % i) == 0) {
				prime = false;
				break;
			}
		}
		return prime;
	}

	public int getNextPrime(int num) {
		if (num <= 1)
            return 2;
		else if(isPrime(num))
			return num;
        boolean found = false;   
        while (!found)
        {
            num++;     
            if (isPrime(num))
                found = true;
        }     
        return num;
	}

	public V add(K key, V value) {
		V oldValue; 
		
		if (isHashTableTooFull())
			rehash();
		int index = getHashIndex(key);
		index = probe(index, key,value);

		if ((hashTable[index] == null) || hashTable[index].isRemoved()) { //  if not inside hashtable create
			hashTable[index] = new TableEntry<K, V>(key, value);
			
			numberOfEntries++;
			locationsUsed++;
			oldValue = null;
		} else { 
			oldValue=hashTable[index].getValue();
			
		} 
		return oldValue;
	}
	

	private int getHashIndex(K key) { //simple hashing 
		int hashIndex = key.hashCode() % hashTable.length;
		if (hashIndex < 0)
			hashIndex = hashIndex + hashTable.length;
		return hashIndex;
	}

	public boolean isHashTableTooFull() {
		double load_factor = (double)locationsUsed / (double)hashTable.length;

		if (load_factor >= MAX_LOAD_FACTOR) {
            
               return true;
		
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	public void rehash() {
		TableEntry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(2 * oldSize);
		hashTable = new TableEntry[newSize]; 
		numberOfEntries = 0; 
		locationsUsed = 0;
		
		for (int index = 0; index < oldSize; index++) {
			if ((oldTable[index] != null) && oldTable[index].isIn())
				add(oldTable[index].getKey(), oldTable[index].getValue());
		}
	}
	private int Doublehashing(K key,int k) {
		int result=0;
		int q=31;
		
		result=(int) (k*(q-key.hashCode()%q));
		if (result< 0)
			result = result + hashTable.length;
		
		return result;
	}

	private int probe(int index, K key,V value) {
		boolean found = false;
		int tempindex =index;
		String value_string=((Word) value).getWord();
		int removedStateIndex = -1; 
		int k=1;
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn()) {
				if (key.equals(hashTable[index].getKey())&&value_string.equals(((Word) hashTable[index].getValue()).getWord()))
					found = true; 
				else {
					  
					 index=(tempindex+Doublehashing(key,k)) % hashTable.length; //Double Hashing
					 // index = (index + 1) % hashTable.length; // Hashing
					  k++;
				}
				
					
			} 
			else 
			{
				if (removedStateIndex == -1)
					removedStateIndex = index;
				index = (index + 1) % hashTable.length; 
			} 
		} 
		if (found || (removedStateIndex == -1))
			return index; 
		else
			return removedStateIndex; 
	}

	

	public V remove(K key) {
		V removedValue = null;
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1) { 
			removedValue = hashTable[index].getValue();
			hashTable[index].setToRemoved();
			numberOfEntries--;
		} 
		return removedValue;
	}

	//Follows the probe sequence that begins at index (key’s hash index) and returns either the index
	//of the entry containing key or -1, if no such entry exists.
	private int locate(int index, K key) {
		boolean found = false;
		int tempindex =index;
		while (!found && (hashTable[index] != null)) {
			 if ( hashTable[index].isIn() && 
			     key.equals(hashTable[index].getKey()) )
			    found = true; // Key found
			    else // Follow probe sequence
			    index = (tempindex + 1) % hashTable.length; // Linear probing
		} 
		int result = -1;
		if (found)
			result = index;
		return result;
	}
	
	private int locateobjects(int index, K key,String word) {//two word have same key so we have to check the word
		boolean found = false;
		int tempindex =index;
		int k=1;
		while (!found && (hashTable[index] != null)) {
			 if ( hashTable[index].isIn() && 
			     key.equals(hashTable[index].getKey()) &&word.equals(((Word) hashTable[index].getValue()).getWord()))
			    found = true; // Key found
			    else {// Follow probe sequence
			    	//index = (index + 1) % hashTable.length; // Linear probing
			    	index=(tempindex+Doublehashing(key,k)) % hashTable.length; //Double Hashing
			    k++;
			    }
		} 
		int result = -1;
		if (found)
			result = index;
		return result;
	}
	private int locatesearchwords(int index, K key,String word) {//two word have same key so we have to check the word
		boolean found = false;
		int tempindex=index;
		int falsecollision=0;
		int k=1;
		while (!found && (hashTable[index] != null)) {
			 if ( hashTable[index].isIn() && 
			     key.equals(hashTable[index].getKey()) &&word.equals(((Word) hashTable[index].getValue()).getWord()))
			    found = true; // Key found
			    else // Follow probe sequence
			    {
			    	//index = (index + 1) % hashTable.length; // Linear probing
			     index=(tempindex+Doublehashing( key,k)) % hashTable.length; //Double Hashing
			    collision_count++;
			    falsecollision++;
			    k++;
			    }
		} 
		int result = -1;
		
		if (found)
			result = index;
		else
			collision_count=collision_count-falsecollision;//if word doesnt exixst in table then we have to delete falsecollision
		return result;
	}

	public V getValue(K key) {
		V result = null;
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1)
			result = hashTable[index].getValue(); 
		return result;
	}
	
	public V getValueobjects(K key,String word) {// get the value maybe two word have same key so we have to check the word
		V result = null;
		int index = getHashIndex(key);
		index = locateobjects(index, key,word);
		if (index != -1)
			result = hashTable[index].getValue(); 
		return result;
	}
	

	public boolean contains(K key) {
		int index = getHashIndex(key);
		index = locate(index, key);
		if (index != -1)
			return true;
		return false;
	}
	public boolean containsword(K key,String word,String txtname) {//check if word in hashtable if so add txt for this word
		
		int index = getHashIndex(key);
		index = locateobjects(index, key,word);
		
		if (index != -1) {
			((Word) hashTable[index].getValue()).addtxt(txtname);
			
			return true;	
		}
			
		return false;	
		
	
	}
    public boolean containsword_insearchtxt(K key,String word) {//check if word in hashtable  and do nothing
		
		int index = getHashIndex(key);
		index = locatesearchwords(index, key,word);//ayaoa
		
		if (index != -1) {
			
			
			return true;	
		}
			
		return false;	
		
	
	}
	public int collision() {
		
		return collision_count;
	}
	

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public int getSize() {
		return numberOfEntries;
	}

	public void clear() {
		while(getKeyIterator().hasNext()) {
			remove(getKeyIterator().next());		
		}
	}
	
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	private class TableEntry<S, T> {
		private S key;
		private T value;
		private boolean inTable;

		private TableEntry(S key, T value) {
			this.key = key;
			this.value = value;
			inTable = true;
		}

		private S getKey() {
			return key;
		}

		private T getValue() {
			return value;
		}

		private void setValue(T value) {
			this.value = value;
		}

		private boolean isRemoved() {
			return inTable == false;
		}

		private void setToRemoved() {
			inTable = false;
		}

		private void setToIn() {
			inTable = true;
		}

		private boolean isIn() {
			return inTable == true;
		}
	}

	private class KeyIterator implements Iterator<K> {
		private int currentIndex; 
		private int numberLeft; 

		private KeyIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public K next() {
			K result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
	
	private class ValueIterator implements Iterator<V> {
		private int currentIndex; 
		private int numberLeft; 

		private ValueIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		@SuppressWarnings("unchecked")
		public V next() {
			V result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = (V) ((Word)hashTable[currentIndex].getValue()).getWord();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 
        @SuppressWarnings("unused")
		public Boolean findword(String word) {
        	
        	boolean find =false; 
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				if(word.equals(((Word)hashTable[currentIndex].getValue()).getWord()))
				{
					find=true;
					
				}
			    numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return find;
        }
		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
}
