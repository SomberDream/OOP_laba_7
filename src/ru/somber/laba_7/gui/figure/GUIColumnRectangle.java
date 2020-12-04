package ru.somber.laba_7.gui.figure;

import ru.somber.laba_7.util.Vector2F;

public class GUIColumnRectangle extends AABBFigure {
    private static final Vector2F templateVertexA = new Vector2F(-1F, -1.5F);
    private static final Vector2F templateVertexB = new Vector2F(1F, -1.5F);
    private static final Vector2F templateVertexC = new Vector2F(1F, 1.5F);
    private static final Vector2F templateVertexD = new Vector2F(-1F, 1.5F);


    public GUIColumnRectangle(double x, double y, float size) {
        super(x, y, size,
              templateVertexA, templateVertexB,
              templateVertexC, templateVertexD);
    }


    @Override
    public String toString() {
        return "GUIColumnRectangle{ " + super.toString() + "}";
    }

}