package ru.somber.laba_7.figure;

import ru.somber.laba_7.util.Vector2F;

public class ColumnRectangle extends AABBFigure {
    private static final Vector2F templateVertexA = new Vector2F(-1F, -1.5F);
    private static final Vector2F templateVertexB = new Vector2F(1F, -1.5F);
    private static final Vector2F templateVertexC = new Vector2F(1F, 1.5F);
    private static final Vector2F templateVertexD = new Vector2F(-1F, 1.5F);


    public ColumnRectangle() {
        super(templateVertexA, templateVertexB,
              templateVertexC, templateVertexD);
    }


    @Override
    public String toString() {
        return "GUIColumnRectangle{ " + super.toString() + "}";
    }

}
