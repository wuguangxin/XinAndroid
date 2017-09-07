package com.wuguangxin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuguangxin.R;

/**
 * Item项使用的View（LinearLayout+TextView的组合控件，水平排列 key-value）
 *
 * <p>Created by wuguangxin on 15/7/10 </p>
 */
public class ItemView extends LinearLayout{
	private Context context;
	private LayoutParams iconLeftParams;
	private LayoutParams iconRightParams;
	private LayoutParams keyParams;
	private LayoutParams valueParams;
	private ImageView mIconLeftView;
	private ImageView mIconRightView;
	private TextView mKeyView;
	private TextView mValueView;

	// 指示器
	private DividerMode dividerMode = DividerMode.None;
	private int dividerSize = 1; 			// 线条大小(DIP) 0.5F
	private int dividerColor = 0xffDFDFDF;  // 线条颜色
	// 左图标
	private Drawable iconLeft;
	private int iconLeftWidth = LayoutParams.WRAP_CONTENT;	// 左边图标默认宽(DIP)
	private int iconLeftHeight = LayoutParams.WRAP_CONTENT;	// 左边图标默认高(DIP)
	private int iconLeftMarginLeft; 		// 左边图标外边距右(DIP)
	private int iconLeftMarginTop;			// 左边图标外边距上(DIP)
	private int iconLeftMarginRight = 10;	// 左边图标外边距右(DIP)
	private int iconLeftMarginBottom;		// 左边图标外边距下(DIP)
	// 右图标
	private Drawable iconRight;
	private int iconRightWidth = LayoutParams.WRAP_CONTENT;	// 右边图标外边距(DIP)
	private int iconRightHeight = LayoutParams.WRAP_CONTENT;// 右边图标默认高(DIP)
	private int iconRightMarginLeft = 5;	// 右边图标外边距左(DIP)
	private int iconRightMarginTop;			// 右边图标外边距上(DIP)
	private int iconRightMarginRight;		// 右边图标外边距右(DIP)
	private int iconRightMarginBottom;		// 右边图标外边距下(DIP)

	// key
	private String key;
	private int keyColor = Color.BLACK;				// key文本颜色
	private int keySize = 14;						// key文本大小
	private int keyStyle = 0; 						// 0正常，1粗体
	private int keyLines = 0;						// 当前显示几行
//	private int keyMinLines = 0;					// 最小行数
//	private int keyMaxLines = Integer.MAX_VALUE; 	// 最大行数
	// key-hint
	private String keyHint;
	private int keyHintColor = Color.LTGRAY;		// key-hint文本颜色
	// key-width-height
	private float keyWeight = 0;							// key权重
	private int keyWidth = LayoutParams.WRAP_CONTENT;		// key宽
	private int keyHeight = LayoutParams.WRAP_CONTENT;		// key高
//	private int keyMinWidth = LayoutParams.WRAP_CONTENT;	// key最小宽
//	private int keyMinHeight = LayoutParams.WRAP_CONTENT;	// key最小高
//	private int keyMaxWidth = LayoutParams.WRAP_CONTENT;	// key最大宽
//	private int keyMaxHeight = LayoutParams.WRAP_CONTENT;	// key最大高
	// key-margin
	private int keyMargin = -1; 			// 当 keyMargin >= 0 时，左上右下的值等于 keyMargin
	private int keyMarginLeft;				// keyView距左
	private int keyMarginTop;				// keyView距上
	private int keyMarginRight;				// keyView距右
	private int keyMarginBottom;			// keyView距下
	// key-padding
	private int keyPadding = -1; 			// 当 keyPadding >= 0 时，左上右下的值等于 keyPadding
	private int keyPaddingLeft;				// key距离keyView的左
	private int keyPaddingTop;				// key距离keyView的上
	private int keyPaddingRight;			// key距离keyView的右
	private int keyPaddingBottom;			// key距离keyView的下
	// key-drawable
	private int keyDrawablePadding;			// 图标与内容的间距
	private Drawable keyDrawableLeft;		// keyView的drawableLeft
	private Drawable keyDrawableRight;		// keyView的drawableRight
	private Drawable keyBackground;  		// keyView的背景
	private GravityMode keyGravity = GravityMode.left;	// keyView文本对齐方式

	// value
	private String value;
	private int valueColor = Color.BLACK;
	private int valueSize = 14;
	private int valueStyle = 0; // 0正常，1粗体
	private int valueLines = 0;
//	private int valueMinLines = 1;
//	private int valueMaxLines = Integer.MAX_VALUE;
	// value-hint
	private String valueHint;
	private int valueHintColor = Color.LTGRAY;
	// value-width-height
	private float valueWeight;
	private int valueWidth = LayoutParams.WRAP_CONTENT;
	private int valueHeight = LayoutParams.WRAP_CONTENT;
//	private int valueMinWidth = LayoutParams.WRAP_CONTENT;
//	private int valueMinHeight = LayoutParams.WRAP_CONTENT;
//	private int valueMaxWidth = LayoutParams.WRAP_CONTENT;
//	private int valueMaxHeight = LayoutParams.WRAP_CONTENT;
	// value-margin
	private int valueMargin = -1; // 当 valueMargin >= 0 时，左上右下的值等于 valueMargin
	private int valueMarginLeft;
	private int valueMarginTop;
	private int valueMarginRight;
	private int valueMarginBottom;
	// value-padding
	private int valuePadding = -1; // 当 valuePadding >= 0 时，左上右下的值等于 valuePadding
	private int valuePaddingLeft;
	private int valuePaddingTop;
	private int valuePaddingRight;
	private int valuePaddingBottom;
	// value-drawable
	private int valueDrawablePadding;
	private Drawable valueDrawableLeft;
	private Drawable valueDrawableRight;
	private Drawable valueBackground;  // value的背景
	private GravityMode valueGravity = GravityMode.left;

	private OnClickListener keyOnClickListener, valueOnClickListener;

	public ItemView(Context context){
		this(context, null);
	}

	public ItemView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public ItemView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
		this.context = context;
		init(attrs, defStyleAttr);
	}

	@SuppressWarnings("deprecation")
	private void init(AttributeSet attrs, int defStyle){
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyle, 0);
		if (a != null) {
			// 线
			dividerColor = a.getColor(R.styleable.ItemView_dividerColor, dividerColor);
			dividerSize = a.getDimensionPixelSize(R.styleable.ItemView_dividerSize, dividerSize);
			dividerMode = DividerMode.fromValue(a.getInteger(R.styleable.ItemView_dividerMode, dividerMode.value));
			dividerTopMargin = a.getDimensionPixelSize(R.styleable.ItemView_dividerTop_margin, dividerTopMargin);
			dividerTopMarginLeft = a.getDimensionPixelSize(R.styleable.ItemView_dividerTop_marginLeft, dividerTopMarginLeft);
			dividerTopMarginRight = a.getDimensionPixelSize(R.styleable.ItemView_dividerTop_marginRight, dividerTopMarginRight);
			dividerBottomMargin = a.getDimensionPixelSize(R.styleable.ItemView_dividerBottom_margin, dividerBottomMargin);
			dividerBottomMarginLeft = a.getDimensionPixelSize(R.styleable.ItemView_dividerBottom_marginLeft, dividerBottomMarginLeft);
			dividerBottomMarginRight = a.getDimensionPixelSize(R.styleable.ItemView_dividerBottom_marginRight, dividerBottomMarginRight);

			// 左图标
			iconLeft = a.getDrawable(R.styleable.ItemView_iconLeft);
			iconLeftWidth = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_width, iconLeftWidth);
			iconLeftHeight = a.getDimensionPixelSize(R.styleable.ItemView_iconLeft_height, iconLeftHeight);
			iconLeftMarginLeft = a.getDimensionPixelSize(R.styleable.ItemView_iconLeft_marginLeft, iconLeftMarginLeft);
			iconLeftMarginTop = a.getDimensionPixelSize(R.styleable.ItemView_iconLeft_marginTop, iconLeftMarginTop);
			iconLeftMarginRight = a.getDimensionPixelSize(R.styleable.ItemView_iconLeft_marginRight, iconLeftMarginRight);
			iconLeftMarginBottom = a.getDimensionPixelSize(R.styleable.ItemView_iconLeft_marginBottom, iconLeftMarginBottom);

			// 右图标
			iconRight = a.getDrawable(R.styleable.ItemView_iconRight);
			iconRightWidth = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_width, iconRightWidth);
			iconRightHeight = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_height, iconRightHeight);
			iconRightMarginLeft = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_marginLeft, iconRightMarginLeft);
			iconRightMarginTop = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_marginTop, iconRightMarginTop);
			iconRightMarginRight = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_marginRight, iconRightMarginRight);
			iconRightMarginBottom = a.getDimensionPixelSize(R.styleable.ItemView_iconRight_marginBottom, iconRightMarginBottom);

			// key
			key = a.getString(R.styleable.ItemView_key);
			keySize = a.getDimensionPixelSize(R.styleable.ItemView_keySize, keySize);
			keyColor = a.getColor(R.styleable.ItemView_keyColor, keyColor);
			keyStyle = a.getInt(R.styleable.ItemView_keyStyle, keyStyle);
			keyLines = a.getInt(R.styleable.ItemView_keyLines, keyLines);
//			keyMinLines = a.getInt(R.styleable.ItemView_keyMinLines, keyMinLines);
//			keyMaxLines = a.getInt(R.styleable.ItemView_keyMaxLines, keyMaxLines);
			keyBackground = a.getDrawable(R.styleable.ItemView_keyBackground);
			keyGravity = GravityMode.fromValue(a.getInteger(R.styleable.ItemView_keyGravity, keyGravity.value));
			// key-hint
			keyHint = a.getString(R.styleable.ItemView_keyHint);
			keyHintColor = a.getColor(R.styleable.ItemView_keyHintColor, keyHintColor);
			// key-width-height
			keyWeight = a.getFloat(R.styleable.ItemView_keyWeight, keyWeight);
			keyWidth = a.getDimensionPixelSize(R.styleable.ItemView_keyWidth, keyWidth);
			keyHeight = a.getDimensionPixelSize(R.styleable.ItemView_keyHeight, keyHeight);
//			keyMinWidth = a.getDimensionPixelSize(R.styleable.ItemView_keyMinWidth, keyMinWidth);
//			keyMinHeight = a.getDimensionPixelSize(R.styleable.ItemView_keyMinHeight, keyMinHeight);
//			keyMaxWidth = a.getDimensionPixelSize(R.styleable.ItemView_keyMaxWidth, keyMaxWidth);
//			keyMaxHeight = a.getDimensionPixelSize(R.styleable.ItemView_keyMaxHeight, keyMaxHeight);
			// key-margin
			keyMargin = a.getDimensionPixelSize(R.styleable.ItemView_keyMargin, keyMargin);
			keyMarginLeft = a.getDimensionPixelSize(R.styleable.ItemView_keyMarginLeft, keyMarginLeft);
			keyMarginTop = a.getDimensionPixelSize(R.styleable.ItemView_keyMarginTop, keyMarginTop);
			keyMarginRight = a.getDimensionPixelSize(R.styleable.ItemView_keyMarginRight, keyMarginRight);
			keyMarginBottom = a.getDimensionPixelSize(R.styleable.ItemView_keyMarginBottom, keyMarginBottom);
			// key-padding
			keyPadding = a.getDimensionPixelSize(R.styleable.ItemView_keyPadding, keyPadding);
			keyPaddingLeft = a.getDimensionPixelSize(R.styleable.ItemView_keyPaddingLeft, keyPaddingLeft);
			keyPaddingTop = a.getDimensionPixelSize(R.styleable.ItemView_keyPaddingTop, keyPaddingTop);
			keyPaddingRight = a.getDimensionPixelSize(R.styleable.ItemView_keyPaddingRight, keyPaddingRight);
			keyPaddingBottom = a.getDimensionPixelSize(R.styleable.ItemView_keyPaddingBottom, keyPaddingBottom);
			// key-drawable
			keyDrawablePadding = a.getDimensionPixelSize(R.styleable.ItemView_keyDrawablePadding, keyDrawablePadding);
			keyDrawableLeft = a.getDrawable(R.styleable.ItemView_keyDrawableLeft);
			keyDrawableRight = a.getDrawable(R.styleable.ItemView_keyDrawableRight);

			// value
			value = a.getString(R.styleable.ItemView_value);
			if (value != null) {
				valueSize = a.getDimensionPixelSize(R.styleable.ItemView_valueSize, valueSize);
				valueColor = a.getColor(R.styleable.ItemView_valueColor, valueColor);
				valueStyle = a.getInt(R.styleable.ItemView_valueStyle, valueStyle);
				valueLines = a.getInt(R.styleable.ItemView_valueLines, valueLines);
//				valueMinLines = a.getInt(R.styleable.ItemView_valueMinLines, valueMinLines);
//				valueMaxLines = a.getInt(R.styleable.ItemView_valueMaxLines, valueMaxLines);
				valueBackground = a.getDrawable(R.styleable.ItemView_valueBackground);
				valueGravity = GravityMode.fromValue(a.getInteger(R.styleable.ItemView_valueGravity, valueGravity.value));
				// value-hint
				valueHint = a.getString(R.styleable.ItemView_valueHint);
				valueHintColor = a.getColor(R.styleable.ItemView_valueHintColor, valueHintColor);
				// value-width-height
				valueWeight = a.getFloat(R.styleable.ItemView_valueWeight, valueWeight);
				valueWidth = a.getDimensionPixelSize(R.styleable.ItemView_valueWidth, valueWidth);
				valueHeight = a.getDimensionPixelSize(R.styleable.ItemView_valueHeight, valueHeight);
//				valueMinWidth = a.getDimensionPixelSize(R.styleable.ItemView_valueMinWidth, valueMinWidth);
//				valueMinHeight = a.getDimensionPixelSize(R.styleable.ItemView_valueMinHeight, valueMinHeight);
//				valueMaxWidth = a.getDimensionPixelSize(R.styleable.ItemView_valueMaxWidth, valueMaxWidth);
//				valueMaxHeight = a.getDimensionPixelSize(R.styleable.ItemView_valueMaxHeight, valueMaxHeight);
				// value-margin
				valueMargin = a.getDimensionPixelSize(R.styleable.ItemView_valueMargin, valueMargin);
				valueMarginLeft = a.getDimensionPixelSize(R.styleable.ItemView_valueMarginLeft, valueMarginLeft);
				valueMarginTop = a.getDimensionPixelSize(R.styleable.ItemView_valueMarginTop, valueMarginTop);
				valueMarginRight = a.getDimensionPixelSize(R.styleable.ItemView_valueMarginRight, valueMarginRight);
				valueMarginBottom = a.getDimensionPixelSize(R.styleable.ItemView_valueMarginBottom, valueMarginBottom);
				// value-padding
				valuePadding = a.getDimensionPixelSize(R.styleable.ItemView_valuePadding, valuePadding);
				valuePaddingLeft = a.getDimensionPixelSize(R.styleable.ItemView_valuePaddingLeft, valuePaddingLeft);
				valuePaddingTop = a.getDimensionPixelSize(R.styleable.ItemView_valuePaddingTop, valuePaddingTop);
				valuePaddingRight = a.getDimensionPixelSize(R.styleable.ItemView_valuePaddingRight, valuePaddingRight);
				valuePaddingBottom = a.getDimensionPixelSize(R.styleable.ItemView_valuePaddingBottom, valuePaddingBottom);
			}
			// value-drawable
			valueDrawablePadding = a.getDimensionPixelSize(R.styleable.ItemView_valueDrawablePadding, valueDrawablePadding);
			valueDrawableLeft = a.getDrawable(R.styleable.ItemView_valueDrawableLeft);
			valueDrawableRight = a.getDrawable(R.styleable.ItemView_valueDrawableRight);

			a.recycle();
		}

		mIconLeftView = new ImageView(context);
		mIconRightView = new ImageView(context);
		mKeyView = new TextView(context);
		mValueView = new TextView(context);
		// icon left
		if (iconLeft != null) {
			iconLeftParams = new LayoutParams(iconLeftWidth, iconLeftHeight);
			iconLeftParams.leftMargin = iconLeftMarginLeft;
			iconLeftParams.topMargin = iconLeftMarginTop;
			iconLeftParams.rightMargin = iconLeftMarginRight;
			iconLeftParams.bottomMargin = iconLeftMarginBottom;
			mIconLeftView.setLayoutParams(iconLeftParams);
			mIconLeftView.setImageDrawable(iconLeft);
			addView(mIconLeftView);
		}

		// key View
		keyParams = new LayoutParams(keyWidth, keyHeight, keyWeight);
		if(keyMargin != -1) keyMarginLeft = keyMarginTop = keyMarginRight = keyMarginBottom = keyMargin;
		keyParams.leftMargin = keyMarginLeft;
		keyParams.topMargin = keyMarginTop;
		keyParams.rightMargin = keyMarginRight;
		keyParams.bottomMargin = keyMarginBottom;
		mKeyView.setLayoutParams(keyParams);
		mKeyView.setGravity(keyGravity.value);
		mKeyView.setBackgroundDrawable(keyBackground);

		if(keyPadding != -1) keyPaddingLeft = keyPaddingTop = keyPaddingRight = keyPaddingBottom = keyPadding;
		mKeyView.setPadding(keyPaddingLeft, keyPaddingTop, keyPaddingRight, keyPaddingBottom);
		mKeyView.setText(key);
		mKeyView.setTextColor(keyColor);
		mKeyView.getPaint().setFakeBoldText(keyStyle == 1);
		mKeyView.getPaint().setTextSize(keySize);

		mKeyView.setLines(keyLines);
//		mKeyView.setMinLines(keyMinLines);
//		mKeyView.setMaxLines(keyMaxLines);
//		mKeyView.setMinWidth(keyMinWidth);
//		mKeyView.setMinHeight(keyMinHeight);
//		mKeyView.setMaxWidth(keyMaxWidth);
//		mKeyView.setMaxHeight(keyMaxHeight);

		mKeyView.setHint(keyHint);
		mKeyView.setHintTextColor(keyHintColor);

		// 设置Key的图标
		setKeyDrawablesPadding(keyDrawablePadding);
		setKeyDrawablesLeft(keyDrawableLeft);
		setKeyDrawablesRight(keyDrawableRight);

		addView(mKeyView);

		// value View
		if (value != null) {
			Log.e("AAA", "valueParams valueWidth = " + valueWidth);
			Log.e("AAA", "valueParams valueWeight = " + valueWeight);
			valueParams = new LayoutParams(valueWidth, valueHeight, valueWeight);
			if(valueMargin != -1) {
				valueMarginLeft = valueMarginTop = valueMarginRight = valueMarginBottom = valueMargin;
			}
			valueParams.leftMargin = valueMarginLeft;
			valueParams.topMargin = valueMarginTop;
			valueParams.rightMargin = valueMarginRight;
			valueParams.bottomMargin = valueMarginBottom;
			mValueView.setLayoutParams(valueParams);

			if(valuePadding != -1) {
				valuePaddingLeft = valuePaddingTop = valuePaddingRight = valuePaddingBottom = valuePadding;
			}
			mValueView.setPadding(valuePaddingLeft, valuePaddingTop, valuePaddingRight, valuePaddingBottom);

			mValueView.setText(value);
			mValueView.setTextColor(valueColor);
			mValueView.getPaint().setFakeBoldText(valueStyle == 1);
			mValueView.getPaint().setTextSize(valueSize);
			mValueView.setGravity(valueGravity.value);
			mValueView.setBackgroundDrawable(valueBackground);

			mValueView.setLines(valueLines);
//			mValueView.setMinLines(valueMinLines);
//			mValueView.setMaxLines(valueMaxLines);
//			mValueView.setMinWidth(valueMinWidth);
//			mValueView.setMinHeight(valueMinHeight);
//			mValueView.setMaxWidth(valueMaxWidth);
//			mValueView.setMaxHeight(valueMaxHeight);

			mValueView.setHint(valueHint);
			mValueView.setHintTextColor(valueHintColor);

			// 设置Value的图标
			setValueDrawablesPadding(valueDrawablePadding);
			setValueDrawablesLeft(valueDrawableLeft);
			setValueDrawablesRight(valueDrawableRight);

			addView(mValueView);
		}

		// icon right
		if (iconRight != null) {
			iconRightParams = new LayoutParams(iconRightWidth, iconRightHeight);
			iconRightParams.setMargins(iconRightMarginLeft, iconRightMarginTop, iconRightMarginRight, iconRightMarginBottom);
			mIconRightView.setLayoutParams(iconRightParams);
			mIconRightView.setImageDrawable(iconRight);
			addView(mIconRightView);
		}

		// 加入layout
		initDivider(context);
	}

	@Override
	final public void setOrientation(int orientation) {
		if (orientation == VERTICAL) {
			throw new SecurityException("只能设置为水平排列：HORIZONTAL");
		}
		super.setOrientation(LinearLayout.HORIZONTAL);
	}

	/**
	 * 获取ItemView的key文本
	 * @return
	 */
	public String getKey(){
		this.key = mKeyView.getText().toString();
		return this.key;
	}

	/**
	 * 获取ItemView的value文本
	 * @return
	 */
	public String getValue(){
		this.value = mValueView.getText().toString();
		return this.value;
	}

	//	key
	private void setKeyDrawablesPadding(int drawablePadding) {
		this.keyDrawablePadding = drawablePadding;
		mKeyView.setCompoundDrawablePadding(keyDrawablePadding);
	}

	private void setKeyDrawablesLeft(Drawable drawablesLeft) {
		this.keyDrawableLeft = drawablesLeft;
		if (keyDrawableLeft != null) {
			keyDrawableLeft.setBounds(0, 0, keyDrawableLeft.getIntrinsicWidth(), keyDrawableLeft.getIntrinsicHeight());
		}
		mKeyView.setCompoundDrawablesWithIntrinsicBounds(keyDrawableLeft, null, keyDrawableRight, null);
	}

	private void setKeyDrawablesRight(Drawable drawablesRight) {
		this.keyDrawableRight = drawablesRight;
		if (keyDrawableRight != null) {
			keyDrawableRight.setBounds(0, 0, keyDrawableRight.getIntrinsicWidth(), keyDrawableRight.getIntrinsicHeight());
		}
		mKeyView.setCompoundDrawablesWithIntrinsicBounds(keyDrawableLeft, null, keyDrawableRight, null);
	}

//	value
	private void setValueDrawablesPadding(int drawablePadding) {
		this.valueDrawablePadding = drawablePadding;
		mValueView.setCompoundDrawablePadding(valueDrawablePadding);
	}

	private void setValueDrawablesLeft(Drawable drawablesLeft) {
		this.valueDrawableLeft = drawablesLeft;
		if (valueDrawableLeft != null) {
			valueDrawableLeft.setBounds(0, 0, valueDrawableLeft.getIntrinsicWidth(), valueDrawableLeft.getIntrinsicHeight());
		}
		mValueView.setCompoundDrawablesWithIntrinsicBounds(valueDrawableLeft, null, valueDrawableRight, null);
	}

	private void setValueDrawablesRight(Drawable drawablesRight) {
		this.valueDrawableRight= drawablesRight;
		if (valueDrawableRight != null) {
			valueDrawableRight.setBounds(0, 0, valueDrawableRight.getIntrinsicWidth(), valueDrawableRight.getIntrinsicWidth());
		}
		mValueView.setCompoundDrawablesWithIntrinsicBounds(null, null, valueDrawableRight, null);
	}

	public void setKeyOnClickListener(OnClickListener keyOnClickListener) {
		this.keyOnClickListener = keyOnClickListener;
		if (mKeyView != null) {
			mKeyView.setOnClickListener(this.keyOnClickListener);
		}
	}

	public void setValueOnClickListener(OnClickListener valueOnClickListener) {
		this.valueOnClickListener = valueOnClickListener;
		if (mValueView != null) {
			mValueView.setOnClickListener(this.valueOnClickListener);
		}
	}

	/**
	 * 如果设置此值，则设置以下将无效：
	 * dividerTopMargin、
	 * dividerTopMarginLeft、
	 * dividerTopMarginRight、
	 *
	 * dividerBottomMargin、
	 * dividerBottomMarginLeft、
	 * dividerBottomMarginRight。
	 */
	private int dividerMargin; // px

	/**
	 * 如果设置此值，则设置以下将无效：
	 * dividerTopMarginLeft、
	 * dividerTopMarginRight、
	 */
	private int dividerTopMargin; // px
	private int dividerTopMarginLeft; // px
	private int dividerTopMarginRight; // px

	/**
	 * 如果设置此值，则设置以下将无效：
	 * dividerBottomMarginLeft、
	 * dividerBottomMarginRight、
	 */
	private int dividerBottomMargin = 0; // px
	private int dividerBottomMarginLeft = 0; // px
	private int dividerBottomMarginRight = 0; // px

	private Paint dividerPaint;

	private void initDivider(Context context) {
		this.context = context;
		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);//去除锯齿
		dividerPaint.setStrokeWidth(dividerSize);// 线条宽度
		dividerPaint.setColor(dividerColor);//设置线条颜色
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		initMargin();
		switch (dividerMode) {
		case None:
			break;
		case SingleTop:
			drawDividerTop(canvas);	// 上
			break;
		case SingleBottom:
			drawDividerBottom(canvas); // 下
			break;
		case Both:
			drawDividerTop(canvas);	// 上
			drawDividerBottom(canvas); // 下
			break;
		default:
			break;
		}
	}

	private void initMargin() {
		if(dividerMargin > 0){
			dividerTopMargin = dividerTopMarginLeft = dividerTopMarginRight = dividerMargin;
			dividerBottomMargin = dividerBottomMarginLeft = dividerBottomMarginRight = dividerMargin;
		} else if(dividerTopMargin > 0){
			dividerTopMarginLeft = dividerTopMarginRight = dividerTopMargin;
		} else if(dividerBottomMargin > 0){
			dividerBottomMarginLeft = dividerBottomMarginRight = dividerBottomMargin;
		}
	}

	/**
	 * 画上线
	 * @param canvas
	 */
	private void drawDividerTop(Canvas canvas) {
		int measuredWidth = getMeasuredWidth();
		float topStartX = 0 + dividerTopMarginLeft;
		float topStopX = measuredWidth - dividerTopMarginRight;
		float topStartY = 0 + dividerSize/2;
		float topStopY = topStartY;
		canvas.drawLine(topStartX, topStartY, topStopX, topStopY, dividerPaint); // 上
	}

	/**
	 * 画下线
	 * @param canvas
	 */
	private void drawDividerBottom(Canvas canvas) {
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();
		float bottomStartX = 0 + dividerBottomMarginLeft;
		float bottomStopX = measuredWidth - dividerBottomMarginRight;
		float bottomStartY = measuredHeight - dividerSize/2;
		float bottomStopY = bottomStartY;
		canvas.drawLine(bottomStartX, bottomStartY, bottomStopX, bottomStopY, dividerPaint); // 下
	}

	/**
	 * 获取key的View
	 * @return key View
	 */
	public TextView getKeyView(){
		return mKeyView;
	}

	/**
	 * 获取value的View
	 * @return value View
	 */
	public TextView getValueView(){
		return mValueView;
	}

	/**
	 * 设置Key文本资源ID
	 * @param resId 文本资源ID
	 */
	public void setKey(int resId){
		mKeyView.setText(getResources().getString(resId));
	}

	/**
	 * 设置Key文本
	 * @param text 文本
	 */
	public void setKey(String text){
		mKeyView.setText(text);
	}

	/**
	 * 设置Key文字
	 * @param text 文本
	 */
	public void setKey(CharSequence text){
		mKeyView.setText(text);
	}

	/**
	 * 设置Key文字大小
	 * @param size 文本大小
	 */
	public void setKeySize(float size){
		mKeyView.setTextSize(size);
	}

	/**
	 * 设置key文本大小（px）
	 * @param unit 单位 {@link TypedValue}
	 * @param size 文本大小
	 */
	public void setKeySize(int unit, float size) {
		mKeyView.setTextSize(unit, size);
	}

	/**
	 * 设置Key文字颜色
	 * @param color 文本颜色
	 */
	public void setKeyColor(int color){
		mKeyView.setTextColor(color);
	}

	/**
	 * 设置Key文字颜色字符
	 * @param color 文本颜色 如#FFFFFF
	 */
	public void setKeyColor(String color){
		try {
			mKeyView.setTextColor(Color.parseColor(color));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置value的文本ID
	 * @param resId 文本资源ID
	 */
	public void setValue(int resId){
		mValueView.setText(getResources().getString(resId));
	}

	/**
	 * 设置value的文本
	 * @param text 文本
	 */
	public void setValue(String text){
		mValueView.setText(text);
	}

	/**
	 * 设置value的文本
	 * @param text 文本
	 */
	public void setValue(CharSequence text){
		mValueView.setText(text);
	}

	/**
	 * 设置value文本大小（px）
	 * @param size 文本大小
	 */
	public void setValueSize(float size){
		mValueView.setTextSize(size);
	}

	/**
	 * 设置value文本大小
	 * @param unit 单位 {@link TypedValue}
	 * @param size 文本大小.
	 */
	public void setValueSize(int unit, float size) {
		mValueView.setTextSize(unit, size);
	}

	/**
	 * 设置value文本颜色
	 * @param color
	 */
	public void setValueColor(int color){
		mValueView.setTextColor(color);
	}

	/**
	 * 设置value文本颜色
	 * @param color 如："#000000"
	 */
	public void setValueColor(String color){
		try {
			mValueView.setTextColor(Color.parseColor(color));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setKeyBoldText(boolean fakeBoldText){
		mKeyView.getPaint().setFakeBoldText(fakeBoldText);
	}

	public void setValueBoldText(boolean fakeBoldText){
		mValueView.getPaint().setFakeBoldText(fakeBoldText);
	}

	public void setValueGravity(int gravity){
		mValueView.setGravity(gravity);
	}

	/**
	 * 设置key和value的文本资源ID
	 * @param keyResId key资源ID
	 * @param valueResId value资源ID
	 */
	public void setKeyValue(int keyResId, int valueResId){
		setKey(keyResId);
		setValue(valueResId);
	}

	/**
	 * 设置key和value的文本
	 * @param key key文本
	 * @param value value文本
	 */
	public void setKeyValue(String key, String value){
		setKey(key);
		setValue(value);
	}

	/**
	 * 设置key和value的文本
	 * @param key key文本
	 * @param value value文本
	 */
	public void setKeyValue(CharSequence key, CharSequence value){
		setKey(key);
		setValue(value);
	}

	/**
	 * 设置左边icon的margins
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setIconLeftMargins(int left, int top, int right, int bottom){
		iconLeftParams.setMargins(left, top, right, bottom);
	}

	/**
	 * 设置右边icon的margins
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setIconRightMargins(int left, int top, int right, int bottom){
		iconRightParams.setMargins(left, top, right, bottom);
	}

	/**
	 * 设置左边图标ID
	 * @param resid 左边图标ID
	 */
	public void setIconLeftImageResource(int resid){
		mIconLeftView.setBackgroundResource(resid);
	}

	/**
	 * 设置左边图标Drawable
	 * @param drawable 左边图标Drawable
	 */
	@SuppressWarnings("deprecation")
	public void setIconLeftImageResource(Drawable drawable){
		mIconLeftView.setBackgroundDrawable(drawable);
	}

	/**
	 * 设置右边图标id
	 * @param resid 右边图标ID
	 */
	public void setIconRightImageResource(int resid){
		mIconRightView.setBackgroundResource(resid);
	}

	/**
	 * 设置右边图标Drawable
	 * @param drawable 右边图标Drawable
	 */
	@SuppressWarnings("deprecation")
	public void setIconRightImageDrawable(Drawable drawable){
		mIconRightView.setBackgroundDrawable(drawable);
	}

	public void setDividerMargin(int dividerMargin) {
		this.dividerMargin = dividerMargin;
		invalidate();
	}

	public void setDividerTopMargin(int dividerTopMargin) {
		this.dividerTopMargin = dividerTopMargin;
		invalidate();
	}

	public void setDividerTopMarginLeft(int dividerTopMarginLeft) {
		this.dividerTopMarginLeft = dividerTopMarginLeft;
		invalidate();
	}

	public void setDividerTopMarginRight(int dividerTopMarginRight) {
		this.dividerTopMarginRight = dividerTopMarginRight;
		invalidate();
	}

	public void setDividerBottomMargin(int dividerBottomMargin) {
		this.dividerBottomMargin = dividerBottomMargin;
		invalidate();
	}

	public void setDividerBottomMarginLeft(int dividerBottomMarginLeft) {
		this.dividerBottomMarginLeft = dividerBottomMarginLeft;
		invalidate();
	}

	public void setDividerBottomMarginRight(int dividerBottomMarginRight) {
		this.dividerBottomMarginRight = dividerBottomMarginRight;
		invalidate();
	}

	/**
	 * dip转换为px
	 * @param dipValue dip值
	 * @return xp值
	 */
	public int dip2px(float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转换为dip
	 * @param pxValue px值
	 * @return dip值
	 */
	public int px2dip(float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 分割线显示模式
	 *
	 * <p>Created by wuguangxin on 15/7/10 </p>
	 */
	public enum DividerMode {
		/**
		 * 不显示分割线
		 */
		None(0),
		/**
		 * 显示上下分割线
		 */
		Both(1),
		/**
		 * 只显示上面的分割线
		 */
		SingleTop(2),
		/**
		 * 只显示下面的分割线
		 */
		SingleBottom(3);
		public final int value;

		DividerMode(int value){
			this.value = value;
		}

		public static DividerMode fromValue(int value){
			for (DividerMode position: DividerMode.values()) {
				if (position.value == value) {
					return position;
				}
			}
			return null;
		}
	}

	/**
	 * 对齐方式
	 * 
	 * <p>Created by wuguangxin on 2017/8/18.</p>
	 */
	public enum GravityMode {
		/** 左对齐 */
		left(Gravity.LEFT),
		/** 上对齐 */
		top(Gravity.TOP),
		/** 右对齐 */
		right(Gravity.RIGHT),
		/** 下对齐 */
		bottom(Gravity.BOTTOM),
		/** 居中 */
		center(Gravity.CENTER),
		/** 水平居中 */
		center_horizontal(Gravity.CENTER_HORIZONTAL),
		/** 垂直居中 */
		center_vertical(Gravity.CENTER_VERTICAL);

		public int value;

		GravityMode(int value){
			this.value = value;
		}

		public static GravityMode fromValue(int value){
			for (GravityMode position: GravityMode.values()) {
				if (position.value == value) {
					return position;
				}
			}
			return left;
		}
	}
}
