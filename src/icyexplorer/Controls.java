/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icyexplorer;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Ritesh
 */
public class Controls {
    
    private static PerspectiveCamera pc;
    private static int rot1, dir = 1;
    private static double degreeS = 90.0, degreeS2 = 0.0, degreeC = 0.0;
    
    
    public Controls(PerspectiveCamera pc){
        this.pc = pc;
    }
    
    public void forward(){
        degreeS2 = Math.cos(Math.toRadians(degreeS));
        
        if((degreeS >180)||(degreeS > -180 && degreeS < 0)) dir = -1;
        else dir = 1;
        
        pc.setTranslateZ(pc.getTranslateZ()+50*dir);
        pc.setTranslateX(pc.getTranslateX()+50*degreeS2);
    }
    
    public void back(){
        degreeS2 = Math.cos(Math.toRadians(degreeS));
        
        pc.setTranslateZ(pc.getTranslateZ()-50);
        pc.setTranslateX(pc.getTranslateZ()-50*degreeS2);
    }
    
    public void jump(){
        
        if (System.currentTimeMillis() < Main.time + 250) {
                pc.setTranslateY(pc.getTranslateY() -4 );
            }
        else if (pc.getTranslateY() < Main.yMin) {
                pc.setTranslateY(pc.getTranslateY() +4 );
                if (pc.getTranslateY() == Main.yMin) {
                    Main.done = true;
                    Main.jumping = false;
                }
            }
        
    }
    
    public void up(){
        pc.setTranslateY(pc.getTranslateY()-100);
        
    }
    
    public void down(){
        if(pc.getTranslateY()<0)
        pc.setTranslateY(pc.getTranslateY()+100);
    }
    
    public void left(){
        pc.setTranslateX(pc.getTranslateX()-50);
    }
    
    public void right(){
        pc.setTranslateX(pc.getTranslateX()+50);
    }
    
    public void lookUp(){
        pc.getTransforms().add(new Rotate(1, Rotate.X_AXIS));
    }
    
    public void lookDown(){
        pc.getTransforms().add(new Rotate(-1, Rotate.X_AXIS));
    }
    
    public void lookLeft(){
        if(degreeS >= 360 || degreeS <= -360) degreeS = 0.0;
        pc.setRotationAxis(Rotate.Y_AXIS);
        pc.setRotate(rot1-=1);
        degreeS+=1;
        
    }
    
    public void lookRight(){
        if(degreeS >= 360 || degreeS <= -360) degreeS = 0.0;
        pc.setRotationAxis(Rotate.Y_AXIS);
        pc.setRotate(rot1+=1);
        degreeS-=1;
        
    }
    
    public double toDegree(double radian){
        double degree = (radian*180)/Math.PI;
        return degree;
        
    }
    
    public void reset(){
        pc.setTranslateX(0);
        pc.setTranslateZ(0);
        degreeS = 90.0;
    } 

}
