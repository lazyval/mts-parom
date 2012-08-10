package me.levelapp.parom.model.events;

public enum RotateWheelEvent implements BaseEvent {
    TURN_WHEEL_ON, TURN_WHEEL_OFF;

    @Override
    public String toString() {
        return this.name();
    }
}
