package de.hawlandshut.rueckfallprophylaxe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;

/*
 * This subclass is required to identify the origin view of a context menu
 */

public class ImageViewWithContextMenuInfo extends ImageView  {
	public ImageViewWithContextMenuInfo(Context context) {
		super(context);
	}

	@Override
	protected ContextMenuInfo getContextMenuInfo() {
		return new ImageViewContextMenuInfo(this);
	}

	public ImageViewWithContextMenuInfo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageViewWithContextMenuInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public static class ImageViewContextMenuInfo implements ContextMenu.ContextMenuInfo {
		public ImageViewContextMenuInfo(View targetView) {
			this.targetView = (ImageView) targetView;
		}

		public ImageView targetView;
	}
}