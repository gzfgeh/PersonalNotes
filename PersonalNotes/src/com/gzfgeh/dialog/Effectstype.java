package com.gzfgeh.dialog;

import com.gzfgeh.customdialog.dialog.effects.BaseEffects;
import com.gzfgeh.customdialog.dialog.effects.FadeIn;
import com.gzfgeh.customdialog.dialog.effects.Fall;
import com.gzfgeh.customdialog.dialog.effects.FlipH;
import com.gzfgeh.customdialog.dialog.effects.FlipV;
import com.gzfgeh.customdialog.dialog.effects.NewsPaper;
import com.gzfgeh.customdialog.dialog.effects.RotateBottom;
import com.gzfgeh.customdialog.dialog.effects.RotateLeft;
import com.gzfgeh.customdialog.dialog.effects.Shake;
import com.gzfgeh.customdialog.dialog.effects.SideFall;
import com.gzfgeh.customdialog.dialog.effects.SlideBottom;
import com.gzfgeh.customdialog.dialog.effects.SlideLeft;
import com.gzfgeh.customdialog.dialog.effects.SlideRight;
import com.gzfgeh.customdialog.dialog.effects.SlideTop;
import com.gzfgeh.customdialog.dialog.effects.Slit;


/**
 * Created by lee on 2014/7/30.
 */
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
