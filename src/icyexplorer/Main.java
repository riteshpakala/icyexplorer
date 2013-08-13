/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icyexplorer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    
    
    static PerspectiveCamera pc = null;
    static Scene scene;
    static ObservableList<Node> content;
    
    static Timeline everySecond;
    
    static Controls move;
    
    static double time;
    static double yMin;
    
    static boolean forward = false;
    static boolean back = false;
    static boolean up = false;
    static boolean down = false;
    static boolean jumpKey = false;
    static boolean jumping = false;
    static boolean done = true;
    static boolean canMove = false;
    static boolean left = false;
    static boolean right = false;
    static boolean lookUp = false;
    static boolean lookDown = false;
    static boolean lookLeft = false;
    static boolean lookRight = false;
    
    static double degreeC = 1.0, degreeS = 1.0;
    static int rotateUD = 0, rotateLR = 0;
    static double x, y, x2, y2;
    
    static int mill = 10;
    static int counter = 1;
    static int counter2 = 1;
    static int counter3 = 1;
    static int counter4 = 1;
    static int timer = 30;
    
    static int score = 0;
    
    static Shape3D[] firstStep = new Shape3D[]{new Box(300,100,200)};
    static  PhongMaterial material = new PhongMaterial();
    
    static PhongMaterial material2 = new PhongMaterial();
    static PhongMaterial material3 = new PhongMaterial();
    
    static PhongMaterial material4 = new PhongMaterial();
    static PhongMaterial material5 = new PhongMaterial();
    
    static Shape3D[] row1, row2, row3;
    
    static Text t1 = new Text();
    static Text t2 = new Text();
    
    
    public static Scene scene2, scene3;
    public static ObservableList<Node> content2, content3;
    
    
    @Override public void start(final Stage stage) {
        //Group root = new Group(meshView);
        scene = new Scene(new Group(), 690, 720, true);
        content = ((Group) scene.getRoot()).getChildren();
        
        ////// MENU SCREENS /////

        scene2 = new Scene(new Group(), 690, 720, Color.WHITE);
        content2 = ((Group) scene2.getRoot()).getChildren();

        scene3 = new Scene(new Group(), 690, 720, Color.WHITE);
        content3 = ((Group) scene3.getRoot()).getChildren();


        Button play = new Button("Play");
            play.setTranslateX(720/2 - 30);
            play.setTranslateY(450);

            Button htp = new Button("How to Play");
            htp.setTranslateX(720/2 - 55);
            htp.setTranslateY(500);
    
    
            final Canvas canvas = new Canvas(690,720);
            final GraphicsContext gc = canvas.getGraphicsContext2D();

            final Canvas canvasMenu = new Canvas(690,720);
            final GraphicsContext gc2 = canvasMenu.getGraphicsContext2D();
            Image bgd = new Image(getClass().getResourceAsStream("res/bgd.png"));
            Image logo = new Image(getClass().getResourceAsStream("res/logo.png"));

            gc.drawImage(bgd, (double)0, (double)0, (double)690, (double)720);

            gc.drawImage(logo, (double)600, (double)700, (double)80, (double)15);

            /*******MENU CANVAS*******/

            Image header = new Image(getClass().getResourceAsStream("res/header.png"));
            gc.drawImage(header, (double)60, (double)100, (double)559, (double)166);

            Image ver = new Image(getClass().getResourceAsStream("res/ver.png"));
            gc.drawImage(ver, (double)150, (double)350, (double)386, (double)39);

            gc.drawImage(logo, (double)600, (double)700, (double)80, (double)15);

            /************************/

            content2.addAll(canvas, play/*, htp*/);
            
            
            
        ////// SHAPES /////////


            material.setDiffuseColor(Color.LIGHTGRAY);
            material.setSpecularColor(Color.rgb(30, 30, 30));


            material2.setDiffuseColor(Color.GREEN);
            material2.setSpecularColor(Color.rgb(30, 30, 30));

            material3.setDiffuseColor(Color.RED);
            material3.setSpecularColor(Color.rgb(30, 30, 30));

            material4.setDiffuseColor(Color.LIGHTGREEN);
            material4.setSpecularColor(Color.rgb(30, 30, 30));


            Shape3D[] road = new Shape3D[]{new Box(300,200,50000), new Box(300,200,50000), new Box(300,200,50000)};


            // 3 rows for whack a mole
            row1 = new Shape3D[] {
                new Sphere(100),
                new Sphere(100),
                new Sphere(100),
            };

            row2 = new Shape3D[] {
                new Sphere(100),
                new Sphere(100),
                new Sphere(100),
            };

            row3 = new Shape3D[] {
                new Sphere(100),
                new Sphere(100),
                new Sphere(100),
            };

            for (int i=0; i!=3; ++i) {
                row1[i].setMaterial(material);
                row1[i].setTranslateX((i + 1) * 220);
                row1[i].setTranslateY(500);
                row1[i].setTranslateZ(5000);
                row1[i].setDrawMode(DrawMode.FILL);
                row1[i].setCullFace(CullFace.BACK);
                content.add(row1[i]);
            };

            for (int i=0; i!=3; ++i) {
                row2[i].setMaterial(material);
                row2[i].setTranslateX((i + 1) * 220);
                row2[i].setTranslateY(250);
                row2[i].setTranslateZ(5000);
                row2[i].setDrawMode(DrawMode.FILL);
                row2[i].setCullFace(CullFace.BACK);
                content.add(row2[i]);
            };

            for (int i=0; i!=3; ++i) {
                row3[i].setMaterial(material);
                row3[i].setTranslateX((i + 1) * 220);
                row3[i].setTranslateY(0);
                row3[i].setTranslateZ(5000);
                row3[i].setDrawMode(DrawMode.FILL);
                row3[i].setCullFace(CullFace.BACK);
                content.add(row3[i]);
            };

            PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
            pointLight.setTranslateX(800);
            pointLight.setTranslateY(-100);
            pointLight.setTranslateZ(-1000);


            for (int i=0; i!=3; ++i) {
                road[i].setMaterial(material);
                road[i].setTranslateX((i+1)*220);
                road[i].setTranslateZ(3000);
                road[i].setTranslateY(900);
                content.add(road[i]);
            };

            for (int i=0; i!=1; ++i) {
                firstStep[i].setMaterial(material2);
                firstStep[i].setTranslateX(430);
                firstStep[i].setTranslateZ(200);
                firstStep[i].setTranslateY(800);
                content.add(firstStep[i]);
            };



            content.add(pointLight);


            pc = new PerspectiveCamera(false);
            pc.setTranslateX(100);
            pc.setTranslateZ(-225);
            move = new Controls(pc);


            t1 = new Text(-450, 650, "Score: "+score);
            t1.setFont(Font.font ("Verdana", 100));
            t1.setTranslateZ(5000);

            t1.setFill(Color.WHITE);

            t2 = new Text(900, 650, "Time: "+timer);
            t2.setFont(Font.font ("Verdana", 100));
            t2.setTranslateZ(5000);

            t2.setFill(Color.WHITE);
            content.addAll(t1, t2);

            scene.setFill(Color.rgb(10, 10, 40));

            scene.setCamera(pc);

            stage.setScene(scene2);
            stage.show();

            play.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    stage.setScene(scene);
                    score = 0;
                    timer = 30;
                }
                });
       
        
        
        
     
    ////// KEY CASES ///////
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
                public void handle(KeyEvent ke){
                    switch (ke.getCode()) {
                        case W:
                            forward = true;
                        break;
                            
                        case S:
                            back = true;
                        break;
                            
                        case E:
                            up = true;
                        break;
                            
                        case Q:
                            down = true;
                        break;
                            
                        case A:
                            //left = true;
                        break;
                            
                        case D:
                            //right = true;
                        break;
                            
                        case SPACE:
                            jumpKey = true;
                        break;
                            
                        case UP:
                            lookUp = true;
                        break;
                            
                        case DOWN:
                            lookDown = true;
                        break;
                            
                        case LEFT:
                            lookLeft = true;
                        break;
                            
                        case RIGHT:
                            lookRight = true;
                        break;
                            
                        case R:
                            move.reset();
                        break;
                    }
                }
            
            
            });
        
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                public void handle(KeyEvent ke){
                    forward = false;
                    back = false;
                    up = false;
                    down = false;
                    //left = false;
                    //right = false;
                    jumpKey = false;
                    lookUp = false;
                    lookDown = false;
                    lookLeft = false;
                    lookRight = false;
            }
        });
            
        
        
        ///// Game Loop /////
        
        Timeline delayTimeline = new Timeline();
                final int millis = 10;//updates ever 10 ms
                delayTimeline.getKeyFrames().add(
                        new KeyFrame(new Duration(millis - (System.currentTimeMillis() % millis)), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        everySecond = new Timeline();
                        everySecond.setCycleCount(Timeline.INDEFINITE);//keeps on running until user shuts down
                        everySecond.getKeyFrames().add(
                                new KeyFrame(Duration.valueOf(millis+"ms"), new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                
                                updateGame();
                                
                                
                                if(firstStep[0].getTranslateZ() <= pc.getTranslateZ()){
                                    firstStep[0].setMaterial(material3);
                                }else firstStep[0].setMaterial(material2);
                                
                                
                                if(firstStep[0].getMaterial() == material3){
                                    //THE GAME
                                    if(mill/(2*counter3) == 1000){
                                        timer--;
                                        
                                        counter3++;
                                    }
                                    
                                    if(mill / (2*counter) == 500){
                                        double row = Math.random()*3;
                                        double col = Math.random()*3;
                                        
                                        if((int)row == 0){
                                            row1[(int)col].setMaterial(material2);
                                        }else if((int)row == 1){
                                            row2[(int)col].setMaterial(material2);
                                        }else if((int)row == 2){
                                            row3[(int)col].setMaterial(material2);
                                        }
                                     
                                        counter++;
                                    }
                                    
                                    
                                    if(mill / (2*counter2) == 125){
                                        
                                        double row = Math.random()*3;
                                        double col = Math.random()*3;
                                        
                                        if((int)row == 0){
                                            row1[(int)col].setMaterial(material);
                                            
                                        }else if((int)row == 1){
                                            row2[(int)col].setMaterial(material);
                                        }else if((int)row == 2){
                                            row3[(int)col].setMaterial(material);
                                        }
                                        counter2++;
                                    }
                                    
                                    
                                    
                                    
                                    
                                    mill+=10;
                                    
                                    row1[0].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(row1[0].getMaterial() == material2){
                        row1[0].setMaterial(material4);
                        score+=10;
                    }else {row1[0].setMaterial(material3); score -= 10;}
                }
            
            
            });
        
        row1[1].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row1[1].getMaterial() == material2){
                        row1[1].setMaterial(material4);
                        score+=10;
                    }else {row1[1].setMaterial(material3); score -= 10;}
                    
                    }
                }
            
            
            });
        
        row1[2].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row1[2].getMaterial() == material2){
                        row1[2].setMaterial(material4);
                        score+=10;
                    }else {row1[2].setMaterial(material3); score -= 10;}
                }
                }
            
            
            });
        
        row2[0].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row2[0].getMaterial() == material2){
                        row2[0].setMaterial(material4);
                        score+=10;
                    }else {row2[0].setMaterial(material3); score -= 10;}
                }
                }
            
            
            });
        
        row2[1].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(row2[1].getMaterial() == material2){
                        row2[1].setMaterial(material4);
                        score+=10;
                    }else {row2[1].setMaterial(material3); score -= 10;}
                }
            
            
            });
        
        row2[2].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row2[2].getMaterial() == material2){
                        row2[2].setMaterial(material4);
                        score+=10;
                    }else {row2[2].setMaterial(material3); score -= 10;}
                }
            
                }
            });
        
        row3[0].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row3[0].getMaterial() == material2){
                        row3[0].setMaterial(material4);
                        score+=10;
                    }else {row3[0].setMaterial(material3); score -= 10;}
                }
                }
            
            });
        
        row3[1].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row3[1].getMaterial() == material2){
                        row3[1].setMaterial(material4);
                        score+=10;
                    }else {row3[1].setMaterial(material3); score -= 10;}
                }
                }
            
            });
        
        row3[2].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    if(firstStep[0].getMaterial() == material3){
                    if(row3[2].getMaterial() == material2){
                        row3[2].setMaterial(material4);
                        score+=10;
                    }else {row3[2].setMaterial(material3); score -= 10;}
                }
                }
            
            });
        
        
                                    
                                }
                                
                                if(timer <= 0){
                                    stage.setScene(scene2);
                                }
                                
                            }

                        }));

                        everySecond.play();

                    }
                }));

                delayTimeline.play();
                
        // -------------
                
       // CLICK CHECK
        
        
                
    }
    
    public void updateGame(){
        t1.setText("Score: "+score);
        t2.setText("Time: "+timer);
        //stage.setScene(scene);
        
        if(forward) {move.forward();}
        if(back) {move.back();}
        if(up) {move.up();}
        if(down) {move.down();}
        //if(left) {move.left();}
        //if(right) {move.right();}
        
        if(lookUp) {move.lookUp();}
        if(lookDown) {move.lookDown();}
        if(lookLeft) {move.lookLeft(); }
        if(lookRight) {move.lookRight();}
        
        //Jumping
        if (jumpKey && done == true) {
            
            yMin = pc.getTranslateY();
            time = System.currentTimeMillis();
            done = false;
            jumping = true;
            
        }
        if(jumping){move.jump();}
    }
    
    
    // Maths

   public double getDistanceBetween(double startX, double startY, double endX, double endY) {
        double dx = endX - startX;
        double dy = endY - startY;
        return Math.sqrt(dx*dx + dy*dy);
   }

   public double getTargetAngle(double startX, double startY, double targetX, double targetY) {
      double dx = targetX - startX;
        double dy = targetY - startY;
        return Math.toDegrees(Math.atan2(dy, dx));
   }
    
    public static void main(String[] args) {
        launch(args);
    }
}
