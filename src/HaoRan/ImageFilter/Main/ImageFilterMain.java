package HaoRan.ImageFilter.Main;

import java.util.ArrayList;
import java.util.List;

import HaoRan.ImageFilter.AutoAdjustFilter;
import HaoRan.ImageFilter.BannerFilter;
import HaoRan.ImageFilter.BigBrotherFilter;
import HaoRan.ImageFilter.BlackWhiteFilter;
import HaoRan.ImageFilter.BlindFilter;
import HaoRan.ImageFilter.BlockPrintFilter;
import HaoRan.ImageFilter.BrickFilter;
import HaoRan.ImageFilter.BrightContrastFilter;
import HaoRan.ImageFilter.CleanGlassFilter;
import HaoRan.ImageFilter.ColorQuantizeFilter;
import HaoRan.ImageFilter.ColorToneFilter;
import HaoRan.ImageFilter.ComicFilter;
import HaoRan.ImageFilter.EdgeFilter;
import HaoRan.ImageFilter.FeatherFilter;
import HaoRan.ImageFilter.FilmFilter;
import HaoRan.ImageFilter.FocusFilter;
import HaoRan.ImageFilter.GammaFilter;
import HaoRan.ImageFilter.GaussianBlurFilter;
import HaoRan.ImageFilter.Gradient;
import HaoRan.ImageFilter.IImageFilter;
import HaoRan.ImageFilter.IllusionFilter;
import HaoRan.ImageFilter.Image;
import HaoRan.ImageFilter.InvertFilter;
import HaoRan.ImageFilter.LensFlareFilter;
import HaoRan.ImageFilter.LightFilter;
import HaoRan.ImageFilter.LomoFilter;
import HaoRan.ImageFilter.MistFilter;
import HaoRan.ImageFilter.MonitorFilter;
import HaoRan.ImageFilter.MosaicFilter;
import HaoRan.ImageFilter.NeonFilter;
import HaoRan.ImageFilter.NightVisionFilter;
import HaoRan.ImageFilter.NoiseFilter;
import HaoRan.ImageFilter.OilPaintFilter;
import HaoRan.ImageFilter.OldPhotoFilter;
import HaoRan.ImageFilter.PaintBorderFilter;
import HaoRan.ImageFilter.PixelateFilter;
import HaoRan.ImageFilter.PosterizeFilter;
import HaoRan.ImageFilter.R;
import HaoRan.ImageFilter.RadialDistortionFilter;
import HaoRan.ImageFilter.RainBowFilter;
import HaoRan.ImageFilter.RaiseFrameFilter;
import HaoRan.ImageFilter.RectMatrixFilter;
import HaoRan.ImageFilter.ReflectionFilter;
import HaoRan.ImageFilter.ReliefFilter;
import HaoRan.ImageFilter.SaturationModifyFilter;
import HaoRan.ImageFilter.SceneFilter;
import HaoRan.ImageFilter.SepiaFilter;
import HaoRan.ImageFilter.SharpFilter;
import HaoRan.ImageFilter.ShiftFilter;
import HaoRan.ImageFilter.SmashColorFilter;
import HaoRan.ImageFilter.SoftGlowFilter;
import HaoRan.ImageFilter.SupernovaFilter;
import HaoRan.ImageFilter.ThreeDGridFilter;
import HaoRan.ImageFilter.ThresholdFilter;
import HaoRan.ImageFilter.TileReflectionFilter;
import HaoRan.ImageFilter.TintFilter;
import HaoRan.ImageFilter.VignetteFilter;
import HaoRan.ImageFilter.VintageFilter;
import HaoRan.ImageFilter.WaterWaveFilter;
import HaoRan.ImageFilter.XRadiationFilter;
import HaoRan.ImageFilter.ZoomBlurFilter;
import HaoRan.ImageFilter.Distort.BulgeFilter;
import HaoRan.ImageFilter.Distort.RippleFilter;
import HaoRan.ImageFilter.Distort.TwistFilter;
import HaoRan.ImageFilter.Distort.WaveFilter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageFilterMain extends Activity {

	private ImageView imageView;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		imageView= (ImageView) findViewById(R.id.imgfilter);
		textView = (TextView) findViewById(R.id.runtime);
		//ע����androidϵͳ�ϣ��ֻ�ͼƬ�ߴ羡��������480*480��Χ��,�����ڸ�˹����ʱ��������ڴ����������
		Bitmap bitmap = BitmapFactory.decodeResource(ImageFilterMain.this.getResources(), R.drawable.image);
		imageView.setImageBitmap(bitmap);

		LoadImageFilter();
	}

	/**
	 * ����ͼƬfilter
	 */
	private void LoadImageFilter() {
		Gallery gallery = (Gallery) findViewById(R.id.galleryFilter);
		final ImageFilterAdapter filterAdapter = new ImageFilterAdapter(
				ImageFilterMain.this);
		gallery.setAdapter(new ImageFilterAdapter(ImageFilterMain.this));
		gallery.setSelection(2);
		gallery.setAnimationDuration(3000);
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				IImageFilter filter = (IImageFilter) filterAdapter.getItem(position);
				new processImageTask(ImageFilterMain.this, filter).execute();
			}
		});
	}

	public class processImageTask extends AsyncTask<Void, Void, Bitmap> {
		private IImageFilter filter;
        private Activity activity = null;
		public processImageTask(Activity activity, IImageFilter imageFilter) {
			this.filter = imageFilter;
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			textView.setVisibility(View.VISIBLE);
		}

		public Bitmap doInBackground(Void... params) {
			Image img = null;
			try
	    	{
				Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.image);
				img = new Image(bitmap);
				if (filter != null) {
					img = filter.process(img);
				}
				return img.getImage();
	    	}
			catch(Exception e){
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result != null){
				super.onPostExecute(result);
				imageView.setImageBitmap(result);	
			}
			textView.setVisibility(View.GONE);
		}
	}

	public class ImageFilterAdapter extends BaseAdapter {
		private class FilterInfo {
			public int filterID;
			public IImageFilter filter;

			public FilterInfo(int filterID, IImageFilter filter) {
				this.filterID = filterID;
				this.filter = filter;
			}
		}

		private Context mContext;
		private List<FilterInfo> filterArray = new ArrayList<FilterInfo>();

		public ImageFilterAdapter(Context c) {
			mContext = c;
			//�����˾��б�
			
			//v0.3  Ŀǰ ���ṩ73��Ч��
			filterArray.add(new FilterInfo(R.drawable.zoomblur_filter, new ZoomBlurFilter(30)));
			filterArray.add(new FilterInfo(R.drawable.threedgrid_filter, new ThreeDGridFilter(16, 100)));
			filterArray.add(new FilterInfo(R.drawable.colortone_filter, new ColorToneFilter(Color.rgb(33, 168, 254), 192)));
			filterArray.add(new FilterInfo(R.drawable.colortone_filter2, new ColorToneFilter(0x00FF00, 192)));//green
			filterArray.add(new FilterInfo(R.drawable.colortone_filter3, new ColorToneFilter(0xFF0000, 192)));//blue
			filterArray.add(new FilterInfo(R.drawable.colortone_filter4, new ColorToneFilter(0x00FFFF, 192)));//yellow
			filterArray.add(new FilterInfo(R.drawable.softglow_filter, new SoftGlowFilter(10, 0.1f, 0.1f)));
			filterArray.add(new FilterInfo(R.drawable.tilereflection_filter, new TileReflectionFilter(20, 8)));
			filterArray.add(new FilterInfo(R.drawable.blind_filter1, new BlindFilter(true, 96, 100, 0xffffff)));
			filterArray.add(new FilterInfo(R.drawable.blind_filter2, new BlindFilter(false, 96, 100, 0x000000)));
			filterArray.add(new FilterInfo(R.drawable.raiseframe_filter, new RaiseFrameFilter(20)));
			filterArray.add(new FilterInfo(R.drawable.shift_filter, new ShiftFilter(10)));
			filterArray.add(new FilterInfo(R.drawable.wave_filter, new WaveFilter(25, 10)));
			filterArray.add(new FilterInfo(R.drawable.bulge_filter, new BulgeFilter(-97)));
			filterArray.add(new FilterInfo(R.drawable.twist_filter, new TwistFilter(27, 106)));
			filterArray.add(new FilterInfo(R.drawable.ripple_filter, new RippleFilter(38, 15, true)));
			filterArray.add(new FilterInfo(R.drawable.illusion_filter, new IllusionFilter(3)));
			filterArray.add(new FilterInfo(R.drawable.supernova_filter, new SupernovaFilter(0x00FFFF,20,100)));
			filterArray.add(new FilterInfo(R.drawable.lensflare_filter, new LensFlareFilter()));
			filterArray.add(new FilterInfo(R.drawable.posterize_filter, new PosterizeFilter(2)));
			filterArray.add(new FilterInfo(R.drawable.gamma_filter, new GammaFilter(50)));
			filterArray.add(new FilterInfo(R.drawable.sharp_filter, new SharpFilter()));
			
			//v0.2
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new ComicFilter()));
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene())));//green
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene1())));//purple
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene2())));//blue
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene3())));
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new FilmFilter(80f)));
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new FocusFilter()));
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new CleanGlassFilter()));
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0x00FF00)));//green
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0x00FFFF)));//yellow
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0xFF0000)));//blue
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new LomoFilter()));
			
			//v0.1
			filterArray.add(new FilterInfo(R.drawable.invert_filter, new InvertFilter()));
			filterArray.add(new FilterInfo(R.drawable.blackwhite_filter, new BlackWhiteFilter()));
			filterArray.add(new FilterInfo(R.drawable.edge_filter, new EdgeFilter()));
			filterArray.add(new FilterInfo(R.drawable.pixelate_filter, new PixelateFilter()));
			filterArray.add(new FilterInfo(R.drawable.neon_filter, new NeonFilter()));
			filterArray.add(new FilterInfo(R.drawable.bigbrother_filter, new BigBrotherFilter()));
			filterArray.add(new FilterInfo(R.drawable.monitor_filter, new MonitorFilter()));
			filterArray.add(new FilterInfo(R.drawable.relief_filter, new ReliefFilter()));
			filterArray.add(new FilterInfo(R.drawable.brightcontrast_filter,new BrightContrastFilter()));
			filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter,	new SaturationModifyFilter()));
			filterArray.add(new FilterInfo(R.drawable.threshold_filter,	new ThresholdFilter()));
			filterArray.add(new FilterInfo(R.drawable.noisefilter,	new NoiseFilter()));
			filterArray.add(new FilterInfo(R.drawable.banner_filter1, new BannerFilter(10, true)));
			filterArray.add(new FilterInfo(R.drawable.banner_filter2, new BannerFilter(10, false)));
			filterArray.add(new FilterInfo(R.drawable.rectmatrix_filter, new RectMatrixFilter()));
			filterArray.add(new FilterInfo(R.drawable.blockprint_filter, new BlockPrintFilter()));
			filterArray.add(new FilterInfo(R.drawable.brick_filter,	new BrickFilter()));
			filterArray.add(new FilterInfo(R.drawable.gaussianblur_filter,	new GaussianBlurFilter()));
			filterArray.add(new FilterInfo(R.drawable.light_filter,	new LightFilter()));
			filterArray.add(new FilterInfo(R.drawable.mosaic_filter,new MistFilter()));
			filterArray.add(new FilterInfo(R.drawable.mosaic_filter,new MosaicFilter()));
			filterArray.add(new FilterInfo(R.drawable.oilpaint_filter,	new OilPaintFilter()));
			filterArray.add(new FilterInfo(R.drawable.radialdistortion_filter,new RadialDistortionFilter()));
			filterArray.add(new FilterInfo(R.drawable.reflection1_filter,new ReflectionFilter(true)));
			filterArray.add(new FilterInfo(R.drawable.reflection2_filter,new ReflectionFilter(false)));
			filterArray.add(new FilterInfo(R.drawable.saturationmodify_filter,	new SaturationModifyFilter()));
			filterArray.add(new FilterInfo(R.drawable.smashcolor_filter,new SmashColorFilter()));
			filterArray.add(new FilterInfo(R.drawable.tint_filter,	new TintFilter()));
			filterArray.add(new FilterInfo(R.drawable.vignette_filter,	new VignetteFilter()));
			filterArray.add(new FilterInfo(R.drawable.autoadjust_filter,new AutoAdjustFilter()));
			filterArray.add(new FilterInfo(R.drawable.colorquantize_filter,	new ColorQuantizeFilter()));
			filterArray.add(new FilterInfo(R.drawable.waterwave_filter,	new WaterWaveFilter()));
			filterArray.add(new FilterInfo(R.drawable.vintage_filter,new VintageFilter()));
			filterArray.add(new FilterInfo(R.drawable.oldphoto_filter,new OldPhotoFilter()));
			filterArray.add(new FilterInfo(R.drawable.sepia_filter,	new SepiaFilter()));
			filterArray.add(new FilterInfo(R.drawable.rainbow_filter,new RainBowFilter()));
			filterArray.add(new FilterInfo(R.drawable.feather_filter,new FeatherFilter()));
			filterArray.add(new FilterInfo(R.drawable.xradiation_filter,new XRadiationFilter()));
			filterArray.add(new FilterInfo(R.drawable.nightvision_filter,new NightVisionFilter()));

			filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter,null/* �˴�������ԭͼЧ�� */));
		}

		public int getCount() {
			return filterArray.size();
		}

		public Object getItem(int position) {
			return position < filterArray.size() ? filterArray.get(position).filter
					: null;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Bitmap bmImg = BitmapFactory
					.decodeResource(mContext.getResources(),
							filterArray.get(position).filterID);
			int width = 100;// bmImg.getWidth();
			int height = 100;// bmImg.getHeight();
			bmImg.recycle();
			ImageView imageview = new ImageView(mContext);
			imageview.setImageResource(filterArray.get(position).filterID);
			imageview.setLayoutParams(new Gallery.LayoutParams(width, height));
			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);// ������ʾ��������
			return imageview;
		}
	};

}
