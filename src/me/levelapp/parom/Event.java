package me.levelapp.parom;

import java.util.Date;

final class Event implements Comparable<Event> {
    final String name;
    final Date date;
    final String place;
    final String description;
    final String[] images;

    public Event(
            final String name,
            final Date date,
            final String place,
            final String description,
            final String[] images)  {
        this.name = name;
        this.place = place;
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
