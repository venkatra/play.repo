package ca.effacious.learn.dflow.meetup.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by d3vl0p3r on 2017-03-05.
 */
public class Group {

    @Key
    public List<GroupTopics> topics;

    @Key("group_city")
    public String city;

    @Key("group_country")
    public String country;


    @Key("group_state")
    public String state;

    @Key("group_name")
    public String name;

    @Key("group_id")
    public Long gid;


}
