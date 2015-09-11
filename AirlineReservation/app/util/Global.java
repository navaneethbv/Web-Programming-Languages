package util;

import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;

/**
 * Class to enable compression
 * 
 * @author Ekal.Golas
 *
 */
public class Global extends GlobalSettings {
	/*
	 * (non-Javadoc)
	 * 
	 * @see play.GlobalSettings#filters()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends EssentialFilter> Class<T>[] filters() {
		Logger.info("Compressing....");
		return new Class[] { GzipFilter.class };
	}
}