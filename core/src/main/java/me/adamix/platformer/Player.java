package me.adamix.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import lombok.Getter;
import lombok.Setter;

public class Player {
    public static final String GROUND_SENSOR_ID = "playerGroundSensor";

    private static final float MOVE_SPEED = 50f;
    private static final float JUMP_VELOCITY = 40f;
    private static final float SENSOR_WIDTH_RATIO = 0.9f;
    private static final float SENSOR_HALF_HEIGHT = 2f;

    private final World world;
    private final Body body;
    private final float width;
    private final float height;

    @Setter
    @Getter
    private boolean onGround;

    public Player(World world, float spawnX, float spawnY, float width, float height) {
        this.world = world;
        this.width = width;
        this.height = height;
        this.body = createBody(spawnX + width / 2f, spawnY + height / 2f);
    }

    private Body createBody(float centerX, float centerY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(centerX, centerY);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2f, height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        box.dispose();

        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(width / 2f * SENSOR_WIDTH_RATIO, SENSOR_HALF_HEIGHT, new Vector2(0, -height / 2f), 0);
        FixtureDef sensorDef = new FixtureDef();
        sensorDef.shape = sensorShape;
        sensorDef.isSensor = true;
        Fixture sensor = body.createFixture(sensorDef);
        sensor.setUserData(GROUND_SENSOR_ID);
        sensorShape.dispose();

        return body;
    }

    public void update() {
        handleInput();
        clampToScreen();
    }

    private void handleInput() {
        float velocityX = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = MOVE_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -MOVE_SPEED;
        }

        float velocityY = body.getLinearVelocity().y;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && onGround) {
            velocityY = JUMP_VELOCITY;
            onGround = false;
        }

        body.setLinearVelocity(velocityX, velocityY);
    }

    private void clampToScreen() {
        float minX = width / 2f;
        float maxX = Gdx.graphics.getWidth() - width / 2f;
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        if (x < minX) {
            body.setTransform(minX, y, 0f);
        } else if (x > maxX) {
            body.setTransform(maxX, y, 0f);
        }
    }

    public void draw(ShapeRenderer shapes) {
        Vector2 position = body.getPosition();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.9f, 0.3f, 0.3f, 1f);
        shapes.rect(position.x - width / 2f, position.y - height / 2f, width, height);
        shapes.end();
    }
}
