package ru.somber.laba_7.figure;

import ru.somber.laba_7.util.Vector2F;

public class Quad extends AABBFigure {
    private static final Vector2F templateVertexA = new Vector2F(-1, -1);
    private static final Vector2F templateVertexB = new Vector2F(1, -1);
    private static final Vector2F templateVertexC = new Vector2F(1, 1);
    private static final Vector2F templateVertexD = new Vector2F(-1, 1);


    public Quad() {
        super(templateVertexA, templateVertexB,
              templateVertexC, templateVertexD);
    }


    @Override
    public String toString() {
        return "GUIQuad{ " + super.toString() + "}";
    }

    @Override
    protected String getDescriptorForSave() {
        return getDescriptor();
    }


    public static String getDescriptor() {
        return "quad";
    }

}
