package me.adamix.platformer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CSO {
     public Struct struct;
     public World world;
    public ShapeRenderer shape = new ShapeRenderer();
    public BodyDef staticBodyDef;
    public Body body;

    public CSO(Struct struct, World world){
        this.struct = struct;
        this.world = world;
        create();
    }

    private void create(){
        staticBodyDef = new BodyDef();
        staticBodyDef.type = BodyDef.BodyType.StaticBody;
        staticBodyDef.position.set(struct.positionX + struct.sirka/2, struct.positionY + struct.vyska /2);
        body = world.createBody(staticBodyDef);
        PolygonShape objekt = new PolygonShape();
        objekt.setAsBox(struct.sirka / 2, struct.vyska / 2);
        body.createFixture(objekt, 0.0f);
        objekt.dispose();
    }

    public void drawCSO(){
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(struct.positionX, struct.positionY, struct.sirka, struct.vyska);
        shape.end();
    }
}
