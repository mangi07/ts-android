package com.door43.translationstudio.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.door43.translationstudio.R;
import com.door43.translationstudio.core.SourceLanguage;
import com.door43.translationstudio.library.temp.ServerLibraryCache;
import com.door43.translationstudio.projects.Project;
import com.door43.translationstudio.tasks.DownloadLanguageTask;
import com.door43.translationstudio.util.AppContext;
import com.door43.translationstudio.util.TabsAdapterNotification;
import com.door43.translationstudio.util.TranslatorBaseFragment;
import com.door43.util.tasks.TaskManager;
import com.door43.util.tasks.ThreadableUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joel on 3/16/2015.
 */
public class TranslationDraftsTab extends TranslatorBaseFragment implements TabsAdapterNotification {
    private LibraryLanguageAdapter mAdapter;
    public static final String DOWNLOAD_DRAFT_PREFIX = "download-draft-";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_library_languages, container, false);
        mAdapter = new LibraryLanguageAdapter(AppContext.context(), DOWNLOAD_DRAFT_PREFIX, true);

//        if (getArguments().containsKey(ServerLibraryDetailFragment.ARG_PROJECT_ID)) {
//            String id = getArguments().getString(ServerLibraryDetailFragment.ARG_PROJECT_ID);
//            setLanguages(id);
//        }

        ListView list = (ListView)view.findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: download on click
//                if(ServerLibraryCache.getEnableEditing()) {
//                    // TODO: place this in a task
//                    SourceLanguage lang = mAdapter.getItem(i);
//                    AppContext.projectManager().deleteSourceLanguage(mProject.getId(), lang.getId());
//                    mAdapter.notifyDataSetChanged();
//                } else {
//                    SourceLanguage lang = mAdapter.getItem(i);
//                    connectDownloadTask(lang);
//                }
            }
        });

//        populateList();

        return view;
    }

//    /**
//     * Begins or connects to an existing download
//     * @param language
//     */
//    private void connectDownloadTask(SourceLanguage language) {
//        String taskId = DOWNLOAD_DRAFT_PREFIX +mProject.getId()+"-"+language.getId();
//        DownloadLanguageTask task = (DownloadLanguageTask)TaskManager.getTask(taskId);
//        if(task == null) {
//            // start new download
//            task = new DownloadLanguageTask(mProject, language);
//            TaskManager.addTask(task, taskId);
//            // NOTE: the LibraryLanguageAdapter handles the onProgress and onFinish events
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public void NotifyAdapterDataSetChanged() {
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setLanguages(List<SourceLanguage> sourceLanguages) {
        mAdapter.changeDataSet(sourceLanguages);
    }

    public static interface Callbacks {
        public void onEmptyDraftsList();
    }
}
