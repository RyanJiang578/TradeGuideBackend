package TradeGuide.AI;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RetrieveValue{
    @Value("${currentvaluekey}")
    private String Key;
    final  HttpClient client = HttpClient.newHttpClient();
    public Double getValue(String symbol) throws Exception{
        String URL="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+symbol+"&apikey="+Key;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonOBJ = new JSONObject(response.body());
        JSONObject quote = jsonOBJ.getJSONObject("Global Quote");
        return Double.valueOf(quote.getString("05. price"));
    }
}