package me.adamix.platformer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;

public class Killable extends CSO{

    public Hrac hrac;
    public BodyDef staticBodyDef;
    public Body body;

    public Killable(int positionX, int positionY, int vyska, int sirka, World world) {
        super(positionX, positionY, vyska, sirka, world);

        create();
    }

    private void create(){
        staticBodyDef = new BodyDef();
        staticBodyDef.type = BodyDef.BodyType.StaticBody;
        staticBodyDef.position.set(positionX + sirka/2, positionY + vyska /2);
        body = world.createBody(staticBodyDef);
        PolygonShape objekt = new PolygonShape();
        objekt.setAsBox(sirka / 2, vyska / 2);
        Fixture fixture = body.createFixture(objekt, 0.0f);
        fixture.setUserData("killable");
        objekt.dispose();
    }

    @Override
    public void drawCSO() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(positionX, positionY, sirka, vyska);
        shape.end();
    }
}
