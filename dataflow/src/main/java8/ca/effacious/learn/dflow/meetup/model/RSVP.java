package ca.effacious.learn.dflow.meetup.model;

import com.google.api.client.util.Key;

/**
 * Created by d3vl0p3r on 2017-03-05.
 */
public class RSVP {

    @Key("response")
    public boolean is_attending;

    @Key("rsvp_id")
    public Long rid;



}
