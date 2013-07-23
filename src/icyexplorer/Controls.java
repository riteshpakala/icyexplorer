/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icyexplorer;

import javafx.scene.PerspectiveCamera;

/**
 *
 * @author Ritesh
 */
public class Controls {
    
    private static PerspectiveCamera pc;
    
    public Controls(PerspectiveCamera pc){
        this.pc = pc;
    }
    
    public void forward(double degreeC, double degreeS){
        pc.setTranslateZ(pc.getTranslateZ()+50*degreeS);
        if(degreeS != 1.0)
        pc.setTranslateX(pc.getTranslateZ()*degreeS);
    }
    
    public void back(double degreeC, double degreeS){
        pc.setTranslateZ(pc.getTranslateZ()-50*degreeC);
        if(degreeS != 1.0)
        pc.setTranslateX(pc.getTranslateZ()*degreeS);
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
    
    public void left(double degreeS){
        pc.setTranslateX(pc.getTranslateX()-50*degreeS);
    }
    
    public void right(double degreeS){
        pc.setTranslateX(pc.getTranslateX()+50*degreeS);
    }

}
