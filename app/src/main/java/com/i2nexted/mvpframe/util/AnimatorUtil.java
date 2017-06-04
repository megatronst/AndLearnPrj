package com.i2nexted.mvpframe.util;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Alex on 2016/4/12.
 */
public class AnimatorUtil {
	public static final long ANIM_TIME = 300 ;

	public static void setViewVisible(View view){
		if(view.getVisibility()!=View.VISIBLE){
			view.setVisibility(View.VISIBLE);
		}
	}

	public static void alphaAppear(View view){
		alphaAppear(view,ANIM_TIME,null);
	}

	public static void alphaAppear(View view,AnimatorListener listener){
		alphaAppear(view,ANIM_TIME,listener);
	}

	public static void alphaAppear(View view,long time){
		alphaAppear(view,time,null);
	}

	public static void alphaAppear(View view,long time,AnimatorListener listener){
		ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha",0,1);
		animator.setDuration(time);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		if(listener!=null){
			animator.addListener(listener);
		}
		setViewVisible(view);
		animator.start();
	}

	public static void alphaDisappear(View view){
		alphaDisappear(view,ANIM_TIME);
	}

	public static void alphaDisappear(View view,AnimatorListener listener){
		alphaDisappear(view,ANIM_TIME,listener);
	}

	public static void alphaDisappear(View view,long time){
		alphaDisappear(view,time,null);
	}

	public static void alphaDisappear(View view,long time,AnimatorListener listener){
		ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha",0);
		animator.setDuration(time);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		if(listener!=null){
			animator.addListener(listener);
		}
		animator.start();
	}

	public static void alphaDisappearAndHide(View view){
		alphaDisappearAndHide(view,ANIM_TIME);
	}

	public static void alphaDisappearAndHide(final View view, long time){
		ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha",0);
		animator.setDuration(time);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.addListener(new SimpleAnimatorListener(){
			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
			}
		});
		animator.start();
	}

	public static void hideView(View view){
		hideView(view,ANIM_TIME);
	}

	public static void hideView(View view,long time){
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator[] animatorArray = new ObjectAnimator[3];
		animatorArray[0] = ObjectAnimator.ofFloat(view,"scaleX",0);
		animatorArray[1] = ObjectAnimator.ofFloat(view,"scaleY",0);
		animatorArray[2] = ObjectAnimator.ofFloat(view,"alpha",0);
		animatorSet.playTogether(animatorArray);
		animatorSet.setDuration(time);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animatorSet.start();
	}

	public static void showView(View view){
		showView(view,null);
	}

	public static void showView(View view, AnimatorListener animatorListener){
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator[] animatorArray = new ObjectAnimator[3];
		animatorArray[0] = ObjectAnimator.ofFloat(view,"scaleX",0,1);
		animatorArray[1] = ObjectAnimator.ofFloat(view,"scaleY",0,1);
		animatorArray[2] = ObjectAnimator.ofFloat(view,"alpha",0,1);
		animatorSet.playTogether(animatorArray);
		animatorSet.setDuration(ANIM_TIME);
		animatorSet.setInterpolator(new LinearInterpolator());
		if(animatorListener!=null){
			animatorSet.addListener(animatorListener);
		}
		animatorSet.start();
	}

	public static void scaleDownOut(View view){
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator[] animatorArray = new ObjectAnimator[2];
		animatorArray[0] = ObjectAnimator.ofFloat(view,"scaleY",0);
		animatorArray[1] = ObjectAnimator.ofFloat(view,"alpha",0);
		animatorSet.playTogether(animatorArray);
		animatorSet.setDuration(ANIM_TIME);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		view.setPivotY(view.getHeight());
		animatorSet.start();
	}

	public static void scaleOutHide(View view){
		scaleOutHide(view,null);
	}

	public static void scaleOutHide(final View view, final AnimatorListener listener){
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator[] animatorArray = new ObjectAnimator[3];
		animatorArray[0] = ObjectAnimator.ofFloat(view,"scaleX",0);
		animatorArray[1] = ObjectAnimator.ofFloat(view,"scaleY",0);
		animatorArray[2] = ObjectAnimator.ofFloat(view,"alpha",0);
		animatorSet.playTogether(animatorArray);
		animatorSet.setDuration(ANIM_TIME);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animatorSet.addListener(new SimpleAnimatorListener(){
			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
				listener.onAnimationEnd(animation);
			}
		});
		setViewVisible(view);
		animatorSet.start();
	}

	public static void scaleUpIn(View view){
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator[] animatorArray = new ObjectAnimator[2];
		animatorArray[0] = ObjectAnimator.ofFloat(view,"scaleY",1);
		animatorArray[1] = ObjectAnimator.ofFloat(view,"alpha",1);
		animatorSet.playTogether(animatorArray);
		animatorSet.setDuration(ANIM_TIME);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		view.setPivotY(view.getHeight());
		animatorSet.start();
	}

	public static void scaleIn(View view){
		scaleIn(view,null);
	}

	public static void scaleIn(View view,AnimatorListener listener){
		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator[] animatorArray = new ObjectAnimator[3];
		animatorArray[0] = ObjectAnimator.ofFloat(view,"scaleX",1);
		animatorArray[1] = ObjectAnimator.ofFloat(view,"scaleY",1);
		animatorArray[2] = ObjectAnimator.ofFloat(view,"alpha",1);
		animatorSet.playTogether(animatorArray);
		animatorSet.setDuration(ANIM_TIME);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		if(listener!=null){
			animatorSet.addListener(listener);
		}
		setViewVisible(view);
		animatorSet.start();
	}

	public static void animateHeight(final View view, int startHeight, int endHeight,AnimatorListener listener){
		animateHeight(view,startHeight,endHeight,ANIM_TIME,listener);
	}

	public static void animateHeight(final View view, int startHeight, int endHeight,long time,AnimatorListener listener){
		ValueAnimator animator = ValueAnimator.ofInt(startHeight,endHeight);
		animator.setDuration(time);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int newHeight = (int) animation.getAnimatedValue();
				setHeight(view,newHeight);
			}
		});
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		if(listener!=null){
			animator.addListener(listener);
		}
		animator.start();
	}

	public static void animateHeight(final View view, int startHeight, int endHeight){
		animateHeight(view,startHeight,endHeight,null);
	}

	public static void setHeight(View view,int height){
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.height = height ;
		view.setLayoutParams(params);
	}

	public static void animateWidth(final View view, int startWidth, int endWidth,AnimatorListener listener){
		ValueAnimator animator = ValueAnimator.ofInt(startWidth,endWidth);
		animator.setDuration(ANIM_TIME);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int newWidth = (int) animation.getAnimatedValue();
				setWidth(view,newWidth);
			}
		});
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		if(listener!=null){
			animator.addListener(listener);
		}
		animator.start();
	}

	public static void animateWidth(final View view, int startWidth, int endWidth){
		animateWidth(view,startWidth,endWidth,null);
	}

	public static void setWidth(View view,int width){
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.width = width ;
		view.setLayoutParams(params);
	}

	public static void animateBG(final View view, int color, int endColor){
		ValueAnimator animator = ValueAnimator.ofInt(color,endColor);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int newColor = (int) animation.getAnimatedValue();
				view.setBackgroundColor(newColor);
			}
		});
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.setDuration(ANIM_TIME);
		animator.start();
	}
}
