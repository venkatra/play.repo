package ca.effacious.learn.dflow.meetup.model;

import com.google.api.client.util.Key;

/**
 * Created by d3vl0p3r on 2017-03-05.
 */
public class Event {
    @Key("event_url")
    public String eurl;

    @Key("event_id")
    public Long eid;

}
