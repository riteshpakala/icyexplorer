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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
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
    
    static double degreeC = 1.0, degreeS = 1.0, rotationX, rotationY;
    
    static double x, y, x2, y2;
    @Override public void start(Stage stage) {
        //Group root = new Group(meshView);
        scene = new Scene(new Group(), 800, 800, true);
        content = ((Group) scene.getRoot()).getChildren();
        
        
    ////// SHAPES /////////
        
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);
        material.setSpecularColor(Color.rgb(30, 30, 30));
        
        Shape3D[] road = new Shape3D[]{new Box(300,200,50000), new Box(300,200,50000), new Box(300,200,50000)};
        Shape3D[] meshView = new Shape3D[] {
            new Box(200, 200, 200),
            new Sphere(100),
            new Cylinder(100, 200),
        };
 
        for (int i=0; i!=3; ++i) {
            meshView[i].setMaterial(material);
            meshView[i].setTranslateX((i + 1) * 220);
            meshView[i].setTranslateY(500);
            meshView[i].setTranslateZ(5000);
            meshView[i].setDrawMode(DrawMode.FILL);
            meshView[i].setCullFace(CullFace.BACK);
            content.add(meshView[i]);
        };
 
        PointLight pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(800);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(-1000);
        
        for (int i=0; i!=3; ++i) {
            road[i].setMaterial(material);
            road[i].setTranslateX((i+1)*220);
            road[i].setTranslateZ(3000);
            road[i].setTranslateY(700);
            content.add(road[i]);
        };
        
        
        content.add(pointLight);
        
        pc = new PerspectiveCamera(false);
        move = new Controls(pc);
        
        meshView[1].setOnMousePressed(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent me){
                    System.out.println("Clicked");
                }
            
            
            });
        
        
        scene.setFill(Color.rgb(10, 10, 40));
        
        scene.setCamera(pc);
        
        stage.setScene(scene);
        stage.show();
        
        
        
     
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
                            left = true;
                        break;
                            
                        case D:
                            right = true;
                        break;
                            
                        case SPACE:
                            jumpKey = true;
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
                    left = false;
                    right = false;
                    jumpKey = false;
            }
        });
            
        
        
        ///// Game Loop /////
        
        Timeline delayTimeline = new Timeline();
                final int millis = 10;//updates ever 14 ms
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
                                
                                
                                
                            }

                        }));

                        everySecond.play();

                    }
                }));

                delayTimeline.play();
        
    }
    
    public void updateGame(){
                  
      scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
              public void handle(MouseEvent me){
                    
                          //float targetAng = (float) getTargetAngle((float)scene.getHeight()/2, (float)scene.getWidth()/2, (float)me.getSceneX(), (float)me.getSceneY());
                                
                        x2 = me.getSceneX();
                        y2 = me.getSceneY();
                        
                        rotationX = (x-x2);
                        
                         pc.setRotationAxis(Rotate.Y_AXIS);
                         pc.setRotate(rotationX);
                         
                         rotationY = (y-y2);
                         
                         if(Math.abs(rotationY)>100){
                         pc.setRotationAxis(Rotate.X_AXIS);
                         pc.setRotate(rotationY);
                         }
                         
                         //pc.getTransforms().add(new Rotate(rotationX, 0, 0, 5));
                         degreeC = Math.cos(390);
                         degreeS = Math.sin(390);
                             }
                        
                    });
      
      scene.setOnMousePressed(new EventHandler<MouseEvent>(){
              public void handle(MouseEvent me){
                             x = me.getSceneX();
                             y = me.getSceneY();
                        }
                    });
      
     
      
        scene.setOnMouseExited(
                new EventHandler<MouseEvent>(){
              public void handle(MouseEvent me){
                  System.out.println(canMove);
                  canMove = false;
              }
        });
        
        
        if(forward) {move.forward(degreeC, degreeS);}
        if(back) {move.back(degreeC, degreeS);}
        if(up) {move.up();}
        if(down) {move.down();}
        if(left) {move.left(degreeS);}
        if(right) {move.right(degreeS);}
        
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

   public double getDistanceBetween(float startX, float startY, float endX, float endY) {
        float dx = endX - startX;
        float dy = endY - startY;
        return (float)Math.sqrt(dx*dx + dy*dy);
   }

   public double getTargetAngle(float startX, float startY, float targetX, float targetY) {
      float dx = targetX - startX;
        float dy = targetY - startY;
        return (float)Math.toDegrees(Math.atan2(dy, dx));
   }
    
    public static void main(String[] args) {
        launch(args);
    }
}
