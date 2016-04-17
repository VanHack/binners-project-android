package ca.com.androidbinnersproject.activities.pickup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.util.Logger;

public class PickupBottlesFragment extends Fragment {

	public static final int PictureRequestCode = 1;

	private static final String PictureFolder = "BinnersApp";

	public PickupBottlesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_pickup_bottles, container, false);

		FrameLayout takePictureButton = (FrameLayout) view.findViewById(R.id.pickup_bottles_takePicture);

		takePictureButton.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 takePicture();
			 }
		});

		return view;
	}

	private void takePicture() {

		File mediaFile = getMediaFile();

		if(mediaFile == null)
			return;

		Uri fileUri = Uri.fromFile(mediaFile);

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(cameraIntent, PictureRequestCode);
	}

	private File getMediaFile() {

		File imagesDir;
		String externalStorageState;

		if(!(externalStorageState = Environment.getExternalStorageState()).equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			//TODO handle external storage not available
			Logger.Info("External storage not available, current state = " + externalStorageState);
			return null;
		}

		imagesDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PictureFolder);

		if(!imagesDir.exists()) {
			if(!imagesDir.mkdirs()) {
				Logger.Error("Failed to create application pictures folder");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());

		return new File(imagesDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
	}
}
