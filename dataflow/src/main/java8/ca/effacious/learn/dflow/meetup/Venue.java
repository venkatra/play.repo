package ca.effacious.learn.dflow.meetup;

import com.google.api.client.util.Key;

/**
 * Created by d3vl0p3r on 2017-03-05.
 */
public class Venue {

    @Key("venue_name")
    public String name;

    @Key("venue_id")
    public Long vid;

}
