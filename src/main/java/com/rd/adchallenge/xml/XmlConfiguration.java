/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlConfiguration {

  @Bean
  public XPath xpath() {
    XPathFactory factory = XPathFactory.newInstance();
    return factory.newXPath();
  }
  
  @Bean
  public DocumentBuilder documentBuilder() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      return factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("Cannot create XML DocumentBuilder", e);
    }
  }
}

