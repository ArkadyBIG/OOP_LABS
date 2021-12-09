/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld.state;


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.SkyFactory;

/**
 *
 * @author arkady_big
 */
public class NewClass extends AbstractAppState
{
    private final Node rootNode;
    private final Node localRootNode = new Node("Level 1");
    private final AssetManager assetManager;
    private Geometry geom;
    private BulletAppState bulletAppState;
    private Material slow_mat;
    private Spatial cannon;
    private Vector3f cannonSpan;
    private final InputManager inputManager;
    private float gravity = 20f;
    private BitmapText txt;

    public NewClass(SimpleApplication app) {
        rootNode = app.getRootNode();
        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        
        bulletAppState = new BulletAppState();
        //bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);
        
        rootNode.attachChild(localRootNode);
        
        
        Spatial scene = assetManager.loadModel("Scenes/CannonSimulation.j3o"); 
        
        localRootNode.attachChild(scene);
        localRootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/BrightSky.dds", SkyFactory.EnvMapType.CubeMap));
        
        Spatial floor = localRootNode.getChild("Floor");
        bulletAppState.getPhysicsSpace().add(floor.getControl(RigidBodyControl.class));
        
        Geometry mesh = (Geometry) localRootNode.getChild("Floor");
        mesh.getMesh().scaleTextureCoordinates(new Vector2f(40,40));
        
        slow_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        slow_mat.setColor("Color", ColorRGBA.Red);
        
        cannon = localRootNode.getChild("Cannon");
        cannonSpan = cannon.getWorldTranslation();
        
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("IncreaseGravity", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("DecreaseGravity", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(actionListener, "Shoot", "IncreaseGravity", "DecreaseGravity");
        
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, 600, 300));
        txt.setQueueBucket(Bucket.Transparent);
        txt.setSize( 11f );
        txt.setLocalTranslation(0, 0, -100);
        localRootNode.attachChild(txt);
        setGravityText();
    }
    
    private void setGravityText(){
        txt.setText("Gravity: " + gravity + "m/s^2");
    }
    
    private final ActionListener actionListener = new ActionListener() {
            
            @Override
            public void onAction(String name, boolean isPressed, float tpf){
                if (!isPressed)
                    return;
                switch (name) {
                    case "Shoot":
                        makeCannonBall();
                        break;
                    case "IncreaseGravity":
                        gravity += 10;
                        break;
                    case "DecreaseGravity":
                        gravity -= 10;
                        break;
                }
                setGravityText();
            }
    };
    
    @Override
    public void cleanup () {
        rootNode.detachChild(localRootNode);
        super.cleanup();
    }
    
    public void makeCannonBall() {
        /** Create a cannon ball geometry and attach to scene graph. */
        Geometry ball_geo = new Geometry("cannon ball", new Sphere(128, 128, 3f, true, false));
        ball_geo.setMaterial(slow_mat);
        rootNode.attachChild(ball_geo);
        /** Position the cannon ball  */
        ball_geo.setLocalTranslation(cannonSpan);
        /** Make the ball physcial with a mass > 0.0f */
        RigidBodyControl ball_phy = new RigidBodyControl(1f);
        /** Add physical ball to physics space. */
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        /** Accelerate the physcial ball to shoot it. */
        ball_phy.setGravity(new Vector3f(0, -gravity, 0));
        Vector3f initialVelocity = new Vector3f(-10, 5, 0);
        initialVelocity = initialVelocity.normalize();
        ball_phy.setLinearVelocity(initialVelocity.mult(130));


  }
    
}
