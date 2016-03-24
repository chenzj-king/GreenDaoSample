package cn.chenzhongjin.greendao.sample.utils;

import android.content.Context;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import cn.chenzhongjin.greendao.sample.R;

/**
 * Created by chenzj on 2015/11/24.
 */
public class MdDialogFactory {

    public static MaterialDialog showBasic(Context context, String title, String content, String positiveText, String negativeText) {
        return new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(positiveText)
                .negativeText(negativeText).build();
    }

    public static MaterialDialog showBasicLongContent(Context context, String title, String content, String positiveText, String
            negativeText) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .negativeText(negativeText).build();
    }

    public static MaterialDialog showBasicIcon(Context context, int iconId, String title, String content, String positiveText,
                                               String negativeText) {
        return new MaterialDialog.Builder(context)
                .iconRes(iconId)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .negativeText(negativeText).build();
    }

    public static MaterialDialog showStacked(Context context, String title, String content, String positiveText, String negativeText) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .btnStackedGravity(GravityEnum.END)
                .forceStacking(true).build();  // context generally should not be forced, but is used for demo purposes
    }

    public static MaterialDialog showNeutral(Context context, String title, String content, String positiveText, String
            negativeText, String neutralText) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .neutralText(neutralText)
                .build();
    }

    public static MaterialDialog showCallbacks(Context context, String title, String content, String positiveText, String negativeText,
                                               MaterialDialog.SingleButtonCallback singleButtonCallback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .onPositive(singleButtonCallback)
                .build();
    }

    public static MaterialDialog showList(Context context, String title, int itesId, MaterialDialog.ListCallback listCallback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(itesId)
                .itemsCallback(listCallback)
                .build();
    }

    public static MaterialDialog showListNoTitle(Context context, int itemsId, MaterialDialog.ListCallback listCallback) {
        return new MaterialDialog.Builder(context)
                .items(itemsId)
                .itemsCallback(listCallback)
                .build();
    }

    public static MaterialDialog showLongList(Context context, String title, int itemsId, MaterialDialog.ListCallback listCallback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(itemsId)
                .itemsCallback(listCallback)
                .positiveText(android.R.string.cancel)
                .show();
    }

    public static MaterialDialog showListLongItems(Context context, String title, int itemsId, MaterialDialog.ListCallback
            listCallback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(itemsId)
                .itemsCallback(listCallback).build();
    }

    public static MaterialDialog showSingleChoice(Context context, String title, int itemsId, MaterialDialog
            .ListCallbackSingleChoice listCallbackSingleChoice) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(itemsId)
                .itemsCallbackSingleChoice(2, listCallbackSingleChoice)
                .positiveText(R.string.md_choose_label)
                .build();
    }

    public static MaterialDialog showSingleChoiceLongItems(Context context, String title, int itemsId, MaterialDialog
            .ListCallbackSingleChoice listCallbackSingleChoice) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(itemsId)
                .itemsCallbackSingleChoice(2, listCallbackSingleChoice)
                .positiveText(R.string.md_choose_label)
                .build();
    }
}
