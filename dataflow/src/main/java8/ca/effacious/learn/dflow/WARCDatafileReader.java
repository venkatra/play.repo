package ca.effacious.learn.dflow;

import ca.effacious.learn.dflow.funcs.ExtractWARCDataFN;
import ca.effacious.learn.dflow.funcs.FilterEmptyRowsFN;
import ca.effacious.learn.dflow.funcs.FormatOutputFN;
import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.runners.BlockingDataflowPipelineRunner;
import com.google.cloud.dataflow.sdk.transforms.*;
import com.google.cloud.dataflow.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads the input WARC data file and spits out
 *  - WARC-IP-ADDRESS
 *  - WARC-Target-URI
 *  - server
 *
 *  found in the warc resoponse.
 */
public class WARCDatafileReader {

    private static final Logger LOG = LoggerFactory.getLogger(WARCDatafileReader.class);

    public static void main(String[] args) {
        DFlowCmdLineArgs options = PipelineOptionsFactory.fromArgs(args).withValidation()
                .as(DFlowCmdLineArgs.class);

        //execution is on google dataflow
        options.setRunner(BlockingDataflowPipelineRunner.class);

        // Create the Pipeline object with the options we defined above.
        Pipeline p = Pipeline.create(options);

        // Apply the pipeline's transforms.
        p.apply(TextIO.Read.from(options.getInputFile()))

                //filters only those lines which has the information that we need and spits out the same
                .apply(ParDo.named("FilterOutWARCRecord").of(new ExtractWARCDataFN() ))

                //concatenates the output into a single csv line
                .apply("FormatResults", MapElements.via(new FormatOutputFN()))

                //remove empty rows
                .apply(ParDo.named("FilterOutEmptyRows").of(new FilterEmptyRowsFN() ))

                //remove duplicate entries
                .apply(RemoveDuplicates.<String>create())

                //store the output into a storage file (csv)
                .apply(TextIO.Write.to(options.getOutput()));

        // Run the pipeline.
        p.run();
    }

}
