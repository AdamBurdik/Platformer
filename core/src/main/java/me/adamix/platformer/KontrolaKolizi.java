package me.adamix.platformer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class KontrolaKolizi implements ContactListener {

    private Hrac hrac;
    private int i = 0;

    public KontrolaKolizi(Hrac hrac){
        this.hrac = hrac;
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("dotknul se A :" + contact.getFixtureA().getUserData() + "s   B: " + contact.getFixtureB().getUserData());
        if ("HracDetektor".equals(contact.getFixtureA().getUserData()) || "HracDetektor".equals(contact.getFixtureB().getUserData() )){
            i++;
        }
        hrac.isOnGround = true;
    }

    @Override
    public void endContact(Contact contact) {
        if ("HracDetektor".equals(contact.getFixtureA().getUserData()) || "HracDetektor".equals(contact.getFixtureB().getUserData() )){
           i--;
        }
        if (i <= 0){
            hrac.isOnGround = false;
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold manifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }
}
