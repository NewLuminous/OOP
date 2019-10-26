package com.game;

import com.badlogic.gdx.physics.box2d.*;

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

    public Body getCircleBody(float posx, float posy, float radius, Materials material, BodyDef.BodyType bodyType, boolean fixedRotation){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        boxBody.createFixture(getFixture(material,circleShape));
        circleShape.dispose();
        return boxBody;
    }

    public Body getCircleBody(float posx, float posy, float radius, Materials material, BodyDef.BodyType bodyType){
        return getCircleBody(posx, posy, radius, material, bodyType, false);
    }

    public Body getCircleBody(float posx, float posy, float radius, BodyDef.BodyType bodyType){
        return getCircleBody(posx, posy, radius, Materials.STEEL, bodyType, false);
    }

    public Body getCircleBody(float posx, float posy, float radius){
        return getCircleBody(posx, posy, radius, Materials.STEEL, BodyDef.BodyType.KinematicBody, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height, Materials material, BodyDef.BodyType bodyType, boolean fixedRotation){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width/2, height/2);
        boxBody.createFixture(getFixture(material,poly));
        poly.dispose();

        return boxBody;
    }

    public Body getBoxBody(float posx, float posy, float width, float height, Materials material, BodyDef.BodyType bodyType){
        return getBoxBody(posx, posy, width, height, material, bodyType, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height, BodyDef.BodyType bodyType){
        return getBoxBody(posx, posy, width, height, Materials.STEEL, bodyType, false);
    }

    public Body getBoxBody(float posx, float posy, float width, float height){
        return getBoxBody(posx, posy, width, height, Materials.STEEL, BodyDef.BodyType.KinematicBody, false);
    }
}
