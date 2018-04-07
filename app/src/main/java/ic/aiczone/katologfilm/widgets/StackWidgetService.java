package ic.aiczone.katologfilm.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by aic on 04/04/18.
 * Email goeroeku@gmail.com.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
