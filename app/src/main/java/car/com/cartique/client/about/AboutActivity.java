package car.com.cartique.client.about;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import car.com.cartique.client.R;

public class AboutActivity extends MaterialAboutActivity {

    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull final Context context) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder().addItem(new MaterialAboutTitleItem.Builder()
                .text("Cartique")
                .desc("© 2018 Source Code PH")
                .icon(R.mipmap.ic_launcher)
                .build());
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Version")
                .subText("1.0.0")
                .icon(R.mipmap.ic_info)
                .build());
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Open Source Licences")
                .icon(R.mipmap.ic_license)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        new LibsBuilder()
                                //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                //start the activity
                                .start(context);
                    }
                })
                .build());

        MaterialAboutCard.Builder appAuthorCardBuilder = new MaterialAboutCard.Builder().addItem(new MaterialAboutActionItem.Builder()
                .text("Author")
                .subText("©Source Code PH")
                .icon(R.mipmap.ic_person)
                .build());

        appAuthorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Website")
                .subText("www.sourcecodeph.co.za")
                .icon(R.mipmap.ic_website)
                .build());

        MaterialAboutCard.Builder appPrivacyCardBuilder = new MaterialAboutCard.Builder().addItem(new MaterialAboutActionItem.Builder()
                .text("Privacy")
                .icon(R.mipmap.ic_privacy)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent(getApplicationContext(),PrivacyPolicyActivity.class);
                        startActivity(intent);
                    }
                }).build());

        appPrivacyCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Terms")
                .icon(R.mipmap.ic_terms)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        Intent termsIntent = new Intent(getApplicationContext(),TermsActivity.class);
                        startActivity(termsIntent);
                    }
                })
                .build());

        MaterialAboutCard.Builder appSupportCardBuilder = new MaterialAboutCard.Builder().addItem(new MaterialAboutActionItem.Builder()
                .text("Contact support")
                .icon(R.mipmap.ic_support)
                .build());

        appSupportCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("FAQ's")
                .icon(R.mipmap.ic_question)
                .build());
        appSupportCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Review")
                .icon(R.mipmap.ic_review)
                .build());
        appSupportCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Share")
                .icon(R.mipmap.ic_share)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBodyText = "Url to pla store";
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
                        intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                        startActivity(Intent.createChooser(intent, "Choose sharing method"));
                    }
                })
                .build());

        return new MaterialAboutList.Builder()
                .addCard(appCardBuilder.build())
                .addCard(appAuthorCardBuilder.title("Author").build())
                .addCard(appPrivacyCardBuilder.title("Privacy").build())
                .addCard(appSupportCardBuilder.title("Support").build())
                .build();
    }

    @Nullable
    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.mal_title_about);
    }
}
