/* 
 * HaoRan ImageFilter Classes v0.4
 * Copyright (C) 2012 Zhenjun Dai
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation.
 */


package HaoRan.ImageFilter;

import android.app.Activity;

public class FillPatternFilter  implements IImageFilter{
	private Image pattern;

    public FillPatternFilter(Activity activity, int resourceId)
    {
        this.pattern = Image.LoadImage(activity, resourceId);
    }

    public Image process(Image imageIn)
    {
        int r, g, b;
        for (int x = 0; x < imageIn.getWidth(); x++)
        {
            for (int y = 0; y < imageIn.getHeight(); y++)
            {
                int xx = x % pattern.getWidth();
                int yy = y % pattern.getHeight();

                r = imageIn.getRComponent(x, y) + pattern.getRComponent(xx, yy);
                g = imageIn.getGComponent(x, y) + pattern.getGComponent(xx, yy);
                b = imageIn.getBComponent(x, y) + pattern.getBComponent(xx, yy);
                imageIn.setPixelColor(x, y, Image.SAFECOLOR(r), Image.SAFECOLOR(g), Image.SAFECOLOR(b));
            }
        }
        return imageIn;
    }
}
