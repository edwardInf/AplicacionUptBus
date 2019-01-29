package com.example.gcoaquira.aplicacionuptbus.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.gcoaquira.aplicacionuptbus.R;
import com.example.gcoaquira.aplicacionuptbus.interfaces.AlertDialogEvent;

public class AlertDialogBuilder {
    public enum DialogButton {
        OK_CANCEL, OK, CANCEL_RETRY
    }

    public enum DialogResult {
        CANCEL, OK, RETRY
    }

    public static void show(final Context context, final String message, final String title, DialogButton button, final AlertDialogEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);

        builder.setMessage(message);
        if (button == DialogButton.OK || button == DialogButton.OK_CANCEL) {
            String positiveText = context.getString(R.string.alert_ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (event != null) event.onAnswerDialog(DialogResult.OK);
                        }
                    });
        }
        if (button == DialogButton.OK_CANCEL || button == DialogButton.CANCEL_RETRY) {
            String negativeText = context.getString(R.string.alert_cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (event != null) event.onAnswerDialog(DialogResult.CANCEL);
                        }
                    });
        }
        if (button == DialogButton.CANCEL_RETRY) {
            String positiveText = context.getString(R.string.alert_retry);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (event != null) event.onAnswerDialog(DialogResult.RETRY);
                        }
                    });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void show(final Context context, final String message, DialogButton button, final AlertDialogEvent event) {
        show(context, message, context.getString(R.string.message_default_title), button, event);
    }

    public static void show(final Context context, final String message) {
        show(context, message, context.getString(R.string.message_default_title), DialogButton.OK, null);
    }
}
