package ca.effacious.learn.dflow.funcs;


import com.google.cloud.dataflow.sdk.transforms.Aggregator;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.Sum;
import com.google.cloud.dataflow.sdk.values.KV;

import java.util.regex.Pattern;

public class FilterEmptyRowsFN extends DoFn<String,String> {

    @Override
    public void processElement(ProcessContext c) {

        String line = c.element().trim();

        if (line.isEmpty()) {
            return;
        }

        c.output(line);
    }
}
