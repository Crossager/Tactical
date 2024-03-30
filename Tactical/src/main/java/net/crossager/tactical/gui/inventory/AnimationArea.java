package net.crossager.tactical.gui.inventory;

import java.util.Objects;

public final class AnimationArea {
    private final int updateInterval;
    private final int taskId;
    private final int fromX;
    private final int fromY;
    private final int toX;
    private final int toY;
    private int lastUpdateTime;

    public AnimationArea(int updateInterval, int taskId, int fromX, int fromY, int toX, int toY) {
        this.updateInterval = updateInterval;
        this.taskId = taskId;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public int updateInterval() {
        return updateInterval;
    }

    public int taskId() {
        return taskId;
    }

    public int fromX() {
        return fromX;
    }

    public int fromY() {
        return fromY;
    }

    public int toX() {
        return toX;
    }

    public int toY() {
        return toY;
    }

    public int lastUpdateTime() {
        return lastUpdateTime;
    }

    public void lastUpdateTime(int lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AnimationArea) obj;
        return this.fromX == that.fromX ||
                this.fromY == that.fromY ||
                this.toX == that.toX ||
                this.toY == that.toY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateInterval, fromX, fromY, toX, toY);
    }

    @Override
    public String toString() {
        return "AnimationArea[" +
                "updateInterval=" + updateInterval + ", " +
                "fromX=" + fromX + ", " +
                "fromY=" + fromY + ", " +
                "toX=" + toX + ", " +
                "toY=" + toY + ']';
    }

}
