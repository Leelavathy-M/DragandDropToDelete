package com.example.draganddroptodelete;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    ImageButton img;
    ItemAdapter adapterl;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rv = findViewById(R.id.rv);
        img = findViewById(R.id.img);

        list = new ArrayList<>(); 
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");
        list.add("Item 6");

        adapterl = new ItemAdapter(list, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapterl);

        img.setOnDragListener((v, event) -> {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setAlpha(0.7f);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setAlpha(1.0f);
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    int position = Integer.parseInt(item.getText().toString());
                    adapterl.removeItem(position);
                    Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    v.setAlpha(1.0f);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setAlpha(1.0f);
                    return true;
                default:
                    return false;
            }
        });
    }
}