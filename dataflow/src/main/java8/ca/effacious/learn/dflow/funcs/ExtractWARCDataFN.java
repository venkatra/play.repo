package ca.effacious.learn.dflow.funcs;


import com.google.cloud.dataflow.sdk.transforms.Aggregator;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.Sum;
import com.google.cloud.dataflow.sdk.values.KV;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractWARCDataFN extends DoFn<String, KV<String,String>> {

    private final Aggregator<Long, Long> warc_ip_address =
            createAggregator("warc_ip_address", new Sum.SumLongFn());

    private final Aggregator<Long, Long> warc_target_url =
            createAggregator("warc_target_url", new Sum.SumLongFn());

    private final Pattern http_url_string_pattern = Pattern.compile("http://(.*?)\\.(.*?)/.*");


    @Override
    public void processElement(ProcessContext c) {

        String line = c.element().trim();

        if (line.isEmpty()) {
            return;
        }

        if(line.startsWith("WARC-IP-Address:")) {
            String ip_address = line.substring(line.indexOf(":")+1).trim();
            c.output(KV.of("WARC-IP-Address",ip_address));
            warc_ip_address.addValue(1L);

        }

        if(line.startsWith("WARC-Target-URI:")) {
            String target_uri = line.substring(line.indexOf(":")+1).trim();
            c.output(KV.of("WARC-Target-URI",target_uri));

            warc_target_url.addValue(1L);

        }

        if(line.startsWith("Server:")) {
            String server = line.substring(line.indexOf(":")+1).trim();
            c.output(KV.of("Server",server));
        }

    }
}
