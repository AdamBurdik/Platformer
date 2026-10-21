package me.adamix.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Hrac {
    public int positionX;
    public int positionY;
    public int vyska;
    public int sirka;

    public boolean isOnGround;
    public boolean necoNaPravo;
    public boolean necoNaLevo;
    private SpriteBatch batch;
    private Texture image;
    public ShapeRenderer shape;
    public World world;
    BodyDef bodyDef;
    Body body;

    public Hrac(ShapeRenderer shape, int positionX, int positionY, int vyska, int sirka, World world) {
        this.shape = shape;
        this.vyska = vyska;
        this.positionY = positionY;
        this.positionX = positionX;
        this.sirka = sirka;

        this.world = world;
        create();
    }


    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(positionX, positionY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        PolygonShape ctverec = new PolygonShape();
        ctverec.setAsBox(sirka / 2, vyska / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ctverec;
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");
        ctverec.dispose();

        detector("HracDetektorPrava", "prava");
        detector("HracDetektorDole", "dole");
        detector("HracDetektorLeva", "leva");
    }

    public void detector(String jmeno_detektoru, String ff){
        PolygonShape contactDetector = new PolygonShape();
        if (ff.equals("dole")) {
            contactDetector.setAsBox(sirka / 2 * 0.9f, vyska * 0.02f, new Vector2(0, -vyska / 2), 0);
        } else if (ff.equals("prava")) {
            contactDetector.setAsBox(sirka * 0.02f, vyska / 2 * 0.9f, new Vector2(sirka / 2, 0), 0);
        } else if (ff.equals("leva")) {
            contactDetector.setAsBox(sirka * 0.02f, vyska / 2 * 0.9f, new Vector2(-sirka / 2, 0), 0);
        }
        FixtureDef sensorDef = new FixtureDef();
        sensorDef.shape = contactDetector;
        sensorDef.isSensor = true;
        Fixture detectorKolizi = body.createFixture(sensorDef);
        detectorKolizi.setUserData(jmeno_detektoru);
        contactDetector.dispose();

    }

    public void gravitaceIg(){
        Vector2 vector = body.getLinearVelocity();
       // System.out.println("vector y je " + vector.y);
        if (vector.y <= 0){
            body.setGravityScale(2.5f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            body.setGravityScale(1f);
        }else {
            if (vector.y < 25 && vector.y > -5){
                vector.y -= 25;
            }

            vector.y -= 10;
            body.setGravityScale(4f);
        }
       // System.out.println(body.getGravityScale());
    }

    public void aktualizacePozice() {
        Vector2 vec = body.getPosition();
        positionX = (int) vec.x;
        positionY = (int) vec.y;
    }

    public void drawCtverec() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(positionX - sirka / 2, positionY - vyska / 2, sirka, vyska);
        shape.end();
    }

    public void doIt(){
        aktualizacePozice();
        pohyb();
        drawCtverec();
        gravitaceIg();
    }

    public void pohyb() {
        if (Gdx.input.isKeyPressed(Input.Keys.D) && !necoNaPravo) {
                System.out.println(Gdx.graphics.getWidth());
                System.out.println(positionY);
                body.setLinearVelocity(+50, body.getLinearVelocity().y);
                clampToScreen();
                //struct.positionX+= 3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !necoNaLevo) {
                body.setLinearVelocity(-50, body.getLinearVelocity().y);
                //struct.positionX -= 3;
                clampToScreen();


        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            clampToScreen();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isOnGround) {
            body.setLinearVelocity(body.getLinearVelocity().x, 40);
        }
    }

    private void clampToScreen() {
        float minX = sirka / 2f;
        float maxX = Gdx.graphics.getWidth() - vyska / 2f;
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        if (x < minX) {
            body.setTransform(minX, y, 0f);
        } else if (x > maxX) {
            body.setTransform(maxX, y, 0f);
        }
    }

}
