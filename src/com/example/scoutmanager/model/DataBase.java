package com.example.scoutmanager.model;

import com.example.scoutmanager.model.context.ApplicationDataContext;

/**
 * Application Singleton Data Context.
 * @author   Luis Mart’nez (@LuisMartiZa)
 * @category Data Context
 * @version  1.0
 */

public class DataBase {
	public static ApplicationDataContext Context;
	
	/**
	 * This method initialize the singleton instance of Application ObjectContext.
	 * @param pContext
	 */
	public static void initialize(android.content.Context pContext) {
		if (Context == null) {
			Context = new ApplicationDataContext(pContext);
		}
	}
}
