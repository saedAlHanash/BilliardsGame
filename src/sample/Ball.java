package sample;


import javafx.scene.Group;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Vector;


class Ball extends Circle {
    double vx;
    double vy;
    double radios;
    double mass;
    double ax;
    double ay;
    boolean test = true;

    Ball(double x, double y, Group root, double radius, double vvx, double vvy, double mass, Color color) {

        super(x, y, radius, color);
        root.getChildren().add(this);
        this.vx = vvx;
        this.vy = vvy;
        this.radios = radius;
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getX() {
        return this.getCenterX();
    }

    public double getY() {
        return this.getCenterY();
    }

    public void setVx(double x) {
        vx = x;
    }

    public void setVy(double y) {
        vy = y;
    }

    public double getVelocity() {
        return Math.sqrt((vx * vx) + (vy * vy));
    }

    public double getVelocityAngle() {
        return (Math.atan2(vy, vx));
    }

    public void updateCenter() {

        setCenterX(getCenterX() + vx);
        setCenterY(getCenterY() + vy);

        double vxOld = vx, vyOld = vy;
        if (vx != 0 || vy != 0 && test) {
            ax = -Main.g * ((Main.mui * Math.cos(this.getVelocityAngle()))) / 60;
            ay = -Main.g * ((Main.mui * Math.sin(this.getVelocityAngle()))) / 60;

            vx = vx + ax;
            vy = vy + ay;
            //System.out.println("saed");

        }
        if (vxOld > 0 && vx < 0 || vxOld < 0 && vx > 0 || vyOld > 0 && vy < 0 || vyOld < 0 && vy > 0) {
            test = false;
//            vx=0;
//            vy=0;
        }
        if (!test) {
            if (vx > 0.0011) {
                vx = vx - 0.001;
                vx = vx / 1.02;
            }
            if (vx < (-0.0011)) {
                vx = vx + 0.001;
                vx = vx / 1.02;
            }
            if ((vx <= 0.011) && (vx >= (-0.011))) {
                vx = 0;
                vy =0 ;
                test= true;
            }
            if (vy > 0.0011) {
                vy = vy - 0.001;
                vy = vy / 1.02;
            }
            if (vy < (-0.0011)) {
                vy = vy + 0.001;
                vy = vy / 1.02;
            }
            if ((vy <= 0.011) && (vy >= (-0.011))) {
                vy = 0;
                vx=0;
                test=true;
            }
        }
    }

    public void collision() {

        if (getCenterX() + getRadius() >= 946) {
            if (vx > 0) vx = -vx / 2;
        }
        if (getCenterX() - getRadius() <= 252) {
            if (vx < 0) vx = -vx / 2;
        }
        if (getCenterY() + getRadius() >= 528) {
            if (vy > 0) vy = -vy / 2;
        }
        if (getCenterY() - getRadius() <= 169) {
            if (vy < 0) vy = -vy / 2;
        }

    }

}



