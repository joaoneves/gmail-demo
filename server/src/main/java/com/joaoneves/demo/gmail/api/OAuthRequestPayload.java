package com.joaoneves.demo.gmail.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthRequestPayload implements Serializable {
	private static final long serialVersionUID = -3154804701339788167L;

	private String code;
	
	private String clientId;
	
	private String clientSecret;
	
	private final String redirectUri = "http://localhost:8083/contacts/import";
	
	private final String grantType = "authorization_code";
	
	public static List<NameValuePair> toNameValuePairArray(OAuthRequestPayload payload) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("code", payload.getCode()));
	    params.add(new BasicNameValuePair("client_id", payload.getClientId()));
	    params.add(new BasicNameValuePair("client_secret", payload.getClientSecret()));
	    params.add(new BasicNameValuePair("redirect_uri", payload.getRedirectUri()));
	    params.add(new BasicNameValuePair("grant_type", payload.getGrantType()));
		
	    return params;
	}
}
