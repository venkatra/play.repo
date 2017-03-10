package ca.effacious.learn.dflow.meetup.dflow;

import com.google.api.services.bigquery.model.TableReference;
import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.io.BigQueryIO;
import com.google.cloud.dataflow.sdk.io.PubsubIO;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.runners.BlockingDataflowPipelineRunner;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Applies transformation logic on rsvp message present in the rsvp topic.
 *
 */
public class RSVPDflowAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(RSVPDflowAdapter.class);

    public static void main(String[] args) {
        RSVPDFlowCmdLineArgs options = PipelineOptionsFactory.fromArgs(args).withValidation()
                .as(RSVPDFlowCmdLineArgs.class);

        //execution is on google dataflow
        options.setRunner(BlockingDataflowPipelineRunner.class);

        //BQ table information
        TableReference tableRef = new TableReference();
        tableRef.setProjectId(options.getProject());
        tableRef.setDatasetId(options.getBigQueryDataset());
        tableRef.setTableId(options.getBigQueryTable());

        // Create the Pipeline object with the options we defined above.
        Pipeline p = Pipeline.create(options);

        // Apply the pipeline's transforms.
        p.apply(PubsubIO.Read
                .timestampLabel("event_time_label")
                .subscription(options.getTopicSubscription()))

                //Extracts the rsvp response from the message
                .apply(
                        ParDo.named("ExtractResponse")
                                .of(new RSVPLocationExtractFN()))

                //convert to table row
                .apply(
                        ParDo.named("Transform2TRow")
                                .of(new BQWriteFormat_RSVPLocation()))

                //write to big query table
                 .apply(BigQueryIO.Write
                         .withWriteDisposition(
                                 BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                         .to(tableRef)
                         .withSchema(BQWriteFormat_RSVPLocation.getSchema())
                 );

        // Run the pipeline.
        p.run();
    }

}
