/* 
 * HaoRan ImageFilter Classes v0.3
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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;


public class TileReflectionFilter extends RadialDistortionFilter {
	
    // angle ==> radian
    double AngleToRadian (int nAngle) {return IImageFilter.LIB_PI * nAngle / 180.0;}

	double   m_sin, m_cos ;
    double   m_scale ;
    double   m_curvature ;
	final   int aasamples = 17;
	Point[]   m_aapt = new Point[aasamples];
	
	public TileReflectionFilter (int nSquareSize, int nCurvature){
	    this(nSquareSize, nCurvature, 45);
	}
	
	/**
	Constructor \n
	param -45 <= nAngle <= 45 \n
	param 2 <= nSquareSize <= 200 \n
	param -20 <= nCurvature <= 20
	*/
	public TileReflectionFilter (int nSquareSize, int nCurvature, int nAngle)
	{
	    nAngle = Function.FClamp (nAngle, -45, 45) ;
	    m_sin = Math.sin (AngleToRadian(nAngle)) ;
	    m_cos = Math.cos (AngleToRadian(nAngle)) ;
	
	    nSquareSize = Function.FClamp (nSquareSize, 2, 200) ;
	    m_scale = IImageFilter.LIB_PI / nSquareSize ;
	
	    nCurvature = Function.FClamp (nCurvature, -20, 20) ;
	    if (nCurvature == 0)
	        nCurvature = 1 ;
	    m_curvature = nCurvature * nCurvature / 10.0 * (Math.abs(nCurvature)/nCurvature) ;
	
	    for (int i=0 ; i < aasamples ; i++)
	    {
	        double  x = (i * 4) / (double)aasamples,
	                y = i / (double)aasamples ;
	        x = x - (int)x ;
	        m_aapt[i] = new Point((float)(m_cos * x + m_sin * y), (float)(m_cos * y - m_sin * x));
	    }
	}
	
	public Image process(Image imageIn) {
	{
		  int r, g, b;
		  int width = imageIn.getWidth();
		  int height = imageIn.getHeight();
		  double hw = width / 2.0;
          double hh = imageIn.getHeight() / 2.0;
		  for(int x = 0 ; x < width ; x++){
			  for(int y = 0 ; y < height ; y++){
			    int i = (int)(x - hw);
				int j = (int)(y - hh);
				b=0; g=0; r=0;
				for (int mm=0 ; mm < aasamples ; mm++){
					double   u = i + m_aapt[mm].X ;
					double   v = j - m_aapt[mm].Y ;

					double   s =  m_cos * u + m_sin * v ;
					double   t = -m_sin * u + m_cos * v ;

					s += m_curvature * Math.tan(s * m_scale) ;
					t += m_curvature * Math.tan(t * m_scale) ;
					u = m_cos * s - m_sin * t ;
					v = m_sin * s + m_cos * t ;
        
					int xSample = (int)(hw + u) ;
					int ySample = (int)(hh + v) ;

					xSample = Function.FClamp (xSample, 0, width -1) ;
					ySample = Function.FClamp (ySample, 0, height-1) ;

					r += imageIn.getRComponent(xSample, ySample);
			        g += imageIn.getGComponent(xSample, ySample);
			        b += imageIn.getBComponent(xSample, ySample);
				 }
				 imageIn.setPixelColor(x, y, Image.SAFECOLOR(r/aasamples), Image.SAFECOLOR(g/aasamples), Image.SAFECOLOR(b/aasamples));
			  }
		  }
		  return imageIn;
	 }
   }
}
