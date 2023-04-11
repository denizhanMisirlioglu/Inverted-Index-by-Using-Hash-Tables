public class HashTable<K,V> {

    private HashEntry[] table;
    public int collisionCount=0;
    private int count=0;


    public HashTable() {
        table = new HashEntry[997];
        for (int i = 0; i < table.length; i++)
            table[i] = null;
    }

    private int pHashFunction(K key) {//Polynomial Accumulation Function (PAF)
        int hashValue=0;
        String word=(String)key;
        word=word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {// Create a hash function
            hashValue+=Math.pow(31,word.length()-1-i)*(word.charAt(i)-96);//31 as prime number
        }
        hashValue%=table.length;
        return Math.abs(hashValue);
    }

    private int sHashFunction(K key){//Simple summation function(SSF)
        int hashValue=0;
        String word=(String)key;
        word=word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            hashValue+=word.charAt(i)-96;
        }
        hashValue%= table.length;
        return Math.abs(hashValue);
    }

    public void valueGet(K key) {//Get value from hashmap with key
        if (!isThere(key)){
            System.out.println("Not Found");
        }
        else{
            HashEntry word=table[findKey(key)];
            System.out.println("Search: "+word.getKey());
            System.out.println(word.getList().size()+" documents found.");
            for (int i = 0; i < word.getList().size(); i++) {
                Node node=(Node) word.getList().get(i);
                System.out.println(node.getInfo());
            }
        }

    }

    //LINEAR PROBING
    //DON'T FORGET TO CHANGE THE findKey FUNCTION TO LINEAR PROBING
    public void put(K key, V node) {//Filling Hashmap with Linear Probing
        if (!isThere(key)){
            /*
            //WHEN LINEAR PROBING USING, OPEN THIS COMMAND LINES (57-66 region choose one of load if condition  AS YOUR LOAD FACTOR PREFERENCE
            // Load factors to %50 resizing of hash table
            if (count>=(table.length*0.5)){
                reSize();
            }
            // Load factors to %80 resizing of hash table
//            if (count>=(table.length*0.8)){
//                reSize();
//            }
*/
            //WHEN DOUBLE HASHING USING, OPEN THIS COMMAND LINES AS YOUR LOAD FACTOR PREFERENCE

            // Load factors to %50 resizing of hash table
//            if (count>=(table.length*0.5)){
//                reSizeDouble();
//            }
            // Load factors to %80 resizing of hash table
            if (count>=(table.length*0.8)){
                reSizeDouble();
            }

            //-----------------------------Polynomial Accumulation Function-----------------------------//
            int hash = pHashFunction(key);// Calculate hash value
            //-----------------------------Simple Summation Function----------------------------------//
            //int hash = sHashFunction(key);//Calculate Hash value

            HashEntry entryToInsert=new HashEntry(key);
            entryToInsert.getList().add(node);
            //LINEAR PROBING
/*
            if (table[hash]!=null){
                int px=0;
                while (table[hash]!=null) {
                    hash = (pHashFunction(key) + px) % table.length;
                    px++;
                    collisionCount++;
                }
                table[hash]=entryToInsert;
                count++;
            }
*/
            //DOUBLE HASHING

            if (table[hash]!=null){
                int secondHash;
                int j=0;
                while (table[hash]!=null){
                    secondHash=13-(sHashFunction(key)%13);//I cant find anything to put here
                    if (secondHash==0)
                        secondHash=1;
                    hash=(hash+(j*secondHash))%table.length;
                    j++;
                    collisionCount++;
                }
                table[hash]=entryToInsert;
                count++;
            }

            else {
                table[hash] = entryToInsert;
                count++;
            }


        }else {
            Node node2 = (Node) node;
            boolean flag=false;
            for (Object n: table[findKey(key)].getList()) {
                Node node1=(Node) n;
                if (!node1.getName().equals(node2.getName()))
                    flag=true;
                else
                    flag=false;
            }
            if (flag)
                table[findKey(key)].getList().add(node);

        }
    }

    public void reSize(){//To make double length of table
        HashEntry[] temp;
        temp=table;
        table=new HashEntry[table.length*2];
        for (int i = 0; i < temp.length; i++) {
            if (temp[i]!=null) {
                int hash=pHashFunction((K)temp[i].getKey());
                //int hash=sHashFunction((K)temp[i].getKey());
                if (table[hash]!=null){
                    //Linear Probing

                    int px=0;
                    while (table[hash]!=null) {
                        hash = (pHashFunction((K)temp[i].getKey()) + px) % table.length;
                        px++;
                    }
                    table[hash]=temp[i];

                }
                else {
                    table[hash] = temp[i];
                }
            }
        }
    }

    public void reSizeDouble(){//To make double length of table
        HashEntry[] temp;
        temp=table;
        int nextPrime=table.length*2;
        nextPrime++;
        for (int i = 2; i < nextPrime; i++) {
            if(nextPrime%i == 0) {
                nextPrime++;
                i=2;
            } else {
                continue;
            }
        }
        // Reference https://www.tutorialspoint.com/java-program-to-check-for-prime-and-find-next-prime-in-java
        table=new HashEntry[nextPrime];
        for (int i = 0; i < temp.length; i++) {
            if (temp[i]!=null) {
                int hash=pHashFunction((K)temp[i].getKey());
                //int hash=sHashFunction((K)temp[i].getKey());
                if (table[hash] != null) {
                    int secondHash;
                    int j = 0;
                    while (table[hash] != null) {
                        secondHash = 13 - (sHashFunction((K)temp[i].getKey()) % 13);//I cant find anything to put here
                        if (secondHash == 0)
                            secondHash = 1;
                        hash = (hash + (j * secondHash)) % table.length;
                        j++;
                    }
                    table[hash] = temp[i];
                } else {
                    table[hash] = temp[i];

                }
            }
        }
    }

    public boolean isThere(K key){//Avoid adding same word
        boolean flag;
        if (findKey(key)!=-1)
            flag=true;
        else
            flag=false;
        return flag;
    }

    public int findKey(K key){
        //Search for double hashing

        int j=0;
        int index=pHashFunction(key);
        //int index=sHashFunction(key);
        int actualIndex=13-(sHashFunction(key)%13);
        while (table[index]!=null&&!table[index].getKey().toString().equals(key)){
            if (actualIndex==0)
                actualIndex=1;
            index=(index+j*actualIndex)%table.length;
            j++;
        }
        if (table[index]!=null&&table[index].getKey().toString().equals(key))
            return index;
        else
            return -1;

/*
        //Search for LINEAR PROBING
        int index=pHashFunction(key);
        //int index=sHashFunction(key);
        int i=0;
        while (table[index]!=null&&!table[index].getKey().toString().equals(key)) {
            index=(pHashFunction(key)+i)%table.length;
            i++;
        }
        if (table[index]!=null&&table[index].getKey().toString().equals(key))
            return index;
        else
            return -1;
*/
    }

    public void remove(K key){
        if (!isThere(key))
            return;
        //LINEAR PROBING REMOVE
        int hash=pHashFunction(key);
        while (table[hash]!=null&&!table[hash].getKey().toString().equals(key)) {
            hash=(hash+1)%table.length;
        }
        table[hash]=null;
        //DOUBLE HASHING REMOVE
        int j=0;
        int index=pHashFunction(key);
        int actualIndex=13-(sHashFunction(key)%13);
        while (table[index]!=null&&!table[index].getKey().toString().equals(key)){
            if (actualIndex==0)
                actualIndex=1;
            index=(index+j*actualIndex)%table.length;
            j++;
        }
        table[index]=null;
    }
}
