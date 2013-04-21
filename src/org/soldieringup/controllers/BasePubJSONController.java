/**
 * 
 */
package org.soldieringup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Every JSON controller should inherit from this Controller if it contains public, non securied content
 * @author jjennings
 *
 */
@Controller
@RequestMapping("/rest/pub")
public abstract class BasePubJSONController extends BaseJSONController
{

}
