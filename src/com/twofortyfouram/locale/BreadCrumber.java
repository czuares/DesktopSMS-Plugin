/*
 * Copyright 2011 two forty four a.m. LLC <http://www.twofortyfouram.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at <http://www.apache.org/licenses/LICENSE-2.0>
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.twofortyfouram.locale;

import com.koushikdutta.desksms.plugin.R;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Utility class to generate a breadcrumb title string for {@code Activity} instances in Locale.
 * <p>
 * This class cannot be instantiated.
 */
public final class BreadCrumber
{
    /**
     * Private constructor prevents instantiation
     * 
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private BreadCrumber()
    {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }

    /**
     * Static helper method to generate breadcrumbs. Breadcrumb strings will be properly formatted for the current language,
     * including right-to-left languages, as long as the proper {@link R.string#twofortyfouram_locale_breadcrumb_format} string
     * resources have been created.
     * 
     * @param c {@code Context} for loading platform resources. Cannot be null.
     * @param intent {@code Intent} to extract the breadcrumb from.
     * @param currentCrumb The last element of the breadcrumb path.
     * @return {@code String} presentation of the breadcrumb. If the intent parameter is null, then this method returns
     *         currentCrumb. If currentCrumb is null, then this method returns the empty string "". If intent contains a private
     *         Serializable instances as an extra, then this method returns the empty string "".
     */
    public static CharSequence generateBreadcrumb(final Context c, final Intent intent, final String currentCrumb)
    {
        if (c == null)
        {
            throw new IllegalArgumentException("context cannot be null"); //$NON-NLS-1$
        }

        try
        {
            if (currentCrumb == null)
            {
                Log.w(Constants.LOG_TAG, "currentCrumb cannot be null"); //$NON-NLS-1$
                return ""; //$NON-NLS-1$
            }
            if (intent == null)
            {
                Log.w(Constants.LOG_TAG, "intent cannot be null"); //$NON-NLS-1$
                return currentCrumb;
            }

            /*
             * Note: this is vulnerable to a private serializable attack, but the try-catch will solve that
             */
            final String breadcrumbString = intent.getStringExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BREADCRUMB);
            if (breadcrumbString != null)
            {
                return c.getString(R.string.twofortyfouram_locale_breadcrumb_format, breadcrumbString, c.getString(R.string.twofortyfouram_locale_breadcrumb_separator), currentCrumb);
            }
            return currentCrumb;
        }
        catch (final Exception e)
        {
            Log.e(Constants.LOG_TAG, "Encountered error generating breadcrumb", e); //$NON-NLS-1$
            return ""; //$NON-NLS-1$
        }
    }
}