package org.openjfx.geometry;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.stream.Collectors;

public class ShapePane extends Pane {
    private double paneWidth;
    private double paneHeight;

    public ShapePane(double width, double height) {
        super();
        this.paneWidth = width;
        this.paneHeight = height;
    }

    public final void drawCircle(Point point, double radius, Color color) {
        Circle circle = new Circle();
        circle.setFill(color);
        circle.setRadius(radius);
        setCenterOfCircle(circle, point);
        this.getChildren().add(circle);
    }

    public final void drawLine(Point p1, Point p2, Color color) {
        Line line = new Line();
        line.setFill(color);
        setEndpointsOfLine(line, p1, p2);
        this.getChildren().add(line);
    }

    public final void drawSquare(Point point, double length, Color color) {
        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(color);
        rectangle.setHeight(length);
        rectangle.setWidth(length);
        setCenterOfSquare(rectangle, point);
        this.getChildren().add(rectangle);
    }

    public final void eraseShapes() {
        getChildren().clear();
    }

    public final void setColorOfCircle(int i, Color color) {
        getCircle(i).setFill(color);
    }

    public final void setColorOfLine(int i, Color color) {
        getLine(i).setStroke(color);
    }

    public final void setFillOfRectangle(int i, Color color) {
        getRectangle(i).setFill(color);
    }

    public final void setCenterOfCircle(int i, Point center) {
        Circle circle = getCircle(i);
        setCenterOfCircle(circle, center);
    }

    public final void setCenterOfCircle(Circle circle, Point center) {
        circle.setCenterX(center.getX());
        circle.setCenterY(center.getY());
    }

    public final void setEndpointsOfLine(int i, Point start, Point end) {
        Line line = getLine(i);
        setEndpointsOfLine(line, start, end);
    }

    public final void setEndpointsOfLine(Line line, Point start, Point end) {
        line.setStartX(start.getX());
        line.setStartY(start.getY());
        line.setEndX(end.getX());
        line.setEndY(end.getY());
    }

    public final void setCenterOfSquare(int i, Point center) {
        Rectangle rectangle = getRectangle(i);
        setCenterOfSquare(rectangle, center);
    }

    public final void setCenterOfSquare(Rectangle rectangle, Point center) {
        rectangle.setX(center.getX() - rectangle.getHeight() / 2);
        rectangle.setY(center.getY() - rectangle.getWidth() / 2);
    }

    private Circle getCircle(int i) {
        return (Circle) getChildren().stream().filter(c -> c instanceof Circle).collect(Collectors.toList()).get(i);
    }

    private Line getLine(int i) {
        return (Line) getChildren().stream().filter(l -> l instanceof Line).collect(Collectors.toList()).get(i);
    }

    private Rectangle getRectangle(int i) {
        return (Rectangle) getChildren().stream().filter(r -> r instanceof Rectangle).collect(Collectors.toList()).get(i);
    }

    public final Point getCentrePoint() {
        return new Point(getPaneWidth() / 2, getPaneHeight() / 2);
    }

    public double getPaneWidth() {
        return paneWidth;
    }

    public void setPaneWidth(double width) {
        paneWidth = width;
    }

    public double getPaneHeight() {
        return paneHeight;
    }

    public void setPaneHeight(double height) {
        this.paneHeight = height;
    }
}
