package me.adamix.platformer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionListener implements ContactListener {
    private final Player player;
    private int groundContacts = 0;

    public CollisionListener(Player player) {
        this.player = player;
    }

    private static boolean isGroundSensor(Fixture fixture) {
        return Player.GROUND_SENSOR_ID.equals(fixture.getUserData());
    }

    @Override
    public void beginContact(Contact contact) {
        if (involvesGroundSensor(contact)) {
            groundContacts++;
            player.setOnGround(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        if (involvesGroundSensor(contact)) {
            groundContacts = Math.max(0, groundContacts - 1);
            if (groundContacts == 0) {
                player.setOnGround(false);
            }
        }
    }

    private static boolean involvesGroundSensor(Contact contact) {
        return isGroundSensor(contact.getFixtureA()) || isGroundSensor(contact.getFixtureB());
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
}
