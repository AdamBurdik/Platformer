package me.adamix.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Hrac {

    public boolean isOnGround;
    private SpriteBatch batch;
    private Texture image;
    public ShapeRenderer shape;
    public World world;
    Struct struct;
    BodyDef bodyDef;
    Body body;

    public Hrac(ShapeRenderer shape, Struct struct, World world) {
        this.shape = shape;
        this.struct = struct;

        this.world = world;
        create();
    }


    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(struct.positionX, struct.positionY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        PolygonShape ctverec = new PolygonShape();
        ctverec.setAsBox(struct.sirka / 2, struct.vyska / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ctverec;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        Fixture fixture = body.createFixture(fixtureDef);
        ctverec.dispose();

        PolygonShape contactDetector = new PolygonShape();
        contactDetector.setAsBox(struct.vyska / 2 * 0.95f, 1, new Vector2(0, -struct.vyska / 2), 0);
        FixtureDef sensorDef = new FixtureDef();
        sensorDef.shape = contactDetector;
        sensorDef.isSensor = true;
        Fixture detectorKolizi = body.createFixture(sensorDef);
        detectorKolizi.setUserData("HracDetektor");
        contactDetector.dispose();


    }

    public void aktualizacePozice() {
        Vector2 vec = body.getPosition();
        struct.positionX = (int) vec.x;
        struct.positionY = (int) vec.y;
    }

    public void drawCtverec() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(struct.positionX - struct.sirka / 2, struct.positionY - struct.vyska / 2, struct.sirka, struct.vyska);
        shape.end();
    }

    public void pohyb() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (struct.positionX + 3 < Gdx.graphics.getWidth() - struct.sirka) { // trochu nefunguje neda se nic
                System.out.println(Gdx.graphics.getWidth());
                System.out.println(struct.positionY);
                body.setLinearVelocity(+50, body.getLinearVelocity().y);
                //struct.positionX+= 3;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (struct.positionX - 3 > 0) { // taky nefunguje fixnu nekdy jindy
                body.setLinearVelocity(-50, body.getLinearVelocity().y);
                //struct.positionX -= 3;
            }

        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isOnGround) {
            body.setLinearVelocity(body.getLinearVelocity().x, 40);
        }
    }

}
