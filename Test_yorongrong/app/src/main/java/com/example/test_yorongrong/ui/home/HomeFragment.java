package com.example.test_yorongrong.ui.home;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.test_yorongrong.R;
import com.example.test_yorongrong.ShowCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.EncodedKeySpec;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    ImageButton camera_btn;
    private MediaRecorder mediaRecorder;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        camera_btn = root.findViewById(R.id.btn_camera);

        frameLayout = root.findViewById(R.id.frameLayout);
        camera = Camera.open();

        showCamera = new ShowCamera(this.getActivity(), camera);
        frameLayout.addView(showCamera);

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null,null,PictureCallback);
            }
        });


        return root;
    }

    Camera.PictureCallback PictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picture_file = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if(picture_file==null){
                return;
            }

                try{
                FileOutputStream fos=new FileOutputStream(picture_file);
                fos.write(data);
                fos.close();

                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }


        }
    };

    private File getOutputMediaFile(int mediaTypeImage) {
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED)){
            return null;
        }
        else{
            File folder_gui=new File(Environment.getExternalStorageDirectory()+File.separator+"GUI");

            if(!folder_gui.exists()){
                folder_gui.mkdir();
            }
            File outputFile = new File(folder_gui,"temp.jpg");
            return outputFile;
        }
    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//        releaseMediaRecorder();
//        releaseCamera();
//    }
}