package com.andrognito.patternlockdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

/**
 * Created By LRD
 * on 2019/3/22  notes：
 */
public class TestDemoActivity extends AppCompatActivity {

	private PatternLockView mPatternLockView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);

		initView();
	}

	private void initView() {
		mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view_test);
		mPatternLockView.addPatternLockListener(mPatternLockViewListener);

	}
	private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
		@Override
		public void onStarted() {
			Log.d(getClass().getName(), "Pattern drawing started");
		}

		@Override
		public void onProgress(List<PatternLockView.Dot> progressPattern) {
			Log.d(getClass().getName(), "Pattern progress: " +
					PatternLockUtils.patternToString(mPatternLockView, progressPattern));
		}


		@Override
		public void onComplete(List<PatternLockView.Dot> pattern) {
			Log.d(getClass().getName(), "Pattern complete: " +
					PatternLockUtils.patternToString(mPatternLockView, pattern));
			if("1478".equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
				mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
			}else {
				mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
			}
		}

		@Override
		public void onCleared() {
			Log.d(getClass().getName(), "Pattern has been cleared");
		}
	};
}
