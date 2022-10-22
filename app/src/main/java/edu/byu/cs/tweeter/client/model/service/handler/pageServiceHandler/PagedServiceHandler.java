package edu.byu.cs.tweeter.client.model.service.handler.pageServiceHandler;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.handler.TaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.PagedServiceObserver;

public abstract class PagedServiceHandler<ItemType> extends TaskHandler {
    protected final PagedServiceObserver<ItemType> pagedServiceObserver;

    protected PagedServiceHandler(PagedServiceObserver<ItemType> pagedServiceObserver){
        super(pagedServiceObserver);
        this.pagedServiceObserver = pagedServiceObserver;
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<ItemType> items = (List<ItemType>) msg.getData().getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(PagedTask.MORE_PAGES_KEY);

        pagedServiceObserver.handleSuccess(items, hasMorePages);
    }

}
