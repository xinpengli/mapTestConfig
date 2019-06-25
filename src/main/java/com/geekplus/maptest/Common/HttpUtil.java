package com.geekplus.maptest.Common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpUtil {
	/**
	 * post json格式的数据
	 * @param
	 * @param URL
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static JSONObject postJsonData(String URL,JSONObject request) throws ClientProtocolException, IOException{
		CloseableHttpClient client =  HttpClients.createDefault();
		 HttpResponse response;
	     String conResult;
	     JSONObject obj;
	     StringEntity entity = new StringEntity(request.toString(),"utf-8");
			entity.setContentEncoding("UTF-8");    
			entity.setContentType("application/json");
      
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(entity);
			//httpPost.setHeader("cookie", cookie);
			response = client.execute(httpPost);
			//System.out.println(response.getStatusLine());
			//resopnse.getEntity();
			conResult = EntityUtils.toString(response
                .getEntity());
			obj=JSON.parseObject(conResult);
			return obj;
	}
	public static JSONObject getRequestHeder(String version )  {
		CloseableHttpClient client =  HttpClients.createDefault();
		HttpResponse response;
		String conResult;
		JSONObject requestHeder=new JSONObject();
		requestHeder.put( "requestId",System.currentTimeMillis());
		requestHeder.put( "clientCode","rui");

		requestHeder.put( "warehouseCode","rui");
		requestHeder.put( "userId","rui");
		requestHeder.put( "userKey","rui");
		requestHeder.put( "userKey","rui");
		switch (version){
			case "2.0":

			case "3.2":

				requestHeder.put( "version","3.1.0");

		}






		return requestHeder;
	}

}
