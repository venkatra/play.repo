package ca.effacious.learn.dflow;

import com.google.cloud.dataflow.sdk.options.*;
import com.google.cloud.dataflow.sdk.util.gcsfs.GcsPath;


public interface DFlowCmdLineArgs extends PipelineOptions {
    @Description("Path of the file to read from")
    //Default.String("gs://dataflow-samples/shakespeare/kinglear.txt")
    String getInputFile();
    void setInputFile(String value);

    @Description("Path of the file to write to")
    @Default.InstanceFactory(OutputFactory.class)
    String getOutput();
    void setOutput(String value);

    /**
     * Returns "gs://${YOUR_STAGING_DIRECTORY}/counts.txt" as the default destination.
     */
    class OutputFactory implements DefaultValueFactory<String> {
        @Override
        public String create(PipelineOptions options) {
            DataflowPipelineOptions dataflowOptions = options.as(DataflowPipelineOptions.class);
            if (dataflowOptions.getStagingLocation() != null) {
                return GcsPath.fromUri(dataflowOptions.getStagingLocation())
                        .resolve("extract.txt").toString();
            } else {
                throw new IllegalArgumentException("Must specify --output or --stagingLocation");
            }
        }
    }

}