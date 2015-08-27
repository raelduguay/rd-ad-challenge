/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.common.OAuthConsumerParameter;
import org.springframework.security.oauth.common.signature.CoreOAuthSignatureMethodFactory;
import org.springframework.security.oauth.common.signature.InvalidSignatureException;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethodFactory;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.OAuthProviderSupport;
import org.springframework.security.oauth.provider.filter.CoreOAuthProviderSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class OAuthSignatureVerifierFilter extends GenericFilterBean {
  
  // Quick hack to implement Oauth signature verification based on primitives available on spring-security-oauth.
  // (didn't bothered to implement the full oauth provider for this demo code).
  
  private static final Log LOGGER = LogFactory.getLog(OAuthSignatureVerifierFilter.class);

  private OAuthProviderSupport providerSupport = new CoreOAuthProviderSupport();
  private OAuthSignatureMethodFactory signatureMethodFactory = new CoreOAuthSignatureMethodFactory();
  
  @Autowired
  private ConsumerDetailsService consumerDetailsService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
  
    if (!verifyAuthorizationHeader(httpRequest)) {
      httpResponse.setStatus(Status.FORBIDDEN.getStatusCode());
      return;
    }
    
    chain.doFilter(request, response);
  }

  protected boolean verifyAuthorizationHeader(HttpServletRequest request) {
    Map<String, String> oauthParams = providerSupport.parseParameters(request);
    
    String consumerKey = oauthParams.get(OAuthConsumerParameter.oauth_consumer_key.toString());    
    if (StringUtils.isBlank(consumerKey)) {
      throw new IllegalArgumentException("Missing " + OAuthConsumerParameter.oauth_consumer_key);
    }
    
    ConsumerDetails consumerDetails = consumerDetailsService.loadConsumerByConsumerKey(consumerKey);
    if (consumerDetails == null) {
      throw new ForbiddenException();
    }

    String signatureMethod = oauthParams.get(OAuthConsumerParameter.oauth_signature_method.toString());
    String signature = oauthParams.get(OAuthConsumerParameter.oauth_signature.toString());
    String signatureBaseString = providerSupport.getSignatureBaseString(request);

    try {
      OAuthSignatureMethod method = signatureMethodFactory.getSignatureMethod(signatureMethod, consumerDetails.getSignatureSecret(), null);
      method.verify(signatureBaseString, signature);
      return true;
    }
    catch (UnsupportedSignatureMethodException | InvalidSignatureException e) {
      LOGGER.error("OAuth Signature Verification failed", e);
      return false;
    }
  }

}

