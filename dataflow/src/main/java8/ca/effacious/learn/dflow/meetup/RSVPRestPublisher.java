package ca.effacious.learn.dflow.meetup;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Used for publishing meetup rsvp events to the gcp cloud pub-sub topic 'meetup-rsvp'.
 *
 */
public class RSVPRestPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(RSVPRestPublisher.class);
    private static final GenericUrl MEETUP_RSVP_STREAM_URL = new GenericUrl("http://stream.meetup.com/2/rsvps");
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static void main(String[] args) throws IOException {

        //Build the get request
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(MEETUP_RSVP_STREAM_URL);

        LOG.info("Connecting to url ...");
        HttpResponse response = request.execute();

        BufferedInputStream bis = new BufferedInputStream(response.getContent());
        BufferedReader rsvp_stream_response = new BufferedReader(
                new InputStreamReader(bis));

        try {
            final Random r = new Random();
            final AtomicLong messages_published = new AtomicLong();
            final RestPublisher publisher = new RestPublisher();

            rsvp_stream_response.lines().forEach(rsvp -> {
                //identify the message sequence
                long mseq_no = messages_published.incrementAndGet();

                Timestamp event_timestamp = new Timestamp(System.currentTimeMillis());
                int publish_delay_time = 0;
                if(mseq_no%10 == 0) {
                    publish_delay_time = r.nextInt(60*1000*10) ;

                    //act like as if there was a delay in publishing
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(event_timestamp.getTime());
                    cal.add(Calendar.SECOND, publish_delay_time);
                    event_timestamp = new Timestamp(cal.getTime().getTime());

                    try {
                        //forcibly sleep for a sec.
                        Thread.sleep(1000);
                    }catch (Exception ex){
                        //ignore
                    }
                }
                //publish message
                publisher.publish_message(mseq_no ,publish_delay_time ,event_timestamp ,rsvp );

                //LOG.info("-> " + rsvp);
            });

        } finally {
            //close  stream
            rsvp_stream_response.close();

        }

        LOG.info("Disconnected ...");
    }

}
