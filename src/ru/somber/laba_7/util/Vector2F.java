package ru.somber.laba_7.util;

import java.util.Objects;

public class Vector2F {
    private float x;
    private float y;


    public Vector2F() {
        this(0, 0);
    }

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2F vector2F = (Vector2F) o;
        return Float.compare(vector2F.x, x) == 0 &&
                Float.compare(vector2F.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "Vector2F{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

}
