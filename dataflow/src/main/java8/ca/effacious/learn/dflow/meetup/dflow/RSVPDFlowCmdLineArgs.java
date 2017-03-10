package ca.effacious.learn.dflow.meetup.dflow;

import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.dataflow.sdk.options.*;
import com.google.cloud.dataflow.sdk.util.gcsfs.GcsPath;


public interface RSVPDFlowCmdLineArgs extends PipelineOptions {

    @Description("Project-id")
    @Default.String("get2know-gcp-2017")
    String getProject();
    void setProject(String project_id);


    @Description("Topic to subscribe")
    String getTopic();
    void setTopic(String value);

    @Description("Subscription to Topic")
    String getTopicSubscription();
    void setTopicSubscription(String value);

    @Description("BigQuery dataset name")
    @Default.String("meetup_rsvp")
    String getBigQueryDataset();
    void setBigQueryDataset(String dataset);

    @Description("BigQuery table name")
    @Default.String("rsvp_location")
    String getBigQueryTable();
    void setBigQueryTable(String table);

}