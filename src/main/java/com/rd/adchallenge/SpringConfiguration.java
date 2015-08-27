/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ImportResource("classpath:/applicationContext.xml")
@PropertySource("classpath:/application.properties")
public class SpringConfiguration {
}

