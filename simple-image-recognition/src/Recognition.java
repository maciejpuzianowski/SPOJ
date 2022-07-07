import java.util.ArrayList;

public class Recognition {
    final private ArrayList<String> Input;
    Recognition(){
        Input = new ArrayList<>();
    }

    public void addInput(String s){
        Input.add(s);
    }

    public void start(){
        int testCases = Integer.parseInt(Input.get(0));
        int startLine = 2;
        for(int i = 1; i <= testCases; i++) {
            ArrayList<Picture> pictures = new ArrayList<>();
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
                   if (ref.length() >= 3 && ref.contains(" ")){
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
                       if(counter == data.length-1) pictures.add(new Picture(width, height, data));
                       counter++;
                   }
                   k++;
                }
                if(numberOfPicturesInTest == pictures.size()) {
                    for (Picture x: pictures) System.out.print(x.whatAmI());
                    System.out.println();
                    break;
                }
            }
        }
    }
}
