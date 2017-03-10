package ca.effacious.learn.dflow.meetup;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.spotify.google.cloud.pubsub.client.Message;
import com.spotify.google.cloud.pubsub.client.Publisher;
import com.spotify.google.cloud.pubsub.client.Pubsub;
import com.spotify.google.cloud.pubsub.client.Topic;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static com.spotify.google.cloud.pubsub.client.Message.encode;

/**
 * Used for publishing message to pub-sub topic via REST.
 *
 */
class RestPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(RestPublisher.class);

    private static final String PROJECT_ID = "get2know-gcp-2017";
    private static final String MEETUP_TOPIC = "meetup-rsvp";

    private Pubsub c_pubsub = null;

    public RestPublisher() {
       c_pubsub  = Pubsub.builder()
                .build();
    }


    public void publish_message(long p_message_seqno , int p_message_delay , Timestamp p_timestamp, String p_message) {
        try {
        Message message = Message.builder()
                .attributes("mseqno", "" + p_message_seqno)
                .attributes("mdelay", "" + p_message_delay)
                .attributes("event_time_label" ,"" + p_timestamp.getTime())
                .data(encode(p_message))
                .build();

        // Publish the messages
        final List<String> messageIds = c_pubsub.publish(PROJECT_ID, MEETUP_TOPIC, message).get();
            LOG.info(String.format("Published Message ID[%s] Seq[%s] simulated lag [%d]"
                    ,messageIds,p_message_seqno,p_message_delay));

        } catch (Exception e) {
            LOG.error("Unable to publish ",e);
        }


    }

}

