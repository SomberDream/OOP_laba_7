package ru.somber.laba_7.gui.figure;

import ru.somber.laba_7.util.Vector2F;

public class GUIRowRectangle extends AABBFigure {
    private static final Vector2F templateVertexA = new Vector2F(-1.5F, -1F);
    private static final Vector2F templateVertexB = new Vector2F(1.5F, -1F);
    private static final Vector2F templateVertexC = new Vector2F(1.5F, 1F);
    private static final Vector2F templateVertexD = new Vector2F(-1.5F, 1F);


    public GUIRowRectangle(double x, double y, float size) {
        super(x, y, size,
              templateVertexA, templateVertexB,
              templateVertexC, templateVertexD);
    }


    @Override
    public String toString() {
        return "GUIRowRectangle{ " + super.toString() + "}";
    }

}
