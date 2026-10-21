package me.adamix.platformer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class KontrolaKolizi implements ContactListener {

    private Hrac hrac;
    private int i = 0;
    private int p = 0;
    private int l = 0;
    private boolean res = false;

    public int RESPAWN_X = 50;
    public int RESPAWN_Y = 50;

    public KontrolaKolizi(Hrac hrac){
        this.hrac = hrac;
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("dotknul se A :" + contact.getFixtureA().getUserData() + "s   B: " + contact.getFixtureB().getUserData());
        if ("HracDetektorDole".equals(contact.getFixtureA().getUserData()) || "HracDetektorDole".equals(contact.getFixtureB().getUserData() )){
            i++;
            hrac.isOnGround = true;
        }if ("HracDetektorPrava".equals(contact.getFixtureA().getUserData()) || "HracDetektorPrava".equals(contact.getFixtureB().getUserData() )){
            p++;
            hrac.necoNaPravo = true;
        }if ("HracDetektorLeva".equals(contact.getFixtureA().getUserData()) || "HracDetektorLeva".equals(contact.getFixtureB().getUserData() )){
            l++;
            hrac.necoNaLevo = true;
        }
        if (("killable".equals(contact.getFixtureA().getUserData()) || "killable".equals(contact.getFixtureB().getUserData())) &&
            ("Player".equals(contact.getFixtureA().getUserData()) ||"Player".equals(contact.getFixtureB().getUserData())) )
        {
           res = true;
        }
    }

    public void  respawn(){
        if (res == true){
            hrac.body.setTransform(RESPAWN_X, RESPAWN_Y, 0);
            res = false;
        }
    }

    @Override
    public void endContact(Contact contact) {
        if ("HracDetektorDole".equals(contact.getFixtureA().getUserData()) || "HracDetektorDole".equals(contact.getFixtureB().getUserData() )){
            i--;

        }if ("HracDetektorPrava".equals(contact.getFixtureA().getUserData()) || "HracDetektorPrava".equals(contact.getFixtureB().getUserData() )){
            p--;

        }if ("HracDetektorLeva".equals(contact.getFixtureA().getUserData()) || "HracDetektorLeva".equals(contact.getFixtureB().getUserData() )){
            l--;
        }
        if (i <= 0){
            hrac.isOnGround = false;
        }
        if (l <=0){
            hrac.necoNaLevo = false;
        }
        if (p <= 0){
            hrac.necoNaPravo = false;
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold manifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }
}
