package me.adamix.platformer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CSO {
   public int positionX;
    public int positionY;
    public int vyska;
    public int sirka;
     public World world;
    public ShapeRenderer shape = new ShapeRenderer();
    public BodyDef staticBodyDef;
    public Body body;

    public CSO(int positionX, int positionY, int vyska, int sirka, World world){
        this.vyska = vyska;
        this.positionY = positionY;
        this.positionX = positionX;
        this.sirka = sirka;
        this.world = world;
        create();
    }

    private void create(){
        staticBodyDef = new BodyDef();
        staticBodyDef.type = BodyDef.BodyType.StaticBody;
        staticBodyDef.position.set(positionX + sirka/2, positionY + vyska /2);
        body = world.createBody(staticBodyDef);
        PolygonShape objekt = new PolygonShape();
        objekt.setAsBox(sirka / 2, vyska / 2);
        body.createFixture(objekt, 0.0f);
        objekt.dispose();
    }

    public void drawCSO(){
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(positionX, positionY, sirka, vyska);
        shape.end();
    }
}
