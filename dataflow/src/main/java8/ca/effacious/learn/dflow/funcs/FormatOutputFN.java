package ca.effacious.learn.dflow.funcs;


import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.SimpleFunction;
import com.google.cloud.dataflow.sdk.values.KV;

import java.net.URL;
import java.util.regex.Pattern;

public class FormatOutputFN extends SimpleFunction<KV<String, String>, String> {

    private transient String prev_val = "";

        @Override
        public String apply(KV<String, String> input) {

            if(input.getKey().equalsIgnoreCase("WARC-IP-Address")) {
                prev_val = input.getValue();

            }else if(input.getKey().equalsIgnoreCase("WARC-Target-URI")) {

                String target_uri = input.getValue().replaceAll(",","_");

                prev_val = prev_val + "," + target_uri;

                try {
                    URL turl = new URL(target_uri);
                    prev_val = prev_val + "," + turl.getAuthority();

                }catch (Exception ex) {
                    prev_val = prev_val + ",";
                }

                //return prev_val;

            }else if(input.getKey().equalsIgnoreCase("Server")) {
                prev_val = prev_val + "," + input.getValue();
                return prev_val;

            }

            return "";
        }
}
