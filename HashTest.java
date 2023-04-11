import java.io.File;  // Imported the File class for file operations
import java.io.FileNotFoundException;  // Imported this class to handle errors
import java.util.*;


public class HashTest<K, V> {


    public static void main(String[] args) {
        String DELIMITERS = "[-+=" +
                " " +        //space
                "\r\n" +    //carriage return line fit
                "1234567890" + //numbers
                "’'\"" +       // apostrophe
                "(){}<>\\[\\]" + // brackets
                ":" +        // colon
                "," +        // comma
                "‒–—―" +     // dashes
                "…" +        // ellipsis
                "!" +        // exclamation mark
                "." +        // full stop/period
                "«»" +       // guillemets
                "-‐" +       // hyphen
                "?" +        // question mark
                "‘’“”" +     // quotation marks
                ";" +        // semicolon
                "/" +        // slash/stroke
                "⁄" +        // solidus
                "␠" +        // space?
                "·" +        // interpunct
                "&" +        // ampersand
                "@" +        // at sign
                "*" +        // asterisk
                "\\" +       // backslash
                "•" +        // bullet
                "^" +        // caret
                "¤¢$€£¥₩₪" + // currency
                "†‡" +       // dagger
                "°" +        // degree
                "¡" +        // inverted exclamation point
                "¿" +        // inverted question mark
                "¬" +        // negation
                "#" +        // number sign (hashtag)
                "№" +        // numero sign ()
                "%‰‱" +      // percent and related signs
                "¶" +        // pilcrow
                "′" +        // prime
                "§" +        // section sign
                "~" +        // tilde/swung dash
                "¨" +        // umlaut/diaeresis
                "_" +        // underscore/understrike
                "|¦" +       // vertical/pipe/broken bar
                "⁂" +        // asterism
                "☞" +        // index/fist
                "∴" +        // therefore sign
                "‽" +        // interrobang
                "※" +          // reference mark
                "]";
        long startTime=0;
        HashTable hashTable=new HashTable();
        try {
            String word="";
            String stop_word="";
            String[] splitted=null;
            ArrayList<String> splitClean=new ArrayList<>();
            File stopFile =new File("stop_words_en.txt");
            Scanner myReader2 = new Scanner(stopFile);
            while (myReader2.hasNextLine()) {
                stop_word += myReader2.nextLine();
                stop_word +=" ";
            }
            stop_word=stop_word.toLowerCase().replaceAll("  "," ");
            String[] stopWords=stop_word.split(" ");
            File file=new File("bbc");
            File[] fileArray=file.listFiles();
            startTime=System.currentTimeMillis();
            for (File f:fileArray){
                File[] file2=f.listFiles();
                for (File f2:file2){
                    word="";
                    Scanner myReader = new Scanner(f2);
                    while (myReader.hasNextLine()) {
                        word += myReader.nextLine();
                        word+=" ";
                    }
                    myReader.close();
                    word=word.toLowerCase();
                    splitted = word.split(DELIMITERS);
                    for (String s:splitted){
                        if (s.hashCode()!=0){
                            splitClean.add(s);
                        }
                    }
                    splitClean=removeStopWords(splitClean,stopWords); // removing stop words
                    while (splitClean.size()>0){
                        if (splitClean.get(0)!=null){
                            int count=0;
                            for (String s1:splitClean){
                                if (s1!=null&&splitClean.get(0).equals(s1)){
                                    count++;
                                }
                            }
                            Node node=new Node(f2.getName(), count,f.getName());
                            hashTable.put(splitClean.get(0),node); // putting Clean data to hashmap

                            System.out.println(node.getName()+"->"+f.getName());//This code is for observing that  program isn't in infinite loop since Linear P. takes so long.
                            splitClean.removeAll(Collections.singleton(splitClean.get(0)));//Prevent extra loops for same words
                        }
                    }
                }
            }
            long indexingTime=System.currentTimeMillis()-startTime;
            try {
                Scanner scanner=new Scanner(System.in);

                System.out.print("Please enter a word for search:");
                String searchWord=scanner.nextLine();
                searchWord=searchWord.toLowerCase();
                long startSearchtTime=System.nanoTime();
                hashTable.valueGet(searchWord);
                long searchTime=System.nanoTime()-startSearchtTime;
                System.out.println("Collision count:"+hashTable.collisionCount);
                System.out.println("Indexing Time:"+indexingTime);
                System.out.println("Search Time:"+searchTime);
            }catch (Exception e){
                System.out.println("Wrong input");
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Wrong path.");
            e.printStackTrace();
        }

    }

    public static ArrayList<String> removeStopWords(ArrayList<String> text, String[] stopWords){ // removing stop words
        List<String> stopWord=new ArrayList<>();
        stopWord.addAll(Arrays.asList(stopWords));
        text.removeAll(stopWord);
        return text;
    }
}
