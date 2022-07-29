package challanges;

class KAMIL{public static void main(String[] args){var s=new java.util.Scanner(System.in);int i=0,f=0;for(; i<10; i++){int r=1;for(var c:s.nextLine().toCharArray()){var a=(c=='T'||c=='D'||c=='L'||c=='F')?r*=2:f;}System.out.println(r);}}}