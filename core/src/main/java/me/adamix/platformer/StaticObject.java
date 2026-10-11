package me.adamix.platformer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class StaticObject {
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public StaticObject(World world, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2f, y + height / 2f);

        Body body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2f, height / 2f);
        body.createFixture(box, 0f);
        box.dispose();
    }

    public void draw(ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.4f, 0.75f, 0.4f, 1f);
        shapes.rect(x, y, width, height);
        shapes.end();
    }
}
