package com.door43.translationstudio.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.door43.translationstudio.R;
import com.door43.translationstudio.library.temp.LibraryTempData;
import com.door43.translationstudio.projects.Project;
import com.door43.translationstudio.projects.SourceLanguage;
import com.door43.translationstudio.tasks.DownloadLanguageTask;
import com.door43.translationstudio.util.AppContext;
import com.door43.translationstudio.util.TabsFragmentAdapterNotification;
import com.door43.translationstudio.util.TranslatorBaseFragment;
import com.door43.util.threads.ManagedTask;
import com.door43.util.threads.TaskManager;
import com.door43.util.threads.ThreadableUI;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by joel on 3/16/2015.
 */
public class TranslationDraftsTabFragment extends TranslatorBaseFragment implements TabsFragmentAdapterNotification {
    private LibraryLanguageAdapter mAdapter;
    private Project mProject;
    public static final String DOWNLOAD_DRAFT_PREFIX = "download-draft-";
    private boolean mShowNewProjects;
    private boolean mShowProjectUpdates;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_library_languages, container, false);

        mShowNewProjects = getActivity().getIntent().getBooleanExtra(ProjectLibraryListActivity.ARG_ONLY_SHOW_NEW, false);
        mShowProjectUpdates = getActivity().getIntent().getBooleanExtra(ProjectLibraryListActivity.ARG_ONLY_SHOW_UPDATES, false);

        if (getArguments().containsKey(ProjectLibraryDetailFragment.ARG_ITEM_INDEX)) {
            int index;
            try {
                index = Integer.parseInt(getArguments().getString(ProjectLibraryDetailFragment.ARG_ITEM_INDEX));
            } catch (Exception e) {
                index = getArguments().getInt(ProjectLibraryDetailFragment.ARG_ITEM_INDEX);
            }
            if(mShowNewProjects && !mShowProjectUpdates) {
                mProject = LibraryTempData.getNewProject(index);
            } else if(mShowProjectUpdates && !mShowNewProjects) {
                mProject = LibraryTempData.getUpdatedProject(index);
            } else {
                mProject = LibraryTempData.getProject(index);
            }
        }

        mAdapter = new LibraryLanguageAdapter(AppContext.context(), mProject.getId());

        ListView list = (ListView)view.findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SourceLanguage lang = mAdapter.getItem(i);
                connectDownloadTask(lang);
            }
        });

        populateList();

        return view;
    }

    /**
     * Begins or connects to an existing download
     * @param language
     */
    private void connectDownloadTask(SourceLanguage language) {
        String taskId = DOWNLOAD_DRAFT_PREFIX +mProject.getId()+"-"+language.getId();
        DownloadLanguageTask task = (DownloadLanguageTask)TaskManager.getTask(taskId);
        if(task == null) {
            // start new download
            task = new DownloadLanguageTask(mProject, language);
            TaskManager.addTask(task, taskId);
            // NOTE: the LibraryLanguageAdapter handles the onProgress and onFinish events
            mAdapter.notifyDataSetChanged();
        }
    }

    private void populateList() {
        // filter languages
        // TODO: it would be safer to put this in the task manager
        new ThreadableUI(getActivity()) {
            List<SourceLanguage> languages = new ArrayList<>();
            @Override
            public void onStop() {

            }

            @Override
            public void run() {
                if(mProject != null) {
                    for(SourceLanguage l: mProject.getSourceLanguages()) {
                        if(l.checkingLevel() < AppContext.context().getResources().getInteger(R.integer.min_source_lang_checking_level)) {
//                            if(mShowNewProjects && !mShowProjectUpdates) {
//                                if(!AppContext.projectManager().isSourceLanguageDownloaded(mProject.getId(), l.getId())) {
//                                    languages.add(l);
//                                }
//                            } else if(mShowProjectUpdates && !mShowNewProjects) {
//                                if(AppContext.projectManager().isSourceLanguageDownloaded(mProject.getId(), l.getId())) {
//                                    languages.add(l);
//                                }
//                            } else {
                                languages.add(l);
//                            }
                        }
                    }
                }
            }

            @Override
            public void onPostExecute() {
                if(mAdapter != null) {
                    mAdapter.changeDataSet(languages);
                }
            }
        }.start();
    }

    @Override
    public void NotifyAdapterDataSetChanged() {
        populateList();
    }
}
