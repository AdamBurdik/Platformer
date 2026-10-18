package me.adamix.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    public ShapeRenderer shape;
    public Hrac hrac;

    public KontrolaKolizi colisionListener;
    public CSO randomTest;
    public World world;
    public CSO zem;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        image = new Texture("libgdx.png");
        world = new World(new Vector2(0, -9.8f), true);
        hrac = new Hrac(shape, 50,150,50,50, world);

        colisionListener = new KontrolaKolizi(hrac);
        zem = new CSO(0, 0, 10, 640, world);   // pozor: přepíše proměnnou zem z řádku výše
        randomTest = new CSO(50, 50, 40, 50, world);
        world.setContactListener(colisionListener);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        batch.end();
        randomTest.drawCSO();
        zem.drawCSO();
        hrac.doIt();
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shape.dispose();
        world.dispose();
    }
}
