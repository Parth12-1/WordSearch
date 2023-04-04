package sample;

import java.util.ArrayList;

public class Words {
    ArrayList<String> Start= new ArrayList<>();
    ArrayList<String> Chosen = new ArrayList<>();

    public String Word(int Topic){ // the Arraylist will have words that will be used in the Word Search, RN im just using CAT.  Uses the TextField Later On
        if(Topic == 1){
            return Animals();
        }
        else if(Topic == 2){
            return Teams();
        }
        else{
            return Countries();
        }
    }

    private String Animals(){
        Start.add("CAT");
        Start.add("SNAKE");
        Start.add("LIZARD");
        Start.add("DOG");
        Start.add("SPIDER");
        Start.add("RAT");
        Start.add("LION");
        Start.add("SHARK");
        Start.add("TIGER");
        int Choice = (int)Math.floor(Math.random()*Start.size());
        Chosen.add(Start.get(Choice));
        Start.remove(Choice);
        return Chosen.get(Chosen.size()-1);
    }

    private String Teams(){
        Start.add("BULLS");
        Start.add("BEARS");
        Start.add("CAVALIERS");
        Start.add("RAVENS");
        Start.add("CUBS");
        Start.add("HEAT");
        Start.add("LAKERS");
        Start.add("ROCKETS");
        Start.add("PATRIOTS");
        int Choice = (int)Math.floor(Math.random()*Start.size());
        Chosen.add(Start.get(Choice));
        Start.remove(Choice);
        return Chosen.get(Chosen.size()-1);
    }

    private String Countries(){
        Start.add("USA");
        Start.add("CANADA");
        Start.add("MEXICO");
        Start.add("UK");
        Start.add("RUSSIA");
        Start.add("INDIA");
        Start.add("GERMANY");
        Start.add("FRANCE");
        Start.add("WAKANDA");
        int Choice = (int)Math.floor(Math.random()*Start.size());
        Chosen.add(Start.get(Choice));
        Start.remove(Choice);
        return Chosen.get(Chosen.size()-1);
    }

    public Boolean Check(String Search){
        Boolean Find = false;
        for (int i = 0; i<Chosen.size(); i++){
            if(Search.equals(Chosen.get(i))){
                Find = true;
                Chosen.remove(i);
                break;
            }
        }
        return Find;
    }

    public void Restart(){
        Start.clear();
        Chosen.clear();
    }


}
