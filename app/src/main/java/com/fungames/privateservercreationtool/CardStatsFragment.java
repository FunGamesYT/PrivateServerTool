package com.fungames.privateservercreationtool;

/**
 * Created by Fabian on 24.11.2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CardStatsFragment extends Fragment {
    private Set<FileInfo> cardStatsItems = new HashSet<>();

    public CardStatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cardstats, container, false);
        return rootView;
    }

    public void setCardStatsItems(Set<FileInfo> cardStatsItems) {
        this.cardStatsItems = cardStatsItems;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView csvList = (ListView) getView().findViewById(R.id.csvList);
        csvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                FileInfo item = (FileInfo) adapter.getItem(i);
                File csv = new File(item.getFilePath());
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(csv), getMimeType(csv.getAbsolutePath()));
                startActivityForResult(intent, 10);
                Toast.makeText(getContext(), item.getFilePath(), Toast.LENGTH_SHORT).show();
            }
        });
        if (cardStatsItems != null) {

            ArrayAdapter<FileInfo> adapter = new ArrayAdapter<FileInfo>(csvList.getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, cardStatsItems.toArray(new FileInfo[0]));
            csvList.setAdapter(adapter);
        }
        else {
            View relativeLayout =  getView().findViewById(R.id.rl);
            TextView valueTV = new TextView(getView().getContext());
            valueTV.setText("Please select an apk before you edit Card Stats");
            valueTV.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            valueTV.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            ((RelativeLayout) relativeLayout).addView(valueTV);
        }
    }
    private String getMimeType(String url)
    {
        String parts[]=url.split("\\.");
        String extension=parts[parts.length-1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

}
