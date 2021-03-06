/*
 * ApplicationUtils.java
 *
 * Copyright (C) 2009-12 by RStudio, Inc.
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

package org.rstudio.studio.client.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rstudio.core.client.Debug;
import org.rstudio.core.client.dom.WindowEx;
import org.rstudio.core.client.regex.Pattern;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;


public class ApplicationUtils
{
   public static String getHostPageBaseURLWithoutContext(boolean includeSlash)
   {
      String replaceWith = includeSlash ? "/" : "";
      String url = GWT.getHostPageBaseURL();
      Pattern pattern = Pattern.create("/s/[A-Fa-f0-9]{5}[A-Fa-f0-9]{8}[A-Fa-f0-9]{8}/");
      return pattern.replaceAll(url, replaceWith);
   }
   
   public static String getRemainingQueryString(List<String> removeKeys)
   {  
      Map<String, List<String>> params = Window.Location.getParameterMap();
      StringBuilder queryString =  new StringBuilder();
      String prefix = "";
      for (Map.Entry<String, List<String>> entry : params.entrySet()) {
         
         // skip keys we are supposed to remove
         if (removeKeys.contains(entry.getKey()))
            continue;
         
         for (String val : entry.getValue()) {
          queryString.append(prefix)
              .append(URL.encodeQueryString(entry.getKey()))
              .append('=');
          if (val != null) {
            queryString.append(URL.encodeQueryString(val));
          }
          prefix = "&";
        }
      }
     
      // return string
      return queryString.toString();
   }
   
   public static void removeQueryParam(String param)
   {
      ArrayList<String> params = new ArrayList<String>();
      params.add(param);
      removeQueryParams(params);
   }
   
   public static void removeQueryParams(List<String> removeKeys)
   {
      // determine the new URL
      String url = GWT.getHostPageBaseURL();
      String queryString = getRemainingQueryString(removeKeys);
      if (queryString.length() > 0)
         url = url + "?" + queryString;
      
      // replace history state (try/catch in case the browser doesn't
      // support this or has it disabled)
      try
      {
         WindowEx.get().replaceHistoryState(url);
      }
      catch(Exception e)
      {
         Debug.logException(e);
      }
   }
}
