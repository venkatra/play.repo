package ca.effacious.learn.dflow.meetup.dflow;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.dataflow.sdk.transforms.Aggregator;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.Sum;
import com.google.cloud.dataflow.sdk.values.KV;

import java.util.ArrayList;
import java.util.List;


/**
 * Formatter for writing rsvp location to biq query table.
 */
public class BQWriteFormat_RSVPLocation extends DoFn<RSVPLocation, TableRow> {

    private final Aggregator<Long, Long> agg_responses_observed =
            createAggregator("Responses observed", new Sum.SumLongFn());

    @Override
    public void processElement(ProcessContext c) {
        RSVPLocation r = c.element();
        TableRow row = new TableRow()
                .set("rid", r.getRid())
                .set("attending", r.isAttending())
                .set("city", r.getCity())
                .set("state", r.getState())
                .set("country", r.getCountry())
                .set("mdelay", r.getMdelay());

        agg_responses_observed.addValue(1L);

        c.output(row);
    }

    /**
     * Defines the BigQuery schema used for the output.
     */
    static TableSchema getSchema() {
        List<TableFieldSchema> fields = new ArrayList<>();
        fields.add(new TableFieldSchema().setName("rid").setType("STRING"));
        fields.add(new TableFieldSchema().setName("attending").setType("BOOLEAN"));
        fields.add(new TableFieldSchema().setName("city").setType("STRING"));
        fields.add(new TableFieldSchema().setName("state").setType("STRING"));
        fields.add(new TableFieldSchema().setName("country").setType("STRING"));
        fields.add(new TableFieldSchema().setName("mdelay").setType("INTEGER"));
        TableSchema schema = new TableSchema().setFields(fields);
        return schema;
    }
}