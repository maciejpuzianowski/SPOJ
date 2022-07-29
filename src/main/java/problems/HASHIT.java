package problems;

import java.util.*;
import java.util.stream.Collectors;

public class HASHIT {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        //reading input
        int cases = 0;
        if(sc.hasNextLine()){
            cases = Integer.parseInt(sc.nextLine());
        }

        for(int i = 0; i < cases; i++){

            //obtaining operations count
            int operations = 0;
            if(sc.hasNextLine()){
                operations = Integer.parseInt(sc.nextLine());
            }

            var hh = new HashHelper();

            for(int k = 0; k < operations; k++){
                String s = sc.nextLine();

                //adding operation to the table
                if(s.startsWith("ADD:")){
                    var split = s.split(":");
                    if(split.length > 1) hh.add(split[1]);
                    else hh.add("");
                }

                //deleting operation from the table
                else if(s.startsWith("DEL:")){
                    var split = s.split(":");
                    if(split.length > 1) hh.delete(split[1]);
                    else hh.delete("");
                }

                //exception thrown
                else throw new Exception("Wrong input.");
            }

            //printing result
            hh.print();
        }
    }
}

class HashHelper{

    //defining table as List of key value pairs
    List<KeyValuePair<Integer,String>> Table;

    HashHelper(){
        Table = new ArrayList<>();
    }

    public List<KeyValuePair<Integer,String>> getTable(){
        return Table;
    }
    public void add(String key){
        //creating new key value pair for passed string
        var ref = new KeyValuePair<>(hash(key), key);

        //checking whether it already exists in the table
        for(var x: Table){
            if (x.valueEquals(ref)) return;
        }

        //searching for collisions
        for(var x: Table){
            if (x.keyEquals(ref)){

                //handling collisions
                for(int i = 1; i <=19; i++){
                    int newKey = (hash(ref.getValue())+i*i+23*i)%101;
                    if(!keyExists(newKey)){
                        ref.setKey(newKey);
                        Table.add(ref);
                        return;
                    }
                    //insert cannot be performed

                }
                return;
            }
        }

        //if no collision nor already existing object we add
        Table.add(ref);
    }

    private boolean keyExists(int key){
        //checking whether key exists in the table
        for(var vp: Table){
            if(vp.getKey() == key) return true;
        }
        return false;
    }

    public void delete(String key){
        var ref = new KeyValuePair<>(hash(key), key);
        //removing element if it has the same value
        Table.removeIf(x -> x.valueEquals(ref));
    }

    public void print(){
        //printing table`s size
        if(Table.size() != 0) System.out.println(Table.size());

        //sorting table by indices
        Table = Table.stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());

        //printing...
        for(var vp: Table){
            System.out.println(vp.getKey() + ":" + vp.getValue());
        }

        System.out.println();
    }

    /*
    * Hash methods as described in problem.
    * */
    private int hash(String key){
        return h(key)%101;
    }

    private int h(String key){
        int result = 0;
        for(int i = 0; i < key.length(); i++){
            result += (int)key.charAt(i)*(i+1);
        }
        return result*19;
    }

}


/*
* Simple problems.KeyValuePair class
* */
class KeyValuePair<K, V> implements Map.Entry<K, V>
{
    private K key;
    private V value;

    public KeyValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return this.key;
    }

    public V getValue()
    {
        return this.value;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V setValue(V value)
    {
        return this.value = value;
    }

    public boolean keyEquals(KeyValuePair<K, V> obj){
        return this.key.equals(obj.getKey());
    }

    public boolean valueEquals(KeyValuePair<K, V> obj){
        return this.value.equals(obj.getValue());
    }
}