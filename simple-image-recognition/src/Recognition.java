import java.util.ArrayList;

public class Recognition {
    private ArrayList<String> Input;
    private ArrayList<Picture> Pictures;

    Recognition(){
        Input = new ArrayList<>();
        Pictures = new ArrayList<>();
    }

    public void addInput(String s){
        Input.add(s);
    }

    /*
    *
                    1

                    5

                    5 5
                    x...x
                    .x.x.
                    ..x..
                    .x.x.
                    x...x
                    5 5
                    xxxxx
                    x...x
                    x...x
                    x...x
                    xxxxx
                    6 6
                    ..x...
                    ..x...
                    xxxxxx
                    ..x...
                    ..x...
                    ......
                    5 5
                    .xxx.
                    x...x
                    x...x
                    x...x
                    .xxx.
                    5 5
                    .xxx.
                    .x.x.
                    .xxx.
                    .....
                    .....

    *
    * */

    public void dataToPictures(){
        int testCases = Integer.parseInt(Input.get(0));
        int startLine = 2;
        for(int i = 1; i <= testCases; i++) {
            boolean firstGo = true;
            int numberOfPicturesInTest = 0;
            String[] data = new String[1];
            for (int k = startLine; k < Input.size(); ) {
                if(firstGo) {
                    numberOfPicturesInTest = Integer.parseInt(Input.get(k));
                    firstGo = false;
                    k+=2;
                    continue;
                }
                int counter = 0;
                int width = -1, height = -1;
                while (k < Input.size() && Input.get(k).length() != 1){
                   var ref = Input.get(k);
                   if (ref.length() == 3){
                       if(height != -1){
                           counter = 0;
                       }
                       var dim = ref.split(" ");
                       height = Integer.parseInt(dim[0]);
                       width = Integer.parseInt(dim[1]);
                       data = new String[height];
                   }
                   if (ref.length() == width){
                       data[counter] = ref;
                       if(counter == data.length-1) Pictures.add(new Picture(width, height, data));
                       counter++;
                   }
                   k++;
                }
            }
        }
    }

    public void recognize(){
        for(var p: Pictures) System.out.println(p.whatAmI());
    }
}
