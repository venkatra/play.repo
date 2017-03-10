package ca.effacious.learn.dflow.meetup.dflow;

import java.io.Serializable;

class RSVPLocation implements Serializable {

    String rid;
    boolean attending;
    String city;
    String state;
    String country;
    int mdelay;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public boolean isAttending() {
        return attending;
    }

    public void setAttending(boolean attending) {
        this.attending = attending;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMdelay() {
        return mdelay;
    }

    public void setMdelay(int mdelay) {
        this.mdelay = mdelay;
    }
}
