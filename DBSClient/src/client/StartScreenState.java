
package client;

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
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author xissburg
 */
public class StartScreenState implements AppState {

    private Node rootNode;
    private Application app;
    private Geometry buttonShape;
    private boolean initialized;
    private boolean active;
    
    private World world;
    private Body discBody;

    public StartScreenState(Node rootNode) {
        this.rootNode = rootNode;
        this.initialized = false;
        this.active = true;
    }
        
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        //Attach stuff onto root
        this.app = app;
        
        Material buttonMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        buttonMat.setFloat("Shininess", 64);
        buttonMat.setBoolean("UseMaterialColors", true);
        buttonMat.setColor("Diffuse", new ColorRGBA(0.8f, 0.8f, 1f, 1f));
        buttonMat.setColor("Ambient",  ColorRGBA.Black);
        buttonMat.setColor("Specular", ColorRGBA.LightGray);
        
        //create button shape
        Cylinder buttonCyl = new Cylinder(32, 16, 0.3f, 0.1f, true);
        TangentBinormalGenerator.generate(buttonCyl);
        buttonShape = new Geometry("disc", buttonCyl);
        buttonShape.setMaterial(buttonMat);
        buttonShape.setLocalRotation(new Quaternion().fromAngleNormalAxis((float)Math.PI/2, Vector3f.UNIT_X));
        buttonShape.setLocalTranslation(Vector3f.ZERO);

        rootNode.attachChild(buttonShape);
        
        PointLight pl = new PointLight();
        pl.setPosition(new Vector3f(2f, 10f, 2f));
        pl.setColor(ColorRGBA.White.clone());
        pl.setRadius(200f);
        rootNode.addLight(pl);
        
        Camera camera = app.getCamera();
        camera.setLocation(new Vector3f(0f, 4f, -1f));
        camera.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        
        
        world = new World(new Vec2(0f,-0.1f), false);
        
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(new Vec2(0f,0f));
        discBody = world.createBody(bd);
        
        CircleShape cs = new CircleShape();
        cs.m_radius = 0.3f;
        
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 20f;
        fd.restitution = 0.5f;
        
        discBody.createFixture(fd);
        
        discBody.setUserData(buttonShape);
        
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
        rootNode.detachChild(buttonShape);
        buttonShape = null;
    }

    @Override
    public void update(float tpf) {
        world.step(tpf, 10, 10);
        
        Vec2 c = discBody.getWorldCenter();
        Spatial spatial = (Spatial)discBody.getUserData();
        spatial.setLocalTranslation(c.x, 0f, c.y);
    }

    @Override
    public void render(RenderManager rm) {}

    @Override
    public void postRender() {}

    @Override
    public void cleanup() {
        
    }
}
