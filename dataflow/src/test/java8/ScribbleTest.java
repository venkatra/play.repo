import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.dataflow.sdk.values.KV;
import org.apache.commons.lang3.BooleanUtils;
import org.codehaus.plexus.util.FileUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by d3vl0p3r on 2017-02-27.
 */
public class ScribbleTest {

    public static void main(String[] args) throws IOException {
        String json = FileUtils.fileRead("/Users/d3vl0p3r/Downloads/rsvp.json");

        System.out.println("Data : " + json);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<String, Object>();

        // convert JSON string to Map
        map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});

        System.out.println(map.get("rsvp_id"));

        Map<String,Object> group_map = (Map<String,Object>)map.get("group");

        System.out.println(group_map.get("group_city"));
        System.out.println(group_map.get("group_country"));
        System.out.println(map.get("response") + " " + BooleanUtils.toBoolean("" + map.get("response")));
    }
}
