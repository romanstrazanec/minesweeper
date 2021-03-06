package sk.romanstrazanec.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Tile {
    private final Point start;
    private float width;
    private int state, numberOfNearBombs, color;
    private boolean bomb, flag;
    private final boolean colorfulNumbers;

    public Tile(Point start, float width, int color, boolean colorfulNumbers) {
        numberOfNearBombs = 0;
        state = 0;
        bomb = false;
        flag = false;

        this.start = start;
        this.width = width;
        this.color = color;
        this.colorfulNumbers = colorfulNumbers;
    }

    private void paintTile(Canvas canvas, Paint paint, int color) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(start.x, start.y, start.x + width, start.y + width, paint);
        paint.setColor(color);
        canvas.drawRect(start.x + width / 60, start.y + width / 60, start.x + width - width / 60, start.y + width - width / 60, paint);
    }

    private void paintSelectedTile(Canvas canvas, Paint paint) {
        switch (color) {
            case Color.BLUE:
                paintTile(canvas, paint, Color.CYAN);
                break;
            case Color.GREEN:
                paintTile(canvas, paint, Color.YELLOW);
                break;
            case Color.YELLOW:
                paintTile(canvas, paint, Color.WHITE);
                break;
            case Color.RED:
                paintTile(canvas, paint, Color.MAGENTA);
                break;
        }
    }

    private void paintBomb(Canvas canvas, Paint paint, int color) {
        paintTile(canvas, paint, color);
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(start.x + width / 2, start.y + width / 2, (width - width / 12) / 2, paint);
    }

    private void paintBlownBomb(Canvas canvas, Paint paint) {
        if (color == Color.RED) paintBomb(canvas, paint, Color.YELLOW);
        else paintBomb(canvas, paint, Color.RED);
    }

    private void paintFlag(Canvas canvas, Paint paint) {
        paintTile(canvas, paint);

        if (color == Color.RED) paint.setColor(Color.YELLOW);
        else paint.setColor(Color.RED);

        canvas.drawRect(start.x + width / 12, start.y + width / 12, start.x + width - width / 12, start.y + width / 2 - width / 12, paint);
        paint.setColor(Color.WHITE);
        canvas.drawLine(start.x + width - width / 12, start.y + width / 12, start.x + width - width / 12, start.y + width - width / 12, paint);
    }

    private void paintOpenTile(Canvas canvas, Paint paint) {
        paintTile(canvas, paint);

        if (colorfulNumbers) {
            switch (numberOfNearBombs) {
                case 1:
                    paint.setColor(Color.BLUE);
                    break;
                case 2:
                    paint.setColor(Color.GREEN);
                    break;
                case 3:
                    paint.setColor(Color.RED);
                    break;
                case 4:
                    paint.setColor(Color.MAGENTA);
                    break;
                case 5:
                    paint.setColor(Color.YELLOW);
                    break;
                case 6:
                    paint.setColor(Color.rgb(255, 128, 0));
                    break;
                case 7:
                    paint.setColor(Color.rgb(255, 0, 128));
                    break;
                case 8:
                    paint.setColor(Color.CYAN);
                    break;
                default:
                    break;
            }
        } else paint.setColor(Color.BLACK);

        if (numberOfNearBombs != 0) {
            paint.setTextSize(width / 3);
            canvas.drawText(String.valueOf(numberOfNearBombs), start.x + width / 2 - width / 12, start.y + width / 2 + width / 12, paint);
        }
    }

    private void paintTile(Canvas canvas, Paint paint) {
        switch (color) {
            case Color.BLUE:
            case Color.GREEN:
                paintTile(canvas, paint, Color.GRAY);
                break;
            case Color.YELLOW:
                paintTile(canvas, paint, Color.rgb(200, 150, 0));
                break;
            case Color.RED:
                paintTile(canvas, paint, Color.rgb(255, 0, 128));
                break;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        switch (state) {
            case 0:
                paintTile(canvas, paint, color);
                break;
            case 1:
                paintSelectedTile(canvas, paint);
                break;
            case 2:
                paintOpenTile(canvas, paint);
                break;
            case 3:
                switch (color) {
                    case Color.BLUE:
                    case Color.GREEN:
                        paintBomb(canvas, paint, Color.GRAY);
                        break;
                    case Color.YELLOW:
                        paintBomb(canvas, paint, Color.rgb(200, 150, 0));
                        break;
                    case Color.RED:
                        paintBomb(canvas, paint, Color.rgb(255, 0, 128));
                        break;
                }
                break;
            case 4:
                paintBlownBomb(canvas, paint);
                break;
            case 5:
                paintFlag(canvas, paint);
                break;
            default:
                break;
        }
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        if (isClosed()) {
            this.flag = flag;
            state = flag ? 5 : 0;
        }
    }

    public boolean isClosed() {
        return (state != 2 && state != 4);
    }

    public void setState(int state) {
        this.state = state;
    }

    public void openTile() {
        state = bomb ? 3 : 2;
    }

    public int getNumberOfNearBombs() {
        return numberOfNearBombs;
    }

    public void setNumberOfNearBombs(int numberOfNearBombs) {
        this.numberOfNearBombs = numberOfNearBombs;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
