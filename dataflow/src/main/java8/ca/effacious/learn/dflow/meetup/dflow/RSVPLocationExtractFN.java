package ca.effacious.learn.dflow.meetup.dflow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by d3vl0p3r on 2017-03-08.
 */
public class RSVPLocationExtractFN extends DoFn<String, RSVPLocation> {
    private static final Logger LOG = LoggerFactory.getLogger(RSVPLocationExtractFN.class);
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void processElement(ProcessContext c) {

        //parse the fields from the json message
        String rsvp_json = c.element().trim();
        boolean parsed = false;
        RSVPLocation rsvp = new RSVPLocation();

        try {
            Map<String, Object> map = mapper.readValue(rsvp_json, new TypeReference<Map<String, Object>>() {
            });
            Map<String, Object> group_map = (Map<String, Object>) map.get("group");

            rsvp.setRid("" + map.get("rsvp_id"));
            rsvp.setCity("" + group_map.get("group_city"));
            rsvp.setCountry("" + group_map.get("group_country"));

            if (map.containsKey("response")) {
                rsvp.setAttending(
                        BooleanUtils.toBoolean("" + map.get("response"))
                );
            }

            c.output(rsvp);

        } catch (IOException e) {
            LOG.error("Unable to extact rsvp information ", e);
        }

    }
}
