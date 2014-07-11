package com.ezest.javafx.demogallery.animation;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import com.ezest.javafx.common.MathEval;

/**
 * ExpressionTransitionMaker is free to use, distribute, alter, and abuse in any
 * way shape or form. I am not responsible for any harm or casualties this code
 * my cause.
 *
 * @author Jose Martinez, jmartine_1026@yahoo.com
 * @Ref http://www.javacodegeeks.com/2012/09/expression-based-pathtransitions-in.html
 */
public class ExpressionTransitionMaker {

    //////////////////////////////////////////////////////////////////////////
    //enums
    public enum GraphType {

        vertical("y"), horizontal("x"), polar("a");
        private String var;

        GraphType(String var) {
            this.var = var;
        }

        public String getVar() {
            return var;
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    //protected variables
    protected LinkedList<ExpressionEntry> expressionEntries = new LinkedList<ExpressionEntry>();
    protected float speed;
    ///////////////////////////////////////////////////////////////////////////
    //private variables
    private final double MAX = 2000;
    private final double MIN = -100;

    ///////////////////////////////////////////////////////////////////////////
    //constructors
    ExpressionTransitionMaker(float speed) {
        this.speed = speed;
    }

    ///////////////////////////////////////////////////////////////////////////
    //public functions
    public void clear() {
        this.expressionEntries.clear();
    }

    public void addExpressionEntry(double start, double end, double poll, GraphType type, String exp) throws IllegalArgumentException {
        addExpressionEntry(start, end, poll, type, exp, 0, 0);
    }

    public void addExpressionEntry(double start, double end, double poll, GraphType type, String exp, double xOrigin, double yOrigin) throws IllegalArgumentException {
//        System.out.println("in ExpressionTransitionMaker.addExpressionEntry");
        if (start == end) {
            throw new IllegalArgumentException("start == end");
        }
        if (start > end && poll > 0) {
            throw new IllegalArgumentException("start > end && poll > 0");
        }
        if (poll == 0) {
            throw new IllegalArgumentException("poll == 0");
        }
        if (exp == null || exp.isEmpty()) {
            throw new IllegalArgumentException("exp == null || exp.isEmpty()s");
        }
        this.expressionEntries.add(new ExpressionEntry(start, end, poll, type, exp, xOrigin, yOrigin));
    }

    public int size() {
        return this.expressionEntries.size();
    }

    public SequentialTransition getSequentialTransition() {
        SequentialTransition seqTrans = new SequentialTransition();

        for (ExpressionEntry entry : this.expressionEntries) {
            LinkedList<Point2D> points = getPoints(entry);
            PathTransition pathTrans = new PathTransition();
            pathTrans.setDuration(Duration.seconds(getPathDistance(points) / speed));
            pathTrans.setPath(getPath(points));
            pathTrans.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTrans.setInterpolator(Interpolator.LINEAR);
            seqTrans.getChildren().add(pathTrans);
        }
        return seqTrans;
    }

    ///////////////////////////////////////////////////////////////////////////
    //private functions
    private LinkedList<Point2D> getPoints(ExpressionEntry entry) {
//        System.out.println("in ExpressionTransitionMaker.getPoints");
        double curPos = entry.getStartPosition();
        double endPos = entry.getEndPosition();
        double totalDistance = Math.abs(curPos - endPos);
        double curDistance = 0;

        LinkedList<Point2D> points = new LinkedList<Point2D>();

        String var = entry.getType().getVar();

        MathEval mathEval = new MathEval();
        while (curDistance < totalDistance) {
            mathEval.setVariableRequired(true);
            mathEval.setVariable(var, curPos);
            String exp = entry.getExpression();

            try {
                double x = 0;
                double y = 0;

                if (entry.getType() == GraphType.polar) {
                    x = mathEval.evaluate("(" + exp + ")*cos(a) + ") + entry.xOrigin;
                    y = mathEval.evaluate("(" + exp + ")*sin(a) + ") + entry.yOrigin;
                } else if (entry.getType() == GraphType.horizontal) {
                    x = curPos + entry.xOrigin;
                    y = mathEval.evaluate(exp) + entry.yOrigin;
                } else if (entry.getType() == GraphType.vertical) {
                    x = mathEval.evaluate(exp) + entry.xOrigin;
                    y = curPos + entry.yOrigin;
                }

//                System.out.println("...x = " + x + ", y = " + y);
                if (x > MAX && x < MIN && y > MAX && y < MIN) {
                    return points;
                }
                points.add(new Point2D(x, y));

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            curPos += entry.getPollInterval();
            curDistance += Math.abs(entry.getPollInterval());

        }
        return points;
    }

    private Path getPath(LinkedList<Point2D> points) {
        Path path = new Path();
        ListIterator<Point2D> it = points.listIterator();
        while (it.hasNext()) {
            Point2D pnt = it.next();
            if (it.previousIndex() == 0) {
                path.getElements().add(new MoveTo(pnt.getX(), pnt.getY()));
            } else {
                path.getElements().add(new LineTo(pnt.getX(), pnt.getY()));
            }
        }
        return path;
    }

    private double getPathDistance(List<Point2D> points) {
        Point2D p2 = null;
        double distance = 0;
        for (Point2D p1 : points) {
            if (p2 != null) {
                distance += Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
            }
            p2 = p1;
        }
//        System.out.println("in getPathDistance, distance = " + distance);
        return distance;
    }

    ///////////////////////////////////////////////////////////////////////////
    //inner class
    private class ExpressionEntry {

        /////////////////////////
        //private variables
        String expression;
        double startPosition;
        double endPosition;
        double pollInterval;
        double xOrigin = 0;
        double yOrigin = 0;
        GraphType type;

        /////////////////////////
        //contructors
        public ExpressionEntry(double start, double end, double poll, GraphType et, String exp, double xOrigin, double yOrigin) {
            assert exp != null;
            expression = exp;
            startPosition = start;
            endPosition = end;
            pollInterval = poll;
            type = et;
            this.xOrigin = xOrigin;
            this.yOrigin = yOrigin;
        }

        ////////////////////////
        //public functions
        public String getExpression() {
            return expression;
        }

        public double getStartPosition() {
            return startPosition;
        }

        public double getEndPosition() {
            return endPosition;
        }

        public double getPollInterval() {
            return pollInterval;
        }

        public GraphType getType() {
            return type;
        }
    }
}
