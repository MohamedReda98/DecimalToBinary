package sample;
//import wanted libarries

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
    // initial our tools
    // buttons
    Button bit32 = new Button(" 32 bit");
    Button bit64 = new Button(" 64 bit");
    // textboxe
    Text show = new Text(400, 300, "your number");
    //  lbels
    Label welcome = new Label(" WELCOME  \n Floating point representation \n Please enter your decimal number \n Then pres one button ");
    Label alert = new Label(" alert ");
    Label ldec = new Label(" Enter Decimal Number ");
    // text field
    TextField decimal = new TextField();
    // panes
    HBox hBox = new HBox(20);
    VBox vBox1 = new VBox(20);
    VBox vBox2 = new VBox(20);
    // color and fonts
    Font f1 = new Font(16);
    // variables
    static double savenum=0.0; // to save number static and get from anywhere
    static int signnum=0; //to store sign number static
    static String int2bin ; //string to store integer part of decimal number
    static String fr2bin ; //string to store friction part of decimal number
    static int exp ; // to save exponential num static and get from anywhere


    @Override
    public void start(Stage primaryStage) throws Exception{
        // start our javafx gui
        // add objects to panes
        hBox.getChildren().addAll(welcome);
        vBox1.getChildren().addAll(ldec,bit32,alert);
        vBox2.getChildren().addAll(decimal,bit64,show);
        BorderPane pane = new BorderPane();
        // put boxes position in panes
        pane.setTop(hBox);
        pane.setLeft(vBox1);
        pane.setRight(vBox2);
        pane.setPadding(new Insets(40)); // padding from frame
        // align center view
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        // customize objects and fonts
        welcome.setTextAlignment(TextAlignment.CENTER);
        show.setFont(f1);
        ldec.setFont(f1);
        welcome.setFont(f1);
        welcome.setTextFill(Color.RED);
        alert.setFont(f1);
        alert.setTextFill(Color.BLUE);

//      my buttons action
        // to covert into 32 bit
        bit32.setOnAction(event -> {
            try {
                Double decnum =Double.parseDouble(decimal.getText());//check exist or null and if it is a valid enter
                savenum=decnum;//if valid save to another static variable
                SignNum(); // to check sign
                Decimal2Binary32(savenum); // use this method to convert into binary and exp num
                String expbias = Integer.toBinaryString(127+exp); // to get exponential number as 2^(8-1) -1
                int2bin=int2bin.substring(1);// to delete first element "1"
                show.setText(signnum+expbias+int2bin+fr2bin); // Final number in conversion
                alert.setText("Your Decimal Number : "+savenum+"\n IN 32 BIT IS : " ); // to show what is the output
            }
            // if there is in valid enter or null enter then alert
            catch (Exception e) {
                alert.setText("there is an error plese enter a decimal number");
            }
        });
        // to covert into 64 bit
        bit64.setOnAction(event -> {
            try {
                Double decnum =Double.parseDouble(decimal.getText());//check exist or null and if it is a valid enter
                savenum=decnum;//if valid save to another static variable
                SignNum(); // to check sign
                Decimal2Binary64(savenum); // use this method to convert into binary and exp num
                String expbias = Integer.toBinaryString(1023+exp); // to get exponential number as 2^(11-1) -1
                int2bin=int2bin.substring(1);// to delete first elemnt 1
                show.setText(signnum+expbias+int2bin+fr2bin); // Final number in conversion
                alert.setText("Your Decimal Number : "+savenum+"\n IN 64 BIT IS : " );// to show what is the output
            }
            // if there is in valid enter or null enter then alert
            catch (Exception e) {
                alert.setText("there is an error plese enter a decimal number");
            }
        });

        primaryStage.setTitle("Floating point representation"); // our frame label
        primaryStage.setScene(new Scene(pane, 600, 600)); // add pane to scene
        primaryStage.show(); // to show scene
    }
    // use this method to convert into binary 32 and exp num
    void Decimal2Binary32(double x) {
        double absnum = Math.abs(x); // to get absolute number to override negative number
        int intnuum = (int) absnum; // separate integer
        double frnum = absnum - intnuum;// separate fraction
        int2bin = ""; // to clear content after new click
        fr2bin = "";// to clear content after new click
        int2bin = Integer.toBinaryString(intnuum); // to convert integer number into binary
        int i = 0; // variable is used in while loop
        exp = 0; // to clear content after new click
        exp = int2bin.length() - 1; // exponential number
        // to convert fraction number into binary with limit 32-1 sign -8 exponential -exp
        while (frnum >= 0 && i < 23 - exp) {
            double r = frnum * 2;
            i += 1;
            if (r >= 1) {
                fr2bin += "1";
                frnum = r - 1;

            } else {
                fr2bin += "0";
                frnum = r;

            }
        }
    }
    // use this method to convert into binary 64 and exp num
        void Decimal2Binary64(double x) {
            double absnum = Math.abs(x); // to get absolute number to override negative number
            int intnuum = (int) absnum;  // separate integer
            double frnum = absnum - intnuum; // separate fraction
            int2bin = ""; // to clear content after new click
            fr2bin = ""; // to clear content after new click
            int2bin = Integer.toBinaryString(intnuum); // to convert integer number into binary
            int i = 0; // variable is used in while loop
            exp = 0; // to clear content after new click
            exp = int2bin.length() - 1; // exponential number
            // to convert fraction number into binary with limit 64-1 sign -11 exponential -exp
            while (frnum >= 0 && i < 52 - exp) {
                double r = frnum * 2;
                i += 1;
                if (r >= 1) {
                    fr2bin += "1";
                    frnum = r - 1;

                } else {
                    fr2bin += "0";
                    frnum = r;

                }
            }
        }
    // to check sign
    void SignNum(){
        if (savenum<0.0){signnum=1;} //if negative
        else {signnum=0;} //if positive
    }

    public static void main(String[] args) {
        launch(args); // to launch
    }
}