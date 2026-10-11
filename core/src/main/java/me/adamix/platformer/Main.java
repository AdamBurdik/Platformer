package me.adamix.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private static final float TIME_STEP = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    
    private static final float GRAVITY = -9.8f;

    private ShapeRenderer shapes;
    private World world;

    private Player player;
    private StaticObject ground;
    private StaticObject block;
    private StaticObject platform;

    @Override
    public void create() {
        shapes = new ShapeRenderer();
        world = new World(new Vector2(0, GRAVITY), true);

        player = new Player(world, 50, 150, 50, 50);

        ground = new StaticObject(world, 0, 0, 640, 10);
        block = new StaticObject(world, 200, 10, 60, 40);
        platform = new StaticObject(world, 450, 150, 140, 20);

        world.setContactListener(new CollisionListener(player));
    }

    @Override
    public void render() {
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        player.update();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        ground.draw(shapes);
        block.draw(shapes);
        platform.draw(shapes);
        player.draw(shapes);
    }

    @Override
    public void dispose() {
        shapes.dispose();
        world.dispose();
    }
}
