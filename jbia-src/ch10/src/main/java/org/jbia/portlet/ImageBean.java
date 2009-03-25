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

/**
 * Bean used to pass data from the image portlet to the both the view and edit
 * JSPs.
 */
public class ImageBean {

  /**
   * @see #getUrl()
   */
  private String url;

  /**
   * @see #getLink()
   */
  private String link;

  /**
   * @see #getRegex()
   */
  private String regex;

  /**
   * @see #getTitle()
   */
  private String title;

  /**
   * @see #getEditAction()
   */
  private String editAction;

  /**
   * Gets the URL that is used as the href for the image. That is, if the user
   * clicks on the image, the user is taken to this URL.
   * 
   * @return The URL for the page containing the image.
   */
  public final String getLink() {
    return link;
  }

  /**
   * @see #getLink()
   * @param link
   *          The new value for the image href.
   */
  public final void setLink(String link) {
    this.link = link;
  }

  /**
   * Gets the regular expression that can be used to extract the URL for the
   * image. This value is used only if the value of the URL property is for a
   * page containing the image, and not a direct URL to the image itself.
   * 
   * @return The regular expression.
   */
  public final String getRegex() {
    return regex;
  }

  /**
   * @see #getRegex()
   * @param regex
   *          The new regular expression to use to extract the URL of an image
   *          from the Web page containing the image.
   */
  public final void setRegex(String regex) {
    this.regex = regex;
  }

  /**
   * Gets the title to use for the image portlet window.
   * 
   * @return The image portlet window title.
   */
  public final String getTitle() {
    return title;
  }

  /**
   * @see #getTitle()
   * @param title
   *          The new image portlet window title.
   */
  public final void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the URL used to locate the image or the page containing the image.
   * 
   * @return The image or page URL.
   */
  public final String getUrl() {
    return url;
  }

  /**
   * @see #getUrl()
   * @param url
   *          The new value for the image or page URL.
   */
  public final void setUrl(String url) {
    this.url = url;
  }

  /**
   * Gets the value for the action attribute on the edit form. The value for the
   * action is portal-specific, thus its value is obtained from the
   * RednerResponse object using the createActionURL method.
   * 
   * @return The value for the action on the edit form.
   */
  public final String getEditAction() {
    return editAction;
  }

  /**
   * @see #getEditAction()
   * @param editAction
   *          The string value returned from the cretaActionURL method.
   */
  public final void setEditAction(String editAction) {
    this.editAction = editAction;
  }
}
