package com.pogamadores.concrete.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.pogamadores.concrete.R;
import com.pogamadores.concrete.application.ConcreteApplication;
import com.pogamadores.concrete.domain.Player;
import com.pogamadores.concrete.domain.Shot;
import com.pogamadores.concrete.request.GsonRequest;
import com.pogamadores.concrete.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShotFragment extends Fragment {

    private static final String SHOT_DETAIL = "SHOT_DETAIL";
    private Shot mShot;
    private ImageView shotImage;
    private TextView shotTitle;
    private ImageView userImage;
    private TextView userName;
    private TextView shotContent;
    private DisplayImageOptions imageOptions;
    private TextView viewCount;

    public static ShotFragment newInstance(Shot shot) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SHOT_DETAIL, shot);

        ShotFragment fragment = new ShotFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SHOT_DETAIL, mShot);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = (savedInstanceState != null ? savedInstanceState : getArguments());
        if(bundle != null) {
            mShot = bundle.getParcelable(SHOT_DETAIL);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_shot, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.actionShare) {
            if(mShot != null) {
                shareUrl(mShot.getShortUrl().toString(), mShot.getDescription());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shot, container, false);

        int imageSize = getResources().getDimensionPixelSize(R.dimen.user_image_size);

        imageOptions = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(imageSize))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        shotImage = ((ImageView) rootView.findViewById(R.id.shotImage));
        shotTitle = ((TextView) rootView.findViewById(R.id.shotTitle));
        userImage = (ImageView)rootView.findViewById(R.id.userImage);
        userName = ((TextView) rootView.findViewById(R.id.userName));
        shotContent = ((TextView) rootView.findViewById(R.id.shotContent));
        viewCount = (TextView)rootView.findViewById(R.id.shotViewCount);

        updateVisualInformations(mShot);
        updateModel(mShot);

        return rootView;
    }

    private void updateModel(Shot shot) {

        Uri.Builder builder = Uri.parse(Constants.DRIBBBLE_API_URL).buildUpon();
        builder.appendPath(Constants.SHOT_PATH);
        builder.appendPath(String.valueOf(shot.getId()));

        GsonRequest<Shot> request = new GsonRequest<>(
                Request.Method.GET,
                GsonRequest.getDefaultHeaders(),
                builder.toString(),
                null,
                new Response.Listener<Shot>() {
                    @Override
                    public void onResponse(Shot response) {
                        mShot = response;
                        updateVisualInformations(mShot);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                },
                Shot.class
        );

        ConcreteApplication.get().addRequestToQueue(request, ShortsFragment.class.getSimpleName());
    }

    private void updateVisualInformations(Shot shot) {
        if(shot != null) {
            shotTitle.setText(shot.getTitle());
            viewCount.setText(String.valueOf(shot.getViewsCount()));
            ImageLoader.getInstance().displayImage(
                    shot.getImageUrl().toString(),
                    shotImage
            );

            Player player = shot.getPlayer();
            if(player != null) {
                ImageLoader.getInstance().displayImage(
                        shot.getPlayer().getAvatarUrl().toString(),
                        userImage,
                        imageOptions
                );
                userName.setText(shot.getPlayer().getUsername());
            }

            shotContent.setText(Html.fromHtml(shot.getDescription()));
        }
    }

    private void shareUrl(String url, String message) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, url);
        share.putExtra(Intent.EXTRA_SUBJECT, message);
        startActivity(Intent.createChooser(share, getResources().getString(R.string.share_title)));
    }

}
