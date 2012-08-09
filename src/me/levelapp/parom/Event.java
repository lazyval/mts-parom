package me.levelapp.parom;

import java.util.Date;

public final class Event implements Comparable<Event> {
    public final String name;
    final Date date;
    public final String location;
    public final String description;
    final String[] images;

    public Event(
            final String name,
            final Date date,
            final String location,
            final String description,
            final String[] images)  {
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.images = images;
    }

    @Override
    final public int compareTo(final Event that) {
        return this.date.compareTo(that.date);
    }

//    @Override
//    public boolean equals(final Object that) {
//        return super.equals(that) || (this.name.equals(that.name) && this.date.equals(that.date));
//    }
}
