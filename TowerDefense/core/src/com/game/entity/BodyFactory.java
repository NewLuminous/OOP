package com.game.entity;

import com.badlogic.gdx.physics.box2d.*;

/**
 * @class   BodyFactory
 * @brief   A factory which creates <code>Body</code>
 * @note    A Singleton class.
 */
public final class BodyFactory {
    private static BodyFactory instance;

    public enum Materials {
        STEEL, WOOD, RUBBER, STONE
    }

    private final float DEGTORAD = 0.0174533f;

    private World world;

    private BodyFactory(World world) {
        this.world = world;
    }

    /**
     * @brief   Gets the <code>BodyFactory</code> instance. Creates one if none exist.
     * @note    At most one <code>BodyFactory</code> will be created.
     * @param   world Where all entities of the game act in.
     * @return  The sole <code>BodyFactory</code> object.
     */
    public static BodyFactory getInstance(World world) {
        if (instance == null) instance = new BodyFactory(world);
        return instance;
    }

    private static FixtureDef getFixture(Materials material, Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch(material){
            case STEEL:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case RUBBER:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case STONE:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }

    public Body getCircleBody(float posx, float posy, float radius, boolean isSensor, Materials material, BodyDef.BodyType bodyType, boolean fixedRotation){
        /**
         * Creates a definition of the body.
         */
        BodyDef circleBodyDef = new BodyDef();
        circleBodyDef.type = bodyType;
        circleBodyDef.position.x = posx;
        circleBodyDef.position.y = posy;
        circleBodyDef.fixedRotation = fixedRotation;

        /**
         * Creates the body to attach said definition
         */
        Body circleBody = world.createBody(circleBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        circleBody.createFixture(getFixture(material,circleShape));
        circleBody.getFixtureList().first().setSensor(isSensor);
        circleShape.dispose();
        return circleBody;
    }

    public Body getCircleBody(float posx, float posy, float radius, boolean isSensor, Materials material, BodyDef.BodyType bodyType){
        return getCircleBody(posx, posy, radius, isSensor, material, bodyType, false);
    }

    public Body getCircleBody(float posx, float posy, float radius, boolean isSensor, BodyDef.BodyType bodyType){
        return getCircleBody(posx, posy, radius, isSensor, Materials.STEEL, bodyType, false);
    }

    public Body getCircleBody(float posx, float posy, float radius, boolean isSensor){
        return getCircleBody(posx, posy, radius, isSensor, Materials.STEEL, BodyDef.BodyType.KinematicBody, false);
    }

    public Body getCircleBody(float posx, float posy, float radius){
        return getCircleBody(posx, posy, radius, false, Materials.STEEL, BodyDef.BodyType.KinematicBody, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height, boolean isSensor, Materials material, BodyDef.BodyType bodyType, boolean fixedRotation){
        /**
         * Creates a definition of the body.
         */
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        /**
         * Creates the body to attach said definition
         */
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width/2, height/2);
        boxBody.createFixture(getFixture(material,poly));
        boxBody.getFixtureList().first().setSensor(isSensor);
        poly.dispose();

        return boxBody;
    }

    public Body getBoxBody(float posx, float posy, float width, float height, boolean isSensor, Materials material, BodyDef.BodyType bodyType){
        return getBoxBody(posx, posy, width, height, isSensor, material, bodyType, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height, boolean isSensor, BodyDef.BodyType bodyType){
        return getBoxBody(posx, posy, width, height, isSensor, Materials.STEEL, bodyType, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height, boolean isSensor){
        return getBoxBody(posx, posy, width, height, isSensor, Materials.STEEL, BodyDef.BodyType.KinematicBody, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height){
        return getBoxBody(posx, posy, width, height, false, Materials.STEEL, BodyDef.BodyType.KinematicBody, false);
    }
}
