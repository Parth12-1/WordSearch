package sample;



import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

//IMPORTANT
//IMPORTANT
//IMPORTANT
//IMPORTANT
//IMPORTANT
//IMPORTANT
public class Controller {
    Button[][] btn = new Button[10][10]; // the Actual Board in the Game The ACTUAL BOARD

    @FXML
    private AnchorPane aPane;

    @FXML
    private GridPane gPane;

    @FXML
    private ListView Lst1; //List View for the user ot see the remaining words left to find

    @FXML private Label UserChse, ColorInd; //The label that shows what it chosen

    private Boolean[][] Taken = new Boolean[btn.length][btn.length]; //Boolean Array that Looks if a place has a random or a meant code in the game;
    private String[][] Letters = new String[btn.length][btn.length];
    private Boolean[][] LastSearched = new Boolean[btn.length][btn.length];

    private Words X = new Words(); //Pathway to Words Class

    private String User =""; //The String of what the User Chooses.

    private int x1; //The first choice that the User Uses
    private int y1; //The first Y that the User Uses
    private Boolean Random = true; //The Boolen that Asks if you want to use randoms or not
    @FXML private ToggleButton ToggleBTN;
    @FXML private Button Conf, Reset;
    //private int Start;
    //private int End;
    private int howmany;
    private int Topic = 0; //1 = animals  2 = Teams  3 = Country

    @FXML
    private void Toggle(){ // Toggles the code if you want to use randoms or not
        if(Random == true){ //This Says that if the button is clicked, then It should be false if true in the start
            Random = false; //No Randoms
            ToggleBTN.setText("OFF");
        }
        else if(Random == false){ //This Says that if the button is clicked, then It should be true if false in the start
            Random = true; //Yes Randoms
            ToggleBTN.setText("ON");
            //handleButtonAction();
        }
    }


    @FXML
    private void Animals(){
        System.out.println("IN ANIMAL");
        reset();
        System.out.println("IN ANIMAL 2");
        Topic = 1;
        Conf.setVisible(false);
        System.out.println("IN ANIMAL 3");
        Reset.setVisible(false);
        handleButtonAction();
        System.out.println("IN ANIMAL 4");
    }

    @FXML
    private void Teams(){
        reset();
        Topic = 2;
        Conf.setVisible(false);
        Reset.setVisible(false);
        handleButtonAction();
    }

    @FXML
    private void Country(){
        reset();
        Topic = 3;
        Conf.setVisible(false);
        Reset.setVisible(false);
        handleButtonAction();
    }

    private void reset(){
        X.Restart();
        for (int i = 0; i<Taken.length; i++){ //Makes all locations false in the boolean array because it is starting fresh
            for (int j = 0; j<Taken.length; j++){
                Taken[i][j] = false;
                Letters[i][j] = "A";
                LastSearched[i][j] = false;
            }
        }
        User =""; //The String of what the User Chooses.
        howmany =5;
        Topic = 0; //1 = animals  2 = Teams  3 = Country
    }


    private void handleButtonAction() {
        //after adding the grdipane in scenebuilder, modify the fxml manually to eliminate
        // rows and columns
        //Goes y,x not math.
        System.out.println("in hangle");
        for (int i = 0; i<Taken.length; i++){ //Makes all locations false in the boolean array because it is starting fresh
            for (int j = 0; j<Taken.length; j++){
                Taken[i][j] = false;
            }
        }
        System.out.println("ent");
        User = "";
        Lst1.getItems().clear();
        System.out.println("problem nexT?");
        TextInputDialog count = new TextInputDialog();
        count.setTitle("How many?");
        count.setHeaderText("How many letters (max 5)");
        count.setContentText("count: ");
        Optional<String> result = count.showAndWait();
        howmany = Integer.parseInt(result.get());
        //howmany = Integer.parseInt(JOptionPane.showInputDialog("How many Letters (max 5)"));
        System.out.println("problem not?");
        Conf.setVisible(true);
        Reset.setVisible(true);
        UserChse.setText("");
        System.out.println("nextit?");
        for(int i=0; i<btn.length; i++){
            for(int j=0; j<btn.length;j++){
                //Initializing 2D buttons with values i,j
                btn[i][j] = new Button(" "); //Makes All the Buttons //Initializes Each Button With the text or nothing or just a space
                btn[i][j].setPrefSize(250, 250); //Puts a Size
                //Paramters:  object, columns, rows
                gPane.add(btn[i][j], j, i); //Puts the Buttons in the Gpane
                gPane.setVisible(true); //Makes the Buttons visible
            }
        }
        InputWords(); //The Code adds the letter to the Array.
        Randoms();// the code that fills in the other spots randomly.
        gPane.setGridLinesVisible(true); //Puts Grid lines in Gpane
        gPane.setVisible(true); //Makes the Buttons visible
        System.out.println("Almost?");

        EventHandler z = new EventHandler<ActionEvent>()
        {
            @Override//x is rows and y is columns, the opposite of math
            //rows, columns
            public void handle(ActionEvent t) //Clicking a button
            {
                if (User.length() == 0){
                    User = "";
                    UserChse.setText(User);
                    x1 = GridPane.getRowIndex(((Button) t.getSource()));
                    y1 = GridPane.getColumnIndex(((Button) t.getSource()));
                    User += Letters[x1][y1];
                    btn[x1][y1].setStyle("-fx-background-color:lime; -fx-padding:3px");
                    LastSearched[x1][y1] = true;
                    ColorInd.setStyle("-fx-background-color:black; -fx-padding:3px");
                }
                else if(User.length() == 1){
                    int x2 = GridPane.getRowIndex(((Button) t.getSource()));
                    int y2 = GridPane.getColumnIndex(((Button) t.getSource()));
                    int columnlength = y2-y1;// The length from the y2 to the y1
                    int rowlength = x2-x1;//The Length from the x1 to the x2
                    //the Next Code will take the first location and then search through the code until the second location so you would want to make a separate code for if it is horizontal and right, or if it is horizontal and left or if it is vertical and up...
                    if(columnlength != 0 && rowlength == 0){ //If it is horizontal
                        if (columnlength>= 0){
                            for(int i = 1; i<=columnlength; i++){
                                User += Letters[x1][y1+i];
                                btn[x1][y1+i].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                LastSearched[x1][y1+i] = true;
                            }
                        }
                        else{
                            for(int i = -1; i>=columnlength; i--){
                                User += Letters[x1][y1+i];
                                btn[x1][y1+i].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                LastSearched[x1][y1+i] = true;
                            }
                        }
                    }
                    else if(columnlength == 0 && rowlength != 0){ //If it is vertical
                        if (rowlength>= 0){
                            for(int i = 1; i<=rowlength; i++){
                                User += Letters[x1+i][y1];
                                btn[x1+i][y1].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                LastSearched[x1+i][y1] = true;
                            }
                        }
                        else{
                            for(int i = -1; i>=rowlength; i--){
                                User += Letters[x1+i][y1];
                                btn[x1+i][y1].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                //btn[x1+i][y1].setBackground(null); code for a defult background
                                LastSearched[x1+i][y1] = true;
                            }
                        }
                    }
                    else if(columnlength != 0 && rowlength != 0){ //If it is diagonal DO LATER
                        if (rowlength>= 0 && columnlength >=0){ //Right and Up, row and column are greater than 0 //Quadrent4
                            for(int i = 1; i<=rowlength; i++){
                                User += Letters[x1+i][y1+i];
                                btn[x1+i][y1+i].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                LastSearched[x1+i][y1+i] = true;
                            }
                        }
                        else if(rowlength< 0 && columnlength <0){ //Left and down, row and column are less than 0 //Quadrent2
                            for(int i = -1; i>=rowlength; i--){
                                User += Letters[x1+i][y1+i];
                                btn[x1+i][y1+i].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                LastSearched[x1+i][y1+i] = true;
                            }
                        }
                        else if(rowlength>=0 && columnlength<0){ //Left and Up, Columns are less than 0 and rows are greater than 0 //Quadrent3
                            for(int i = 1; i<=rowlength; i++){
                                User += Letters[x1+i][y1-i];
                                btn[x1+i][y1-i].setStyle("-fx-background-color:lime; -fx-padding:3px"); //padding supposed to be 3px
                                LastSearched[x1+i][y1-i] = true;
                            }
                        }
                        else if(rowlength<0 && columnlength>=0){ //Right and Down, Columns are greater than 0 and rows are less than 0 //Quadrent1
                            for(int i = 1; i<=columnlength; i++){
                                User += Letters[x1-i][y1+i];
                                btn[x1-i][y1+i].setStyle("-fx-background-color:lime; -fx-padding:3px");
                                LastSearched[x1-i][y1+i] = true;
                            }
                        }
                    }
                }
                UserChse.setText(User); //Shows the User What he Selected
            }
        };
        for(int i=0; i<btn.length; i++){ //Generating buttons
            for(int j=0; j<btn.length;j++){
                btn[i][j].setOnAction(z);//Puts a Event Handler on each button
                btn[i][j].setStyle("-fx-background-color:White; -fx-padding:3px");
            }
        }
        System.out.println("done?");
    }


    @FXML
    private void Confirm(){ //Confirms the Seletion to check the List of words that the user needs to find
        Boolean Checked = X.Check(User); //The Boolean That Checks the Word, if the Word is one of the Words, Then it Run a code to eliminate the Words from the List the user Checks from
        if (Checked == true){ //If a Word Randomly becomes this, then it would work for both the word the program put and the random word,
            for (int i = 0; i<Lst1.getItems().size();i++){ //Searches through Each word in the List to Find the Word that is the same as the USERs word
                if (Lst1.getItems().get(i).equals(User)){
                    Lst1.getItems().remove(i);
                    ColorInd.setStyle("-fx-background-color:Green; -fx-padding:3px");
                    for (int x = 0; x<btn.length; x++){
                        for (int j = 0; j<btn.length; j++){
                            if (LastSearched[x][j] == true){
                                btn[x][j].setStyle("-fx-background-color:Yellow; -fx-padding:3px");
                            }
                        }
                    }
                }
            }
        }
        else{
            ColorInd.setStyle("-fx-background-color:Red; -fx-padding:3px");
            for (int x = 0; x<btn.length; x++){
                for (int j = 0; j<btn.length; j++){
                    if (LastSearched[x][j] == true){
                        btn[x][j].setStyle("-fx-background-color:White; -fx-padding:3px");
                    }
//                            String background = btn[x][j].getBackground();
//                            if(String.format("Yellow").equals(btn[x][j].getBackground())){
//                            }
//                            else{
//                                btn[x][j].setStyle("-fx-background-color:White; -fx-padding:3px");
//                            }

                }
            }
        }
        for (int x = 0; x<btn.length; x++){ //Supposed to add the random letters
            for (int j = 0; j<btn.length; j++){
                    LastSearched[x][j] = false;
            }
        }
        User = ""; //Rests the Users Choice
        UserChse.setText(User);
    }


    @FXML
    private void ResetChoice(){ //Resets the Set Words
        User = ""; //Resets the Users Choice
        UserChse.setText(User);
//        Direction = -1; //None of the Directions, it only goes up to 4
//        Newdirection = -1;
//        Oldx = -1;
//        Oldy = -1;
        for (int x = 0; x<btn.length; x++){
            for (int j = 0; j<btn.length; j++){
                if (LastSearched[x][j] == true){
                    btn[x][j].setStyle("-fx-background-color:White; -fx-padding:3px");
                }
            }
        }
    }


    private void Randoms(){
        if(Random == true){ //Fills in Other Spots with Random Letters
            for (int i = 0; i<btn.length; i++){ //Supposed to add the random letters
                for (int j = 0; j<btn.length; j++){
                    if (Taken[i][j] == false){
                        String Random = Character.toString((char)Math.floor(Math.random()*(26)+65)); //Generates a Random Letter
                        btn[i][j].setText(Random); // Puts the Random Letter in the Array.
                        Letters[i][j] = Random;  //Puts the Letter into the Letter Array
                    }
                }
            }
        }
    }


    private void InputWords(){ //Gets the Words that are going to be searched
        for (int x = 0; x<btn.length; x++){ //Supposed to add the random letters
            for (int j = 0; j<btn.length; j++){
                LastSearched[x][j] = false;
            }
        }
        for (int i = 0; i<Taken.length; i++){ //Makes all locations false in the boolean array because it is starting fresh
            for (int j = 0; j<Taken.length; j++){
                Taken[i][j] = false;
            }
        }
        for(int z = 0; z<howmany; z++) {
            putinword();
        }
    }


    private void putinword(){
        String Choice = X.Word(Topic); //GEts the Word the Computer wants to put
        Lst1.getItems().add(Choice); //Adds the word to the LST the user needs to find from
        //Put the Rest of this function in another function
        putin(Choice);
    }


    private void putin(String Choice){
        int locationx;
        int locationy;
        int direction = (int) Math.floor(Math.random() * 3); // o = horizontal, 1 = vertical, 2 = diagonal;
        boolean yesitcan = true;
        if (direction == 0){ //Horizontal
            locationy = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1)); //Find a random location for the word
            locationx = (int) Math.floor(Math.random() * (btn.length - 2)); //Finds a random location for the word
            locationy = (int)Math.floor(Math.random()*10); //Find a random location for the word
            locationx = (int)Math.floor(Math.random()*(btn.length-Choice.length()+1)); //Finds a random location for the word
            for (int i = 0; i < Choice.length(); i++){ //code that checks of that spot is open
                String letter;
                if (i + 1 < Choice.length()) {
                    letter = Choice.substring(i, i + 1);
                }
                else {
                    letter = Choice.substring(i);
                }
                if(Taken[locationy][locationx + i] == true && !(btn[locationy][locationx + i].getText().equals(letter))){ //done
                    yesitcan = false;
                }
                if(Taken[locationy][locationx + i] == true && (btn[locationy][locationx + i].getText().equals(letter))){ //done
                    yesitcan = true;
                }
            }
            if (yesitcan == true){
                for (int i = 0; i < Choice.length(); i++) { //Puts each SubString fo the word into the Game and adds
                    String letter;
                    if (i + 1 < Choice.length()) {
                        letter = Choice.substring(i, i + 1);
                    }
                    else {
                        letter = Choice.substring(i);
                    }
                    btn[locationy][locationx + i].setText(letter); //Sets the buttons location
                    Letters[locationy][locationx + i] = letter;  //Puts the Letter into the Letter Array
                    Taken[locationy][locationx + i] = true; //Makes the Boolean Location say that that location is taken or true for that location.
                }
            }
            else{
                putin(Choice);
            }
        }
        else if (direction == 1) {
            locationy = (int)Math.floor(Math.random()*(btn.length-Choice.length()+1)); //Finds a random location for the word
            locationx = (int)Math.floor(Math.random()*10); //Find a random location for the word
            for (int i = 0; i < Choice.length(); i++){ //code that checks of that spot is open
                String letter;
                if (i + 1 < Choice.length()) {
                    letter = Choice.substring(i, i + 1);
                }
                else {
                    letter = Choice.substring(i);
                }
                if(Taken[locationy + i][locationx] == true && !(btn[locationy+i][locationx].getText().equals(letter))){ //Think about having it run after u get the letter so let the Choice code run, but only change the letters if Taken == true.
                    yesitcan = false;
                }
                else if (Taken[locationy + i][locationx] == true && (btn[locationy+i][locationx].getText().equals(letter))){
                    yesitcan = true;
                }
            }
            if (yesitcan == true) {
                for (int i = 0; i < Choice.length(); i++) { //Puts each SubString fo the word into the Game and adds
                    String letter;
                    if (i + 1 < Choice.length()) {
                        letter = Choice.substring(i, i + 1);
                    }
                    else {
                        letter = Choice.substring(i);
                    }
                    btn[locationy + i][locationx].setText(letter); //Sets the buttons location
                    Letters[locationy + i][locationx] = letter;  //Puts the Letter into the Letter Array
                    Taken[locationy + i][locationx] = true; //Makes the Boolean Location say that that location is taken or true for that location.
                }
            }
            else{
                putin(Choice);
            }
        }
        else if (direction == 2) {
            int temp = (int) Math.floor((Math.random() * 3) + 1); //1 = Quadrent 1, 2 = Q2, 3 = Q3, 4 = Q4
            if (temp == 1) {
                locationy = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1) + Choice.length() - 1); //Find a random location for the word
                locationx = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1)); //Finds a random location for the word
                for (int i = 0; i < Choice.length(); i++){ //code that checks of that spot is open
                    String letter;
                    if (i + 1 < Choice.length()) {
                        letter = Choice.substring(i, i + 1);
                    } else {
                        letter = Choice.substring(i);
                    }
                    if(Taken[locationy - i][locationx + i] == true && !(btn[locationy - i][locationx + i].getText().equals(letter))){
                        yesitcan = false;
                    }
                    if(Taken[locationy - i][locationx + i] == true && (btn[locationy - i][locationx + i].getText().equals(letter))){
                        yesitcan = true;
                    }
                }
                if (yesitcan == true) {
                    for (int i = 0; i < Choice.length(); i++) { //Puts each SubString fo the word into the Game and adds
                        String letter;
                        if (i + 1 < Choice.length()) {
                            letter = Choice.substring(i, i + 1);
                        } else {
                            letter = Choice.substring(i);
                        }
                        btn[locationy - i][locationx + i].setText(letter); //Sets the buttons location
                        Letters[locationy - i][locationx + i] = letter;  //Puts the Letter into the Letter Array
                        Taken[locationy - i][locationx + i] = true; //Makes the Boolean Location say that that location is taken or true for that location.
                    }
                }
                else{
                    putin(Choice);
                }
            }
            else if (temp == 2) {
                locationy = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1) + Choice.length() - 1); //Finds a random location for the word
                locationx = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1) + Choice.length() - 1); //Find a random location for the word
                for (int i = 0; i < Choice.length(); i++){ //code that checks of that spot is open
                    String letter;
                    if (i + 1 < Choice.length()) {
                        letter = Choice.substring(i, i + 1);
                    } else {
                        letter = Choice.substring(i);
                    }
                    if(Taken[locationy - i][locationx - i] == true && !(btn[locationy - i][locationx - i].getText().equals(letter))){
                        yesitcan = false;
                    }
                    if(Taken[locationy - i][locationx - i] == true && (btn[locationy - i][locationx - i].getText().equals(letter))){
                        yesitcan = true;
                    }
                }
                if (yesitcan == true) {
                    for (int i = 0; i < Choice.length(); i++) { //Puts each SubString fo the word into the Game and adds
                        String letter;
                        if (i + 1 < Choice.length()) {
                            letter = Choice.substring(i, i + 1);
                        } else {
                            letter = Choice.substring(i);
                        }
                        btn[locationy - i][locationx - i].setText(letter); //Sets the buttons location
                        Letters[locationy - i][locationx - i] = letter;  //Puts the Letter into the Letter Array
                        Taken[locationy - i][locationx - i] = true; //Makes the Boolean Location say that that location is taken or true for that location.
                    }
                }
                else{
                    putin(Choice);
                }
            }
            else if (temp == 3) {
                locationy = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1)); //Finds a random location for the word
                locationx = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1) + Choice.length() - 1); //Find a random location for the word
                for (int i = 0; i < Choice.length(); i++){ //code that checks of that spot is open
                    String letter;
                    if (i + 1 < Choice.length()) {
                        letter = Choice.substring(i, i + 1);
                    } else {
                        letter = Choice.substring(i);
                    }
                    if(Taken[locationy + i][locationx - i] == true && !(btn[locationy + i][locationx - i].getText().equals(letter))){
                        yesitcan = false;
                    }
                    if(Taken[locationy + i][locationx - i] == true && (btn[locationy + i][locationx - i].getText().equals(letter))){
                        yesitcan = true;
                    }
                }
                if (yesitcan == true) {
                    for (int i = 0; i < Choice.length(); i++) { //Puts each SubString fo the word into the Game and adds
                        String letter;
                        if (i + 1 < Choice.length()) {
                            letter = Choice.substring(i, i + 1);
                        } else {
                            letter = Choice.substring(i);
                        }
                        btn[locationy + i][locationx - i].setText(letter); //Sets the buttons location
                        Letters[locationy + i][locationx - i] = letter;  //Puts the Letter into the Letter Array
                        Taken[locationy + i][locationx - i] = true; //Makes the Boolean Location say that that location is taken or true for that location.
                    }
                }
                else{
                    putin(Choice);
                }
            }
            else if (temp == 4) {
                locationy = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1)); //Finds a random location for the word
                locationx = (int) Math.floor(Math.random() * (btn.length - Choice.length() + 1)); //Find a random location for the word
                for (int i = 0; i < Choice.length(); i++) { //code that checks of that spot is open
                    String letter;
                    if (i + 1 < Choice.length()) {
                        letter = Choice.substring(i, i + 1);
                    } else {
                        letter = Choice.substring(i);
                    }
                    if (Taken[locationy + i][locationx - i] == true && !(btn[locationy + i][locationx + i].getText().equals(letter))){
                        yesitcan = false;
                    }
                    if (Taken[locationy + i][locationx - i] == true && (btn[locationy + i][locationx + i].getText().equals(letter))){
                        yesitcan = true;
                    }
                }
                if (yesitcan == true) {
                    for (int i = 0; i < Choice.length(); i++) { //Puts each SubString fo the word into the Game and adds
                        String letter;
                        if (i + 1 < Choice.length()) {
                            letter = Choice.substring(i, i + 1);
                        } else {
                            letter = Choice.substring(i);
                        }
                        btn[locationy + i][locationx + i].setText(letter); //Sets the buttons location
                        Letters[locationy + i][locationx + i] = letter;  //Puts the Letter into the Letter Array
                        Taken[locationy + i][locationx + i] = true; //Makes the Boolean Location say that that location is taken or true for that location.
                    }
                }
            }
            else{
                putin(Choice);
            }
        } //Direction
    }
}
