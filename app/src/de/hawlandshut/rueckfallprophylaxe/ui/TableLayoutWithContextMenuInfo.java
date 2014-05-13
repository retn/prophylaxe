package de.hawlandshut.rueckfallprophylaxe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TableLayout;

/*
 * This subclass is required to identify the origin view of a context menu
 */

public class TableLayoutWithContextMenuInfo extends TableLayout  {
	public TableLayoutWithContextMenuInfo(Context context) {
		super(context);
	}

	@Override
	protected ContextMenuInfo getContextMenuInfo() {
		return new TableLayoutContextMenuInfo(this);
	}

	public TableLayoutWithContextMenuInfo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
	}

	public TableLayoutWithContextMenuInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public static class TableLayoutContextMenuInfo implements ContextMenu.ContextMenuInfo {
		public TableLayoutContextMenuInfo(View targetView) {
			this.targetView = (TableLayout) targetView;
		}
		
		public TableLayout targetView;
	}
}