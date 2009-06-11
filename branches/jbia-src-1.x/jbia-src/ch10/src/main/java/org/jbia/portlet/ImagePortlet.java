/*
 *  Copyright 2008, Javid Jamae and Peter Johnson
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */
package org.jbia.portlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Example portlet that display an image.
 */
public class ImagePortlet extends GenericPortlet {

  /**
   * Name of the JSP used when the portlet is in view mode.
   */
  private String jspView;

  /**
   * Name of the JSP used when the portlet is in help mode.
   */
  private String jspHelp;

  /**
   * Name of the JSP used when the portlet is in edit mode.
   */
  private String jspEdit;

  /**
   * Initialization method that reads the initialization parameters to set the
   * JSPs used woth the various modes.
   * 
   * @see javax.portlet.GenericPortlet#init(javax.portlet.PortletConfig)
   */
  @Override
  public void init(PortletConfig config) throws PortletException {
    super.init(config);
    jspView = config.getInitParameter("jsp-view");
    jspHelp = config.getInitParameter("jsp-help");
    jspEdit = config.getInitParameter("jsp-edit");
  }

  /**
   * Method called when the portlet is in view mode. This method populates an
   * ImageBean with the necessary information about the image to display, and
   * passes the bean to the vie JSP.
   * 
   * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest,
   *      javax.portlet.RenderResponse)
   */
  @Override
  protected void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    resolveImage(request, response, true);
    response.setContentType("text/html");
    PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher(
        jspView);
    prd.include(request, response);
  }

  /**
   * Method called when the portlet is in help mode. This method defers to the
   * help JSP.
   * 
   * @see javax.portlet.GenericPortlet#doHelp(javax.portlet.RenderRequest,
   *      javax.portlet.RenderResponse)
   */
  @Override
  protected void doHelp(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    response.setContentType("text/html");
    PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher(
        jspHelp);
    prd.include(request, response);
  }

  /**
   * Method called when the portlet is in help mode. This method populates an
   * ImageBean with the current information about the image to display and
   * passes that data to the edit JSP, which will display that information.
   * 
   * @see javax.portlet.GenericPortlet#doEdit(javax.portlet.RenderRequest,
   *      javax.portlet.RenderResponse)
   */
  @Override
  protected void doEdit(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    ImageBean image = resolveImage(request, response, false);

    /*
     * We have thei mage bean, but still need to set the action URL:
     */
    PortletURL urlEdit = response.createActionURL();
    urlEdit.setPortletMode(PortletMode.VIEW);
    image.setEditAction(urlEdit.toString());

    response.setContentType("text/html");
    PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher(
        jspEdit);
    prd.include(request, response);
  }

  /**
   * This method is called when the user makes changes on the edit JSP and
   * clicks the submit button. The method updates the portlet preferences, which
   * are used to store the information about the desired image.
   * 
   * @see javax.portlet.GenericPortlet#processAction(javax.portlet.ActionRequest,
   *      javax.portlet.ActionResponse)
   */
  @Override
  public void processAction(ActionRequest request, ActionResponse response)
      throws PortletException, IOException {
    String title = request.getParameter("title");
    String url = request.getParameter("url");
    String regex = request.getParameter("regex");
    String submit = request.getParameter("submit");
    if (submit != null) {
      try {
        PortletPreferences pref = request.getPreferences();
        pref.setValue("title", title);
        pref.setValue("url", url);
        pref.setValue("regex", regex);
        pref.store();
      } catch (ReadOnlyException e) {/* do nothing */}
    }
    response.setPortletMode(PortletMode.VIEW);
  }

  /**
   * This method determines what image is to be displayed in view mode. If there
   * is no regex value, then the URL is a direct URL for the image. If there is
   * a regex value, then the URL is for a page containing the image. In this
   * case, the method defers to the extractImage method to resolve the actual
   * URL for the image. The method uses this information to populate the
   * ImageBean object.
   * 
   * @param request
   *          The portlet request object.
   * @param response
   *          The portlet response object.
   * @param isView
   *          An indication of whether the portlet is in view or edit mode.
   * @return An ImageBean object containing the proper data to provide to the
   *         JSP that will display the portlet contents.
   */
  private final ImageBean resolveImage(
      RenderRequest request,
      RenderResponse response,
      boolean isView) {

    /*
     * The preferences hold the current information about the image to display.
     */
    PortletPreferences pref = request.getPreferences();
    String title = pref.getValue("title", null);
    String url = pref.getValue("url", null);
    String regex = pref.getValue("regex", null);

    /*
     * Create and populate the image bean, based on if in view mode (in which
     * case we might need to calculate the image's URL) or in edit mode (in
     * which case the preferences are uses as-is to populate the bean).
     */
    ImageBean image = new ImageBean();
    if (isView) {
      if (regex == null || regex.trim().length() == 0) {
        image.setUrl(url);
      } else {
        String extracted = extractImage(url, regex);
        if (extracted != null) {
          image.setUrl(buildImageUrl(url, extracted));
        }
      }
      image.setLink(url);
    } else {
      image.setTitle(title);
      image.setUrl(url);
      image.setRegex(regex);
    }

    /**
     * Place the bean where the JSP has access to it, and set the window title.
     */
    request.setAttribute("image", image);
    if (title != null && title.trim().length() > 0) {
      response.setTitle(title);
    }
    return image;
  }

  /**
   * This method is called only if the URL is for a Web page that contains the
   * desired image. This method attempts to get the Web page and if successful
   * extracts the image's URL for the page. Note that image URL extracted from
   * the Web page is not suitable for use within the portlet because that URL
   * might be relative to the Web page. What we really need is an absolute URL,
   * that is where the buildImageUrl method comes in.
   * 
   * @param url
   *          The URL for the Web page.
   * @param regex
   *          The regular expression used to extract the image URL.
   * @return The URL for the image, as embedded within the Web page, or null if
   *         there were any errors.
   */
  private final String extractImage(String url, String regex) {
    String result = null;
    String page = getWebPage(url);
    if (page != null) {

      /*
       * We have the contents of the Web page, now try to extract the URL for
       * the image using the given regular expression:
       */
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(page);
      if (m.find()) {

        /*
         * Success - we have the image's URL:
         */
        result = m.group(1);
      }
    }
    return result;
  }

  /**
   * This method gets the Web page and returns its contents as a string.
   * 
   * @param strUrl
   *          The URL of the Web page.
   * @return The page contents, as a string, or null if there were any errors.
   */
  private final String getWebPage(String strUrl) {
    String result = null;
    InputStream stream = null;
    try {
      URL url = new URL(strUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      int code = connection.getResponseCode();
      if (code >= 200 && code < 300) {
        stream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String str = null;
        StringBuffer bufResponse = new StringBuffer(32000);
        bufResponse.setLength(0);
        while ((str = in.readLine()) != null) {
          bufResponse.append(str);
        }
        result = bufResponse.toString();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {}
      }
    }
    return result;
  }

  /**
   * This method examines the URL for the Web page, and the extracted URL for
   * the image, and builds an absolute URL that can be used by the portlet to
   * display the image.
   * 
   * @param url
   *          The URL for the Web page containing the image.
   * @param extracted
   *          The URL for the image, as extracted fro mthe Web page.
   * @return The absolute URL for the image.
   */
  private String buildImageUrl(String url, String extracted) {
    String result = null;

    /*
     * If the extracted URL is already an absolute URL, we are done:
     */
    if (extracted.toLowerCase().startsWith("http:")) {
      result = extracted;
    } else {
      String prefix = null;

      /*
       * If the extracted image URL starts with a '/', then the URL is relative
       * to the Web site, in which case we need to extract only the site name
       * from the Web page URL. Example:
       *   url = http://some.com/foo/bar
       *   extracted = /images/some.jpg
       * The resulting image URL is:
       *   http://some.com/images/some.jpg
       */
      if (extracted.startsWith("/")) {
        int slash = url.indexOf('/', "http://".length() + 1);
        if (slash == -1) {
          prefix = url;
        } else {
          prefix = url.substring(0, slash);
        }
      } else {

        /*
         * The extracted image does not start with a '/', then the URL is
         * relative to the Web page, in which case we need to extract everything
         * up to the last slash from the Web page URL. Example:
         *   url = http://some.com/foo/bar
         *   extracted = images/some.jpg
         * The resulting image URL is:
         *   http://some.com/foo/images/some.jpg
         */
        int slash = url.lastIndexOf('/');
        if (slash == -1) {
          prefix = url + "/";
        } else {
          prefix = url.substring(0, slash + 1);
        }
      }
      result = prefix + extracted;
    }
    return result;
  }
}
