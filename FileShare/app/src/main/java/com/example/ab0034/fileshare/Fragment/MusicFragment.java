package com.example.ab0034.fileshare.Fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ab0034.fileshare.R;

import java.util.ArrayList;


public class MusicFragment extends Fragment {
    GridView MusicGrid;
    private ArrayList<String> video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        MusicGrid = (GridView) view.findViewById(R.id. MusicGrid);

        MusicGrid.setAdapter(new VideoAdapter((Activity) view.getContext()));
        MusicGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        MusicGrid.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Select Items");
                mode.setSubtitle("One item selected");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int selectCount = MusicGrid.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }
            }
        });
        MusicGrid.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (null != video && !video.isEmpty())

                    Toast.makeText(getContext(), "asd" +position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private class VideoAdapter extends BaseAdapter {
        private Activity context;
        public VideoAdapter (Activity localContext) {
            context = localContext;
            video = getAllShownImagesPath(context);
        }
        public int getCount() {
            return video.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView.setLayoutParams(new GridView.LayoutParams(200, 200));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(video.get(position))

                    .placeholder(R.drawable.icmusic).centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        private ArrayList<String> getAllShownImagesPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllVideo = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Audio.Media.DISPLAY_NAME};

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);
                listOfAllVideo.add(absolutePathOfImage);
            }
            return listOfAllVideo;
        }
    }
}

