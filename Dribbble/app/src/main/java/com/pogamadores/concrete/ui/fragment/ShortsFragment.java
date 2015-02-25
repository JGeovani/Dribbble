package com.pogamadores.concrete.ui.fragment;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pogamadores.concrete.R;
import com.pogamadores.concrete.application.ConcreteApplication;
import com.pogamadores.concrete.domain.Shot;
import com.pogamadores.concrete.request.GsonRequest;
import com.pogamadores.concrete.response.ShotsResponse;
import com.pogamadores.concrete.ui.activity.MainActivity;
import com.pogamadores.concrete.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortsFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        Response.Listener<ShotsResponse>,
        Response.ErrorListener,
        AbsListView.OnScrollListener {

    private static final String SHORT_NODE = "SHORT_NODE";
    private static final String TAG = ShortsFragment.class.getSimpleName();
    private static final String RELOAD_TABLE = "RELOAD_TABLE";
    private String mNode;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressBar mProgress;
    private ListView mListView;
    private TextView mErrorMessage;
    private int mCurrentPage;
    private int mMaxPages;
    private ListContentAdapter<Shot> mListContentAdapter;
    private boolean mReloadTable = true;

    public static ShortsFragment newInstance(String node) {
        ShortsFragment  fragment = new ShortsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(SHORT_NODE, node);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SHORT_NODE, mNode);
        outState.putBoolean(RELOAD_TABLE, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = (savedInstanceState != null ? savedInstanceState : getArguments());
        if(bundle != null) {
            mNode = bundle.getString(SHORT_NODE);
            mReloadTable = bundle.getBoolean(RELOAD_TABLE, true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shorts, container, false);

        mSwipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSwipeLayout.setRefreshing(true);

        mProgress = ((ProgressBar) rootView.findViewById(R.id.progress));
        mErrorMessage = ((TextView) rootView.findViewById(R.id.msgError));

        mListContentAdapter = new ListContentAdapter<>();

        mListView = ((ListView) rootView.findViewById(R.id.shortList));
        mListView.setOnScrollListener(this);
        mListView.setAdapter(mListContentAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getActivity() != null && getActivity() instanceof MainActivity) {
                    MainActivity activity = ((MainActivity) getActivity());
                    activity.showShotDetail(((Shot) mListContentAdapter.getItem(position)));
                }
            }
        });

        if(mReloadTable)
            loadShorts(1);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        loadShorts(1);
    }

    @Override
    public void onResponse(ShotsResponse response) {
        mCurrentPage = response.getPage();
        mMaxPages = response.getPages();
        if(mCurrentPage == 1) {
            mListContentAdapter.clearAndAddAll(response.getShots());
        } else {
            mListContentAdapter.addAll(response.getShots());
        }
        finishRefresh(true);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        finishRefresh(false);
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if(view.getLastVisiblePosition() >= (view.getCount() - 1) && mCurrentPage < mMaxPages ) {
                nextPage();
            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int topRowVerticalPosition =
                (mListView == null || mListView.getChildCount() == 0) ?
                        0 : mListView.getChildAt(0).getTop();
        mSwipeLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
    }

    private void nextPage() {
        loadShorts(mCurrentPage + 1);
    }

    private void loadShorts(int page) {

        if(!mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(true);
        }

        Uri.Builder builder = Uri.parse(Constants.DRIBBBLE_API_URL).buildUpon();
        builder.appendPath(Constants.SHOT_PATH);
        builder.appendPath(mNode);
        builder.appendQueryParameter("page",String.valueOf(page));

        GsonRequest<ShotsResponse> request = new GsonRequest<>(
                Request.Method.GET,
                GsonRequest.getDefaultHeaders(),
                builder.toString(),
                null,
                this,
                this,
                ShotsResponse.class
        );

        if(ConcreteApplication.get() != null) {
            ConcreteApplication.get().cancelRequest(TAG + "/" + mNode);
            ConcreteApplication.get().addRequestToQueue(request, TAG + "/" + mNode);
        }
    }

    private void finishRefresh(boolean success) {
        mSwipeLayout.setRefreshing(false);
        if(success) {
            if(mCurrentPage == 1) mListView.scrollTo(0,0);
            if(mProgress.getVisibility() == View.VISIBLE) {
                mProgress.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
            }
        } else {
            mErrorMessage.setText(getResources().getString(R.string.msg_load_error));
        }
        mErrorMessage.setVisibility(success?View.GONE:View.VISIBLE);
    }

    public class ListContentAdapter<T> extends BaseAdapter {

        private final DisplayImageOptions mImageOptions;
        private List<T> items = new ArrayList<>();

        public ListContentAdapter() {
            mImageOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.shot_row, parent, false);
            }

            //noinspection unchecked
            ViewHolder holder = (ViewHolder) convertView.getTag();

            if(holder == null) {
                holder = new ViewHolder(convertView);
            }

            final Shot shot = ((Shot) getItem(position));
            holder.getShotTitle().setText(shot.getTitle());
            holder.getShotViewCounter().setText(String.valueOf(shot.getViewsCount()));

            final ImageView imageView = holder.getShotImage();

            if(holder.imageLoadTask != null) {
                holder.imageLoadTask.cancel(true);
            }

            imageView.setImageResource(R.drawable.placeholder);
            holder.imageLoadTask = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    return ImageLoader.getInstance().loadImageSync(
                            shot.getImageUrl().toString(),
                            mImageOptions
                    );
                }
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if(bitmap != null)
                        imageView.setImageBitmap(bitmap);
                }
            }.execute();

            convertView.setTag(holder);

            return convertView;
        }

        public void addAll(Collection<? extends T> elements) {
            this.items.addAll(elements);
            notifyDataSetChanged();
        }

        public void clearAndAddAll(Collection<? extends T> shots) {
            this.items.clear();
            this.addAll(shots);
        }

        public class ViewHolder {

            private TextView shotTitle;
            private ImageView shotImage;
            private TextView shotViewCounter;

            public AsyncTask<Void,Void,Bitmap> imageLoadTask;

            public ViewHolder(View convertView) {
                shotTitle = ((TextView) convertView.findViewById(R.id.shotTitle));
                shotImage = ((ImageView) convertView.findViewById(R.id.shotImage));
                shotViewCounter = ((TextView) convertView.findViewById(R.id.shotViewCount));
            }

            public TextView getShotTitle() {
                return shotTitle;
            }

            public ImageView getShotImage() {
                return shotImage;
            }

            public TextView getShotViewCounter() {
                return shotViewCounter;
            }
        }
    }
}
