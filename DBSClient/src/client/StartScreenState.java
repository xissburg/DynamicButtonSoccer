
package client;

import actions.CameraMoveToAction;
import actions.Action;
import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.util.TangentBinormalGenerator;
import java.util.ArrayList;
import java.util.Collection;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * AppState for the title screen. It has 3 dynamic discs/circles in it. They are
 * physically simulated and can be dragged around by the user. They are attached
 * to springs that keep them in place. 
 * 
 * @author xissburg
 */
public class StartScreenState implements AppState {

    private Node rootNode;
    private ClientApplication app;
    private Collection<Geometry> shapes;
    private boolean initialized;
    private boolean active;
    
    private World world;
    private Collection<Body> bodies;

    public StartScreenState(Node rootNode) {
        this.rootNode = rootNode;
        this.initialized = false;
        this.active = true;
        this.shapes = new ArrayList<Geometry>();
        this.bodies = new ArrayList<Body>();
    }
        
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        //app *must* be an instance of ClientApplication so that methods like runAction
        //can be called on it
        this.app = (ClientApplication)app;
        
        //Setup physics
        Vec2 gravity = new Vec2(0f, -0f);
        world = new World(gravity, false);
        
        //Central disc
        Material buttonMat = new Material(this.app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        buttonMat.setFloat("Shininess", 64);
        buttonMat.setBoolean("UseMaterialColors", true);
        buttonMat.setColor("Diffuse", new ColorRGBA(0.8f, 0.8f, 1f, 1f));
        buttonMat.setColor("Ambient",  ColorRGBA.Black);
        buttonMat.setColor("Specular", ColorRGBA.LightGray);
        
        Cylinder buttonCyl = new Cylinder(32, 16, 0.8f, 0.1f, true);
        TangentBinormalGenerator.generate(buttonCyl);
        Geometry buttonShape = new Geometry("disc", buttonCyl);
        buttonShape.setMaterial(buttonMat);
        buttonShape.setLocalRotation(new Quaternion().fromAngleNormalAxis((float)Math.PI/2, Vector3f.UNIT_X));
        buttonShape.setLocalTranslation(Vector3f.ZERO);
        rootNode.attachChild(buttonShape);
        shapes.add(buttonShape);
        
        //Central disc physics
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(new Vec2(0f,0f));
        Body discBody = world.createBody(bd);
        
        CircleShape cs = new CircleShape();
        cs.m_radius = buttonCyl.getRadius();
        
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 3f;
        fd.restitution = 0.5f;
        
        discBody.createFixture(fd);
        
        discBody.setUserData(buttonShape);
        bodies.add(discBody);
        
        //Left disc
        buttonCyl = new Cylinder(32, 16, 0.3f, 0.1f, true);
        TangentBinormalGenerator.generate(buttonCyl);
        buttonShape = new Geometry("disc", buttonCyl);
        buttonShape.setMaterial(buttonMat);
        buttonShape.setLocalRotation(new Quaternion().fromAngleNormalAxis((float)Math.PI/2, Vector3f.UNIT_X));
        buttonShape.setLocalTranslation(Vector3f.ZERO);
        rootNode.attachChild(buttonShape);
        shapes.add(buttonShape);
        
        //Left disc physics
        bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(new Vec2(-0.9f,0.9f));
        discBody = world.createBody(bd);
        
        cs = new CircleShape();
        cs.m_radius = buttonCyl.getRadius();
        
        fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 2f;
        fd.restitution = 0.5f;
        
        discBody.createFixture(fd);
        discBody.setUserData(buttonShape);
        bodies.add(discBody);
        
        discBody.applyLinearImpulse(new Vec2(0f,-1f), discBody.getWorldCenter());
        
        //Light source
        PointLight pl = new PointLight();
        pl.setPosition(new Vector3f(2f, 10f, 2f));
        pl.setColor(ColorRGBA.White.clone());
        pl.setRadius(200f);
        rootNode.addLight(pl);
        
        //Setup camera
        Camera camera = this.app.getCamera();
        camera.setLocation(new Vector3f(0f, 5f, 0.1f));
        camera.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        
        Action cameraMoveAction = new CameraMoveToAction(3f, camera, new Vector3f(0f, 6f, 0.1f));
        this.app.runAction(cameraMoveAction);
            
        initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void setEnabled(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        //Run initial animations
        
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        for (Geometry g: shapes) {
            rootNode.detachChild(g);
        }
        shapes.clear();
    }

    @Override
    public void update(float tpf) {
        world.step(tpf, 10, 10);
        
        for (Body b: bodies) {
            Vec2 c = b.getWorldCenter();
            Spatial spatial = (Spatial)b.getUserData();
            spatial.setLocalTranslation(c.x, 0f, -c.y);
        }        
    }

    @Override
    public void render(RenderManager rm) {}

    @Override
    public void postRender() {}

    @Override
    public void cleanup() {
        
    }
}
