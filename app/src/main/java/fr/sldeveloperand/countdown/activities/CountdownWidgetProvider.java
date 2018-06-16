package fr.sldeveloperand.countdown.activities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import fr.sldeveloperand.countdown.R;
import fr.sldeveloperand.countdown.models.MyEvent;

import static fr.sldeveloperand.countdown.helpers.DateHelper.shortStrFromDeadline;
import static fr.sldeveloperand.countdown.helpers.SharedPrefsHelper.getDeadlineFromPrefs;
import static fr.sldeveloperand.countdown.helpers.SharedPrefsHelper.getEventNameFromPrefs;
import static fr.sldeveloperand.countdown.helpers.SharedPrefsHelper.init;

public class CountdownWidgetProvider extends AppWidgetProvider {


    private MyEvent myEvent;
    private String deadlineStr;
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
           init(context);
        myEvent = new MyEvent();
        myEvent.setName(getEventNameFromPrefs(context));
        myEvent.setDeadline(getDeadlineFromPrefs(context));
        deadlineStr = shortStrFromDeadline(context, myEvent.getDeadline());
        updateEvent(context,appWidgetManager,appWidgetIds);


    }

    private void updateEvent(Context context , AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, AppController.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countdown_appwidget);
            views.setTextViewText(R.id.appwidget_name, myEvent.getName());

            views.setTextViewText(R.id.appwidget_date, deadlineStr);

            views.setOnClickPendingIntent(R.id.countdownWidgetLayout, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


}