package me.adamix.platformer;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class TileMap {

    private TiledMap map;
    private  World world;
    public ArrayList<CSO> objectArrayList = new ArrayList<>();
    public ArrayList<Killable> killableList = new ArrayList<>();


    public TileMap(World world) {
        this.world = world;
        load();
    }

    public void load(){
        map = new TmxMapLoader().load("map.tmx");
        MapLayer collisionLayer = map.getLayers().get("Collision");
        MapLayer killableLayer = map.getLayers().get("killable");
        if (collisionLayer == null || killableLayer == null){
            System.out.println("Collision layer doesnt exist meyby");
            return;
        }
        MapObjects mapObject = collisionLayer.getObjects();
        for (MapObject object : mapObject) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            CSO staticObjects = new CSO((int)rect.x, (int)rect.y, (int)rect.height, (int)rect.width, world);
            objectArrayList.add(staticObjects);
        }
        MapObjects killableObjects = killableLayer.getObjects();
        for (MapObject killableObject : killableObjects) {
            Rectangle rect = ((RectangleMapObject) killableObject).getRectangle();
            Killable kill = new Killable((int)rect.x, (int)rect.y, (int)rect.height, (int)rect.width, world);
            killableList.add(kill);
        }

    }



    public void draw(){
        for (CSO cso : objectArrayList) {
            cso.drawCSO();
        }
        for (Killable killable : killableList) {

            killable.drawCSO();
        }
    }
}
