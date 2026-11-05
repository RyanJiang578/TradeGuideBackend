package TradeGuide.AI;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import org.json.JSONArray;
@Service
public class OpenRouter{
  @Value("${openrouterapikey}")
  private String APIkey;
  final String URL="https://openrouter.ai/api/v1/chat/completions";
  public Reply send(String type,prompt data) throws Exception{
    String text="";
    if (type.equals("stockCheck")){
      JSONObject prompt = new JSONObject();
      prompt.put("role","system");
      prompt.put("content", "You are a financial analyst. " +
        "Analyze stocks based on the user's risk level and investment timeframe. " +
        "Return valid JSON only with exactly these fields: " +
        "currentValue, projectedValue, information." +
        "currentValue will be provided, projectedValue is the value you believe the stock will hit in the specified timeframe for currentValue use only the provided currentValue"+
        "information is for relevant information including: recent activity, price drops, and future plans."+
        "If there is reason to believe so you can put the projectedValue as lower than current value. limit 250 words for information" +
        "currentValue and projectedValue must be in double form with no dollar sign do not used nested JSON so you will only return currentValue, projectedValue, and information"
    );
    JSONObject userPrompt = new JSONObject();
    userPrompt.put("role", "user");
    userPrompt.put("content",
    "Analyze the stock " + data.getName() + " at current price " + data.getValue()+
    ". The user's risk level is " + translate(data.getRiskLevel(),"risk") +
    " and their investment timeframe is "+data.getTimeFrame()+"months" + "."
    );
    JSONArray messages = new JSONArray(); 
    messages.put(prompt);
    messages.put(userPrompt);
    JSONObject requestBody = new JSONObject();
    requestBody.put("model", "openai/gpt-4.1-mini");
    requestBody.put("messages", messages);
    JSONObject responseFormat = new JSONObject();
    responseFormat.put("type", "json_object");
    requestBody.put("response_format", responseFormat);
    
    text = requestBody.toString();
    }
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
    .header("Authorization", "Bearer " + APIkey)
    .header("Content-Type", "application/json")
    .POST(HttpRequest.BodyPublishers.ofString(text))
    .build();
    HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
    JSONObject message = new JSONObject(response.body()).getJSONArray("choices").getJSONObject(0).getJSONObject("message");
    JSONObject json=new JSONObject(message.getString("content"));
    Reply reply=new Reply();
    reply.setcurrentValue(data.getValue());
    reply.setprojectedValue(json.getDouble("projectedValue"));
    reply.setInformation(json.getString("information"));
    reply.setResponseCode(0);
    return reply;
    }
    public String translate(int number,String type){
      if (type.equals("risk")){
        if (number==1){
          return "low";
        }
        if (number==2){
          return "low/medium";
        }
        if (number==3){
          return "medium";
        }
        if(number==4){
          return "medium/high";
        }
        if (number==5){
          return "high";
        }
      }
      return"";
    }
}
