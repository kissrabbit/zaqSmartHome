/**
 * 
 */
package com.zaq.smartHome.ws.httpHandle;

import org.apache.log4j.Logger;


public abstract class BaseController {
  protected Logger logger = Logger.getLogger(getClass());

  /**
   * 返回成功的JSON字符串
   */
  protected final String SUCCESS = "{\"success\":true}";
  /**
   * 返回失败字符串
   */
  protected final String FAILURE = "{\"success\":false}";
}
