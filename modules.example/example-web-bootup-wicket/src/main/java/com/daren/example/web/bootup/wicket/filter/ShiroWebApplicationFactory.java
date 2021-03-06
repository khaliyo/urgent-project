package com.daren.example.web.bootup.wicket.filter;

import com.daren.example.web.bootup.wicket.IrisShiroApplication;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.atmosphere.cpr.AtmosphereFilter;
import org.ops4j.pax.wicket.api.SuperFilter;
import org.ops4j.pax.wicket.api.SuperFilters;
import org.ops4j.pax.wicket.api.WebApplicationFactory;

/**
 * Simple PaxWicket web application factory to create the WicketApplication
 * instance with an Apache Shiro superfilter.
 *
 * @author Minto van der Sluis (misl)
 */

@SuperFilters(filters = {@SuperFilter(filter= ShiroFilter.class),@SuperFilter(filter= AtmosphereFilter.class)})
public class ShiroWebApplicationFactory implements
        WebApplicationFactory<IrisShiroApplication> {

    // -------------------------------------------------------------------------
    // Object attributes
    // -------------------------------------------------------------------------

    private Class<IrisShiroApplication> wicketApplication;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public ShiroWebApplicationFactory() {
    }

    public ShiroWebApplicationFactory(Class<IrisShiroApplication> wicketApplication) {
        this.wicketApplication = wicketApplication;
    }

    // -------------------------------------------------------------------------
    // Implementing WebApplicationFactory
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public Class<IrisShiroApplication> getWebApplicationClass() {
        return wicketApplication;
    }

    /**
     * {@inheritDoc}
     */
    public void onInstantiation(IrisShiroApplication application) {

    }

    // -------------------------------------------------------------------------
    // Getters / Setters
    // -------------------------------------------------------------------------

    public void setWicketApplication(Class<IrisShiroApplication> wicketApplication) {
        this.wicketApplication = wicketApplication;
    }


}
