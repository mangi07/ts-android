package com.door43.translationstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

import com.door43.translationstudio.core.Profile;
import com.door43.translationstudio.dialogs.CustomAlertDialog;
import com.door43.translationstudio.newui.BaseActivity;
import com.door43.translationstudio.newui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {

    private EditText mName;
    private View mPrivacyNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mPrivacyNotice = findViewById(R.id.privacy_notice);
        mName = (EditText)findViewById(R.id.name_edittext);

        mPrivacyNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrivacyNotice(null);
            }
        });
        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Profile profile = new Profile(mName.getText().toString());
                if (profile.isValid()) {
                    // confirm
                    showPrivacyNotice(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<Profile> profiles = new ArrayList<>();
                            profiles.add(profile);
                            AppContext.setProfiles(profiles);
                            openMainActivity();
                        }
                    });
                } else {
                    CustomAlertDialog.Create(ProfileActivity.this)
                            .setMessage(R.string.complete_required_fields)
                            .setPositiveButton(R.string.label_ok, null)
                            .show("missing-fields");
                }
            }
        });
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Profile> profiles = AppContext.getProfiles();
        if (profiles != null && profiles.size() > 0) {
            openMainActivity();
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Displays the privacy notice
     * @param listener if set the dialog will become a confirmation dialog
     */
    public void showPrivacyNotice(View.OnClickListener listener) {
        CustomAlertDialog privacy = CustomAlertDialog.Create(this)
                .setTitle(R.string.privacy_notice)
                .setIcon(R.drawable.ic_info_black_24dp)
                .setMessage(R.string.publishing_privacy_notice);

        if(listener != null) {
            privacy.setPositiveButton(R.string.label_continue, listener);
            privacy.setNegativeButton(R.string.title_cancel, null);
        } else {
            privacy.setPositiveButton(R.string.dismiss, null);
        }
        privacy.show("privacy-notice");
    }
}
