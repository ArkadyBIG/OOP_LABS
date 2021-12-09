package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import jme3test.helloworld.state.NewClass;

/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class HelloJME3 extends SimpleApplication {
    
    public Box b; 
    public Geometry blue; 
    
    public static void main(String[] args){
        HelloJME3 app = new HelloJME3();
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new NewClass(this));
    }
   
    
    
}