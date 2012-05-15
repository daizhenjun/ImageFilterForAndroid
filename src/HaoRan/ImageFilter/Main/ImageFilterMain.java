package HaoRan.ImageFilter.Main;

import java.util.ArrayList;
import java.util.List;

import HaoRan.ImageFilter.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
		//注：在android系统上，手机图片尺寸尽量控制在480*480范围内,否则在高斯运算时可以造成内存溢出的问题
		Bitmap bitmap = BitmapFactory.decodeResource(ImageFilterMain.this.getResources(), R.drawable.image);
		imageView.setImageBitmap(bitmap);

		LoadImageFilter();
	}

	/**
	 * 加载图片filter
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
					img.copyPixelsFromBuffer();
				}
				return img.getImage();
	    	}
			catch(Exception e){
				if (img != null && img.destImage.isRecycled()) {
					img.destImage.recycle();
					img.destImage = null;
					System.gc(); // 提醒系统及时回收
				}
			}
			finally{
				if (img != null && img.image.isRecycled()) {
					img.image.recycle();
					img.image = null;
					System.gc(); // 提醒系统及时回收
				}
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
			// 加载滤镜列表
			filterArray.add(new FilterInfo(R.drawable.invert_filter,
					new InvertFilter()));
			filterArray.add(new FilterInfo(R.drawable.blackwhite_filter,
					new BlackWhiteFilter()));
			filterArray.add(new FilterInfo(R.drawable.edge_filter,
					new EdgeFilter()));
			filterArray.add(new FilterInfo(R.drawable.pixelate_filter,
					new PixelateFilter()));
			filterArray.add(new FilterInfo(R.drawable.neon_filter,
					new NeonFilter()));
			filterArray.add(new FilterInfo(R.drawable.bigbrother_filter,
					new BigBrotherFilter()));
			filterArray.add(new FilterInfo(R.drawable.monitor_filter,
					new MonitorFilter()));
			filterArray.add(new FilterInfo(R.drawable.relief_filter,
					new ReliefFilter()));
			filterArray.add(new FilterInfo(R.drawable.brightcontrast_filter,
					new BrightContrastFilter()));
			filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter,
					new SaturationModifyFilter()));
			filterArray.add(new FilterInfo(R.drawable.threshold_filter,
					new ThresholdFilter()));
			filterArray.add(new FilterInfo(R.drawable.noisefilter,
					new NoiseFilter()));
			filterArray.add(new FilterInfo(R.drawable.banner_filter1,
					new BannerFilter(10, true)));
			filterArray.add(new FilterInfo(R.drawable.banner_filter2,
					new BannerFilter(10, false)));
			filterArray.add(new FilterInfo(R.drawable.rectmatrix_filter,
					new RectMatrixFilter()));
			filterArray.add(new FilterInfo(R.drawable.blockprint_filter,
					new BlockPrintFilter()));
			filterArray.add(new FilterInfo(R.drawable.brick_filter,
					new BrickFilter()));
			filterArray.add(new FilterInfo(R.drawable.gaussianblur_filter,
					new GaussianBlurFilter()));
			filterArray.add(new FilterInfo(R.drawable.light_filter,
					new LightFilter()));
			filterArray.add(new FilterInfo(R.drawable.mosaic_filter,
					new MistFilter()));
			filterArray.add(new FilterInfo(R.drawable.mosaic_filter,
					new MosaicFilter()));
			filterArray.add(new FilterInfo(R.drawable.oilpaint_filter,
					new OilPaintFilter()));
			filterArray.add(new FilterInfo(R.drawable.radialdistortion_filter,
					new RadialDistortionFilter()));
			filterArray.add(new FilterInfo(R.drawable.reflection1_filter,
					new ReflectionFilter(true)));
			filterArray.add(new FilterInfo(R.drawable.reflection2_filter,
					new ReflectionFilter(false)));
			filterArray.add(new FilterInfo(R.drawable.saturationmodify_filter,
					new SaturationModifyFilter()));
			filterArray.add(new FilterInfo(R.drawable.smashcolor_filter,
					new SmashColorFilter()));
			filterArray.add(new FilterInfo(R.drawable.tint_filter,
					new TintFilter()));
			filterArray.add(new FilterInfo(R.drawable.vignette_filter,
					new VignetteFilter()));
			filterArray.add(new FilterInfo(R.drawable.autoadjust_filter,
					new AutoAdjustFilter()));
			filterArray.add(new FilterInfo(R.drawable.colorquantize_filter,
					new ColorQuantizeFilter()));
			filterArray.add(new FilterInfo(R.drawable.waterwave_filter,
					new WaterWaveFilter()));
			filterArray.add(new FilterInfo(R.drawable.vintage_filter,
					new VintageFilter()));
			filterArray.add(new FilterInfo(R.drawable.oldphoto_filter,
					new OldPhotoFilter()));
			filterArray.add(new FilterInfo(R.drawable.sepia_filter,
					new SepiaFilter()));
			filterArray.add(new FilterInfo(R.drawable.rainbow_filter,
					new RainBowFilter()));
			filterArray.add(new FilterInfo(R.drawable.feather_filter,
					new FeatherFilter()));
			filterArray.add(new FilterInfo(R.drawable.xradiation_filter,
					new XRadiationFilter()));
			filterArray.add(new FilterInfo(R.drawable.nightvision_filter,
					new NightVisionFilter()));

			filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter,
					null/* 此处会生成原图效果 */));
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
			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);// 设置显示比例类型
			return imageview;
		}
	};

}
