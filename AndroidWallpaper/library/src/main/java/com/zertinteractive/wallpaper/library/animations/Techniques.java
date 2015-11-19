
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zertinteractive.wallpaper.library.animations;

import com.zertinteractive.wallpaper.library.animations.alpha.AlphaAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.BounceAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.FlashAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.PulseAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.RubberBandAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.ShakeAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.StandUpAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.SwingAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.TadaAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.WaveAnimator;
import com.zertinteractive.wallpaper.library.animations.attention.WobbleAnimator;
import com.zertinteractive.wallpaper.library.animations.bouncing_entrances.BounceInAnimator;
import com.zertinteractive.wallpaper.library.animations.bouncing_entrances.BounceInDownAnimator;
import com.zertinteractive.wallpaper.library.animations.bouncing_entrances.BounceInLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.bouncing_entrances.BounceInRightAnimator;
import com.zertinteractive.wallpaper.library.animations.bouncing_entrances.BounceInUpAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_entrances.FadeInAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_entrances.FadeInDownAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_entrances.FadeInLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_entrances.FadeInRightAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_entrances.FadeInUpAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_exits.FadeOutAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_exits.FadeOutDownAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_exits.FadeOutLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_exits.FadeOutRightAnimator;
import com.zertinteractive.wallpaper.library.animations.fading_exits.FadeOutUpAnimator;
import com.zertinteractive.wallpaper.library.animations.flippers.FlipInXAnimator;
import com.zertinteractive.wallpaper.library.animations.flippers.FlipInYAnimator;
import com.zertinteractive.wallpaper.library.animations.flippers.FlipOutXAnimator;
import com.zertinteractive.wallpaper.library.animations.flippers.FlipOutYAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_clockwise.RotateAntiClockWiseAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_clockwise.RotateClockWiseAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_entrances.RotateInAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_entrances.RotateInDownLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_entrances.RotateInDownRightAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_entrances.RotateInUpLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_entrances.RotateInUpRightAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_exits.RotateOutAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_exits.RotateOutDownLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_exits.RotateOutDownRightAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_exits.RotateOutUpLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.rotating_exits.RotateOutUpRightAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideInDownAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideInLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideInRightAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideInUpAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideInUpAnimatorPosition;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideOutDownAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideOutLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideOutRightAnimator;
import com.zertinteractive.wallpaper.library.animations.sliders.SlideOutUpAnimator;
import com.zertinteractive.wallpaper.library.animations.specials.HingeAnimator;
import com.zertinteractive.wallpaper.library.animations.specials.RollInAnimator;
import com.zertinteractive.wallpaper.library.animations.specials.RollOutAnimator;
import com.zertinteractive.wallpaper.library.animations.specials.in.DropOutAnimator;
import com.zertinteractive.wallpaper.library.animations.specials.in.LandingAnimator;
import com.zertinteractive.wallpaper.library.animations.specials.out.TakingOffAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_entrances.ZoomInAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_entrances.ZoomInAnimatorPosition;
import com.zertinteractive.wallpaper.library.animations.zooming_entrances.ZoomInDownAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_entrances.ZoomInLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_entrances.ZoomInRightAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_entrances.ZoomInUpAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_exits.ZoomOutAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_exits.ZoomOutAnimatorPosition;
import com.zertinteractive.wallpaper.library.animations.zooming_exits.ZoomOutDownAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_exits.ZoomOutLeftAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_exits.ZoomOutRightAnimator;
import com.zertinteractive.wallpaper.library.animations.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {

    AlphaAnim(AlphaAnimator.class),

    DropOut(DropOutAnimator.class),
    Landing(LandingAnimator.class),
    TakingOff(TakingOffAnimator.class),

    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    Hinge(HingeAnimator.class),
    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    RotateClockWise(RotateClockWiseAnimator.class),
    RotateAntiClockWise(RotateAntiClockWiseAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInUpPosition(SlideInUpAnimatorPosition.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInPosition(ZoomInAnimatorPosition.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutPosition(ZoomOutAnimatorPosition.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);


    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
